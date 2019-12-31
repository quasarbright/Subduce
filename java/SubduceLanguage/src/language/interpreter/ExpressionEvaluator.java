package language.interpreter;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import language.interpreter.expression.Expression;
import language.interpreter.expression.ExpressionVisitor;
import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.SubduceError;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.FunctionSignature;
import language.interpreter.expression.value.functionValue.FunctionValue;
import language.interpreter.expression.value.functionValue.FunctionValueVisitor;
import language.interpreter.expression.value.functionValue.JavaFunctionValue;
import language.interpreter.expression.value.functionValue.SubduceFunctionValue;
import language.interpreter.statement.BaseStatementVisitor;
import language.interpreter.statement.ReturnStatement;
import language.interpreter.statement.Statement;

/**
 * Evaluates expressions.
 * Cannot evaluate definitions.
 * No left-left-lambda. Just accumulate environment in sequences instead. Assumes sequences are just
 * definitions and then a return
 */
public class ExpressionEvaluator implements ExpressionVisitor<Value> {
  // the environment containing variable values to be used in evaluation
  private final Environment<String, Value> environment;
  private final Supplier<Value> defaultBehavior;

  /**
   * Constructs an evaluator with an empty immutable environment
   */
  public ExpressionEvaluator() {
    // by default, use empty immutable environment
    this(new ImmutableVariableEnvironment());
  }

  /**
   * Constructs an evaluator which uses the given environment.
   *
   * @param environment the environment containing variable values to be used in evaluation.
   */
  public ExpressionEvaluator(Environment<String, Value> environment) {
    this.environment = environment;
    // should only happen at top level so it should be fine
    defaultBehavior = () -> null;
  }

  /**
   * Evaluate the expression with the given environment.
   *
   * @param expression expression to be evaluated
   * @param environment the environment containing variable values to be used in the evaluation.
   * @return the value the expression evaluates to
   */
  public Value evaluate(Expression expression, Environment<String, Value> environment) {
    return expression.accept(new ExpressionEvaluator(environment));
  }

  /**
   * Evaluate the expression with the current environment.
   *
   * @param expression expression to be evaluated
   * @return the value the expression evaluates to
   */
  public Value evaluate(Expression expression) {
    return expression.accept(this);
  }

  public List<Value> evaluateAll(List<Expression> expressions) {
    return expressions.stream()
            .map(this::evaluate)
            .collect(Collectors.toList());
  }

  /**
   * Apply the function to the given expressions. For subduce functions, evaluate before passing.
   * For java functions, give them the expressions so they could possibly lazily evaluate.
   *
   * @param functionValue the function to apply the arguments to
   * @param arguments the arguments to pass to the function
   * @return the output of the function
   */
  private Value applyFunction(FunctionValue functionValue, List<Expression> arguments) {
    return functionValue.accept(new FunctionValueVisitor<Value>() {
      @Override
      public Value visitSubduceFunction(List<String> argnames, Statement body, Environment<String, Value> environment, FunctionSignature signature) {
        List<Value> evaluatedArguments = evaluateAll(arguments);
        signature.validateArguments(evaluatedArguments);

        // add arguments with values to environment and evaluate body

        // add new scope for function body
        environment = environment.withNewScope();

        // add evaluated arguments to scope
        for(int i = 0; i < evaluatedArguments.size(); i++) {
          environment = environment.withNewVariable(argnames.get(i), evaluatedArguments.get(i));
        }

        // run statements in body
        environment = body.accept(new StatementRunner(environment, ExpressionEvaluator.this));

        // evaluate function body
        return evaluateFunctionBody(body, environment);
      }

      @Override
      public Value visitJavaFunction(JavaFunctionValue.JavaFunctionImplementation function) {
        return function.apply(ExpressionEvaluator.this::evaluate, arguments);
      }
    });
  }

  private Value evaluateFunctionBody(Statement body, Environment<String, Value> environment) {
    // if the body is just a return statement, evaluate it
    // otherwise, it better be a sequence where the last statement is a return statement.
    // in that case, evaluate it
    return body.accept(new BaseStatementVisitor<>(new IllegalStateException("Function body did not end in a return statement")) {
      @Override
      public Value visitReturn(Expression expression) {
        return visitReturnHelp(expression);
      }

      private Value visitReturnHelp(Expression expression) {
        // I define this so I can use it in the inner visitor
        // (jank)
        return evaluate(expression, environment);
      }

      @Override
      public Value visitSequence(List<Statement> statements) {
        if(statements.isEmpty()) {
          // should be prevented by the parser
          throw new IllegalArgumentException("function body must be non-empty");
        }
        Statement lastState = statements.get(statements.size()-1);
        return lastState.accept(new BaseStatementVisitor<>(() -> {
          throw new IllegalArgumentException("function body must end in a return statement");
        }) {
          @Override
          public Value visitReturn(Expression expression) {
            return visitReturnHelp(expression);
          }
        });
      }
    });
  }

  @Override
  public Value visitFunctionCall(Expression function, List<Expression> arguments) {
    Value evaluatedFunction = evaluate(function);
    FunctionValue functionValue = evaluatedFunction.accept(new BaseValueVisitor<>(() -> {
      throw new SubduceError("cannot call non-functions: "+function);
    }) {
      @Override
      public FunctionValue visitFunction(FunctionValue function) {
        return function;
      }
    });

    return applyFunction(functionValue, arguments);
  }

  @Override
  public Value visitLambda(List<String> argnames, Expression body) {
    return new SubduceFunctionValue.SubduceFunctionBuilder(argnames, new ReturnStatement(body), environment).get();
  }

  @Override
  public Value visitValue(Value value) {
    return value;
  }

  @Override
  public Value visitVariableReference(String name) {
    Optional<Value> value = environment.getValue(name);
    if(value.isPresent()) {
      return value.get();
    } else {
      throw new SubduceError("variable "+name+" is not defined");
    }
  }
}

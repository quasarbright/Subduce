package language.interpreter;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import language.interpreter.expression.Expression;
import language.interpreter.expression.ExpressionVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.ValueVisitor;
import language.interpreter.expression.value.functionValue.FunctionValue;
import language.interpreter.expression.value.functionValue.FunctionValueVisitor;
import language.interpreter.expression.value.functionValue.JavaFunctionValue;
import language.interpreter.expression.value.functionValue.SubduceFunctionValue;

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
      public Value visitSubduceFunction(List<String> argnames, Expression body, Environment<String, Value> environment) {
        // make sure argument names and arguments are the same length
        if(argnames.size() != arguments.size()) {
          // TODO fix
          throw new IllegalArgumentException("function got unexpected number of arguments");
        }
        // add arguments with values to environment and evaluate body

        // add new scope for function body
        List<Value> evaluatedArguents = evaluateAll(arguments);
        environment = environment.withNewScope();
        for(int i = 0; i < argnames.size(); i++) {
          String argname = argnames.get(i);
          Value argumentValue = evaluatedArguents.get(i);
          environment = environment.withNewVariable(argname, argumentValue);
        }
        return evaluate(body, environment);
      }

      @Override
      public Value visitJavaFunction(JavaFunctionValue.JavaFunctionImplementation function) {
        return function.apply(ExpressionEvaluator.this::evaluate, arguments);
      }
    });
  }

  @Override
  public Value visitFunctionCall(Expression function, List<Expression> arguments) {
    Value evaluatedFunction = evaluate(function);
    FunctionValue functionValue = evaluatedFunction.accept(new ValueVisitor<FunctionValue>() {
      // TODO fix
      private final RuntimeException error = new IllegalStateException("cannot call non-functions: "+function);

      @Override
      public FunctionValue visitBoolean(boolean b) {
        throw error;
      }

      @Override
      public FunctionValue visitNumber(double d) {
        throw error;
      }

      @Override
      public FunctionValue visitString(String s) {
        throw error;
      }

      @Override
      public FunctionValue visitFunction(FunctionValue function) {
        return function;
      }
    });

    return applyFunction(functionValue, arguments);
  }

  @Override
  public Value visitFunctionDefinition(String name, List<String> argnames, Expression body) {
    SubduceFunctionValue functionValue = new SubduceFunctionValue(argnames, body, environment);
    Environment<String, Value> newEnvironment = environment.withNewVariable(name, functionValue);
    functionValue.setEnvironment(newEnvironment);
    return functionValue;
  }

  @Override
  public Value visitLambdaDefinition(List<String> argnames, Expression body) {
    return new SubduceFunctionValue(argnames, body, environment);
  }

  @Override
  public Value visitSequence(List<Expression> expressions) {
    if(expressions.isEmpty()) {
      return defaultBehavior.get();
    }
    // accumulate definitions into environment and then evaluate the last one according to the accumulated environment
    Environment<String, Value> accumulatedEnvironment = environment;
    // loop through all but last (last gets evaluated with new env and returned)
    for(int i = 0; i < expressions.size()-1; i++) {
      Expression currentExpression = expressions.get(i);
      Environment<String, Value> finalAccumulatedEnvironment = accumulatedEnvironment;
      accumulatedEnvironment = currentExpression.accept(new DefinitionEvaluator(accumulatedEnvironment, this));
    }
    Expression returnExpression = expressions.get(expressions.size()-1);
    return evaluate(returnExpression, accumulatedEnvironment);
  }

  @Override
  public Value visitVariableAssignment(String name, Expression expression) {
    evaluate(expression);
    return defaultBehavior.get();
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
      // TODO fix
      throw new IllegalStateException("variable "+name+" is not defined");
    }
  }
}

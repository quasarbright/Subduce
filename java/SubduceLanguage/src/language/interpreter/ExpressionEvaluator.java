package language.interpreter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import language.interpreter.expression.DefinitionEvaluator;
import language.interpreter.expression.Expression;
import language.interpreter.expression.ExpressionVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.ValueVisitor;
import language.interpreter.expression.value.functionValue.FunctionValue;
import language.interpreter.expression.value.functionValue.FunctionValueVisitor;

/**
 * Evaluates expressions.
 * Cannot evaluate definitions.
 * No left-left-lambda. Just accumulate environment in sequences instead. Assumes sequences are just
 * definitions and then a return
 */
public class ExpressionEvaluator implements ExpressionVisitor<Value> {
  // the environment containing variable values to be used in evaluation
  private final Environment<String, Value> environment;

  /**
   * Constructs an evaluator with an empty immutable environment
   */
  public ExpressionEvaluator() {
    // by default, use empty immutable environment
    this(new ImmutableVariableEnvironment());
  }

  /**
   * Constructs an evaluator with an empty environment of the given type.
   *
   * @param environmentSupplier creates a base environment to use
   */
  public ExpressionEvaluator(Supplier<Environment<String, Value>> environmentSupplier) {
    this(environmentSupplier.get());
  }

  /**
   * Constructs an evaluator which uses the given environment.
   *
   * @param environment the environment containing variable values to be used in evaluation.
   */
  public ExpressionEvaluator(Environment<String, Value> environment) {
    this.environment = environment;
  }

  /**
   * Evaluate the expression with the given environment.
   *
   * @param expression expression to be evaluated
   * @param environment the environment containing variable values to be used in the evaluation.
   * @return the value the expression evaluates to
   */
  private Value evaluate(Expression expression, Environment<String, Value> environment) {
    return expression.accept(new ExpressionEvaluator(environment));
  }

  /**
   * Evaluate the expression with the current environment.
   *
   * @param expression expression to be evaluated
   * @return the value the expression evaluates to
   */
  private Value evaluate(Expression expression) {
    return expression.accept(this);
  }

  private Value applyFunction(FunctionValue functionValue, List<Value> arguments) {
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
        environment = environment.withNewScope();
        for(int i = 0; i < argnames.size(); i++) {
          String argname = argnames.get(i);
          Value argument = arguments.get(i);
          environment = environment.withNewVariable(argname, argument);
        }
        return evaluate(body, environment);
      }

      @Override
      public Value visitJavaFunction(Function<List<Value>, Value> function) {
        return function.apply(arguments);
      }
    });
  }

  @Override
  public Value visitFunctionCall(Expression function, List<Expression> arguments) {
    Value evaluatedFunction = evaluate(function);
    FunctionValue functionValue = evaluatedFunction.accept(new ValueVisitor<FunctionValue>() {
      // TODO fix
      private final RuntimeException error = new IllegalStateException("expected a function");

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

    List<Value> evaluatedArguments = arguments.stream()
            .map(this::evaluate)
            .collect(Collectors.toList());
    return applyFunction(functionValue, evaluatedArguments);
  }

  @Override
  public Value visitFunctionDefinition(String name, List<String> argnames, Expression body) {
    throw new IllegalStateException();
  }

  @Override
  public Value visitLambdaDefinition(List<String> argnames, Expression body) {
    return null;
  }

  @Override
  public Value visitSequence(List<Expression> expressions) {
    if(expressions.isEmpty()) {
      throw new IllegalStateException();
    }
    // accumulate definitions into environment and then evaluate the last one according to the accumulated environment
    Environment<String, Value> accumulatedEnvironment = environment;
    // loop through all but last (last gets evaluated with new env and returned)
    for(int i = 0; i < expressions.size()-1; i++) {
      Expression currentExpression = expressions.get(i);
      Environment<String, Value> finalAccumulatedEnvironment = accumulatedEnvironment;
      accumulatedEnvironment = currentExpression.accept(new DefinitionEvaluator(accumulatedEnvironment, (Expression e) -> evaluate(e, finalAccumulatedEnvironment)));
    }
    Expression returnExpression = expressions.get(expressions.size()-1);
    return evaluate(returnExpression, accumulatedEnvironment);
  }

  @Override
  public Value visitVariableAssignment(String name, Expression expression) {
    throw new IllegalStateException();
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

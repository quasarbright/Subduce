package language.interpreter;

import java.util.List;
import java.util.function.Function;

import language.interpreter.Environment;
import language.interpreter.expression.Expression;
import language.interpreter.expression.ExpressionVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.SubduceFunctionValue;

/**
 * Adds definition to environment if there is any.
 */
public class DefinitionEvaluator implements ExpressionVisitor<Environment<String, Value>> {
  private final Environment<String, Value> environment;
  private final Function<Expression, Value> evaluator;

  public DefinitionEvaluator(Environment<String, Value> environment, Function<Expression, Value> evaluator) {
    this.environment = environment;
    this.evaluator = evaluator;
  }

  @Override
  public Environment visitFunctionCall(Expression function, List<Expression> arguments) {
    return environment;
  }

  @Override
  public Environment visitFunctionDefinition(String name, List<String> argnames, Expression body) {
    SubduceFunctionValue function = new SubduceFunctionValue(argnames, body, environment);
    Environment<String, Value> newEnvironment = environment.withNewVariable(name, function);
    function.setEnvironment(newEnvironment);
    return newEnvironment;
  }

  @Override
  public Environment visitLambdaDefinition(List<String> argnames, Expression body) {
    return environment;
  }

  @Override
  public Environment visitSequence(List<Expression> expressions) {
    return environment;
  }

  @Override
  public Environment visitVariableAssignment(String name, Expression expression) {
    Value variableValue = evaluator.apply(expression);
    return environment.withNewVariable(name, variableValue);
  }

  @Override
  public Environment visitValue(Value value) {
    return environment;
  }

  @Override
  public Environment visitVariableReference(String name) {
    return environment;
  }
}

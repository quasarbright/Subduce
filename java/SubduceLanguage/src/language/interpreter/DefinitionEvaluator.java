package language.interpreter;

import java.util.List;

import language.interpreter.expression.Expression;
import language.interpreter.expression.ExpressionVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.SubduceFunctionValue;

/**
 * Adds definition to environment if there is any.
 */
public class DefinitionEvaluator implements ExpressionVisitor<Environment<String, Value>> {
  private final Environment<String, Value> environment;
  private final ExpressionEvaluator evaluator;

  public DefinitionEvaluator(Environment<String, Value> environment, ExpressionEvaluator evaluator) {
    this.environment = environment;
    this.evaluator = evaluator;
  }

  @Override
  public Environment<String, Value> visitFunctionCall(Expression function, List<Expression> arguments) {
    return environment;
  }

  @Override
  public Environment<String, Value> visitFunctionDefinition(String name, List<String> argnames, Expression body) {
    SubduceFunctionValue function = new SubduceFunctionValue(argnames, body, environment, name);
    Environment<String, Value> newEnvironment = environment.withNewVariable(name, function);
    function.setEnvironment(newEnvironment);
    return newEnvironment;
  }

  @Override
  public Environment<String, Value> visitLambdaDefinition(List<String> argnames, Expression body) {
    return environment;
  }

  @Override
  public Environment<String, Value> visitSequence(List<Expression> expressions) {
    Environment<String, Value> accumulatedEnvironment = environment;
    for(Expression expression: expressions) {
      accumulatedEnvironment = expression.accept(new DefinitionEvaluator(accumulatedEnvironment, evaluator));
    }
    return accumulatedEnvironment;
  }

  @Override
  public Environment<String, Value> visitVariableAssignment(String name, Expression expression) {
    Value variableValue = evaluator.evaluate(expression, environment);
    return environment.withNewVariable(name, variableValue);
  }

  @Override
  public Environment<String, Value> visitValue(Value value) {
    return environment;
  }

  @Override
  public Environment<String, Value> visitVariableReference(String name) {
    return environment;
  }
}

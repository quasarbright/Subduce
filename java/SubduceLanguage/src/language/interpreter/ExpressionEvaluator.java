package language.interpreter;

import java.util.List;

import language.interpreter.expression.Expression;
import language.interpreter.expression.ExpressionVisitor;
import language.interpreter.expression.value.Value;

public class ExpressionEvaluator implements ExpressionVisitor<Value> {
  @Override
  public Value visitFunctionCall(Expression function, List<Expression> arguments) {
    return null;
  }

  @Override
  public Value visitFunctionDefinition(String name, List<String> argnames, Expression body) {
    return null;
  }

  @Override
  public Value visitLambdaDefinition(List<String> argnames, Expression body) {
    return null;
  }

  @Override
  public Value visitSequence(List<Expression> expressions) {
    return null;
  }

  @Override
  public Value visitVariableAssignment(String name, Expression expression) {
    return null;
  }

  @Override
  public Value visitValue(Value value) {
    return value;
  }

  @Override
  public Value visitVariableReference(String name) {
    return null;
  }
}

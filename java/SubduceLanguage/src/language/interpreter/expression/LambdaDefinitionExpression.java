package language.interpreter.expression;

import java.util.ArrayList;
import java.util.List;

import language.interpreter.expression.Expression;

public class LambdaDefinitionExpression implements Expression {
  private final List<String> argnames;
  private final Expression body;

  public LambdaDefinitionExpression(List<String> argnames, Expression body) {
    this.argnames = argnames;
    this.body = body;
  }

  @Override
  public <R> R accept(ExpressionVisitor<R> visitor) {
    return visitor.visitLambdaDefinition(new ArrayList<>(argnames), body);
  }
}

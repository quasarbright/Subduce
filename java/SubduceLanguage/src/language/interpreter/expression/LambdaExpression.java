package language.interpreter.expression;

import java.util.ArrayList;
import java.util.List;

public class LambdaExpression implements Expression {
  private final List<String> argnames;
  private final Expression body;

  public LambdaExpression(List<String> argnames, Expression body) {
    this.argnames = argnames;
    this.body = body;
  }

  @Override
  public <R> R accept(ExpressionVisitor<R> visitor) {
    return visitor.visitLambda(new ArrayList<>(argnames), body);
  }
}

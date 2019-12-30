package language.interpreter.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

  @Override
  public int hashCode() {
    return Objects.hash(argnames, body);
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj) {
      return true;
    }
    if(obj == null || getClass() != obj.getClass()) {
      return false;
    }
    LambdaExpression other = (LambdaExpression) obj;
    return argnames.equals(other.argnames) && body.equals(other.body);
  }
}

package language.interpreter.expression.value;

import language.interpreter.expression.Expression;
import language.interpreter.expression.ExpressionVisitor;

public interface Value extends Expression {
  <R> R accept(ValueVisitor<R> visitor);

  @Override
  default <R> R accept(ExpressionVisitor<R> visitor) {
    return visitor.visitValue(this);
  }
}

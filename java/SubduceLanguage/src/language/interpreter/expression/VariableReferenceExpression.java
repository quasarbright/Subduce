package language.interpreter.expression;

import java.util.Objects;

public class VariableReferenceExpression implements Expression {
  private final String name;

  public VariableReferenceExpression(String name) {
    this.name = name;
  }

  @Override
  public <R> R accept(ExpressionVisitor<R> visitor) {
    return visitor.visitVariableReference(name);
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    VariableReferenceExpression that = (VariableReferenceExpression) o;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}

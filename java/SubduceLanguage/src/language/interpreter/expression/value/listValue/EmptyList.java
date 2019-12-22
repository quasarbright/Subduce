package language.interpreter.expression.value.listValue;

public class EmptyList implements ListValue {
  @Override
  public <R> R accept(ListValueVisitor<R> visitor) {
    return visitor.visitEmpty();
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj != null && getClass().equals(obj.getClass());
  }

  @Override
  public String toString() {
    return "empty";
  }
}

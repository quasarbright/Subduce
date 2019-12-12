package language.interpreter.expression.value;

public class StringValue implements Value {
  private final String val;

  public StringValue(String val) {
    this.val = val;
  }

  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitString(val);
  }
}

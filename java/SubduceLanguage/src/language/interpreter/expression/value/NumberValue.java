package language.interpreter.expression.value;

public class NumberValue implements Value {
  public final double val;

  public NumberValue(double val) {
    this.val = val;
  }

  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitNumber(val);
  }

  @Override
  public String toString() {
    return Double.toString(val);
  }
}

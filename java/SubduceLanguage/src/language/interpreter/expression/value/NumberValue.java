package language.interpreter.expression.value;

import java.util.Objects;

import language.interpreter.builtins.NumberEqualFunction;
import language.typing.BuiltInType;
import language.typing.ValueType;

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
  public ValueType getType() {
    return BuiltInType.NUMBER;
  }

  @Override
  public String toString() {
    return Double.toString(val);
  }

  @Override
  public boolean equals(Object obj) {
    if(obj == null || getClass() != obj.getClass()) {
      return false;
    }
    return NumberEqualFunction.eq(val, ((NumberValue) obj).val);
  }

  @Override
  public int hashCode() {
    return Objects.hash(val);
  }
}

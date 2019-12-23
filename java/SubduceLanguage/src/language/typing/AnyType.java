package language.typing;

import language.interpreter.expression.value.Value;

/**
 * Universal union type. All values are an Any.
 */
public class AnyType implements ValueType {
  @Override
  public String getName() {
    return "any";
  }

  @Override
  public boolean checkValueType(Value value) {
    return true;
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj != null && getClass().equals(obj.getClass());
  }
}

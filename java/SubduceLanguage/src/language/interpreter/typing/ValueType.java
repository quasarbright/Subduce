package language.interpreter.typing;

import language.interpreter.expression.value.Value;

/**
 * Represents the type of a {@link Value}.
 */
public interface ValueType {
  /**
   * Get the name of this type.
   *
   * @return the name of this type
   */
  String getName();

  /**
   * Is that value of this type?
   *
   * @param value the value to check
   * @return whether it is of this type
   */
  default boolean checkValueType(Value value) {
    return this.equals(value.getType());
  }
}

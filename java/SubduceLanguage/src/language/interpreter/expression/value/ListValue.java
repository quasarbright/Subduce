package language.interpreter.expression.value;

/**
 * Represents a cons-list.
 * A List is one of:
 * empty
 * (cons Any List)
 */
public interface ListValue extends Value {
  /**
   * Retrieve the first element of this list.
   *
   * @return the first element of this list
   * @throws IllegalStateException if list is empty
   */
  Value first();

  /**
   * Retrieve the rest of this list (all elements after the first one).
   *
   * @return the rest of this list (all elements after the first one)
   */
  ListValue rest();

  /**
   * Is this list empty?
   * @return whether this list is empty
   */
  boolean isEmpty();
}

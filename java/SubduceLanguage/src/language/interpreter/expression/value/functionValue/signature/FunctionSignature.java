package language.interpreter.expression.value.functionValue.signature;

import java.util.List;

import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.FunctionValue;

/**
 * Signature of a {@link FunctionValue}
 */
public interface FunctionSignature {
  /**
   * Check if the arguments are valid according to their types.
   * @param arguments arguments to be passed to the function
   * @throws IllegalArgumentException if arguments aren't valid.
   */
  void validateArguments(List<Value> arguments);

  /**
   * Are the arguments valid to be passed to the function?
   *
   * @param arguments arguments to be passed to the function
   * @return whether the arguments are valid according to their types
   */
  boolean areValidArgumentTypes(List<Value> arguments);
}

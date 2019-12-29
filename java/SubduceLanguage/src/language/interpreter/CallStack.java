package language.interpreter;

import java.util.List;

import language.interpreter.expression.FunctionCallExpression;

/**
 * Represents a stack of function calls made inside of each other.
 * This call stack is accumulated within a single evaluation of an expression.
 *
 * @param <C> function call expression type
 */
public interface CallStack<C> {
  /**
   * Create a call stack with the given call added to the top.
   *
   * @param call the new call
   * @return a call stack with the given call added to the top
   */
  CallStack<C> withNewCall(C call);

  /**
   * generate a list of calls representing the call stack
   *
   * @return the function calls in order (most recent last)
   */
  List<C> getCalls();
}

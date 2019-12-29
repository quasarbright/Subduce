package language.interpreter;

/**
 * Represents the context of a running subduce program.
 *
 * @param <C> function call expression type
 * @param <R> variable reference type
 * @param <V> value type
 */
public interface Context<C, R, V> {
  Environment<R, V> getEnvironment();
  CallStack<C> getCallStack();
  Context<C, R, V> withCallStack(CallStack<C> callStack);
  Context<C, R, V> withEnvironment(Environment<R, V> environment);
}

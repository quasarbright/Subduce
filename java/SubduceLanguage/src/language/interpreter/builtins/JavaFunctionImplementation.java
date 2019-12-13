package language.interpreter.builtins;

import java.util.List;
import java.util.function.Function;

import language.interpreter.expression.value.Value;

/**
 * A {@link language.interpreter.expression.value.functionValue.JavaFunctionValue} function
 * implementation
 */
public abstract class JavaFunctionImplementation implements Function<List<Value>, Value> {
  @Override
  public boolean equals(Object other) {
    return other != null && getClass() == other.getClass();
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}

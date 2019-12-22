package language.interpreter.expression.value;

import java.util.function.Supplier;

import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.ValueVisitor;
import language.interpreter.expression.value.functionValue.FunctionValue;
import language.interpreter.expression.value.listValue.ListValue;

/**
 * Value caster with providable default behavior. Default behavior can either return a default value
 * or throw an error depending on what you want.
 *
 * @param <T> the type to cast values to
 */
public abstract class BaseValueVisitor<T> implements ValueVisitor<T> {
  private final Supplier<T> defaultBehavior;

  public BaseValueVisitor(Supplier<T> defaultBehavior) {
    this.defaultBehavior = defaultBehavior;
  }

  public BaseValueVisitor(T defaultValue) {
    this(() -> defaultValue);
  }

  public BaseValueVisitor(RuntimeException castingError) {
    this(() -> {throw castingError;});
  }

  @Override
  public T visitBoolean(boolean b) {
    return defaultBehavior.get();
  }

  @Override
  public T visitNumber(double d) {
    return defaultBehavior.get();
  }

  @Override
  public T visitString(String s) {
    return defaultBehavior.get();
  }

  @Override
  public T visitFunction(FunctionValue function) {
    return defaultBehavior.get();
  }

  @Override
  public T visitList(ListValue list) {
    return defaultBehavior.get();
  }
}

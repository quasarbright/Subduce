package language.interpreter.builtins.casters;

import java.util.Optional;

import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.ValueVisitor;
import language.interpreter.expression.value.functionValue.FunctionValue;

public class Caster<ValueSubtype extends Value> implements ValueVisitor<Optional<ValueSubtype>> {

  @Override
  public Optional<ValueSubtype> visitBoolean(boolean b) {
    return Optional.empty();
  }

  @Override
  public Optional<ValueSubtype> visitNumber(double d) {
    return Optional.empty();
  }

  @Override
  public Optional<ValueSubtype> visitString(String s) {
    return Optional.empty();
  }

  @Override
  public Optional<ValueSubtype> visitFunction(FunctionValue function) {
    return Optional.empty();
  }

  public Optional<ValueSubtype> cast(Value value) {
    return value.accept(this);
  }
}

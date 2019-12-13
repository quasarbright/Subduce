package language.interpreter.builtins.casters;

import java.util.Optional;

import language.interpreter.expression.value.BooleanValue;

public class CastToBoolean extends Caster<BooleanValue> {
  @Override
  public Optional<BooleanValue> visitBoolean(boolean b) {
    return Optional.of(new BooleanValue(b));
  }
}

package language.interpreter.builtins.casters;

import java.util.Optional;

import language.interpreter.expression.value.StringValue;

public class CastToString extends Caster<StringValue> {
  @Override
  public Optional<StringValue> visitString(String s) {
    return Optional.of(new StringValue(s));
  }
}

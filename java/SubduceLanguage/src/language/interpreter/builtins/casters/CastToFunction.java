package language.interpreter.builtins.casters;

import java.util.Optional;

import language.interpreter.expression.value.functionValue.FunctionValue;

public class CastToFunction extends Caster<FunctionValue> {
  @Override
  public Optional<FunctionValue> visitFunction(FunctionValue functionValue) {
    return Optional.of(functionValue);
  }
}

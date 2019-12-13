package language.interpreter.builtins.casters;

import java.util.Optional;

import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.ValueVisitor;
import language.interpreter.expression.value.functionValue.FunctionValue;

public class CastToNumber extends Caster<NumberValue> {
  @Override
  public Optional<NumberValue> visitNumber(double d) {
    return Optional.of(new NumberValue(d));
  }
}

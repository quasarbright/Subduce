package language.interpreter.builtins;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import language.interpreter.builtins.casters.CastToNumber;
import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.Value;

public class AddFunction extends JavaFunctionImplementation {
  private static final CastToNumber caster = new CastToNumber();
  @Override
  public Value apply(List<Value> values) {
    double sum = 0;
    for(Value value: values) {
      Optional<NumberValue> maybeNumberValue = caster.cast(value);
      if(maybeNumberValue.isEmpty()) {
        throw new IllegalArgumentException("+ expected number arguments, got "+value);
      } else {
        NumberValue numberValue = maybeNumberValue.get();
        sum += numberValue.val;
      }
    }
    return new NumberValue(sum);
  }
}

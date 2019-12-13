package language.interpreter.builtins;

import java.util.List;
import java.util.Optional;

import language.interpreter.builtins.casters.CastToNumber;
import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.Value;

public class NumberEqualFunction extends JavaFunctionImplementation {
  private static final CastToNumber caster = new CastToNumber();
  private static final double tolerance = .000001;

  private boolean eq(double a, double b) {
    return Math.abs(a-b) < tolerance;
  }

  private double cast(Value value) {
    Optional<NumberValue> maybeNumberValue = caster.cast(value);
    if(maybeNumberValue.isEmpty()) {
      throw new IllegalArgumentException("+ expected number arguments, got "+value);
    } else {
      NumberValue numberValue = maybeNumberValue.get();
      return numberValue.val;
    }
  }

  @Override
  public Value apply(List<Value> values) {
    if(values.size() < 2) {
      // TODO fix
      throw new IllegalArgumentException("= expected at least 2 arguments, got " + values.size());
    }

    double first = cast(values.get(0));
    for(Value value: values) {
      double current = cast(value);
      if(!eq(first, current)) {
        return new BooleanValue(false);
      }
    }
    return new BooleanValue(true);
  }
}

package language.interpreter.builtins;

import java.util.List;
import java.util.Optional;

import language.interpreter.builtins.casters.CastToBoolean;
import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.Value;

public class IfFunction extends JavaFunctionImplementation {
  private static final CastToBoolean caster = new CastToBoolean();
  @Override
  public Value apply(List<Value> values) {
    if(values.size() != 3) {
      // TODO fix
      throw new IllegalArgumentException("if expects 3 arguments, found "+values.size());
    }
    boolean condition;
    Value conditionValue = values.get(0);
    Optional<BooleanValue> maybeConditionBooleanValue = caster.cast(conditionValue);
    if(maybeConditionBooleanValue.isEmpty()) {
      // TODO fix
      throw new IllegalArgumentException("if expects the first argument to be a boolean, got "+conditionValue);
    } else {
      BooleanValue conditionBooleanValue = maybeConditionBooleanValue.get();
      condition = conditionBooleanValue.val;
    }

    Value trueBranch = values.get(1);
    Value falseBranch = values.get(2);
    if(condition) {
      return trueBranch;
    } else {
      return falseBranch;
    }
  }
}

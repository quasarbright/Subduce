package language.interpreter.builtins;

import java.util.List;
import java.util.function.Supplier;

import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.Value;

public class AddFunction extends ANumberFunction {

  @Override
  public Value apply(List<Value> arguments) {
    double sum = castArguments(arguments)
            .stream()
            .mapToDouble(d -> d)
            .sum();
    return new NumberValue(sum);
  }

  @Override
  protected double defaultCastBehavior(Value value) {
    throw new IllegalArgumentException("+ expects number arguments, got "+value);
  }
}

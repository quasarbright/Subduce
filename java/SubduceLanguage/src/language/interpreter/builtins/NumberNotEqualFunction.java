package language.interpreter.builtins;

import java.util.List;

import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.TypeSequenceSignature;
import language.interpreter.typing.BuiltInType;

public class NumberNotEqualFunction extends ANumberFunction {
  public static final double TOLERANCE = .000001;

  public NumberNotEqualFunction(String name) {
    super(name, new TypeSequenceSignature(name, BuiltInType.NUMBER, BuiltInType.NUMBER));
  }

  @Override
  protected Value apply(List<Value> arguments) {
    validateArguments(arguments);
    List<Double> numbers = castArguments(arguments);
    double left = numbers.get(0);
    double right = numbers.get(1);
    return new BooleanValue(!NumberEqualFunction.eq(left, right));
  }
}

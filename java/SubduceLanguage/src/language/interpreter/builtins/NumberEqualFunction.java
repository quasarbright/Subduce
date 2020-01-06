package language.interpreter.builtins;

import java.util.List;
import java.util.function.Supplier;

import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.TypeSequenceSignature;
import language.interpreter.typing.BuiltInType;

/**
 * Uses percent-difference to determine equality
 */
public class NumberEqualFunction extends ANumberFunction {
  public static final double TOLERANCE = 0.00000000001;

  public NumberEqualFunction(String name) {
    super(name, new TypeSequenceSignature(name, BuiltInType.NUMBER, BuiltInType.NUMBER));
  }


  public static boolean eq(double a, double b) {
    // guaranteed not to divide by zero
    // if both numbers are zero, we return true
    // if one number is not zero and the other is zero, the non-zero number will be the denominator
    // if both are non-zero, a non-zero number will be the denominator
    if(a == b) {
      return true;
    }
    return Math.abs(a-b) / (1.0 * Math.max(Math.abs(a), Math.abs(b))) < TOLERANCE;
  }


  @Override
  protected Value apply(List<Value> arguments) {
    validateArguments(arguments);
    List<Double> numbers = castArguments(arguments);
    double left = numbers.get(0);
    double right = numbers.get(1);
    return new BooleanValue(eq(left, right));
  }
}

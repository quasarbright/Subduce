package language.interpreter.builtins;

import java.util.List;
import java.util.function.Supplier;

import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.Value;

public class NumberEqualFunction extends ANumberComparisonFunction {
  public static final double TOLERANCE = .000001;

  public NumberEqualFunction(String name) {
    super(name);
  }


  public static boolean eq(double a, double b) {
    return Math.abs(a-b) < TOLERANCE;
  }


  @Override
  protected boolean compare(double first, double second) {
    return eq(first, second);
  }
}

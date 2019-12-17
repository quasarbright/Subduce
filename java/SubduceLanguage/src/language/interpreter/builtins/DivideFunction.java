package language.interpreter.builtins;

import java.util.function.BinaryOperator;

public class DivideFunction extends ANumberBinaryOperatorFunction {
  public DivideFunction(String name) {
    super(DivideFunction::divide, 0, name, false);
  }

  private static double divide(double a, double b) {
    if(NumberEqualFunction.eq(b, 0)) {
      throw new IllegalArgumentException("Divide by zero");
    } else {
      return a / b;
    }
  }
}

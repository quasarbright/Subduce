package language.interpreter.builtins;

public class LessThanFunction extends ANumberComparisonFunction {

  public LessThanFunction(String name) {
    super(name);
  }

  @Override
  protected boolean compare(double first, double second) {
    return first < second && !NumberEqualFunction.eq(first, second);
  }
}

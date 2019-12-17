package language.interpreter.builtins;

public class GreaterThanFunction extends ANumberComparisonFunction {

  public GreaterThanFunction(String name) {
    super(name);
  }

  @Override
  protected boolean compare(double first, double second) {
    return first > second && !NumberEqualFunction.eq(first, second);
  }
}

package language.interpreter.builtins;

public class GreaterThanOrEqualToFunction extends ANumberComparisonFunction {

  public GreaterThanOrEqualToFunction(String name) {
    super(name);
  }

  @Override
  protected boolean compare(double first, double second) {
    return first > second || NumberEqualFunction.eq(first, second);
  }
}

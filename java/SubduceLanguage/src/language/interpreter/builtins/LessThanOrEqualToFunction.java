package language.interpreter.builtins;

public class LessThanOrEqualToFunction extends ANumberComparisonFunction {

  public LessThanOrEqualToFunction(String name) {
    super(name);
  }

  @Override
  protected boolean compare(double first, double second) {
    return first < second || NumberEqualFunction.eq(first, second);
  }
}

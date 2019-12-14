package language.interpreter.builtins;

public class GreaterThanOrEqualToFunction extends ANumberComparisonFunction {
  public GreaterThanOrEqualToFunction() {
    super(">=");
  }

  @Override
  protected boolean compare(double first, double second) {
    return first >= second;
  }
}

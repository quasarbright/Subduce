package language.interpreter.builtins;

public class GreaterThanFunction extends ANumberComparisonFunction {
  public GreaterThanFunction() {
    super(">");
  }

  @Override
  protected boolean compare(double first, double second) {
    return first > second;
  }
}

package language.interpreter.builtins;

public class GreaterThanFunction extends ANumberComparisonFunction {

  /**
   * Construct a number comparison function with a given name.
   *
   * @param name the function name
   */
  public GreaterThanFunction(String name) {
    super(name);
  }

  @Override
  protected boolean compare(double first, double second) {
    return first > second && !NumberEqualFunction.eq(first, second);
  }
}

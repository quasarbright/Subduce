package language.interpreter.builtins;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.Value;

/**
 * Abstract number comparison function. Operator must be transitive, so != won't work.
 * Property to be obeyed: (* a1 a2 a3 ...) is equivalent to (and (* a1 a2) (* a2 a3) (* a3 a4) ...)
 * works for <, <=, >, >=, ==, NOT !=
 */
public abstract class ANumberComparisonFunction extends ANumberFunction {
  /**
   * Construct a number comparison function with a given name.
   *
   * @param name the function name
   */
  public ANumberComparisonFunction(String name) {
    super(name);
  }

  @Override
  public Value apply(List<Value> values) {
    if(values.size() < 2) {
      throw new IllegalArgumentException(name+" expects at least two arguments, got "+values.size());
    }
    List<Double> numericalArguments = castArguments(values);
    double prev = numericalArguments.get(0);
    for(int i = 1; i < numericalArguments.size(); i++) {
      double next = numericalArguments.get(i);
      if(!(compare(prev, next))) {
        return new BooleanValue(false);
      }
      prev = next;
    }
    return new BooleanValue(true);
  }

  /**
   * Compare the first with the second.
   * Ex: for a less-than comparison function, return a < b
   * @param first number
   * @param second number
   * @return the result of the comparison
   */
  protected abstract boolean compare(double first, double second);
}

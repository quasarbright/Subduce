package language.interpreter.builtins;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import language.interpreter.expression.Expression;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.JavaFunctionValue;

/**
 * A {@link language.interpreter.expression.value.functionValue.JavaFunctionValue} function
 * implementation written in java
 */
public abstract class BaseJavaFunctionImplementation implements JavaFunctionValue.JavaFunctionImplementation {
  protected final String name;

  public BaseJavaFunctionImplementation(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object other) {
    return other != null && getClass() == other.getClass();
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  /**
   * Apply this function to the given arguments.
   * Override this method for lazy evaluation.
   *
   * @param evaluator evaluates expressions in the current environment
   * @param arguments the arguments to pass to the function
   * @return the output of this function
   */
  @Override
  public Value apply(Function<Expression, Value> evaluator, List<Expression> arguments) {
    return apply(arguments.stream()
            .map(evaluator)
            .collect(Collectors.toList()));
  }

  protected abstract Value apply(List<Value> arguments);

  @Override
  public String toString() {
    return "[function "+name+" at "+hashCode()+"]";
  }
}

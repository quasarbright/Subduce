package language.interpreter.expression.value.functionValue;

import language.interpreter.expression.value.Value;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class JavaFunctionValue implements FunctionValue {
  private final Function<List<Value>, Value> function;

  public JavaFunctionValue(Function<List<Value>, Value> function) {
    this.function = function;
  }

  @Override
  public <R> R accept(FunctionValueVisitor<R> visitor) {
    return visitor.visitJavaFunction(function);
  }

  @Override
  public String toString() {
    return "[function "+hashCode()+"]";
  }

  @Override
  public boolean equals(Object other) {
    if(other == null || getClass() != other.getClass()) {
      return false;
    }
    return function.equals(((JavaFunctionValue) other).function);
  }

  @Override
  public int hashCode() {
    return function.hashCode();
  }
}

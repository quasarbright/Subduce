package language.interpreter.expression.value.functionValue;

import language.interpreter.expression.Expression;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.FunctionSignature;

import java.util.List;
import java.util.function.Function;

/**
 * Takes in a list of expressions a
 */
public class JavaFunctionValue implements FunctionValue {
  private final JavaFunctionImplementation implementation;

  public JavaFunctionValue(JavaFunctionImplementation implementation) {
    this.implementation = implementation;
  }

  @Override
  public <R> R accept(FunctionValueVisitor<R> visitor) {
    return visitor.visitJavaFunction(implementation);
  }

  @Override
  public FunctionSignature getSignature() {
    return implementation.getSignature();
  }

  @Override
  public String toString() {
    return "[function "+implementation.getName()+" at "+hashCode()+"]";
  }

  @Override
  public boolean equals(Object other) {
    if(other == null || getClass() != other.getClass()) {
      return false;
    }
    return implementation.equals(((JavaFunctionValue) other).implementation);
  }

  public String getName() {
    return implementation.getName();
  }

  @Override
  public int hashCode() {
    return implementation.hashCode();
  }

  public interface JavaFunctionImplementation {
    FunctionSignature getSignature();
    Value apply(Function<Expression, Value> evaluator, List<Expression> expressions);
    String getName();
  }
}

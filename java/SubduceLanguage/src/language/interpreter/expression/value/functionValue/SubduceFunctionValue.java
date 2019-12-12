package language.interpreter.expression.value.functionValue;

import language.interpreter.Environment;
import language.interpreter.expression.Expression;
import language.interpreter.expression.value.Value;

import java.util.ArrayList;
import java.util.List;

public class SubduceFunctionValue implements FunctionValue {
  private final List<String> argnames;
  private final Expression body;
  private Environment<String, Value> environment;

  public SubduceFunctionValue(List<String> argnames, Expression body, Environment environment) {
    this.argnames = argnames;
    this.body = body;
    this.environment = environment;
  }

  @Override
  public <R> R accept(FunctionValueVisitor<R> visitor) {
    return visitor.visitSubduceFunction(argnames, body, environment);
  }

  /**
   * Change this functions environment. Used for recursion implementation.
   * @param environment the environment for this function to use
   */
  public void setEnvironment(Environment<String, Value> environment) {
    this.environment = environment;
  }

  @Override
  public String toString() {
    return "[function "+hashCode()+"]";
  }
}

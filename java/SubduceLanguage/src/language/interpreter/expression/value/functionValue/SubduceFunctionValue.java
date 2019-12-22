package language.interpreter.expression.value.functionValue;

import language.interpreter.Environment;
import language.interpreter.expression.Expression;
import language.interpreter.expression.value.Value;
import language.interpreter.statement.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubduceFunctionValue implements FunctionValue {
  private final List<String> argnames;
  private final Statement body;
  private Environment<String, Value> environment;
  private final Optional<String> maybeName;

  public SubduceFunctionValue(List<String> argnames, Statement body, Environment environment) {
    this(argnames, body, environment, Optional.empty());
  }

  public SubduceFunctionValue(List<String> argnames, Statement body, Environment environment, String name) {
    this(argnames, body, environment, Optional.of(name));
  }

  private SubduceFunctionValue(List<String> argnames, Statement body, Environment<String, Value> environment, Optional<String> maybeName) {
    this.argnames = argnames;
    this.body = body;
    this.environment = environment;
    this.maybeName = maybeName;
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
    return maybeName.map(name -> "[function " + name + " at " + hashCode() + "]")
            .orElseGet(() -> "[function at " + hashCode() + "]");
  }

  public String getName() {
    return maybeName.orElse("");
  }
}

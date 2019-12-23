package language.interpreter.expression.value.functionValue;

import language.interpreter.Environment;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.ArgumentLengthSignature;
import language.interpreter.expression.value.functionValue.signature.FunctionSignature;
import language.interpreter.statement.Statement;

import java.util.List;
import java.util.Optional;

public class SubduceFunctionValue implements FunctionValue {
  private final List<String> argnames;
  private final Statement body;
  private Environment<String, Value> environment;
  private final Optional<String> maybeName;
  private final FunctionSignature signature;

  private SubduceFunctionValue(List<String> argnames, Statement body, Environment<String, Value> environment, Optional<String> maybeName, FunctionSignature signature) {
    this.argnames = argnames;
    this.body = body;
    this.environment = environment;
    this.maybeName = maybeName;
    this.signature = signature;
  }

  @Override
  public <R> R accept(FunctionValueVisitor<R> visitor) {
    return visitor.visitSubduceFunction(argnames, body, environment, signature);
  }

  /**
   * Change this functions environment. Used for recursion implementation.
   * @param environment the environment for this function to use
   */
  public void setEnvironment(Environment<String, Value> environment) {
    this.environment = environment;
  }

  @Override
  public FunctionSignature getSignature() {
    return signature;
  }

  @Override
  public String toString() {
    return maybeName.map(name -> "[function " + name + " at " + hashCode() + "]")
            .orElseGet(() -> "[function at " + hashCode() + "]");
  }

  public String getName() {
    return maybeName.orElse("");
  }

  public static class SubduceFunctionBuilder {
    private final List<String> argnames;
    private final Statement body;
    private final Environment<String, Value> environment;
    private Optional<String> maybeName;
    private FunctionSignature signature;

    public SubduceFunctionBuilder(List<String> argnames, Statement body, Environment<String, Value> environment) {
      this.argnames = argnames;
      this.body = body;
      this.environment = environment;
      // defaults
      maybeName = Optional.empty();
      signature = new ArgumentLengthSignature(argnames.size());
    }

    public SubduceFunctionBuilder setName(String name) {
      maybeName = Optional.of(name);
      return this;
    }

    public SubduceFunctionBuilder setSignature(FunctionSignature signature) {
      this.signature = signature;
      return this;
    }

    public SubduceFunctionValue get() {
      return new SubduceFunctionValue(argnames, body, environment, maybeName, signature);
    }
  }
}

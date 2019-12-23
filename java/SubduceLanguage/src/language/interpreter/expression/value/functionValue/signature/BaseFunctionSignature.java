package language.interpreter.expression.value.functionValue.signature;

import java.util.List;
import java.util.Optional;

import language.interpreter.expression.value.Value;

public abstract class BaseFunctionSignature implements FunctionSignature {
  protected final Optional<String> maybeName;

  protected BaseFunctionSignature(Optional<String> maybeName) {
    this.maybeName = maybeName;
  }

  protected String getName() {
    return maybeName.orElse("function");
  }

  @Override
  public boolean areValidArgumentTypes(List<Value> arguments) {
    try {
      validateArguments(arguments);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}

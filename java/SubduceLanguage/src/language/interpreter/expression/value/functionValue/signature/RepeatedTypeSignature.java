package language.interpreter.expression.value.functionValue.signature;

import java.util.List;
import java.util.Optional;

import language.interpreter.expression.value.Value;
import language.typing.ValueType;

/**
 * For when a function can accept an arbitrary number of arguments of a given type.
 * Optional minimum number of arguments.
 * Ex: and can take in an arbitrary number of booleans.
 */
public class RepeatedTypeSignature extends BaseFunctionSignature {
  private final ValueType type;
  private final int minArguments;

  public RepeatedTypeSignature(String name, ValueType type) {
    this(name, type, 0);
  }

  public RepeatedTypeSignature(String name, ValueType type, int minArguments) {
    super(Optional.of(name));
    this.type = type;
    this.minArguments = minArguments;
  }

  @Override
  public void validateArguments(List<Value> arguments) {
    int size = arguments.size();
    if(size < minArguments) {
      throw new IllegalArgumentException(getName()+" expected at least "+minArguments+" arguments, got "+size);
    }
    for(int i = 0; i < arguments.size(); i++) {
      Value argument = arguments.get(i);
      if(!type.checkValueType(argument)) {
        throw new IllegalArgumentException(getName()+" expected an argument of type "+type+" at position "+i+", got "+argument);
      }
    }
  }
}

package language.interpreter.expression.value.functionValue.signature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import language.interpreter.SubduceError;
import language.interpreter.expression.value.Value;
import language.interpreter.typing.ValueType;

/**
 * For specifying each argument's expected type for a fixed number of arguments.
 */
public class TypeSequenceSignature extends ArgumentLengthSignature {
  private final List<ValueType> types;
  public TypeSequenceSignature(String name, List<ValueType> types) {
    super(name, types.size());
    this.types = new ArrayList<>(types);
  }

  public TypeSequenceSignature(String name, ValueType... types) {
    this(name, Arrays.asList(types));
  }

  @Override
  public void validateArguments(List<Value> arguments) {
    super.validateArguments(arguments); // guarantees right length
    for(int i = 0; i < arguments.size(); i++) {
      Value argument = arguments.get(i);
      ValueType expectedType = types.get(i);
      if(!expectedType.checkValueType(argument)) {
        throw new SubduceError(getName()+" expected an argument of type "+expectedType+" at position "+i+", got "+argument);
      }
    }
  }

  @Override
  public boolean areValidArgumentTypes(List<Value> arguments) {
    if(!super.areValidArgumentTypes(arguments)) {
      return false;
    }
    for(int i = 0; i < arguments.size(); i++) {
      Value argument = arguments.get(i);
      ValueType expectedType = types.get(i);
      if(!expectedType.checkValueType(argument)) {
        return false;
      }
    }
    return true;
  }
}

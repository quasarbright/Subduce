package language.interpreter.expression.value.functionValue.signature;

import java.util.List;
import java.util.Optional;

import language.interpreter.SubduceError;
import language.interpreter.expression.value.Value;

/**
 * For specifying only the number of arguments. Any types are acceptable.
 */
public class ArgumentLengthSignature extends BaseFunctionSignature {
  private final int numArgs;

  public ArgumentLengthSignature(int numArgs) {
    super(Optional.empty());
    this.numArgs = numArgs;
  }

  public ArgumentLengthSignature(String name, int numArgs) {
    super(Optional.of(name));
    this.numArgs = numArgs;
  }

  @Override
  public void validateArguments(List<Value> arguments) {
    int size = arguments.size();
    if(size != numArgs) {
      String argumentsWord = "arguments";
      if(numArgs == 1) {
        argumentsWord = "argument";
      }
      String errorMessage = getName()+" expected "+numArgs+" "+argumentsWord+", got "+size;
      throw new SubduceError(errorMessage);
    }
  }

  @Override
  public boolean areValidArgumentTypes(List<Value> arguments) {
    return arguments.size() == numArgs;
  }
}

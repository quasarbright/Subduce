package language.interpreter.builtins;

import java.util.List;

import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.ArgumentLengthSignature;
import language.interpreter.expression.value.functionValue.signature.RepeatedTypeSignature;
import language.interpreter.expression.value.functionValue.signature.TypeSequenceSignature;
import language.interpreter.typing.AnyType;

/**
 * Compares the equality of any two objects.
 * Comparing functions might not always make sense.
 */
public class UniversalEqualFunction extends BaseJavaFunctionImplementation {
  public UniversalEqualFunction(String name) {
    super(name, new ArgumentLengthSignature(name, 2));
  }

  @Override
  protected Value apply(List<Value> arguments) {
    validateArguments(arguments);
    Value left = arguments.get(0);
    Value right = arguments.get(1);
    return new BooleanValue(left.equals(right));
  }
}

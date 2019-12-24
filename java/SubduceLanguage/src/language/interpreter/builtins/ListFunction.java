package language.interpreter.builtins;

import java.util.List;

import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.RepeatedTypeSignature;
import language.interpreter.expression.value.listValue.ListValue;
import language.interpreter.typing.AnyType;

public class ListFunction extends BaseJavaFunctionImplementation {
  public ListFunction(String name) {
    super(name, new RepeatedTypeSignature(name, new AnyType()));
  }

  @Override
  protected Value apply(List<Value> arguments) {
    validateArguments(arguments);
    return ListValue.fromValues(arguments);
  }
}

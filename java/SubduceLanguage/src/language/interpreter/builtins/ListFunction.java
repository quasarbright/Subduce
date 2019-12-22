package language.interpreter.builtins;

import java.util.List;

import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.listValue.ListValue;

public class ListFunction extends BaseJavaFunctionImplementation {
  public ListFunction(String name) {
    super(name);
  }

  @Override
  protected Value apply(List<Value> arguments) {
    return ListValue.fromValues(arguments);
  }
}

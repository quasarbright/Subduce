package language.interpreter.builtins;

import java.util.List;

import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.TypeSequenceSignature;
import language.interpreter.expression.value.listValue.ConsList;
import language.interpreter.expression.value.listValue.ListValue;
import language.interpreter.typing.AnyType;
import language.interpreter.typing.BuiltInType;

public class ConsFunction extends BaseJavaFunctionImplementation {
  public ConsFunction(String name) {
    super(name, new TypeSequenceSignature(name, new AnyType(), BuiltInType.LIST));
  }

  @Override
  protected Value apply(List<Value> arguments) {
    validateArguments(arguments);
    Value first = arguments.get(0);
    ListValue rest = arguments.get(1).accept(new BaseValueVisitor<ListValue>(new IllegalArgumentException("arguments should've been validated")) {
      @Override
      public ListValue visitList(ListValue list) {
        return list;
      }
    });
    return new ConsList(first, rest);
  }
}

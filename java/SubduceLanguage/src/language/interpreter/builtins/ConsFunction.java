package language.interpreter.builtins;

import java.util.List;

import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.listValue.ConsList;
import language.interpreter.expression.value.listValue.ListValue;

public class ConsFunction extends BaseJavaFunctionImplementation {
  public ConsFunction(String name) {
    super(name);
  }

  @Override
  protected Value apply(List<Value> arguments) {
    if(arguments.size() != 2) {
      throw new IllegalArgumentException(name+" expects 2 arguments, got "+arguments.size());
    }
    Value first = arguments.get(0);
    ListValue rest = arguments.get(1).accept(new BaseValueVisitor<ListValue>(() -> {
      throw new IllegalArgumentException(name+" expects a list as its second argument, got "+arguments.get(1));
    }) {
      @Override
      public ListValue visitList(ListValue list) {
        return list;
      }
    });
    return new ConsList(first, rest);
  }
}

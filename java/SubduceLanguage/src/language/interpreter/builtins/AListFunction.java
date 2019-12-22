package language.interpreter.builtins;

import java.util.List;

import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.listValue.ListValue;
import language.interpreter.expression.value.listValue.ListValueVisitor;

/**
 * Abstract class for functions that take in a single list.
 */
public abstract class AListFunction extends BaseJavaFunctionImplementation {
  public AListFunction(String name) {
    super(name);
  }

  @Override
  protected Value apply(List<Value> arguments) {
    if(arguments.isEmpty()) {
      throw new IllegalArgumentException(name+" expects 1 argument, got "+arguments.size());
    }
    Value argument = arguments.get(0);
    return argument.accept(new BaseValueVisitor<>(() -> onNonList(argument)) {
      @Override
      public Value visitList(ListValue list) {
        return list.accept(new ListValueVisitor<>() {
          @Override
          public Value visitEmpty() {
            return onEmpty();
          }

          @Override
          public Value visitCons(Value first, ListValue rest) {
            return onCons(first, rest);
          }
        });
      }
    });
  }

  protected Value onNonList(Value argument) {
    throw new IllegalArgumentException(name+" expects a list, got "+argument);
  }

  protected abstract Value onEmpty();
  protected abstract Value onCons(Value first, ListValue rest);
}

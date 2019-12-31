package language.interpreter.builtins;

import java.util.List;

import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.SubduceError;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.TypeSequenceSignature;
import language.interpreter.expression.value.listValue.ListValue;
import language.interpreter.expression.value.listValue.ListValueVisitor;
import language.interpreter.typing.BuiltInType;

/**
 * Abstract class for functions that take in a single list.
 */
public abstract class AListFunction extends BaseJavaFunctionImplementation {
  public AListFunction(String name) {
    super(name, new TypeSequenceSignature(name, BuiltInType.LIST));
  }

  @Override
  protected Value apply(List<Value> arguments) {
    validateArguments(arguments);
    Value argument = arguments.get(0);
    return argument.accept(new BaseValueVisitor<Value>(new SubduceError("signature should've been validated")) {
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

  protected abstract Value onEmpty();
  protected abstract Value onCons(Value first, ListValue rest);
}

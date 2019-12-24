package language.interpreter.builtins;

import java.util.List;

import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.TypeSequenceSignature;
import language.interpreter.typing.BuiltInType;

public class NotFunction extends BaseJavaFunctionImplementation {
  public NotFunction(String name) {
    super(name, new TypeSequenceSignature(name, BuiltInType.BOOLEAN));
  }

  boolean getBool(Value value) {
    return value.accept(new BaseValueVisitor<>(new IllegalStateException("arguments should have been validated")) {
      @Override
      public Boolean visitBoolean(boolean b) {
        return b;
      }
    });
  }

  @Override
  protected Value apply(List<Value> arguments) {
    validateArguments(arguments);
    Value argument = arguments.get(0);
    return new BooleanValue(!getBool(argument));
  }
}

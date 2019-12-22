package language.interpreter.builtins;

import java.util.List;
import java.util.function.Function;

import language.interpreter.expression.Expression;
import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.Value;

public class NotFunction extends BaseJavaFunctionImplementation {
  public NotFunction(String name) {
    super(name);
  }

  boolean getBool(Value value) {
    return value.accept(new BaseValueVisitor<Boolean>(new IllegalArgumentException(name+"expects boolean arguments, got "+value)) {
      @Override
      public Boolean visitBoolean(boolean b) {
        return b;
      }
    });
  }

  @Override
  protected Value apply(List<Value> arguments) {
    if(arguments.size() != 1) {
      throw new IllegalArgumentException(name+" expects one argument, got "+arguments.size());
    }
    Value argument = arguments.get(0);
    return new BooleanValue(!getBool(argument));
  }
}

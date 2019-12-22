package language.interpreter.builtins;

import java.util.List;
import java.util.function.Function;

import language.interpreter.expression.Expression;
import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.Value;

public class OrFunction extends BaseJavaFunctionImplementation {
  public OrFunction(String name) {
    super(name);
  }

  @Override
  public Value apply(Function<Expression, Value> evaluator, List<Expression> arguments) {
    for(Expression argument: arguments) {
      boolean b = getBool(evaluator.apply(argument));
      if(b) {
        return new BooleanValue(true);
      }
    }
    return new BooleanValue(false);
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
    for(Value argument: arguments) {
      boolean b = getBool(argument);
      if(b) {
        return new BooleanValue(true);
      }
    }
    return new BooleanValue(false);
  }
}

package language.interpreter.builtins;

import java.util.List;
import java.util.function.Function;

import language.interpreter.expression.Expression;
import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.BooleanValue;
import language.interpreter.SubduceError;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.RepeatedTypeSignature;
import language.interpreter.typing.BuiltInType;

public class AndFunction extends BaseJavaFunctionImplementation {
  public AndFunction(String name) {
    super(name, new RepeatedTypeSignature(name, BuiltInType.BOOLEAN, 2));
  }

  @Override
  public Value apply(Function<Expression, Value> evaluator, List<Expression> arguments) {
    for(Expression argument: arguments) {
      boolean b = getBool(evaluator.apply(argument));
      if(!b) {
        return new BooleanValue(false);
      }
    }
    return new BooleanValue(true);
  }

  boolean getBool(Value value) {
    return value.accept(new BaseValueVisitor<Boolean>(new SubduceError(name+" expected boolean arguments, got "+value)) {
      @Override
      public Boolean visitBoolean(boolean b) {
        return b;
      }
    });
  }

  @Override
  protected Value apply(List<Value> arguments) {
    validateArguments(arguments);
    for(Value argument: arguments) {
      boolean b = getBool(argument);
      if(!b) {
        return new BooleanValue(false);
      }
    }
    return new BooleanValue(true);
  }
}

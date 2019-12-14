package language.interpreter.builtins;

import java.util.List;

import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.ValueVisitor;

public class IfFunction extends JavaFunctionImplementation {
  @Override
  public Value apply(List<Value> values) {
    if(values.size() != 3) {
      // TODO fix
      throw new IllegalArgumentException("if expects 3 arguments, found "+values.size());
    }

    Value conditionValue = values.get(0);
    boolean condition = castCondition(conditionValue);

    Value trueBranch = values.get(1);
    Value falseBranch = values.get(2);
    if(condition) {
      return trueBranch;
    } else {
      return falseBranch;
    }
  }

  private boolean castCondition(Value conditionValue) {
    RuntimeException error = new IllegalArgumentException(
            "if expects the first argument to be a boolean, got " + conditionValue);

    ValueVisitor<Boolean> caster = new BaseValueVisitor<Boolean>(error) {
      @Override
      public Boolean visitBoolean(boolean b) {
        return b;
      }
    };
    return conditionValue.accept(caster);
  }
}

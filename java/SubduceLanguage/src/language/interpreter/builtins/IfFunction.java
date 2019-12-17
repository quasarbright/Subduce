package language.interpreter.builtins;

import java.util.List;
import java.util.function.Function;

import language.interpreter.expression.Expression;
import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.ValueVisitor;

public class IfFunction extends BaseJavaFunctionImplementation {
  public IfFunction(String name) {
    super(name);
  }

  @Override
  public Value apply(List<Value> values) {
    if(values.size() != 3) {
      // TODO fix
      throw new IllegalArgumentException(name+" expects 3 arguments, found "+values.size());
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

  @Override
  public Value apply(Function<Expression, Value> evaluator, List<Expression> expressions) {
    if(expressions.size() != 3) {
      // TODO fix
      throw new IllegalArgumentException("if expects 3 arguments, found "+expressions.size());
    }
    Expression first = expressions.get(0);
    Value conditionValue = evaluator.apply(first);
    boolean condition = castCondition(conditionValue);
    Expression trueBranch = expressions.get(1);
    Expression falseBranch = expressions.get(2);
    if(condition) {
      return evaluator.apply(trueBranch);
    } else {
      return evaluator.apply(falseBranch);
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

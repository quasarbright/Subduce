package language.interpreter.builtins;

import java.util.List;
import java.util.function.Function;

import language.interpreter.expression.Expression;
import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.ValueVisitor;
import language.interpreter.expression.value.functionValue.signature.TypeSequenceSignature;
import language.typing.AnyType;
import language.typing.BuiltInType;

public class IfFunction extends BaseJavaFunctionImplementation {
  public IfFunction(String name) {
    super(name, new TypeSequenceSignature(name, BuiltInType.BOOLEAN, new AnyType(), new AnyType()));
  }

  @Override
  public Value apply(List<Value> values) {
    validateArguments(values);
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
      throw new IllegalArgumentException("if expects 3 arguments, got "+expressions.size());
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
            name+" expects the argument at position 0 to be of type boolean, got "+conditionValue);
    ValueVisitor<Boolean> caster = new BaseValueVisitor<Boolean>(error) {
      @Override
      public Boolean visitBoolean(boolean b) {
        return b;
      }
    };
    return conditionValue.accept(caster);
  }
}

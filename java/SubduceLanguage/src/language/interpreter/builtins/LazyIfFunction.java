package language.interpreter.builtins;

import java.util.List;
import java.util.function.Function;

import language.interpreter.expression.Expression;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.ValueVisitor;
import language.interpreter.expression.value.functionValue.FunctionValue;

public class LazyIfFunction implements Function<List<Expression>, Value> {
  private final Function<Expression, Value> evaluator;

  public LazyIfFunction(Function<Expression, Value> evaluator) {
    this.evaluator = evaluator;
  }

  public Value apply(List<Expression> expressions) {
    if(expressions.size() != 3) {
      // TODO fix
      throw new IllegalArgumentException("if expects 3 arguments, found "+expressions.size());
    }
    Expression first = expressions.get(0);
    Value conditionValue = evaluator.apply(first);
    boolean condition = conditionValue.accept(new ValueVisitor<Boolean>() {
      private final RuntimeException error = new IllegalArgumentException("if expects a boolean condition as first argument");
      @Override
      public Boolean visitBoolean(boolean b) {
        return b;
      }

      @Override
      public Boolean visitNumber(double d) {
        throw error;
      }

      @Override
      public Boolean visitString(String s) {
        throw error;
      }

      @Override
      public Boolean visitFunction(FunctionValue function) {
        throw error;
      }
    });
    Expression trueBranch = expressions.get(1);
    Expression falseBranch = expressions.get(2);
    if(condition) {
      return evaluator.apply(trueBranch);
    } else {
      return evaluator.apply(falseBranch);
    }
  }
}

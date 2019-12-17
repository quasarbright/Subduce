package language.interpreter.builtins;

import java.util.List;
import java.util.function.Function;

import language.interpreter.expression.Expression;
import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.Value;

public class MultiplyFunction extends ANumberBinaryOperatorFunction {

  public MultiplyFunction(String name) {
    super((a,b)->a*b, 1, name, true);
  }

  /**
   * Lazy evaluation for multiplication
   */
  @Override
  public Value apply(Function<Expression, Value> evaluator, List<Expression> arguments) {
    if(arguments.size() < 2) {
      // TODO fix
      throw new IllegalArgumentException(name+" expects at least two arguments, got "+arguments.size());
    }
    double ans = 1;
    for(Expression argument: arguments) {
      double number = castArgument(evaluator.apply(argument));
      if(NumberEqualFunction.eq(number, 0)) {
        return new NumberValue(0);
      } else {
        ans *= number;
      }
    }
    return new NumberValue(ans);
  }
}

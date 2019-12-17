package language.interpreter.builtins;

import java.util.List;
import java.util.function.BinaryOperator;

import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.Value;

public class ANumberBinaryOperatorFunction extends ANumberFunction {
  private final BinaryOperator<Double> operator;
  private final double identity;
  private final String name;
  private final boolean isAssociative;
  public ANumberBinaryOperatorFunction(BinaryOperator<Double> operator, double identity, String name, boolean isAssociative) {
    this.operator = operator;
    this.identity = identity;
    this.name = name;
    this.isAssociative = isAssociative;
  }


  @Override
  protected double defaultCastBehavior(Value value) {
    // TODO fix
    throw new IllegalArgumentException(name+" expects number arguments, got "+value);
  }

  @Override
  protected Value apply(List<Value> arguments) {
    List<Double> numbers = castArguments(arguments);

    if(isAssociative) {
      if(arguments.size() < 2) {
        // TODO fix
        throw new IllegalArgumentException(name+" expects at least two arguments, got "+arguments.size());
      }
      return new NumberValue(numbers.stream()
              .reduce(identity, operator));
    } else {
      if(numbers.size() != 2) {
        throw new IllegalArgumentException(name+" expects exactly two arguments, got "+arguments.size());
      }
      double left = numbers.get(0);
      double right = numbers.get(1);
      return new NumberValue(operator.apply(left, right));
    }
  }
}

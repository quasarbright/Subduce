package language.interpreter.builtins;

import java.util.List;
import java.util.function.BinaryOperator;

import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.FunctionSignature;
import language.interpreter.expression.value.functionValue.signature.RepeatedTypeSignature;
import language.interpreter.expression.value.functionValue.signature.TypeSequenceSignature;
import language.typing.BuiltInType;

/**
 * A numerical function based on a binary operator like + or - or * or /.
 * Takes in numbers and returns a number.
 * If it's associative, the function can take in an arbitrary number of arguments.
 * Otherwise, it can only take in two.
 */
public class ANumberBinaryOperatorFunction extends ANumberFunction {
  private final BinaryOperator<Double> operator;
  private final double identity;
  private final boolean isAssociative;

  public ANumberBinaryOperatorFunction(BinaryOperator<Double> operator, double identity, String name, boolean isAssociative) {
    super(name, getSignature(name, isAssociative));
    this.operator = operator;
    this.identity = identity;
    this.isAssociative = isAssociative;
  }

  private static FunctionSignature getSignature(String name, boolean isAssociative) {
    if(isAssociative) {
      return new RepeatedTypeSignature(name, BuiltInType.NUMBER);
    } else {
      return new TypeSequenceSignature(name, BuiltInType.NUMBER, BuiltInType.NUMBER);
    }
  }

  @Override
  protected Value apply(List<Value> arguments) {
    validateArguments(arguments);
    List<Double> numbers = castArguments(arguments);
    if(isAssociative) {
      return new NumberValue(numbers.stream()
              .reduce(identity, operator));
    } else {
      double left = numbers.get(0);
      double right = numbers.get(1);
      return new NumberValue(operator.apply(left, right));
    }
  }
}

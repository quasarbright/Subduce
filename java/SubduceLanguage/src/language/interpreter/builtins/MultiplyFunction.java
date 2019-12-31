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
}

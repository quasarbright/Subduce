package language.interpreter.builtins;

import java.util.function.BinaryOperator;

public class SubtractFunction extends ANumberBinaryOperatorFunction {
  public SubtractFunction(String name) {
    super((a,b) -> a-b, 0, name, false);
  }
}

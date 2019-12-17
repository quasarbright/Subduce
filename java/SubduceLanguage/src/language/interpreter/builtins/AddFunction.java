package language.interpreter.builtins;

public class AddFunction extends ANumberBinaryOperatorFunction {
  public AddFunction() {
    super(Double::sum, 0, "+", true);
  }
}

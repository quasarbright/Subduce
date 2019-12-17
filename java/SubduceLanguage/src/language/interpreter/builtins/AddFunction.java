package language.interpreter.builtins;

public class AddFunction extends ANumberBinaryOperatorFunction {
  public AddFunction(String name) {
    super(Double::sum, 0, name, true);
  }
}

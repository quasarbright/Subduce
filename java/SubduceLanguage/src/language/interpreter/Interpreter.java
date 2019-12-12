package language.interpreter;

public interface Interpreter<ValueType> {
  void run(String source);

  void runWithInteraction(String source);

  ValueType eval(String expression);
}

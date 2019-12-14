package language.interpreter;

import java.util.function.Function;


public interface Interpreter<ValueType> {
  /**
   * Run the program and return an expression evaluator based on the program's definitions.
   *
   * @param source the source code to run
   */
  void run(String source);

  /**
   * Run the program with an interactive prompt after the code runs.
   * @param source the source code to run
   */
  void runWithInteraction(String source);

  /**
   * Evaluate the expression (using only builtins)
   * @param expression the expression to evaluate
   * @return the evaluated expression
   */
  ValueType eval(String expression);
}

package language.interpreter;

import java.util.Optional;

/**
 * Mutable subduce runtime. This is the "life" of a program's running.
 * Stateful: Calling run twice runs both things sequentially without resetting.
 *
 * @param <StatementType> type of statements that can be ran
 * @param <ExpressionType> type of Expressions that can be evaluated
 * @param <ValueType> type of
 */
public interface Runtime<StatementType, ExpressionType, ValueType> {
  /**
   * Run the given subduce code in this runtime.
   * Can be an entire program, or just one statement.
   *
   * @param source the subduce code to run
   * @return the value returned by the code, if any
   */
  Optional<ValueType> run(String source);

  /**
   * Run the given subduce statement in this runtime.
   *
   * @param statement the subduce statement to run
   * @return the value returned by the statement, if any
   */
  Optional<ValueType> run(StatementType statement);

  /**
   * Evaluate the given expression in the context of this runtime.
   *
   * @param expression the expression to evaluate
   * @return the evaluated value of this expression
   */
  ValueType evaluate(String expression);

  /**
   * Evaluate the given expression in the context of this runtime.
   *
   * @param expression the expression to evaluate
   * @return the evaluated value of this expression
   */
  ValueType evaluate(ExpressionType expression);
}

package language.interpreter.statement;

import java.util.List;
import java.util.function.Supplier;

import language.interpreter.expression.Expression;

/**
 * Base visitor for statements.
 * To make a caster easily, supply a default behavior and only override the methods you're interested
 * in.
 *
 * @param <R> return type
 */
public class BaseStatementVisitor<R> implements StatementVisitor<R> {
  private final Supplier<R> defaultBehavior;

  /**
   * Construct a base visitor with the given default behavior.
   *
   * @param defaultBehavior what to do when a method is called that is not overridden
   */
  public BaseStatementVisitor(Supplier<R> defaultBehavior) {
    this.defaultBehavior = defaultBehavior;
  }

  public BaseStatementVisitor(R defaultValue) {
    this(() -> defaultValue);
  }

  private R defaultBehavior() {
    return defaultBehavior.get();
  }

  @Override
  public R visit(Statement statement) {
    return statement.accept(this);
  }

  @Override
  public R visitExpression(Expression expression) {
    return defaultBehavior();
  }

  @Override
  public R visitPrint(Expression expression) {
    return defaultBehavior();
  }

  @Override
  public R visitReturn(Expression expression) {
    return defaultBehavior();
  }

  @Override
  public R visitFunctionDefinition(String name, List<String> argnames, Statement body) {
    return defaultBehavior();
  }

  @Override
  public R visitStructDefinition(String name, List<String> fieldNames) {
    return defaultBehavior();
  }

  @Override
  public R visitVariableDefinition(String name, Expression expression) {
    return defaultBehavior();
  }

  @Override
  public R visitSequence(List<Statement> statements) {
    return defaultBehavior();
  }
}

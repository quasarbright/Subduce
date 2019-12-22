package language.interpreter.statement;

import language.interpreter.expression.Expression;

public class ReturnStatement implements Statement {
  private final Expression expression;

  public ReturnStatement(Expression expression) {
    this.expression = expression;
  }

  @Override
  public <R> R accept(StatementVisitor<R> visitor) {
    return visitor.visitReturn(expression);
  }
}

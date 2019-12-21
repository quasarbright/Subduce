package language.interpreter.statement;

import language.interpreter.expression.Expression;

public class ExpressionStatement implements Statement {
  private final Expression expression;

  public ExpressionStatement(Expression expression) {
    this.expression = expression;
  }

  @Override
  public <R> R accept(StatementVisitor<R> visitor) {
    return visitor.visitExpression(expression);
  }
}

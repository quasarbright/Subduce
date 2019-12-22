package language.interpreter.statement;

import language.interpreter.expression.Expression;

public class PrintStatement implements Statement {
  private final Expression expression;

  public PrintStatement(Expression expression) {
    this.expression = expression;
  }

  @Override
  public <R> R accept(StatementVisitor<R> visitor) {
    return visitor.visitPrint(expression);
  }
}

package language.interpreter.statement;

import java.util.List;

public class SequenceStatement implements Statement {
  private final List<Statement> statements;

  public SequenceStatement(List<Statement> statements) {
    this.statements = statements;
  }

  @Override
  public <R> R accept(StatementVisitor<R> visitor) {
    return visitor.visitSequence(statements);
  }
}

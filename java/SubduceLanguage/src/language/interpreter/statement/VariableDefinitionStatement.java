package language.interpreter.statement;

import language.interpreter.expression.Expression;

public class VariableDefinitionStatement implements Statement {
  private final String name;
  private final Expression expression;

  public VariableDefinitionStatement(String name, Expression expression) {
    this.name = name;
    this.expression = expression;
  }

  @Override
  public <R> R accept(StatementVisitor<R> visitor) {
    return visitor.visitVariableDefinition(name, expression);
  }
}

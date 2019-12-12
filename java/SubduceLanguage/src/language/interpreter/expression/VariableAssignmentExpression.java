package language.interpreter.expression;

public class VariableAssignmentExpression implements Expression {
  private final String name;
  private final Expression expression;

  public VariableAssignmentExpression(String name, Expression expression) {
    this.name = name;
    this.expression = expression;
  }

  @Override
  public <R> R accept(ExpressionVisitor<R> visitor) {
    return visitor.visitVariableAssignment(name, expression);
  }
}

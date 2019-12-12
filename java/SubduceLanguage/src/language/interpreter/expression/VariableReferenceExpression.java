package language.interpreter.expression;

public class VariableReferenceExpression implements Expression {
  private final String name;

  public VariableReferenceExpression(String name) {
    this.name = name;
  }

  @Override
  public <R> R accept(ExpressionVisitor<R> visitor) {
    return visitor.visitVariableReference(name);
  }
}

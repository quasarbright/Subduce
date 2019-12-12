package language.interpreter.expression;

import java.util.ArrayList;
import java.util.List;

public class SequenceExpression implements Expression {
  private final List<Expression> expressions;

  public SequenceExpression(List<Expression> expressions) {
    this.expressions = expressions;
  }

  @Override
  public <R> R accept(ExpressionVisitor<R> visitor) {
    return visitor.visitSequence(new ArrayList<>(expressions));
  }
}

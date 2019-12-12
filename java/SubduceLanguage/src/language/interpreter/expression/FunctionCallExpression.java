package language.interpreter.expression;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallExpression implements Expression {
  private final Expression function;
  private final List<Expression> arguments;

  public FunctionCallExpression(Expression function, List<Expression> arguments) {
    this.function = function;
    this.arguments = arguments;
  }

  @Override
  public <R> R accept(ExpressionVisitor<R> visitor) {
    return visitor.visitFunctionCall(function, new ArrayList<>(arguments));
  }
}

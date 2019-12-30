package language.interpreter.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(function, arguments);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FunctionCallExpression other = (FunctionCallExpression) obj;
        return function.equals(other.function) && arguments.equals(other.arguments);
    }
}

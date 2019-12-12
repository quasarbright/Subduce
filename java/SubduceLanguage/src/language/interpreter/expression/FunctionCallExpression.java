package language.interpreter.expression;

import java.util.List;

public class FunctionCallExpression {
    private final Expression function;

    public FunctionCallExpression(Expression function, List<Expression> arguments) {
        this.function = function;
        this.arguments = arguments;
    }

    private final List<Expression> arguments;
}

package language.interpreter.expression;

import java.util.List;

public class SequenceExpression {
    private final List<Expression> expressions;

    public SequenceExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }
}

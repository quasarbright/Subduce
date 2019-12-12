package language.interpreter.expression;

import language.interpreter.expression.value.Value;

import java.util.List;

public interface ExpressionVisitor<R> {
    R visitFunctionCall(Expression function, List<Expression> arguments);
    R visitFunctionDefinition(String name, List<String> argnames, Expression body);
    R visitSequence(List<Expression> expressions);
    R visitValue(Value value);
}

package language.interpreter.expression;

import language.interpreter.expression.value.Value;

import java.util.List;

public interface ExpressionVisitor<R> {
    R visitFunctionCall(Expression function, List<Expression> arguments);
    R visitLambda(List<String> argnames, Expression body);
    R visitValue(Value value);
    R visitVariableReference(String name);
}

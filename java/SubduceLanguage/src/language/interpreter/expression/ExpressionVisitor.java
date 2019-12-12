package language.interpreter.expression;

import language.interpreter.expression.value.Value;

import java.util.List;

public interface ExpressionVisitor<R> {
    R visitFunctionCall(Expression function, List<Expression> arguments);
    R visitFunctionDefinition(String name, List<String> argnames, Expression body);
    R visitLambdaDefinition(List<String> argnames, Expression body);
    R visitSequence(List<Expression> expressions);
    R visitVariableAssignment(String name, Expression expression);
    R visitValue(Value value);
    R visitVariableReference(String name);
}

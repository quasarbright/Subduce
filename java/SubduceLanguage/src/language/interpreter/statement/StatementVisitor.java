package language.interpreter.statement;

import java.util.List;

import language.interpreter.expression.Expression;

public interface StatementVisitor<R> {
  R visit(Statement statement);
  R visitExpression(Expression expression);
  R visitPrint(Expression expression);
  R visitReturn(Expression expression);
  R visitFunctionDefinition(String name, List<String> argnames, Statement body);
  R visitVariableDefinition(String name, Expression expression);
  R visitStructDefinition(String name, List<String> fieldNames);
  R visitSequence(List<Statement> statements);
}

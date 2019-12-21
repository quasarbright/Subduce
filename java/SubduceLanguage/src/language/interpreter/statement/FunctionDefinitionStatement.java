package language.interpreter.statement;

import java.util.List;

import language.interpreter.expression.Expression;

public class FunctionDefinitionStatement implements Statement {
  private final String name;
  private final List<String> argnames;
  private final Statement body;


  public FunctionDefinitionStatement(String name, List<String> argnames, Statement body) {
    this.name = name;
    this.argnames = argnames;
    this.body = body;
  }

  @Override
  public <R> R accept(StatementVisitor<R> visitor) {
    return visitor.visitFunctionDefinition(name, argnames, body);
  }
}

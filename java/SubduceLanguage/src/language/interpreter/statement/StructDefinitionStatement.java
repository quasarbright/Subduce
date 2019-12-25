package language.interpreter.statement;

import java.util.List;

public class StructDefinitionStatement implements Statement {
  private final String name;
  private final List<String> fieldNames;

  public StructDefinitionStatement(String name, List<String> fieldNames) {
    this.name = name;
    this.fieldNames = fieldNames;
  }

  @Override
  public <R> R accept(StatementVisitor<R> visitor) {
    return visitor.visitStructDefinition(name, fieldNames);
  }
}

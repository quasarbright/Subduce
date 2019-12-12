package language.interpreter.expression;

public class VariableAssignmentExpression {
    private final String name;
    private final Expression value;

    public VariableAssignmentExpression(String name, Expression value) {
        this.name = name;
        this.value = value;
    }
}

package language.interpreter.expression;

import java.util.List;

public class FunctionDefinitionExpression {
    private final String name;
    private final List<String> argnames;
    private final Expression body;

    public FunctionDefinitionExpression(String name, List<String> argnames, Expression body) {
        this.name = name;
        this.argnames = argnames;
        this.body = body;
    }
}

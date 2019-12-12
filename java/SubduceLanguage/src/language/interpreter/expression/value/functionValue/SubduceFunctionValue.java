package language.interpreter.expression.value.functionValue;

import language.interpreter.Environment;
import language.interpreter.expression.Expression;

import java.util.List;

public class SubduceFunctionValue {
    private final List<String> argnames;
    private final Expression body;
    private final Environment environment;

    public SubduceFunctionValue(List<String> argnames, Expression body, Environment environment) {
        this.argnames = argnames;
        this.body = body;
        this.environment = environment;
    }
}

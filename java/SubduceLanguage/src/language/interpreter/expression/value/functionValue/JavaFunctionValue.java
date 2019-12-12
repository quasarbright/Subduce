package language.interpreter.expression.value.functionValue;

import language.interpreter.expression.value.Value;

import java.util.List;
import java.util.function.Function;

public class JavaFunctionValue {
    private final Function<List<Value>, Value> function;

    public JavaFunctionValue(Function<List<Value>, Value> function) {
        this.function = function;
    }
}
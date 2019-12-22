package language.interpreter.expression.value.functionValue;

import language.interpreter.Environment;
import language.interpreter.expression.Expression;
import language.interpreter.expression.value.Value;
import language.interpreter.statement.Statement;

import java.util.List;
import java.util.function.Function;

public interface FunctionValueVisitor<R> {
  R visitSubduceFunction(List<String> argnames, Statement body, Environment<String, Value> environment);
  R visitJavaFunction(JavaFunctionValue.JavaFunctionImplementation function);
}

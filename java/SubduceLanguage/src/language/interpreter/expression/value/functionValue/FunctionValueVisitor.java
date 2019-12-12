package language.interpreter.expression.value.functionValue;

import language.interpreter.Environment;
import language.interpreter.expression.Expression;
import language.interpreter.expression.value.Value;

import java.util.List;
import java.util.function.Function;

public interface FunctionValueVisitor<R> {
  R visitSubduceFunction(List<String> argnames, Expression body, Environment environment);

  R visitJavaFunction(Function<List<Value>, Value> function);
}

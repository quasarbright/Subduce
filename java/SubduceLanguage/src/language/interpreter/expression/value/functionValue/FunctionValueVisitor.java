package language.interpreter.expression.value.functionValue;

import language.interpreter.Environment;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.FunctionSignature;
import language.interpreter.statement.Statement;

import java.util.List;

public interface FunctionValueVisitor<R> {
  R visitSubduceFunction(List<String> argnames, Statement body, Environment<String, Value> environment, FunctionSignature signature);
  R visitJavaFunction(JavaFunctionValue.JavaFunctionImplementation function);
}

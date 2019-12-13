package language.interpreter.builtins;

import java.util.HashMap;
import java.util.Map;

import language.interpreter.Environment;
import language.interpreter.ImmutableVariableEnvironment;
import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.JavaFunctionValue;

public class BuiltinUtilities {
  private final Map<String, Value> builtinValues;
  private Environment<String, Value> baseEnvironment;

  public BuiltinUtilities() {
    this(new ImmutableVariableEnvironment());
  }

  public BuiltinUtilities(Environment<String, Value> baseEnvironment) {
    builtinValues = new HashMap<>();
    addFunction("+", new AddFunction());
    addFunction("if", new IfFunction());
    addFunction("==", new NumberEqualFunction());
    this.baseEnvironment = baseEnvironment;
    fillBaseEnvironment();
  }

  private void addFunction(String name, JavaFunctionImplementation function) {
    builtinValues.put(name, new JavaFunctionValue(function));
  }

  private void addValue(String name, Value value) {
    builtinValues.put(name, value);
  }

  private void fillBaseEnvironment() {
    for(String name: builtinValues.keySet()) {
      Value value = builtinValues.get(name);
      baseEnvironment = baseEnvironment.withNewVariable(name, value);
    }
    baseEnvironment = baseEnvironment.withNewScope();
  }

  public Environment<String, Value> getBaseEnvironment() {
    return baseEnvironment;
  }
}

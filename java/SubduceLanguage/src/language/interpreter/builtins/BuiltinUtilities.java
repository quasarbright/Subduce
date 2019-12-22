package language.interpreter.builtins;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import language.interpreter.Environment;
import language.interpreter.ImmutableVariableEnvironment;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.JavaFunctionValue;
import language.interpreter.expression.value.listValue.ListValue;

public class BuiltinUtilities {
  private final Map<String, Value> builtinValues;
  private Environment<String, Value> baseEnvironment;

  public BuiltinUtilities() {
    this(new ImmutableVariableEnvironment());
  }

  public BuiltinUtilities(Environment<String, Value> baseEnvironment) {
    builtinValues = new HashMap<>();
    addFunction("+", AddFunction::new);
    addFunction("*", MultiplyFunction::new);
    addFunction("-", SubtractFunction::new);
    addFunction("/", DivideFunction::new);
    addFunction("if", IfFunction::new);
    addFunction("==", NumberEqualFunction::new);
    addFunction("<", LessThanFunction::new);
    addFunction("<=", LessThanOrEqualToFunction::new);
    addFunction(">", GreaterThanFunction::new);
    addFunction(">=", GreaterThanOrEqualToFunction::new);
    addFunction("list", ListFunction::new);
    addFunction("cons", ConsFunction::new);
    addValue("empty", ListValue.fromValues(Collections.emptyList()));
    addFunction("first", FirstFunction::new);
    addFunction("rest", RestFunction::new);
    this.baseEnvironment = baseEnvironment;
    fillBaseEnvironment();
  }

  private void addFunction(String name, Function<String, BaseJavaFunctionImplementation> functionFactory) {
    addValue(name, new JavaFunctionValue(functionFactory.apply(name)));
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

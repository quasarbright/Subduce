package language.interpreter.builtins;

import java.util.List;

import language.interpreter.SubduceError;
import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.listValue.ListValue;
import language.interpreter.expression.value.listValue.ListValueVisitor;

public class RestFunction extends AListFunction {
  public RestFunction(String name) {
    super(name);
  }

  @Override
  protected Value onEmpty() {
    throw new SubduceError("cannot call "+name+" on an empty list");
  }

  @Override
  protected Value onCons(Value first, ListValue rest) {
    return rest;
  }
}

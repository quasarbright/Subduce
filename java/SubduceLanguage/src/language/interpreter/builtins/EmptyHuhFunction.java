package language.interpreter.builtins;

import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.listValue.ListValue;

public class EmptyHuhFunction extends AListFunction {
  public EmptyHuhFunction(String name) {
    super(name);
  }

  @Override
  protected Value onEmpty() {
    return new BooleanValue(true);
  }

  @Override
  protected Value onCons(Value first, ListValue rest) {
    return new BooleanValue(false);
  }

  @Override
  protected Value onNonList(Value argument) {
    return new BooleanValue(false);
  }
}

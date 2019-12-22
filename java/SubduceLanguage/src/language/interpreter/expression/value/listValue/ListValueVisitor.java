package language.interpreter.expression.value.listValue;

import language.interpreter.expression.value.Value;

public interface ListValueVisitor<R> {
  default R visit(ListValue list) {
    return list.accept(this);
  }
  R visitEmpty();
  R visitCons(Value first, ListValue rest);
}

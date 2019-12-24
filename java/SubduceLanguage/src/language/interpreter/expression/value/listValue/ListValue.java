package language.interpreter.expression.value.listValue;

import java.util.List;

import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.ValueVisitor;
import language.interpreter.typing.BuiltInType;
import language.interpreter.typing.ValueType;

/**
 * Represents a cons-list.
 * A List is one of:
 * empty
 * (cons Value List)
 */
public interface ListValue extends Value {
  <R> R accept(ListValueVisitor<R> visitor);

  @Override
  default <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitList(this);
  }

  static ListValue fromValues(List<Value> values) {
    ListValue ans = new EmptyList();
    for(int i = values.size()-1; i >= 0; i--) {
      Value value = values.get(i);
      ans = new ConsList(value, ans);
    }
    return ans;
  }

  @Override
  default ValueType getType() {
    return BuiltInType.LIST;
  }
}

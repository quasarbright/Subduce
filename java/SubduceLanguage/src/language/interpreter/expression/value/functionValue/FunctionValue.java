package language.interpreter.expression.value.functionValue;

import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.ValueVisitor;
import language.interpreter.expression.value.functionValue.signature.FunctionSignature;
import language.interpreter.typing.BuiltInType;
import language.interpreter.typing.ValueType;

public interface FunctionValue extends Value {
  <R> R accept(FunctionValueVisitor<R> visitor);

  @Override
  default <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitFunction(this);
  }

  @Override
  default ValueType getType() {
    return BuiltInType.FUNCTION;
  }

  FunctionSignature getSignature();
}

package language.interpreter.expression.value.functionValue;

import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.ValueVisitor;

public interface FunctionValue extends Value {
    <R> R accept(FunctionValueVisitor<R> visitor);

    @Override
    default <R> R accept(ValueVisitor<R> visitor) {
        return visitor.visitFunction(this);
    }
}

package language.interpreter.expression.value;

import java.util.Objects;

import language.typing.BuiltInType;
import language.typing.ValueType;

public class BooleanValue implements Value {
    public final boolean val;

    public BooleanValue(boolean val) {
        this.val = val;
    }

    @Override
    public <R> R accept(ValueVisitor<R> visitor) {
        return visitor.visitBoolean(val);
    }

    @Override
    public String toString() {
        return Boolean.toString(val);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return val == ((BooleanValue) obj).val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public ValueType getType() {
        return BuiltInType.BOOLEAN;
    }
}

package language.interpreter.expression.value;

import java.util.Objects;

public class StringValue implements Value {
    public final String val;

    public StringValue(String val) {
        this.val = val;
    }

    @Override
    public <R> R accept(ValueVisitor<R> visitor) {
        return visitor.visitString(val);
    }

    @Override
    public String toString() {
        return '"'+val+'"';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return val.equals(((StringValue) obj).val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }
}

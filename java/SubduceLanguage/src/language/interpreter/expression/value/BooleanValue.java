package language.interpreter.expression.value;

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
}

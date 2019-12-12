package language.interpreter.expression.value;

public class BooleanValue implements Value {
    private final boolean val;

    public BooleanValue(boolean val) {
        this.val = val;
    }

    @Override
    public <R> R accept(ValueVisitor<R> visitor) {
        return visitor.visitBoolean(val);
    }
}

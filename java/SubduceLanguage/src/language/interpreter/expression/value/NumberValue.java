package language.interpreter.expression.value;

public class NumberValue implements Value {
    private final double val;

    public NumberValue(double val) {
        this.val = val;
    }

    @Override
    public <R> R accept(ValueVisitor<R> visitor) {
        return visitor.visitNumber(val);
    }
}

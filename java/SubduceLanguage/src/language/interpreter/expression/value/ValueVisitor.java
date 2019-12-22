package language.interpreter.expression.value;

import language.interpreter.expression.value.functionValue.FunctionValue;
import language.interpreter.expression.value.listValue.ListValue;

public interface ValueVisitor<R> {
    R visitBoolean(boolean b);
    R visitNumber(double d);
    R visitString(String s);
    R visitFunction(FunctionValue function);
    R visitList(ListValue list);
}

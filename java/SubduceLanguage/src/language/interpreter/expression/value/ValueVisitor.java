package language.interpreter.expression.value;

import java.util.Map;

import language.interpreter.expression.value.functionValue.FunctionValue;
import language.interpreter.expression.value.listValue.ListValue;
import language.interpreter.typing.StructType;

public interface ValueVisitor<R> {
    R visitBoolean(boolean b);
    R visitNumber(double d);
    R visitString(String s);
    R visitFunction(FunctionValue function);
    R visitList(ListValue list);
    R visitStruct(StructType structType, Map<String, Value> data);
}

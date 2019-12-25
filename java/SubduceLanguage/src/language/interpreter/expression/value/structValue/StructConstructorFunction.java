package language.interpreter.expression.value.structValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import language.interpreter.builtins.BaseJavaFunctionImplementation;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.FunctionSignature;
import language.interpreter.expression.value.functionValue.signature.TypeSequenceSignature;
import language.interpreter.typing.AnyType;
import language.interpreter.typing.StructType;
import language.interpreter.typing.ValueType;

public class StructConstructorFunction extends BaseJavaFunctionImplementation {
  private final StructType structType;

  public StructConstructorFunction(StructType structType) {
    super("make-"+structType.getName(), getSignature("make-"+structType.getName(), structType));
    this.structType = structType;
  }

  private static FunctionSignature getSignature(String name, StructType structType) {
    List<ValueType> types = new ArrayList<>();
    for(String fieldName: structType.getFieldNames()) {
      types.add(new AnyType());
    }
    return new TypeSequenceSignature(name, types);
  }

  @Override
  protected Value apply(List<Value> arguments) {
    validateArguments(arguments);
    Map<String, Value> data = new HashMap<>();
    List<String> fieldNames = structType.getFieldNames();
    for(int i = 0; i < fieldNames.size(); i++) {
      String fieldName = fieldNames.get(i);
      data.put(fieldName, arguments.get(i));
    }
    return new StructValue(structType, data);
  }
}

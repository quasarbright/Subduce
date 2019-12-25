package language.interpreter.expression.value.structValue;

import java.util.List;
import java.util.Map;

import language.interpreter.builtins.BaseJavaFunctionImplementation;
import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.TypeSequenceSignature;
import language.interpreter.typing.StructType;

public class StructAccessorFunction extends BaseJavaFunctionImplementation {
  private final StructType structType;
  private final String fieldName;

  public StructAccessorFunction(StructType structType, String fieldName) {
    super(structType.getName()+"-"+fieldName, new TypeSequenceSignature(structType.getName()+"-"+fieldName, structType));
    if(!structType.getFieldNames().contains(fieldName)) {
      throw new IllegalArgumentException("cannot generate accessor for unknown field name "+fieldName);
    }
    this.structType = structType;
    this.fieldName = fieldName;
  }

  @Override
  protected Value apply(List<Value> arguments) {
    validateArguments(arguments);
    Value argument = arguments.get(0);
    return argument.accept(new BaseValueVisitor<>(new IllegalStateException("Arguments should've been validated")) {
      @Override
      public Value visitStruct(StructType structType, Map<String, Value> data) {
        if(data.containsKey(fieldName)) {
          return data.get(fieldName);
        } else {
          throw new IllegalStateException("field name should've been validated or data should contain fieldName");
        }
      }
    });
  }
}

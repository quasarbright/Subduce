package language.interpreter.expression.value.structValue;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.ValueVisitor;
import language.interpreter.typing.BuiltInType;
import language.interpreter.typing.StructType;
import language.interpreter.typing.ValueType;

public class StructValue implements Value {
  private final StructType structType;
  private final Map<String, Value> data;

  public StructValue(StructType structType, Map<String, Value> data) {
    this.structType = structType;
    this.data = new HashMap<>(data);
    validateData();
  }

  private void validateData() {
    Set<String> expected = new HashSet<>(structType.getFieldNames());
    Set<String> actual = data.keySet();
    if(!expected.equals(actual)) {
      throw new IllegalArgumentException("wrong data fields for struct. Expected "+expected+", got "+actual);
    }
  }


  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitStruct(structType, new HashMap<>(data));
  }

  @Override
  public ValueType getType() {
    return BuiltInType.STRUCT;
  }

  @Override
  public int hashCode() {
    return Objects.hash(structType, data);
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj) {
      return true;
    }
    if(obj == null || getClass() != obj.getClass()) {
      return false;
    }
    StructValue other = (StructValue) obj;
    return structType.equals(other.structType) && data.equals(other.data);
  }

  @Override
  public String toString() {
    return "[struct "+structType.getName()+" "+data+"]";
  }
}

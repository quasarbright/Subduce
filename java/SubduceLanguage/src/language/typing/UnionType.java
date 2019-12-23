package language.typing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import language.interpreter.expression.value.Value;

/**
 * Union type. Like an "or" of multiple types
 */
public class UnionType implements ValueType {
  private final List<ValueType> types;

  public UnionType(List<ValueType> types) {
    if(types.isEmpty()) {
      throw new IllegalArgumentException("cannot create an empty union type");
    }
    this.types = new ArrayList<>(types);
  }

  public UnionType(ValueType... types) {
    this(Arrays.asList(types));
  }

  @Override
  public String getName() {
    List<String> strings = types.stream()
            .map(Object::toString)
            .collect(Collectors.toList());
    return "union("+String.join(", ", strings)+")";
  }

  @Override
  public boolean checkValueType(Value value) {
    return types.stream()
            .map((ValueType type) -> type.checkValueType(value))
            .reduce(false, (Boolean::logicalOr));

  }

  @Override
  public String toString() {
    return "[type "+getName()+"]";
  }

  @Override
  public int hashCode() {
    return new HashSet<>(types).hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj) {
      return true;
    }
    if(obj == null || getClass() != obj.getClass()) {
      return false;
    }

    UnionType other = (UnionType) obj;
    return new HashSet<>(types).equals(new HashSet<>(other.types));
  }
}

package language.typing;

import java.util.List;

/**
 * Built-in value types. Does not distinguish between user-defined struct types. (treats all structs
 * the same).
 */
public enum BuiltInType implements ValueType {
  NUMBER("number"), STRING("string"), BOOLEAN("boolean"), LIST("list"),
  FUNCTION("function"), STRUCT("struct");

  private final String name;

  BuiltInType(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }


  @Override
  public String toString() {
    return "[type "+getName()+"]";
  }
}

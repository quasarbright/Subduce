package language.interpreter.typing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.FunctionValue;
import language.interpreter.expression.value.functionValue.JavaFunctionValue;
import language.interpreter.expression.value.structValue.StructAccessorFunction;
import language.interpreter.expression.value.structValue.StructConstructorFunction;
import language.interpreter.expression.value.structValue.StructPredicateFunction;

/**
 * Reperesents a specific struct type.
 * Is a value type, but also has utility methods for generating struct functions including a predicate,
 * constructor, and accessors.
 */
public class StructType implements ValueType {
  private final String name;
  private final List<String> fieldNames;

  public StructType(String name, List<String> fieldNames) {
    this.name = name;
    this.fieldNames = fieldNames;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * Get the field names associated with this struct type.
   *
   * @return the field names associated with this struct type
   */
  public List<String> getFieldNames() {
    return new ArrayList<>(fieldNames);
  }

  /**
   * Create a function that determines whether a value is of this struct type.
   *
   * @return a function that determines whether a value is of this struct type
   */
  public JavaFunctionValue getPredicate() {
    return new JavaFunctionValue(new StructPredicateFunction(this));
  }

  /**
   * Create field accessor functions.
   *
   * @return field accessor functions
   */
  public List<JavaFunctionValue> getAccessors() {
    return fieldNames.stream()
            .map((String fieldName) -> new StructAccessorFunction(this, fieldName))
            .map(JavaFunctionValue::new)
            .collect(Collectors.toList());
  }

  /**
   * Create a constructor function for this struct type.
   *
   * @return constructor function
   */
  public JavaFunctionValue getConstructor() {
    return new JavaFunctionValue(new StructConstructorFunction(this));
  }

  /**
   * Create all auto-generated functions for this struct.
   *
   * @return all auto-generated functions for this struct
   */
  public List<JavaFunctionValue> getStructFunctions() {
    List<JavaFunctionValue> ans = new ArrayList<>();
    ans.add(getPredicate());
    ans.addAll(getAccessors());
    ans.add(getConstructor());
    return ans;
  }

  @Override
  public boolean checkValueType(Value value) {
    return value.accept(new BaseValueVisitor<>(false) {
      @Override
      public Boolean visitStruct(StructType structType, Map<String, Value> data) {
        return StructType.this.equals(structType);
      }
    });
  }
}

package language.interpreter.expression.value.structValue;

import java.util.List;

import language.interpreter.builtins.BaseJavaFunctionImplementation;
import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.TypeSequenceSignature;
import language.interpreter.typing.AnyType;
import language.interpreter.typing.StructType;

/**
 * Is the given value of our struct type?
 */
public class StructPredicateFunction extends BaseJavaFunctionImplementation {
  private final StructType structType;

  private StructPredicateFunction(String name, StructType structType) {
    super(name, new TypeSequenceSignature(name, new AnyType()));
    this.structType = structType;
  }

  /**
   * Use the struct type's name + "?" as the predicate function's name.
   */
  public StructPredicateFunction(StructType structType) {
    this(structType.getName()+"?", structType);
  }

  @Override
  protected Value apply(List<Value> arguments) {
    validateArguments(arguments);
    Value argument = arguments.get(0);
    return new BooleanValue(structType.checkValueType(argument));
  }
}

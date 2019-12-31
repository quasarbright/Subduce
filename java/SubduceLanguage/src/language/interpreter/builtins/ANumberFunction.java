package language.interpreter.builtins;

import java.util.List;
import java.util.stream.Collectors;

import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.SubduceError;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.signature.FunctionSignature;

public abstract class ANumberFunction extends BaseJavaFunctionImplementation {

  public ANumberFunction(String name, FunctionSignature signature) {
    super(name, signature);
  }

  protected List<Double> castArguments(List<Value> arguments) {
    return arguments.stream()
            .map(this::castArgument)
            .collect(Collectors.toList());
  }

  protected double castArgument(Value argument) {
    return argument.accept(new BaseValueVisitor<Double>(new SubduceError("signature should've been validated")) {
      @Override
      public Double visitNumber(double d) {
        return d;
      }
    });
  }
}

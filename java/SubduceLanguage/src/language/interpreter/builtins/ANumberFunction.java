package language.interpreter.builtins;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import language.interpreter.expression.value.BaseValueVisitor;
import language.interpreter.expression.value.Value;

public abstract class ANumberFunction extends JavaFunctionImplementation {
  protected List<Double> castArguments(List<Value> arguments) {
    return arguments.stream()
            .map(this::castArgument)
            .collect(Collectors.toList());
  }

  protected double castArgument(Value argument) {
    return argument.accept(new BaseValueVisitor<Double>(() -> defaultCastBehavior(argument)) {
      @Override
      public Double visitNumber(double d) {
        return d;
      }
    });
  }

  protected abstract double defaultCastBehavior(Value value);
}

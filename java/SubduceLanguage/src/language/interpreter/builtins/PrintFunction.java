package language.interpreter.builtins;

import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.function.Function;

import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.JavaFunctionValue;

public class PrintFunction extends JavaFunctionImplementation {
  @Override
  public Value apply(List<Value> valueList) {
    if(valueList.size() == 0) {
      throw new IllegalStateException();
    }
    valueList.forEach(System.out::println);
    return valueList.get(0);
  }
}

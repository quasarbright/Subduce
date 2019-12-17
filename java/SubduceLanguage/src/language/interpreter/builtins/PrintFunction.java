package language.interpreter.builtins;

import java.util.List;

import language.interpreter.expression.value.Value;

public class PrintFunction extends BaseJavaFunctionImplementation {
  @Override
  public Value apply(List<Value> valueList) {
    if(valueList.size() == 0) {
      throw new IllegalStateException(" the parser shouldn't have allowed an empty print");
    }
    valueList.forEach(System.out::println);
    return valueList.get(0);
  }
}

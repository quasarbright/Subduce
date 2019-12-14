package language.interpreter;

import java.util.Optional;
import java.util.Scanner;

import language.interpreter.expression.Expression;
import language.interpreter.expression.value.Value;

public class DefinitionInterpreter implements Interpreter<Value> {
  private final Scanner in;

  public DefinitionInterpreter() {
    in = new Scanner(System.in);
  }

  @Override
  public void run(String source) {
    new DefinitionRuntime().run(source);
  }

  @Override
  public void runWithInteraction(String source) {
    Runtime<Expression, Expression, Value> runtime = new DefinitionRuntime();
    System.out.println("enter expressions or definitions to evaluate them");
    System.out.println("exit by typing \"exit\" and pressing enter");
    System.out.print("In: ");
    while(in.hasNextLine()) {
      String line = in.nextLine();
      if(line.equals("exit")) {
        System.out.println("exiting");
        return;
      }
      System.out.print("Out: ");

      Optional<Value> maybeValue = runtime.run(line);
      maybeValue.ifPresent(System.out::println);

      System.out.println();
      System.out.print("In: ");
    }
  }

  @Override
  public Value eval(String expressionString) {
    return new DefinitionRuntime().evaluate(expressionString);
  }
}

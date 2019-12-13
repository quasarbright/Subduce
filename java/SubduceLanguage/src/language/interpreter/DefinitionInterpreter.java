package language.interpreter;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import java.util.Scanner;

import language.grammar.SubduceLexer;
import language.grammar.SubduceParser;
import language.interpreter.builtins.BuiltinUtilities;
import language.interpreter.expression.Expression;
import language.interpreter.expression.value.Value;

public class DefinitionInterpreter implements Interpreter<Value> {
  private final Scanner in;
  Environment<String, Value> baseEnvironment;

  public DefinitionInterpreter() {
    baseEnvironment = new BuiltinUtilities().getBaseEnvironment();
    in = new Scanner(System.in);
    // TODO add builtins like addition, cons, if, etc
  }

  SubduceParser getParser(String source) {
    CharStream stream = CharStreams.fromString(source);
    SubduceLexer lexer = new SubduceLexer(stream);
    TokenStream tokenStream = new CommonTokenStream(lexer);
    return new SubduceParser(tokenStream);
  }

  Expression parseProgram(String source) {
//    SubduceParser parser = getParser(source);
//    SubduceParser.ProgramContext ctx = parser.program();
//    return ctx.accept(new BasicParseTreeTranslator());
    CharStream stream = CharStreams.fromString(source);
    SubduceLexer lexer = new SubduceLexer(stream);
    TokenStream tokenStream = new CommonTokenStream(lexer);
    SubduceParser parser = new SubduceParser(tokenStream);
    SubduceParser.ProgramContext ctx = parser.program();
    Expression expression = ctx.accept(new BasicParseTreeTranslator());
    return expression;
  }

  Expression parseExpression(String expressionString) {
    SubduceParser parser = getParser(expressionString);
    SubduceParser.ExpressionContext ctx = parser.expression();
    return ctx.accept(new BasicParseTreeTranslator());
  }

  @Override
  public void run(String source) {
    runAndGetFinalEnvironment(source);
  }

  private Environment<String, Value> runAndGetFinalEnvironment(String source) {
    return runAndGetFinalEnvironment(baseEnvironment, source);
  }

  private Environment<String, Value> runAndGetFinalEnvironment(Environment<String, Value> environment, String source) {
    Expression program = parseProgram(source);
    ExpressionEvaluator evaluator = new ExpressionEvaluator(environment);
    DefinitionEvaluator runner = new DefinitionEvaluator(environment, evaluator);
    return program.accept(runner);
  }


  private Environment<String, Value> readEvalPrint(Environment<String, Value> environment, String line) {
    Expression expression = parseProgram(line);
    ExpressionEvaluator evaluator = new ExpressionEvaluator(environment);
    DefinitionEvaluator runner = new DefinitionEvaluator(environment, evaluator);
    System.out.println(evaluator.evaluate(expression));
    try {
      return expression.accept(runner);
    } catch(Exception e) {
      System.out.println(e.getMessage());
      return environment;
    }
  }

  @Override
  public void runWithInteraction(String source) {
    Environment<String, Value> environment = runAndGetFinalEnvironment(source);
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
      environment = readEvalPrint(environment, line);
      System.out.println();
      System.out.print("In: ");
    }
  }

  @Override
  public Value eval(String expressionString) {
    Expression expression = parseExpression(expressionString);
    return expression.accept(new ExpressionEvaluator(baseEnvironment));
  }
}

package language;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import language.grammar.SubduceLexer;
import language.grammar.SubduceParser;
import language.interpreter.BasicParseTreeTranslator;
import language.interpreter.ExpressionInterpreter;
import language.interpreter.Interpreter;
import language.interpreter.expression.Expression;
import language.interpreter.expression.value.Value;

public class Subduce {
  public static void main(String[] args) {
    String source =
            "x = 2\n" +
            "def (f a){\n" +
            "  b = 2\n" +
            "  return b\n" +
            "}\n" +
            "print x\n";
    source =
            "def (make-false bool) {\n" +
            "  return (if bool (make-false false) false)\n" +
            "}\n" +
            "print (make-false false)\n" +
            "print (make-false true)\n";
    source = "def (sum-range min max) { return (if (== min max) min (+ min (sum-range (+ 1 min) max)))} print (sum-range 0 100)";
    //    System.out.println(source);
    String exp = "2";
    CharStream stream = CharStreams.fromString(source);
    SubduceLexer lexer = new SubduceLexer(stream);
    TokenStream tokenStream = new CommonTokenStream(lexer);
    SubduceParser parser = new SubduceParser(tokenStream);
    SubduceParser.ProgramContext ctx = parser.program();
    Expression expression = ctx.accept(new BasicParseTreeTranslator());
    Interpreter<Value> interpreter = new ExpressionInterpreter();
    interpreter.run(source);
    System.out.println();
  }
}

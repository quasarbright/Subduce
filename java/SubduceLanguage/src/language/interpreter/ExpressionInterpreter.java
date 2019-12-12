package language.interpreter;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import java.util.Arrays;
import java.util.List;

import language.Subduce;
import language.grammar.SubduceLexer;
import language.grammar.SubduceParser;
import language.interpreter.expression.Expression;
import language.interpreter.expression.ExpressionVisitor;
import language.interpreter.expression.FunctionCallExpression;
import language.interpreter.expression.FunctionDefinitionExpression;
import language.interpreter.expression.LambdaDefinitionExpression;
import language.interpreter.expression.SequenceExpression;
import language.interpreter.expression.VariableAssignmentExpression;
import language.interpreter.expression.VariableReferenceExpression;
import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.Value;

public class ExpressionInterpreter implements Interpreter<Value> {
  private Environment<String, Value> baseEnvironment;

  public ExpressionInterpreter() {
     baseEnvironment = new ImmutableVariableEnvironment();
     // TODO add builtins like addition, cons, if, etc
  }

  private SubduceParser getParser(String source) {
    CharStream stream = CharStreams.fromString(source);
    SubduceLexer lexer = new SubduceLexer(stream);
    TokenStream tokenStream = new CommonTokenStream(lexer);
    return new SubduceParser(tokenStream);
  }

  private Expression parseProgram(String source) {
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

  private Expression parseExpression(String expressionString) {
    SubduceParser parser = getParser(expressionString);
    SubduceParser.ExpressionContext ctx = parser.expression();
    return ctx.accept(new BasicParseTreeTranslator());
  }

  @Override
  public void run(String source) {
    Expression program = parseProgram(source);
    // make it "evaluatable" by acting like it's a function body that returns 0
    program = program.accept(new ExpressionVisitor<Expression>() {
      private final Expression blank = new NumberValue(0);

      private SequenceExpression fromSingle(Expression expression) {
        return new SequenceExpression(Arrays.asList(expression, blank));
      }
      @Override
      public Expression visitFunctionCall(Expression function, List<Expression> arguments) {
        return fromSingle(new FunctionCallExpression(function, arguments));
      }

      @Override
      public Expression visitFunctionDefinition(String name, List<String> argnames, Expression body) {
        return fromSingle(new FunctionDefinitionExpression(name, argnames, body));
      }

      @Override
      public Expression visitLambdaDefinition(List<String> argnames, Expression body) {
        return fromSingle(new LambdaDefinitionExpression(argnames, body));
      }

      @Override
      public Expression visitSequence(List<Expression> expressions) {
        expressions.add(blank);
        return new SequenceExpression(expressions);
      }

      @Override
      public Expression visitVariableAssignment(String name, Expression expression) {
        return fromSingle(new VariableAssignmentExpression(name, expression));
      }

      @Override
      public Expression visitValue(Value value) {
        return fromSingle(value);
      }

      @Override
      public Expression visitVariableReference(String name) {
        return fromSingle(new VariableReferenceExpression(name));
      }
    });
    program.accept(new ExpressionEvaluator(baseEnvironment));
  }

  @Override
  public void runWithInteraction(String source) {

  }

  @Override
  public Value eval(String expressionString) {
    Expression expression = parseExpression(expressionString);
    return expression.accept(new ExpressionEvaluator(baseEnvironment));
  }
}

package language.interpreter;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import java.util.Optional;

import language.grammar.SubduceLexer;
import language.grammar.SubduceParser;
import language.interpreter.builtins.BuiltinUtilities;
import language.interpreter.expression.Expression;
import language.interpreter.expression.value.Value;

public class DefinitionRuntime implements Runtime<Expression, Expression, Value> {

  private Environment<String, Value> environment;

  public DefinitionRuntime() {
    environment = new BuiltinUtilities().getBaseEnvironment();
  }


  private SubduceParser getParser(String source) {
    CharStream stream = CharStreams.fromString(source);
    SubduceLexer lexer = new SubduceLexer(stream);
    TokenStream tokenStream = new CommonTokenStream(lexer);
    return new SubduceParser(tokenStream);
  }

  private Expression parseProgram(String source) {
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
  public Optional<Value> run(String source) {
    Expression program = parseProgram(source);
    return run(program);
  }

  @Override
  public Optional<Value> run(Expression statement) {
    ExpressionEvaluator evaluator = getEvaluator();
    Value ans = evaluator.evaluate(statement);
    DefinitionEvaluator runner = getRunner();
    environment = statement.accept(runner);
    return Optional.ofNullable(ans);
  }

  @Override
  public Value evaluate(String expressionString) {
    Expression expression = parseExpression(expressionString);
    return evaluate(expression);
  }

  @Override
  public Value evaluate(Expression expression) {
    return getEvaluator().evaluate(expression, environment);
  }

  private ExpressionEvaluator getEvaluator() {
    return new ExpressionEvaluator(environment);
  }

  private DefinitionEvaluator getRunner() {
    return new DefinitionEvaluator(environment, getEvaluator());
  }
}

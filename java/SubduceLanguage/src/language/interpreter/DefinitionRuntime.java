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
import language.interpreter.statement.BaseStatementVisitor;
import language.interpreter.statement.Statement;
import language.interpreter.statement.StatementVisitor;

public class DefinitionRuntime implements Runtime<Statement, Expression, Value> {

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

  private Statement parseProgram(String source) {
    CharStream stream = CharStreams.fromString(source);
    SubduceLexer lexer = new SubduceLexer(stream);
    TokenStream tokenStream = new CommonTokenStream(lexer);
    SubduceParser parser = new SubduceParser(tokenStream);
    SubduceParser.ProgramContext ctx = parser.program();
    Statement statement = ctx.accept(new ParseTreeToStatement());
    return statement;
  }

  private Expression parseExpression(String expressionString) {
    SubduceParser parser = getParser(expressionString);
    SubduceParser.ExpressionContext ctx = parser.expression();
    return ctx.accept(new ParseTreeToExpression());
  }


  @Override
  public Optional<Value> run(String source) {
    Statement program = parseProgram(source);
    return run(program);
  }

  @Override
  public Optional<Value> run(Statement statement) {
    ExpressionEvaluator evaluator = getEvaluator();
    DefinitionEvaluator runner = getRunner();

    // evaluate if expression statement
    Optional<Value> ans = statement.accept(new BaseStatementVisitor<>(Optional.empty()) {
      @Override
      public Optional<Value> visitExpression(Expression expression) {
        return Optional.of(evaluator.evaluate(expression));
      }
    });

    // only run statement if it's not an expression statement
    // otherwise, we'll evaluate twice
    if(ans.isEmpty()) {
      environment = statement.accept(runner);
    }

    return ans;
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

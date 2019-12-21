package language.interpreter;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import language.grammar.SubduceLexer;
import language.grammar.SubduceParser;
import language.grammar.SubduceParserBaseVisitor;
import language.interpreter.expression.Expression;
import language.interpreter.expression.FunctionCallExpression;
import language.interpreter.expression.LambdaExpression;
import language.interpreter.expression.VariableReferenceExpression;
import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.StringValue;
import language.interpreter.statement.Statement;

public class ParseTreeToExpression extends SubduceParserBaseVisitor<Expression> {
  @Override
  public Expression visitExpression(SubduceParser.ExpressionContext ctx) {
    return ctx.getChild(0).accept(this);
  }

  @Override
  public Expression visitString(SubduceParser.StringContext ctx) {
    List<SubduceParser.StringContentsContext> contentsContexts = ctx.stringContents();
    StringBuilder ans = new StringBuilder();
    for(SubduceParser.StringContentsContext ccctx : contentsContexts) {
      // expect a stringContents to have 1 terminal node child (the character, maybe escaped)
      if(ccctx.getChildCount() != 1) {
        throw new IllegalStateException();
      }
      char current = getCharacter(ccctx);
      ans.append(current);
    }
    return new StringValue(ans.toString());
  }

  private char getCharacter(SubduceParser.StringContentsContext ctx) {
    return ctx.getChild(0).accept(new SubduceParserBaseVisitor<Character>() {
      @Override public Character visitTerminal(TerminalNode node) {
        String text = node.getText();
        if(text.length() != 1 && text.length() != 2) {
          throw new IllegalStateException();
        }

        switch(node.getSymbol().getType()) {
          case SubduceLexer.NORMAL_CHARACTER:
            return text.charAt(0);
          case SubduceLexer.ESCAPED_CHARACTER:
            if(text.charAt(0) != '\\') {
              throw new IllegalStateException();
            } else {
              char escapedCharacter = text.charAt(1);
              Map<Character, Character> escapeMap = new HashMap<>();
              escapeMap.put('n', '\n');
              escapeMap.put('r', '\r');
              escapeMap.put('t', '\t');
              escapeMap.put('f', '\f');
              escapeMap.put('b', '\b');
              // for bogus escape, just return the character they're escaping
              // accounts for \"
              return escapeMap.getOrDefault(escapedCharacter, escapedCharacter);
            }
          default:
            throw new IllegalStateException();
        }
      }
    });
  }

  @Override
  public Expression visitStringContents(SubduceParser.StringContentsContext ctx) {
    throw new IllegalStateException();
  }

  @Override
  public Expression visitTerminal(TerminalNode node) {
    String text = node.getText();
    switch(node.getSymbol().getType()) {
      case SubduceLexer.NUMBER:
        return new NumberValue(Double.parseDouble(text));
      case SubduceLexer.BOOLEAN:
        return new BooleanValue(Boolean.parseBoolean(text));
      case SubduceLexer.IDENTIFIER:
        return new VariableReferenceExpression(text);
      default:
        throw new IllegalStateException();
    }
  }

  @Override
  public Expression visitLambdaDefinition(SubduceParser.LambdaDefinitionContext ctx) {
    Expression body = ctx.expression().accept(this);
    List<TerminalNode> terminalNodes = ctx.IDENTIFIER();
    List<String> argnames = terminalNodes.stream()
            .map(t -> t.getSymbol().getText())
            .collect(Collectors.toList());
    return new LambdaExpression(argnames, body);
  }

  @Override
  public Expression visitFunctionCall(SubduceParser.FunctionCallContext ctx) {
    Expression function = ctx.expression(0).accept(this);
    List<Expression> arguments = ctx.expression().subList(1,ctx.expression().size())
            .stream()
            .map((SubduceParser.ExpressionContext ectx) -> ectx.accept(this))
            .collect(Collectors.toList());
    return new FunctionCallExpression(function, arguments);
  }

  @Override
  protected Expression defaultResult() {
    throw new IllegalStateException("this translator shouldn't see anything else");
  }
}

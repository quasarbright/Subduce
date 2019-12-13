package language.interpreter;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import language.grammar.SubduceLexer;
import language.grammar.SubduceParser;
import language.grammar.SubduceParserBaseVisitor;
import language.interpreter.builtins.PrintFunction;
import language.interpreter.expression.Expression;
import language.interpreter.expression.FunctionCallExpression;
import language.interpreter.expression.FunctionDefinitionExpression;
import language.interpreter.expression.LambdaDefinitionExpression;
import language.interpreter.expression.SequenceExpression;
import language.interpreter.expression.VariableAssignmentExpression;
import language.interpreter.expression.VariableReferenceExpression;
import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.StringValue;
import language.interpreter.expression.value.functionValue.JavaFunctionValue;

public class BasicParseTreeTranslator extends SubduceParserBaseVisitor<Expression> implements ParseTreeTranslator {
  private static final JavaFunctionValue printFunctionValue = new JavaFunctionValue(new PrintFunction());
  @Override
  public Expression fromParseTree(SubduceParser.ProgramContext ctx) {
    return ctx.accept(this);
  }

  @Override
  public Expression visitProgram(SubduceParser.ProgramContext ctx) {
    if(ctx.getChildCount() == 0) {
      return new SequenceExpression(new ArrayList<>());
    }
    List<ParseTree> children = new ArrayList<>();
    for(int i = 0; i < ctx.getChildCount(); i++) {
      children.add(ctx.getChild(i));
    }

    List<Expression> expressions = children.stream()
            .map((ParseTree statementTree) -> statementTree.accept(this))
            .collect(Collectors.toList());
    return new SequenceExpression(expressions);
  }

  @Override
  public Expression visitStatement(SubduceParser.StatementContext ctx) {
    return ctx.getChild(0).accept(this);
  }

  @Override
  public Expression visitVariableAssignment(SubduceParser.VariableAssignmentContext ctx) {
    String name = ctx.IDENTIFIER().getSymbol().getText();
    SubduceParser.ExpressionContext expressionContext = ctx.expression();
    Expression expression = expressionContext.accept(this);
    return new VariableAssignmentExpression(name, expression);
  }

  @Override
  public Expression visitFunctionDefinition(SubduceParser.FunctionDefinitionContext ctx) {
    String name = ctx.IDENTIFIER(0).getSymbol().getText();
    List<TerminalNode> restTerms = ctx.IDENTIFIER().subList(1, ctx.IDENTIFIER().size());
    List<String> argnames = restTerms.stream()
            .map((TerminalNode term) -> term.getSymbol().getText())
            .collect(Collectors.toList());
    List<SubduceParser.StatementContext> statementContexts = ctx.statement();
    List<Expression> expressions = statementContexts.stream()
            .map((SubduceParser.StatementContext sctx) -> sctx.accept(this))
            .collect(Collectors.toList());
    SubduceParser.ReturnStatementContext returnStatementContext = ctx.returnStatement();
    expressions.add(returnStatementContext.accept(this));
    Expression body = new SequenceExpression(expressions);
    return new FunctionDefinitionExpression(name, argnames, body);
  }

  @Override
  public Expression visitReturnStatement(SubduceParser.ReturnStatementContext ctx) {
    return ctx.expression().accept(this);
  }

  @Override
  public Expression visitPrintStatement(SubduceParser.PrintStatementContext ctx) {
    Expression expression = ctx.expression().accept(this);
    Expression printCall = new FunctionCallExpression(printFunctionValue, new ArrayList<>(Collections.singletonList(expression)));
    return new VariableAssignmentExpression(VariableAssignmentExpression.DUMMY_NAME, printCall);
  }

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
    return new LambdaDefinitionExpression(argnames, body);
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
}

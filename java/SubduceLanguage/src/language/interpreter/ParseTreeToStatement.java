package language.interpreter;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import language.grammar.SubduceParser;
import language.grammar.SubduceParserBaseVisitor;
import language.interpreter.expression.Expression;
import language.interpreter.statement.ExpressionStatement;
import language.interpreter.statement.FunctionDefinitionStatement;
import language.interpreter.statement.PrintStatement;
import language.interpreter.statement.ReturnStatement;
import language.interpreter.statement.SequenceStatement;
import language.interpreter.statement.Statement;
import language.interpreter.statement.VariableDefinitionStatement;

public class ParseTreeToStatement extends SubduceParserBaseVisitor<Statement> {
  @Override
  public Statement visitProgram(SubduceParser.ProgramContext ctx) {
    if(ctx.getChildCount() == 0) {
      return new SequenceStatement(new ArrayList<>());
    }
    List<ParseTree> children = new ArrayList<>();
    for(int i = 0; i < ctx.getChildCount(); i++) {
      children.add(ctx.getChild(i));
    }

    List<Statement> statements = children.stream()
            .map((ParseTree statementTree) -> statementTree.accept(this))
            .collect(Collectors.toList());
    return new SequenceStatement(statements);
  }

  @Override
  public Statement visitStatement(SubduceParser.StatementContext ctx) {
    return ctx.getChild(0).accept(this);
  }

  @Override
  public Statement visitVariableAssignment(SubduceParser.VariableAssignmentContext ctx) {
    String name = ctx.IDENTIFIER().getSymbol().getText();
    SubduceParser.ExpressionContext expressionContext = ctx.expression();
    Expression expression = expressionContext.accept(new ParseTreeToExpression());
    return new VariableDefinitionStatement(name, expression);
  }

  @Override
  public Statement visitFunctionDefinition(SubduceParser.FunctionDefinitionContext ctx) {
    String name = ctx.IDENTIFIER(0).getSymbol().getText();
    List<TerminalNode> restTerms = ctx.IDENTIFIER().subList(1, ctx.IDENTIFIER().size());
    List<String> argnames = restTerms.stream()
            .map((TerminalNode term) -> term.getSymbol().getText())
            .collect(Collectors.toList());
    List<SubduceParser.StatementContext> statementContexts = ctx.statement();
    List<Statement> statements = statementContexts.stream()
            .map((SubduceParser.StatementContext sctx) -> sctx.accept(this))
            .collect(Collectors.toList());
    SubduceParser.ReturnStatementContext returnStatementContext = ctx.returnStatement();
    statements.add(returnStatementContext.accept(this));
    SequenceStatement body = new SequenceStatement(statements);
    return new FunctionDefinitionStatement(name, argnames, body);
  }

  @Override
  public Statement visitReturnStatement(SubduceParser.ReturnStatementContext ctx) {
    Expression expression = ctx.expression().accept(new ParseTreeToExpression());
    return new ReturnStatement(expression);
  }

  @Override
  public Statement visitPrintStatement(SubduceParser.PrintStatementContext ctx) {
    Expression expression = ctx.expression().accept(new ParseTreeToExpression());
    return new PrintStatement(expression);
  }

  @Override
  public Statement visitExpression(SubduceParser.ExpressionContext ctx) {
    return new ExpressionStatement(ctx.getChild(0).accept(new ParseTreeToExpression()));
  }

  @Override
  protected Statement defaultResult() {
    throw new IllegalStateException("this translator shouldn't see anything else");
  }
}

// Generated from /Users/mdelmonaco/Documents/GitHub/Subduce/java/SubduceLanguage/src/SubduceParser.g4 by ANTLR 4.7.2
package language.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SubduceParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SubduceParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SubduceParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(SubduceParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link SubduceParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(SubduceParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SubduceParser#variableAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAssignment(SubduceParser.VariableAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link SubduceParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(SubduceParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SubduceParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(SubduceParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SubduceParser#printStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStatement(SubduceParser.PrintStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SubduceParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(SubduceParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SubduceParser#lambdaDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaDefinition(SubduceParser.LambdaDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SubduceParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(SubduceParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link SubduceParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(SubduceParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by {@link SubduceParser#stringContents}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringContents(SubduceParser.StringContentsContext ctx);
}
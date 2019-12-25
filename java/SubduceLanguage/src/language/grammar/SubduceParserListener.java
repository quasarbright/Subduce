// Generated from /Users/mdelmonaco/Documents/GitHub/Subduce/java/SubduceLanguage/src/SubduceParser.g4 by ANTLR 4.7.2
package language.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SubduceParser}.
 */
public interface SubduceParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SubduceParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(SubduceParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubduceParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(SubduceParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubduceParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(SubduceParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubduceParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(SubduceParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubduceParser#variableAssignment}.
	 * @param ctx the parse tree
	 */
	void enterVariableAssignment(SubduceParser.VariableAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubduceParser#variableAssignment}.
	 * @param ctx the parse tree
	 */
	void exitVariableAssignment(SubduceParser.VariableAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubduceParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(SubduceParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubduceParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(SubduceParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubduceParser#structDefinition}.
	 * @param ctx the parse tree
	 */
	void enterStructDefinition(SubduceParser.StructDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubduceParser#structDefinition}.
	 * @param ctx the parse tree
	 */
	void exitStructDefinition(SubduceParser.StructDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubduceParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(SubduceParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubduceParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(SubduceParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubduceParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(SubduceParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubduceParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(SubduceParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubduceParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(SubduceParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubduceParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(SubduceParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubduceParser#lambdaDefinition}.
	 * @param ctx the parse tree
	 */
	void enterLambdaDefinition(SubduceParser.LambdaDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubduceParser#lambdaDefinition}.
	 * @param ctx the parse tree
	 */
	void exitLambdaDefinition(SubduceParser.LambdaDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubduceParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(SubduceParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubduceParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(SubduceParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubduceParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(SubduceParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubduceParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(SubduceParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link SubduceParser#stringContents}.
	 * @param ctx the parse tree
	 */
	void enterStringContents(SubduceParser.StringContentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SubduceParser#stringContents}.
	 * @param ctx the parse tree
	 */
	void exitStringContents(SubduceParser.StringContentsContext ctx);
}
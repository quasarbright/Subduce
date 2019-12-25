// Generated from /Users/mdelmonaco/Documents/GitHub/Subduce/java/SubduceLanguage/src/SubduceParser.g4 by ANTLR 4.7.2
package language.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SubduceParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		EQ=1, LPAREN=2, RPAREN=3, LBRACE=4, RBRACE=5, LAM=6, DEF=7, PRINT=8, RETURN=9, 
		DEFINE_STRUCT=10, NUMBER=11, BOOLEAN=12, IDENTIFIER=13, WS=14, QUOTE=15, 
		NORMAL_CHARACTER=16, ESCAPED_CHARACTER=17;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_variableAssignment = 2, RULE_functionDefinition = 3, 
		RULE_structDefinition = 4, RULE_returnStatement = 5, RULE_printStatement = 6, 
		RULE_expression = 7, RULE_lambdaDefinition = 8, RULE_functionCall = 9, 
		RULE_string = 10, RULE_stringContents = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "statement", "variableAssignment", "functionDefinition", "structDefinition", 
			"returnStatement", "printStatement", "expression", "lambdaDefinition", 
			"functionCall", "string", "stringContents"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'='", "'('", "')'", "'{'", "'}'", "'lam'", "'def'", "'print'", 
			"'return'", "'define-struct'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "EQ", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LAM", "DEF", "PRINT", 
			"RETURN", "DEFINE_STRUCT", "NUMBER", "BOOLEAN", "IDENTIFIER", "WS", "QUOTE", 
			"NORMAL_CHARACTER", "ESCAPED_CHARACTER"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SubduceParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SubduceParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SubduceParserVisitor ) return ((SubduceParserVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << DEF) | (1L << PRINT) | (1L << NUMBER) | (1L << BOOLEAN) | (1L << IDENTIFIER) | (1L << QUOTE))) != 0)) {
				{
				{
				setState(24);
				statement();
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public VariableAssignmentContext variableAssignment() {
			return getRuleContext(VariableAssignmentContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public StructDefinitionContext structDefinition() {
			return getRuleContext(StructDefinitionContext.class,0);
		}
		public PrintStatementContext printStatement() {
			return getRuleContext(PrintStatementContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SubduceParserVisitor ) return ((SubduceParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(35);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(30);
				variableAssignment();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(31);
				functionDefinition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(32);
				structDefinition();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(33);
				printStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(34);
				expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableAssignmentContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(SubduceParser.IDENTIFIER, 0); }
		public TerminalNode EQ() { return getToken(SubduceParser.EQ, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableAssignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).enterVariableAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).exitVariableAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SubduceParserVisitor ) return ((SubduceParserVisitor<? extends T>)visitor).visitVariableAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableAssignmentContext variableAssignment() throws RecognitionException {
		VariableAssignmentContext _localctx = new VariableAssignmentContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_variableAssignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(IDENTIFIER);
			setState(38);
			match(EQ);
			setState(39);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public TerminalNode DEF() { return getToken(SubduceParser.DEF, 0); }
		public TerminalNode LPAREN() { return getToken(SubduceParser.LPAREN, 0); }
		public List<TerminalNode> IDENTIFIER() { return getTokens(SubduceParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(SubduceParser.IDENTIFIER, i);
		}
		public TerminalNode RPAREN() { return getToken(SubduceParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(SubduceParser.LBRACE, 0); }
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(SubduceParser.RBRACE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).enterFunctionDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).exitFunctionDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SubduceParserVisitor ) return ((SubduceParserVisitor<? extends T>)visitor).visitFunctionDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_functionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			match(DEF);
			setState(42);
			match(LPAREN);
			setState(43);
			match(IDENTIFIER);
			setState(45); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(44);
				match(IDENTIFIER);
				}
				}
				setState(47); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IDENTIFIER );
			setState(49);
			match(RPAREN);
			setState(50);
			match(LBRACE);
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << DEF) | (1L << PRINT) | (1L << NUMBER) | (1L << BOOLEAN) | (1L << IDENTIFIER) | (1L << QUOTE))) != 0)) {
				{
				{
				setState(51);
				statement();
				}
				}
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(57);
			returnStatement();
			setState(58);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructDefinitionContext extends ParserRuleContext {
		public List<TerminalNode> LPAREN() { return getTokens(SubduceParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(SubduceParser.LPAREN, i);
		}
		public TerminalNode DEFINE_STRUCT() { return getToken(SubduceParser.DEFINE_STRUCT, 0); }
		public List<TerminalNode> IDENTIFIER() { return getTokens(SubduceParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(SubduceParser.IDENTIFIER, i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(SubduceParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(SubduceParser.RPAREN, i);
		}
		public StructDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).enterStructDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).exitStructDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SubduceParserVisitor ) return ((SubduceParserVisitor<? extends T>)visitor).visitStructDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructDefinitionContext structDefinition() throws RecognitionException {
		StructDefinitionContext _localctx = new StructDefinitionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_structDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(LPAREN);
			setState(61);
			match(DEFINE_STRUCT);
			setState(62);
			match(IDENTIFIER);
			setState(63);
			match(LPAREN);
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(64);
				match(IDENTIFIER);
				}
				}
				setState(69);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(70);
			match(RPAREN);
			setState(71);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(SubduceParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SubduceParserVisitor ) return ((SubduceParserVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(RETURN);
			setState(74);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrintStatementContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(SubduceParser.PRINT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PrintStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).enterPrintStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).exitPrintStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SubduceParserVisitor ) return ((SubduceParserVisitor<? extends T>)visitor).visitPrintStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintStatementContext printStatement() throws RecognitionException {
		PrintStatementContext _localctx = new PrintStatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_printStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(PRINT);
			setState(77);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public LambdaDefinitionContext lambdaDefinition() {
			return getRuleContext(LambdaDefinitionContext.class,0);
		}
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(SubduceParser.IDENTIFIER, 0); }
		public TerminalNode NUMBER() { return getToken(SubduceParser.NUMBER, 0); }
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode BOOLEAN() { return getToken(SubduceParser.BOOLEAN, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SubduceParserVisitor ) return ((SubduceParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_expression);
		try {
			setState(85);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(79);
				lambdaDefinition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(80);
				functionCall();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(81);
				match(IDENTIFIER);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(82);
				match(NUMBER);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(83);
				string();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(84);
				match(BOOLEAN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LambdaDefinitionContext extends ParserRuleContext {
		public List<TerminalNode> LPAREN() { return getTokens(SubduceParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(SubduceParser.LPAREN, i);
		}
		public TerminalNode LAM() { return getToken(SubduceParser.LAM, 0); }
		public List<TerminalNode> RPAREN() { return getTokens(SubduceParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(SubduceParser.RPAREN, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<TerminalNode> IDENTIFIER() { return getTokens(SubduceParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(SubduceParser.IDENTIFIER, i);
		}
		public LambdaDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).enterLambdaDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).exitLambdaDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SubduceParserVisitor ) return ((SubduceParserVisitor<? extends T>)visitor).visitLambdaDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdaDefinitionContext lambdaDefinition() throws RecognitionException {
		LambdaDefinitionContext _localctx = new LambdaDefinitionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_lambdaDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			match(LPAREN);
			setState(88);
			match(LAM);
			setState(89);
			match(LPAREN);
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(90);
				match(IDENTIFIER);
				}
				}
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(96);
			match(RPAREN);
			setState(97);
			expression();
			setState(98);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionCallContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(SubduceParser.LPAREN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(SubduceParser.RPAREN, 0); }
		public FunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).exitFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SubduceParserVisitor ) return ((SubduceParserVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionCallContext functionCall() throws RecognitionException {
		FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_functionCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			match(LPAREN);
			setState(101);
			expression();
			setState(103); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(102);
				expression();
				}
				}
				setState(105); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << NUMBER) | (1L << BOOLEAN) | (1L << IDENTIFIER) | (1L << QUOTE))) != 0) );
			setState(107);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public List<TerminalNode> QUOTE() { return getTokens(SubduceParser.QUOTE); }
		public TerminalNode QUOTE(int i) {
			return getToken(SubduceParser.QUOTE, i);
		}
		public List<StringContentsContext> stringContents() {
			return getRuleContexts(StringContentsContext.class);
		}
		public StringContentsContext stringContents(int i) {
			return getRuleContext(StringContentsContext.class,i);
		}
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).exitString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SubduceParserVisitor ) return ((SubduceParserVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_string);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(QUOTE);
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NORMAL_CHARACTER || _la==ESCAPED_CHARACTER) {
				{
				{
				setState(110);
				stringContents();
				}
				}
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(116);
			match(QUOTE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContentsContext extends ParserRuleContext {
		public TerminalNode NORMAL_CHARACTER() { return getToken(SubduceParser.NORMAL_CHARACTER, 0); }
		public TerminalNode ESCAPED_CHARACTER() { return getToken(SubduceParser.ESCAPED_CHARACTER, 0); }
		public StringContentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringContents; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).enterStringContents(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SubduceParserListener ) ((SubduceParserListener)listener).exitStringContents(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SubduceParserVisitor ) return ((SubduceParserVisitor<? extends T>)visitor).visitStringContents(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContentsContext stringContents() throws RecognitionException {
		StringContentsContext _localctx = new StringContentsContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_stringContents);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			_la = _input.LA(1);
			if ( !(_la==NORMAL_CHARACTER || _la==ESCAPED_CHARACTER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23{\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\3\2\7\2\34\n\2\f\2\16\2\37\13\2\3\3\3\3\3\3\3\3\3\3\5\3"+
		"&\n\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\6\5\60\n\5\r\5\16\5\61\3\5\3\5\3"+
		"\5\7\5\67\n\5\f\5\16\5:\13\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\7\6D\n\6"+
		"\f\6\16\6G\13\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\5\tX\n\t\3\n\3\n\3\n\3\n\7\n^\n\n\f\n\16\na\13\n\3\n\3\n\3\n\3"+
		"\n\3\13\3\13\3\13\6\13j\n\13\r\13\16\13k\3\13\3\13\3\f\3\f\7\fr\n\f\f"+
		"\f\16\fu\13\f\3\f\3\f\3\r\3\r\3\r\2\2\16\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\2\3\3\2\22\23\2~\2\35\3\2\2\2\4%\3\2\2\2\6\'\3\2\2\2\b+\3\2\2\2\n>\3"+
		"\2\2\2\fK\3\2\2\2\16N\3\2\2\2\20W\3\2\2\2\22Y\3\2\2\2\24f\3\2\2\2\26o"+
		"\3\2\2\2\30x\3\2\2\2\32\34\5\4\3\2\33\32\3\2\2\2\34\37\3\2\2\2\35\33\3"+
		"\2\2\2\35\36\3\2\2\2\36\3\3\2\2\2\37\35\3\2\2\2 &\5\6\4\2!&\5\b\5\2\""+
		"&\5\n\6\2#&\5\16\b\2$&\5\20\t\2% \3\2\2\2%!\3\2\2\2%\"\3\2\2\2%#\3\2\2"+
		"\2%$\3\2\2\2&\5\3\2\2\2\'(\7\17\2\2()\7\3\2\2)*\5\20\t\2*\7\3\2\2\2+,"+
		"\7\t\2\2,-\7\4\2\2-/\7\17\2\2.\60\7\17\2\2/.\3\2\2\2\60\61\3\2\2\2\61"+
		"/\3\2\2\2\61\62\3\2\2\2\62\63\3\2\2\2\63\64\7\5\2\2\648\7\6\2\2\65\67"+
		"\5\4\3\2\66\65\3\2\2\2\67:\3\2\2\28\66\3\2\2\289\3\2\2\29;\3\2\2\2:8\3"+
		"\2\2\2;<\5\f\7\2<=\7\7\2\2=\t\3\2\2\2>?\7\4\2\2?@\7\f\2\2@A\7\17\2\2A"+
		"E\7\4\2\2BD\7\17\2\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2FH\3\2\2\2"+
		"GE\3\2\2\2HI\7\5\2\2IJ\7\5\2\2J\13\3\2\2\2KL\7\13\2\2LM\5\20\t\2M\r\3"+
		"\2\2\2NO\7\n\2\2OP\5\20\t\2P\17\3\2\2\2QX\5\22\n\2RX\5\24\13\2SX\7\17"+
		"\2\2TX\7\r\2\2UX\5\26\f\2VX\7\16\2\2WQ\3\2\2\2WR\3\2\2\2WS\3\2\2\2WT\3"+
		"\2\2\2WU\3\2\2\2WV\3\2\2\2X\21\3\2\2\2YZ\7\4\2\2Z[\7\b\2\2[_\7\4\2\2\\"+
		"^\7\17\2\2]\\\3\2\2\2^a\3\2\2\2_]\3\2\2\2_`\3\2\2\2`b\3\2\2\2a_\3\2\2"+
		"\2bc\7\5\2\2cd\5\20\t\2de\7\5\2\2e\23\3\2\2\2fg\7\4\2\2gi\5\20\t\2hj\5"+
		"\20\t\2ih\3\2\2\2jk\3\2\2\2ki\3\2\2\2kl\3\2\2\2lm\3\2\2\2mn\7\5\2\2n\25"+
		"\3\2\2\2os\7\21\2\2pr\5\30\r\2qp\3\2\2\2ru\3\2\2\2sq\3\2\2\2st\3\2\2\2"+
		"tv\3\2\2\2us\3\2\2\2vw\7\21\2\2w\27\3\2\2\2xy\t\2\2\2y\31\3\2\2\2\13\35"+
		"%\618EW_ks";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
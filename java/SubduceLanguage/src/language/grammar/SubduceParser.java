// Generated from /Users/mdelmonaco/Documents/GitHub/Subduce/java/SubduceLanguage/src/Subduce.g4 by ANTLR 4.7.2
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
  static {
    RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
  }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache =
          new PredictionContextCache();
  public static final int
          T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, KEYWORD = 8, DEF = 9,
          PRINT = 10, RETURN = 11, IDENTIFIER = 12, NUMBER = 13, STRING = 14, BOOLEAN = 15;
  public static final int
          RULE_program = 0, RULE_statement = 1, RULE_variableAssignment = 2, RULE_functionDefinition = 3,
          RULE_returnStatement = 4, RULE_printStatement = 5, RULE_expression = 6,
          RULE_lambdaDefinition = 7, RULE_functionCall = 8, RULE_test = 9;

  private static String[] makeRuleNames() {
    return new String[]{
            "program", "statement", "variableAssignment", "functionDefinition", "returnStatement",
            "printStatement", "expression", "lambdaDefinition", "functionCall", "test"
    };
  }

  public static final String[] ruleNames = makeRuleNames();

  private static String[] makeLiteralNames() {
    return new String[]{
            null, "'='", "'('", "')'", "'{'", "'}'", "'lam'", "'t'", null, "'def'",
            "'print'", "'return'"
    };
  }

  private static final String[] _LITERAL_NAMES = makeLiteralNames();

  private static String[] makeSymbolicNames() {
    return new String[]{
            null, null, null, null, null, null, null, null, "KEYWORD", "DEF", "PRINT",
            "RETURN", "IDENTIFIER", "NUMBER", "STRING", "BOOLEAN"
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
  public String getGrammarFileName() {
    return "Subduce.g4";
  }

  @Override
  public String[] getRuleNames() {
    return ruleNames;
  }

  @Override
  public String getSerializedATN() {
    return _serializedATN;
  }

  @Override
  public ATN getATN() {
    return _ATN;
  }

  public SubduceParser(TokenStream input) {
    super(input);
    _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
  }

  public static class ProgramContext extends ParserRuleContext {
    public List<StatementContext> statement() {
      return getRuleContexts(StatementContext.class);
    }

    public StatementContext statement(int i) {
      return getRuleContext(StatementContext.class, i);
    }

    public ProgramContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_program;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener) ((SubduceListener) listener).enterProgram(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener) ((SubduceListener) listener).exitProgram(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SubduceVisitor)
        return ((SubduceVisitor<? extends T>) visitor).visitProgram(this);
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
        setState(23);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DEF) | (1L << PRINT) | (1L << IDENTIFIER))) != 0)) {
          {
            {
              setState(20);
              statement();
            }
          }
          setState(25);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class StatementContext extends ParserRuleContext {
    public VariableAssignmentContext variableAssignment() {
      return getRuleContext(VariableAssignmentContext.class, 0);
    }

    public FunctionDefinitionContext functionDefinition() {
      return getRuleContext(FunctionDefinitionContext.class, 0);
    }

    public PrintStatementContext printStatement() {
      return getRuleContext(PrintStatementContext.class, 0);
    }

    public StatementContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_statement;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener) ((SubduceListener) listener).enterStatement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener) ((SubduceListener) listener).exitStatement(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SubduceVisitor)
        return ((SubduceVisitor<? extends T>) visitor).visitStatement(this);
      else return visitor.visitChildren(this);
    }
  }

  public final StatementContext statement() throws RecognitionException {
    StatementContext _localctx = new StatementContext(_ctx, getState());
    enterRule(_localctx, 2, RULE_statement);
    try {
      setState(29);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
        case IDENTIFIER:
          enterOuterAlt(_localctx, 1);
        {
          setState(26);
          variableAssignment();
        }
        break;
        case DEF:
          enterOuterAlt(_localctx, 2);
        {
          setState(27);
          functionDefinition();
        }
        break;
        case PRINT:
          enterOuterAlt(_localctx, 3);
        {
          setState(28);
          printStatement();
        }
        break;
        default:
          throw new NoViableAltException(this);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class VariableAssignmentContext extends ParserRuleContext {
    public TerminalNode IDENTIFIER() {
      return getToken(SubduceParser.IDENTIFIER, 0);
    }

    public ExpressionContext expression() {
      return getRuleContext(ExpressionContext.class, 0);
    }

    public VariableAssignmentContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_variableAssignment;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener)
        ((SubduceListener) listener).enterVariableAssignment(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener)
        ((SubduceListener) listener).exitVariableAssignment(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SubduceVisitor)
        return ((SubduceVisitor<? extends T>) visitor).visitVariableAssignment(this);
      else return visitor.visitChildren(this);
    }
  }

  public final VariableAssignmentContext variableAssignment() throws RecognitionException {
    VariableAssignmentContext _localctx = new VariableAssignmentContext(_ctx, getState());
    enterRule(_localctx, 4, RULE_variableAssignment);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(31);
        match(IDENTIFIER);
        setState(32);
        match(T__0);
        setState(33);
        expression();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class FunctionDefinitionContext extends ParserRuleContext {
    public TerminalNode DEF() {
      return getToken(SubduceParser.DEF, 0);
    }

    public List<TerminalNode> IDENTIFIER() {
      return getTokens(SubduceParser.IDENTIFIER);
    }

    public TerminalNode IDENTIFIER(int i) {
      return getToken(SubduceParser.IDENTIFIER, i);
    }

    public ReturnStatementContext returnStatement() {
      return getRuleContext(ReturnStatementContext.class, 0);
    }

    public List<StatementContext> statement() {
      return getRuleContexts(StatementContext.class);
    }

    public StatementContext statement(int i) {
      return getRuleContext(StatementContext.class, i);
    }

    public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_functionDefinition;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener)
        ((SubduceListener) listener).enterFunctionDefinition(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener)
        ((SubduceListener) listener).exitFunctionDefinition(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SubduceVisitor)
        return ((SubduceVisitor<? extends T>) visitor).visitFunctionDefinition(this);
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
        setState(35);
        match(DEF);
        setState(36);
        match(T__1);
        setState(37);
        match(IDENTIFIER);
        setState(39);
        _errHandler.sync(this);
        _la = _input.LA(1);
        do {
          {
            {
              setState(38);
              match(IDENTIFIER);
            }
          }
          setState(41);
          _errHandler.sync(this);
          _la = _input.LA(1);
        } while (_la == IDENTIFIER);
        setState(43);
        match(T__2);
        setState(44);
        match(T__3);
        setState(48);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DEF) | (1L << PRINT) | (1L << IDENTIFIER))) != 0)) {
          {
            {
              setState(45);
              statement();
            }
          }
          setState(50);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(51);
        returnStatement();
        setState(52);
        match(T__4);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class ReturnStatementContext extends ParserRuleContext {
    public TerminalNode RETURN() {
      return getToken(SubduceParser.RETURN, 0);
    }

    public ExpressionContext expression() {
      return getRuleContext(ExpressionContext.class, 0);
    }

    public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_returnStatement;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener)
        ((SubduceListener) listener).enterReturnStatement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener)
        ((SubduceListener) listener).exitReturnStatement(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SubduceVisitor)
        return ((SubduceVisitor<? extends T>) visitor).visitReturnStatement(this);
      else return visitor.visitChildren(this);
    }
  }

  public final ReturnStatementContext returnStatement() throws RecognitionException {
    ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
    enterRule(_localctx, 8, RULE_returnStatement);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(54);
        match(RETURN);
        setState(55);
        expression();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class PrintStatementContext extends ParserRuleContext {
    public TerminalNode PRINT() {
      return getToken(SubduceParser.PRINT, 0);
    }

    public ExpressionContext expression() {
      return getRuleContext(ExpressionContext.class, 0);
    }

    public PrintStatementContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_printStatement;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener)
        ((SubduceListener) listener).enterPrintStatement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener)
        ((SubduceListener) listener).exitPrintStatement(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SubduceVisitor)
        return ((SubduceVisitor<? extends T>) visitor).visitPrintStatement(this);
      else return visitor.visitChildren(this);
    }
  }

  public final PrintStatementContext printStatement() throws RecognitionException {
    PrintStatementContext _localctx = new PrintStatementContext(_ctx, getState());
    enterRule(_localctx, 10, RULE_printStatement);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(57);
        match(PRINT);
        setState(58);
        expression();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class ExpressionContext extends ParserRuleContext {
    public LambdaDefinitionContext lambdaDefinition() {
      return getRuleContext(LambdaDefinitionContext.class, 0);
    }

    public FunctionCallContext functionCall() {
      return getRuleContext(FunctionCallContext.class, 0);
    }

    public TerminalNode IDENTIFIER() {
      return getToken(SubduceParser.IDENTIFIER, 0);
    }

    public TerminalNode NUMBER() {
      return getToken(SubduceParser.NUMBER, 0);
    }

    public TerminalNode STRING() {
      return getToken(SubduceParser.STRING, 0);
    }

    public TerminalNode BOOLEAN() {
      return getToken(SubduceParser.BOOLEAN, 0);
    }

    public ExpressionContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_expression;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener) ((SubduceListener) listener).enterExpression(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener) ((SubduceListener) listener).exitExpression(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SubduceVisitor)
        return ((SubduceVisitor<? extends T>) visitor).visitExpression(this);
      else return visitor.visitChildren(this);
    }
  }

  public final ExpressionContext expression() throws RecognitionException {
    ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
    enterRule(_localctx, 12, RULE_expression);
    try {
      setState(66);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 4, _ctx)) {
        case 1:
          enterOuterAlt(_localctx, 1);
        {
          setState(60);
          lambdaDefinition();
        }
        break;
        case 2:
          enterOuterAlt(_localctx, 2);
        {
          setState(61);
          functionCall();
        }
        break;
        case 3:
          enterOuterAlt(_localctx, 3);
        {
          setState(62);
          match(IDENTIFIER);
        }
        break;
        case 4:
          enterOuterAlt(_localctx, 4);
        {
          setState(63);
          match(NUMBER);
        }
        break;
        case 5:
          enterOuterAlt(_localctx, 5);
        {
          setState(64);
          match(STRING);
        }
        break;
        case 6:
          enterOuterAlt(_localctx, 6);
        {
          setState(65);
          match(BOOLEAN);
        }
        break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class LambdaDefinitionContext extends ParserRuleContext {
    public ExpressionContext expression() {
      return getRuleContext(ExpressionContext.class, 0);
    }

    public List<TerminalNode> IDENTIFIER() {
      return getTokens(SubduceParser.IDENTIFIER);
    }

    public TerminalNode IDENTIFIER(int i) {
      return getToken(SubduceParser.IDENTIFIER, i);
    }

    public LambdaDefinitionContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_lambdaDefinition;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener)
        ((SubduceListener) listener).enterLambdaDefinition(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener)
        ((SubduceListener) listener).exitLambdaDefinition(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SubduceVisitor)
        return ((SubduceVisitor<? extends T>) visitor).visitLambdaDefinition(this);
      else return visitor.visitChildren(this);
    }
  }

  public final LambdaDefinitionContext lambdaDefinition() throws RecognitionException {
    LambdaDefinitionContext _localctx = new LambdaDefinitionContext(_ctx, getState());
    enterRule(_localctx, 14, RULE_lambdaDefinition);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(68);
        match(T__1);
        setState(69);
        match(T__5);
        setState(70);
        match(T__1);
        setState(74);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == IDENTIFIER) {
          {
            {
              setState(71);
              match(IDENTIFIER);
            }
          }
          setState(76);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(77);
        match(T__2);
        setState(78);
        expression();
        setState(79);
        match(T__2);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class FunctionCallContext extends ParserRuleContext {
    public List<ExpressionContext> expression() {
      return getRuleContexts(ExpressionContext.class);
    }

    public ExpressionContext expression(int i) {
      return getRuleContext(ExpressionContext.class, i);
    }

    public FunctionCallContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_functionCall;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener) ((SubduceListener) listener).enterFunctionCall(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener) ((SubduceListener) listener).exitFunctionCall(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SubduceVisitor)
        return ((SubduceVisitor<? extends T>) visitor).visitFunctionCall(this);
      else return visitor.visitChildren(this);
    }
  }

  public final FunctionCallContext functionCall() throws RecognitionException {
    FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
    enterRule(_localctx, 16, RULE_functionCall);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(81);
        match(T__1);
        setState(82);
        expression();
        setState(84);
        _errHandler.sync(this);
        _la = _input.LA(1);
        do {
          {
            {
              setState(83);
              expression();
            }
          }
          setState(86);
          _errHandler.sync(this);
          _la = _input.LA(1);
        } while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << IDENTIFIER) | (1L << NUMBER) | (1L << STRING) | (1L << BOOLEAN))) != 0));
        setState(88);
        match(T__2);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class TestContext extends ParserRuleContext {
    public TestContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_test;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener) ((SubduceListener) listener).enterTest(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SubduceListener) ((SubduceListener) listener).exitTest(this);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof SubduceVisitor)
        return ((SubduceVisitor<? extends T>) visitor).visitTest(this);
      else return visitor.visitChildren(this);
    }
  }

  public final TestContext test() throws RecognitionException {
    TestContext _localctx = new TestContext(_ctx, getState());
    enterRule(_localctx, 18, RULE_test);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(90);
        match(T__6);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static final String _serializedATN =
          "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\21_\4\2\t\2\4\3\t" +
                  "\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\3" +
                  "\2\7\2\30\n\2\f\2\16\2\33\13\2\3\3\3\3\3\3\5\3 \n\3\3\4\3\4\3\4\3\4\3" +
                  "\5\3\5\3\5\3\5\6\5*\n\5\r\5\16\5+\3\5\3\5\3\5\7\5\61\n\5\f\5\16\5\64\13" +
                  "\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\5\bE\n" +
                  "\b\3\t\3\t\3\t\3\t\7\tK\n\t\f\t\16\tN\13\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n" +
                  "\6\nW\n\n\r\n\16\nX\3\n\3\n\3\13\3\13\3\13\2\2\f\2\4\6\b\n\f\16\20\22" +
                  "\24\2\2\2`\2\31\3\2\2\2\4\37\3\2\2\2\6!\3\2\2\2\b%\3\2\2\2\n8\3\2\2\2" +
                  "\f;\3\2\2\2\16D\3\2\2\2\20F\3\2\2\2\22S\3\2\2\2\24\\\3\2\2\2\26\30\5\4" +
                  "\3\2\27\26\3\2\2\2\30\33\3\2\2\2\31\27\3\2\2\2\31\32\3\2\2\2\32\3\3\2" +
                  "\2\2\33\31\3\2\2\2\34 \5\6\4\2\35 \5\b\5\2\36 \5\f\7\2\37\34\3\2\2\2\37" +
                  "\35\3\2\2\2\37\36\3\2\2\2 \5\3\2\2\2!\"\7\16\2\2\"#\7\3\2\2#$\5\16\b\2" +
                  "$\7\3\2\2\2%&\7\13\2\2&\'\7\4\2\2\')\7\16\2\2(*\7\16\2\2)(\3\2\2\2*+\3" +
                  "\2\2\2+)\3\2\2\2+,\3\2\2\2,-\3\2\2\2-.\7\5\2\2.\62\7\6\2\2/\61\5\4\3\2" +
                  "\60/\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2\62\63\3\2\2\2\63\65\3\2\2\2\64" +
                  "\62\3\2\2\2\65\66\5\n\6\2\66\67\7\7\2\2\67\t\3\2\2\289\7\r\2\29:\5\16" +
                  "\b\2:\13\3\2\2\2;<\7\f\2\2<=\5\16\b\2=\r\3\2\2\2>E\5\20\t\2?E\5\22\n\2" +
                  "@E\7\16\2\2AE\7\17\2\2BE\7\20\2\2CE\7\21\2\2D>\3\2\2\2D?\3\2\2\2D@\3\2" +
                  "\2\2DA\3\2\2\2DB\3\2\2\2DC\3\2\2\2E\17\3\2\2\2FG\7\4\2\2GH\7\b\2\2HL\7" +
                  "\4\2\2IK\7\16\2\2JI\3\2\2\2KN\3\2\2\2LJ\3\2\2\2LM\3\2\2\2MO\3\2\2\2NL" +
                  "\3\2\2\2OP\7\5\2\2PQ\5\16\b\2QR\7\5\2\2R\21\3\2\2\2ST\7\4\2\2TV\5\16\b" +
                  "\2UW\5\16\b\2VU\3\2\2\2WX\3\2\2\2XV\3\2\2\2XY\3\2\2\2YZ\3\2\2\2Z[\7\5" +
                  "\2\2[\23\3\2\2\2\\]\7\t\2\2]\25\3\2\2\2\t\31\37+\62DLX";
  public static final ATN _ATN =
          new ATNDeserializer().deserialize(_serializedATN.toCharArray());

  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}
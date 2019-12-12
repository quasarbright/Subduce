// Generated from /Users/mdelmonaco/Documents/GitHub/Subduce/java/SubduceLanguage/src/Subduce.g4 by ANTLR 4.7.2
package language.grammar;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SubduceLexer extends Lexer {
  static {
    RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
  }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache =
          new PredictionContextCache();
  public static final int
          T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, KEYWORD = 8, DEF = 9,
          PRINT = 10, RETURN = 11, IDENTIFIER = 12, NUMBER = 13, STRING = 14, BOOLEAN = 15;
  public static String[] channelNames = {
          "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
  };

  public static String[] modeNames = {
          "DEFAULT_MODE"
  };

  private static String[] makeRuleNames() {
    return new String[]{
            "T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "KEYWORD", "DEF",
            "PRINT", "RETURN", "IDENTIFIER", "NUMBER", "DIGIT", "STRING", "CHARACTER",
            "NORMAL_CHARACTER", "ESCAPED_CHARACTER", "BOOLEAN"
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


  public SubduceLexer(CharStream input) {
    super(input);
    _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
  public String[] getChannelNames() {
    return channelNames;
  }

  @Override
  public String[] getModeNames() {
    return modeNames;
  }

  @Override
  public ATN getATN() {
    return _ATN;
  }

  public static final String _serializedATN =
          "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\21\u0088\b\1\4\2" +
                  "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
                  "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
                  "\t\22\4\23\t\23\4\24\t\24\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7" +
                  "\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\5\t@\n\t\3\n\3\n\3\n\3\n" +
                  "\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\7\r" +
                  "U\n\r\f\r\16\rX\13\r\3\16\5\16[\n\16\3\16\6\16^\n\16\r\16\16\16_\3\16" +
                  "\3\16\6\16d\n\16\r\16\16\16e\5\16h\n\16\3\17\3\17\3\20\3\20\7\20n\n\20" +
                  "\f\20\16\20q\13\20\3\20\3\20\3\21\3\21\5\21w\n\21\3\22\3\22\3\23\3\23" +
                  "\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u0087\n\24\2\2" +
                  "\25\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35" +
                  "\2\37\20!\2#\2%\2\'\21\3\2\6\7\2-->AC\\aac|\t\2--//\62;>AC\\aac|\3\2\62" +
                  ";\5\2\f\f\17\17^^\2\u008e\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2" +
                  "\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2" +
                  "\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\37\3\2\2\2\2\'\3" +
                  "\2\2\2\3)\3\2\2\2\5+\3\2\2\2\7-\3\2\2\2\t/\3\2\2\2\13\61\3\2\2\2\r\63" +
                  "\3\2\2\2\17\67\3\2\2\2\21?\3\2\2\2\23A\3\2\2\2\25E\3\2\2\2\27K\3\2\2\2" +
                  "\31R\3\2\2\2\33Z\3\2\2\2\35i\3\2\2\2\37k\3\2\2\2!v\3\2\2\2#x\3\2\2\2%" +
                  "z\3\2\2\2\'\u0086\3\2\2\2)*\7?\2\2*\4\3\2\2\2+,\7*\2\2,\6\3\2\2\2-.\7" +
                  "+\2\2.\b\3\2\2\2/\60\7}\2\2\60\n\3\2\2\2\61\62\7\177\2\2\62\f\3\2\2\2" +
                  "\63\64\7n\2\2\64\65\7c\2\2\65\66\7o\2\2\66\16\3\2\2\2\678\7v\2\28\20\3" +
                  "\2\2\29@\5\23\n\2:@\5\25\13\2;@\5\27\f\2<=\7n\2\2=>\7c\2\2>@\7o\2\2?9" +
                  "\3\2\2\2?:\3\2\2\2?;\3\2\2\2?<\3\2\2\2@\22\3\2\2\2AB\7f\2\2BC\7g\2\2C" +
                  "D\7h\2\2D\24\3\2\2\2EF\7r\2\2FG\7t\2\2GH\7k\2\2HI\7p\2\2IJ\7v\2\2J\26" +
                  "\3\2\2\2KL\7t\2\2LM\7g\2\2MN\7v\2\2NO\7w\2\2OP\7t\2\2PQ\7p\2\2Q\30\3\2" +
                  "\2\2RV\t\2\2\2SU\t\3\2\2TS\3\2\2\2UX\3\2\2\2VT\3\2\2\2VW\3\2\2\2W\32\3" +
                  "\2\2\2XV\3\2\2\2Y[\7/\2\2ZY\3\2\2\2Z[\3\2\2\2[]\3\2\2\2\\^\5\35\17\2]" +
                  "\\\3\2\2\2^_\3\2\2\2_]\3\2\2\2_`\3\2\2\2`g\3\2\2\2ac\7\60\2\2bd\5\35\17" +
                  "\2cb\3\2\2\2de\3\2\2\2ec\3\2\2\2ef\3\2\2\2fh\3\2\2\2ga\3\2\2\2gh\3\2\2" +
                  "\2h\34\3\2\2\2ij\t\4\2\2j\36\3\2\2\2ko\7$\2\2ln\5!\21\2ml\3\2\2\2nq\3" +
                  "\2\2\2om\3\2\2\2op\3\2\2\2pr\3\2\2\2qo\3\2\2\2rs\7$\2\2s \3\2\2\2tw\5" +
                  "#\22\2uw\5%\23\2vt\3\2\2\2vu\3\2\2\2w\"\3\2\2\2xy\n\5\2\2y$\3\2\2\2z{" +
                  "\7^\2\2{|\13\2\2\2|&\3\2\2\2}~\7v\2\2~\177\7t\2\2\177\u0080\7w\2\2\u0080" +
                  "\u0087\7g\2\2\u0081\u0082\7h\2\2\u0082\u0083\7c\2\2\u0083\u0084\7n\2\2" +
                  "\u0084\u0085\7u\2\2\u0085\u0087\7g\2\2\u0086}\3\2\2\2\u0086\u0081\3\2" +
                  "\2\2\u0087(\3\2\2\2\f\2?VZ_egov\u0086\2";
  public static final ATN _ATN =
          new ATNDeserializer().deserialize(_serializedATN.toCharArray());

  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}
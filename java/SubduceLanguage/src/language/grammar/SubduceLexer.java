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
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, LAM=6, DEF=7, PRINT=8, RETURN=9, 
		NUMBER=10, STRING=11, BOOLEAN=12, IDENTIFIER=13, WS=14;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "LAM", "DEF", "PRINT", "RETURN", 
			"NUMBER", "DIGIT", "STRING", "CHARACTER", "NORMAL_CHARACTER", "ESCAPED_CHARACTER", 
			"BOOLEAN", "IDENTIFIER", "IDENTIFIER_START_CHARACTER", "IDENTIFIER_REST_CHARACTER", 
			"WS", "COMMENT", "MULTILINE_COMMENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'='", "'('", "')'", "'{'", "'}'", "'lam'", "'def'", "'print'", 
			"'return'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "LAM", "DEF", "PRINT", "RETURN", 
			"NUMBER", "STRING", "BOOLEAN", "IDENTIFIER", "WS"
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
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Subduce.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\20\u00ac\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\3\3"+
		"\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\5\13P\n\13\3\13\6\13S\n"+
		"\13\r\13\16\13T\3\13\3\13\6\13Y\n\13\r\13\16\13Z\5\13]\n\13\3\f\3\f\3"+
		"\r\3\r\7\rc\n\r\f\r\16\rf\13\r\3\r\3\r\3\16\3\16\5\16l\n\16\3\17\3\17"+
		"\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21|\n\21"+
		"\3\22\3\22\7\22\u0080\n\22\f\22\16\22\u0083\13\22\3\23\3\23\3\24\3\24"+
		"\5\24\u0089\n\24\3\25\6\25\u008c\n\25\r\25\16\25\u008d\3\25\3\25\5\25"+
		"\u0092\n\25\3\25\3\25\3\26\3\26\3\26\3\26\7\26\u009a\n\26\f\26\16\26\u009d"+
		"\13\26\3\26\3\26\3\27\3\27\3\27\3\27\7\27\u00a5\n\27\f\27\16\27\u00a8"+
		"\13\27\3\27\3\27\3\27\4\u009b\u00a6\2\30\3\3\5\4\7\5\t\6\13\7\r\b\17\t"+
		"\21\n\23\13\25\f\27\2\31\r\33\2\35\2\37\2!\16#\17%\2\'\2)\20+\2-\2\3\2"+
		"\7\3\2\62;\6\2\f\f\17\17$$^^\4\2\f\f\17\17\b\2,-//>AC\\aac|\5\2\13\f\17"+
		"\17\"\"\2\u00b1\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2"+
		"\2\31\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2)\3\2\2\2\3/\3\2\2\2\5\61\3\2\2\2"+
		"\7\63\3\2\2\2\t\65\3\2\2\2\13\67\3\2\2\2\r9\3\2\2\2\17=\3\2\2\2\21A\3"+
		"\2\2\2\23G\3\2\2\2\25O\3\2\2\2\27^\3\2\2\2\31`\3\2\2\2\33k\3\2\2\2\35"+
		"m\3\2\2\2\37o\3\2\2\2!{\3\2\2\2#}\3\2\2\2%\u0084\3\2\2\2\'\u0088\3\2\2"+
		"\2)\u0091\3\2\2\2+\u0095\3\2\2\2-\u00a0\3\2\2\2/\60\7?\2\2\60\4\3\2\2"+
		"\2\61\62\7*\2\2\62\6\3\2\2\2\63\64\7+\2\2\64\b\3\2\2\2\65\66\7}\2\2\66"+
		"\n\3\2\2\2\678\7\177\2\28\f\3\2\2\29:\7n\2\2:;\7c\2\2;<\7o\2\2<\16\3\2"+
		"\2\2=>\7f\2\2>?\7g\2\2?@\7h\2\2@\20\3\2\2\2AB\7r\2\2BC\7t\2\2CD\7k\2\2"+
		"DE\7p\2\2EF\7v\2\2F\22\3\2\2\2GH\7t\2\2HI\7g\2\2IJ\7v\2\2JK\7w\2\2KL\7"+
		"t\2\2LM\7p\2\2M\24\3\2\2\2NP\7/\2\2ON\3\2\2\2OP\3\2\2\2PR\3\2\2\2QS\5"+
		"\27\f\2RQ\3\2\2\2ST\3\2\2\2TR\3\2\2\2TU\3\2\2\2U\\\3\2\2\2VX\7\60\2\2"+
		"WY\5\27\f\2XW\3\2\2\2YZ\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[]\3\2\2\2\\V\3\2\2"+
		"\2\\]\3\2\2\2]\26\3\2\2\2^_\t\2\2\2_\30\3\2\2\2`d\7$\2\2ac\5\33\16\2b"+
		"a\3\2\2\2cf\3\2\2\2db\3\2\2\2de\3\2\2\2eg\3\2\2\2fd\3\2\2\2gh\7$\2\2h"+
		"\32\3\2\2\2il\5\35\17\2jl\5\37\20\2ki\3\2\2\2kj\3\2\2\2l\34\3\2\2\2mn"+
		"\n\3\2\2n\36\3\2\2\2op\7^\2\2pq\n\4\2\2q \3\2\2\2rs\7v\2\2st\7t\2\2tu"+
		"\7w\2\2u|\7g\2\2vw\7h\2\2wx\7c\2\2xy\7n\2\2yz\7u\2\2z|\7g\2\2{r\3\2\2"+
		"\2{v\3\2\2\2|\"\3\2\2\2}\u0081\5%\23\2~\u0080\5\'\24\2\177~\3\2\2\2\u0080"+
		"\u0083\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082$\3\2\2\2\u0083"+
		"\u0081\3\2\2\2\u0084\u0085\t\5\2\2\u0085&\3\2\2\2\u0086\u0089\5%\23\2"+
		"\u0087\u0089\t\2\2\2\u0088\u0086\3\2\2\2\u0088\u0087\3\2\2\2\u0089(\3"+
		"\2\2\2\u008a\u008c\t\6\2\2\u008b\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d"+
		"\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u0092\3\2\2\2\u008f\u0092\5+"+
		"\26\2\u0090\u0092\5-\27\2\u0091\u008b\3\2\2\2\u0091\u008f\3\2\2\2\u0091"+
		"\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0094\b\25\2\2\u0094*\3\2\2\2"+
		"\u0095\u0096\7\61\2\2\u0096\u0097\7\61\2\2\u0097\u009b\3\2\2\2\u0098\u009a"+
		"\n\4\2\2\u0099\u0098\3\2\2\2\u009a\u009d\3\2\2\2\u009b\u009c\3\2\2\2\u009b"+
		"\u0099\3\2\2\2\u009c\u009e\3\2\2\2\u009d\u009b\3\2\2\2\u009e\u009f\7\f"+
		"\2\2\u009f,\3\2\2\2\u00a0\u00a1\7\61\2\2\u00a1\u00a2\7,\2\2\u00a2\u00a6"+
		"\3\2\2\2\u00a3\u00a5\13\2\2\2\u00a4\u00a3\3\2\2\2\u00a5\u00a8\3\2\2\2"+
		"\u00a6\u00a7\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a7\u00a9\3\2\2\2\u00a8\u00a6"+
		"\3\2\2\2\u00a9\u00aa\7,\2\2\u00aa\u00ab\7\61\2\2\u00ab.\3\2\2\2\20\2O"+
		"TZ\\dk{\u0081\u0088\u008d\u0091\u009b\u00a6\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
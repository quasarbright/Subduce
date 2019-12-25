// Generated from /Users/mdelmonaco/Documents/GitHub/Subduce/java/SubduceLanguage/src/SubduceLexer.g4 by ANTLR 4.7.2
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
		EQ=1, LPAREN=2, RPAREN=3, LBRACE=4, RBRACE=5, LAM=6, DEF=7, PRINT=8, RETURN=9, 
		DEFINE_STRUCT=10, NUMBER=11, BOOLEAN=12, IDENTIFIER=13, WS=14, QUOTE=15, 
		NORMAL_CHARACTER=16, ESCAPED_CHARACTER=17;
	public static final int
		IN_STRING=1;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "IN_STRING"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"EQ", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LAM", "DEF", "PRINT", 
			"RETURN", "DEFINE_STRUCT", "NUMBER", "DIGIT", "BOOLEAN", "IDENTIFIER", 
			"IDENTIFIER_START_CHARACTER", "IDENTIFIER_REST_CHARACTER", "WS", "WS_C", 
			"COMMENT", "MULTILINE_COMMENT", "QUOTE", "NORMAL_CHARACTER", "ESCAPED_CHARACTER", 
			"QUOTE_IN_STRING"
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


	public SubduceLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SubduceLexer.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23\u00bd\b\1\b\1"+
		"\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t"+
		"\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4"+
		"\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4"+
		"\31\t\31\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3"+
		"\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\5"+
		"\fc\n\f\3\f\6\ff\n\f\r\f\16\fg\3\f\3\f\6\fl\n\f\r\f\16\fm\5\fp\n\f\3\r"+
		"\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16}\n\16\3\17\3\17"+
		"\7\17\u0081\n\17\f\17\16\17\u0084\13\17\3\20\3\20\3\21\3\21\5\21\u008a"+
		"\n\21\3\22\6\22\u008d\n\22\r\22\16\22\u008e\3\22\3\22\5\22\u0093\n\22"+
		"\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\24\7\24\u009d\n\24\f\24\16\24\u00a0"+
		"\13\24\3\24\3\24\3\25\3\25\3\25\3\25\7\25\u00a8\n\25\f\25\16\25\u00ab"+
		"\13\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\31"+
		"\3\31\3\31\3\31\3\31\4\u009e\u00a9\2\32\4\3\6\4\b\5\n\6\f\7\16\b\20\t"+
		"\22\n\24\13\26\f\30\r\32\2\34\16\36\17 \2\"\2$\20&\2(\2*\2,\21.\22\60"+
		"\23\62\2\4\2\3\7\3\2\62;\t\2##,-//>AC\\aac|\5\2\13\f\17\17\"\"\4\2\f\f"+
		"\17\17\6\2\f\f\17\17$$^^\2\u00c1\2\4\3\2\2\2\2\6\3\2\2\2\2\b\3\2\2\2\2"+
		"\n\3\2\2\2\2\f\3\2\2\2\2\16\3\2\2\2\2\20\3\2\2\2\2\22\3\2\2\2\2\24\3\2"+
		"\2\2\2\26\3\2\2\2\2\30\3\2\2\2\2\34\3\2\2\2\2\36\3\2\2\2\2$\3\2\2\2\2"+
		",\3\2\2\2\3.\3\2\2\2\3\60\3\2\2\2\3\62\3\2\2\2\4\64\3\2\2\2\6\66\3\2\2"+
		"\2\b8\3\2\2\2\n:\3\2\2\2\f<\3\2\2\2\16>\3\2\2\2\20B\3\2\2\2\22F\3\2\2"+
		"\2\24L\3\2\2\2\26S\3\2\2\2\30b\3\2\2\2\32q\3\2\2\2\34|\3\2\2\2\36~\3\2"+
		"\2\2 \u0085\3\2\2\2\"\u0089\3\2\2\2$\u0092\3\2\2\2&\u0096\3\2\2\2(\u0098"+
		"\3\2\2\2*\u00a3\3\2\2\2,\u00af\3\2\2\2.\u00b3\3\2\2\2\60\u00b5\3\2\2\2"+
		"\62\u00b8\3\2\2\2\64\65\7?\2\2\65\5\3\2\2\2\66\67\7*\2\2\67\7\3\2\2\2"+
		"89\7+\2\29\t\3\2\2\2:;\7}\2\2;\13\3\2\2\2<=\7\177\2\2=\r\3\2\2\2>?\7n"+
		"\2\2?@\7c\2\2@A\7o\2\2A\17\3\2\2\2BC\7f\2\2CD\7g\2\2DE\7h\2\2E\21\3\2"+
		"\2\2FG\7r\2\2GH\7t\2\2HI\7k\2\2IJ\7p\2\2JK\7v\2\2K\23\3\2\2\2LM\7t\2\2"+
		"MN\7g\2\2NO\7v\2\2OP\7w\2\2PQ\7t\2\2QR\7p\2\2R\25\3\2\2\2ST\7f\2\2TU\7"+
		"g\2\2UV\7h\2\2VW\7k\2\2WX\7p\2\2XY\7g\2\2YZ\7/\2\2Z[\7u\2\2[\\\7v\2\2"+
		"\\]\7t\2\2]^\7w\2\2^_\7e\2\2_`\7v\2\2`\27\3\2\2\2ac\7/\2\2ba\3\2\2\2b"+
		"c\3\2\2\2ce\3\2\2\2df\5\32\r\2ed\3\2\2\2fg\3\2\2\2ge\3\2\2\2gh\3\2\2\2"+
		"ho\3\2\2\2ik\7\60\2\2jl\5\32\r\2kj\3\2\2\2lm\3\2\2\2mk\3\2\2\2mn\3\2\2"+
		"\2np\3\2\2\2oi\3\2\2\2op\3\2\2\2p\31\3\2\2\2qr\t\2\2\2r\33\3\2\2\2st\7"+
		"v\2\2tu\7t\2\2uv\7w\2\2v}\7g\2\2wx\7h\2\2xy\7c\2\2yz\7n\2\2z{\7u\2\2{"+
		"}\7g\2\2|s\3\2\2\2|w\3\2\2\2}\35\3\2\2\2~\u0082\5 \20\2\177\u0081\5\""+
		"\21\2\u0080\177\3\2\2\2\u0081\u0084\3\2\2\2\u0082\u0080\3\2\2\2\u0082"+
		"\u0083\3\2\2\2\u0083\37\3\2\2\2\u0084\u0082\3\2\2\2\u0085\u0086\t\3\2"+
		"\2\u0086!\3\2\2\2\u0087\u008a\5 \20\2\u0088\u008a\t\2\2\2\u0089\u0087"+
		"\3\2\2\2\u0089\u0088\3\2\2\2\u008a#\3\2\2\2\u008b\u008d\5&\23\2\u008c"+
		"\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u008c\3\2\2\2\u008e\u008f\3\2"+
		"\2\2\u008f\u0093\3\2\2\2\u0090\u0093\5(\24\2\u0091\u0093\5*\25\2\u0092"+
		"\u008c\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0091\3\2\2\2\u0093\u0094\3\2"+
		"\2\2\u0094\u0095\b\22\2\2\u0095%\3\2\2\2\u0096\u0097\t\4\2\2\u0097\'\3"+
		"\2\2\2\u0098\u0099\7\61\2\2\u0099\u009a\7\61\2\2\u009a\u009e\3\2\2\2\u009b"+
		"\u009d\n\5\2\2\u009c\u009b\3\2\2\2\u009d\u00a0\3\2\2\2\u009e\u009f\3\2"+
		"\2\2\u009e\u009c\3\2\2\2\u009f\u00a1\3\2\2\2\u00a0\u009e\3\2\2\2\u00a1"+
		"\u00a2\7\f\2\2\u00a2)\3\2\2\2\u00a3\u00a4\7\61\2\2\u00a4\u00a5\7,\2\2"+
		"\u00a5\u00a9\3\2\2\2\u00a6\u00a8\13\2\2\2\u00a7\u00a6\3\2\2\2\u00a8\u00ab"+
		"\3\2\2\2\u00a9\u00aa\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00ac\3\2\2\2\u00ab"+
		"\u00a9\3\2\2\2\u00ac\u00ad\7,\2\2\u00ad\u00ae\7\61\2\2\u00ae+\3\2\2\2"+
		"\u00af\u00b0\7$\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2\b\26\3\2\u00b2-\3"+
		"\2\2\2\u00b3\u00b4\n\6\2\2\u00b4/\3\2\2\2\u00b5\u00b6\7^\2\2\u00b6\u00b7"+
		"\n\5\2\2\u00b7\61\3\2\2\2\u00b8\u00b9\7$\2\2\u00b9\u00ba\3\2\2\2\u00ba"+
		"\u00bb\b\31\4\2\u00bb\u00bc\b\31\5\2\u00bc\63\3\2\2\2\17\2\3bgmo|\u0082"+
		"\u0089\u008e\u0092\u009e\u00a9\6\b\2\2\7\3\2\t\21\2\6\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
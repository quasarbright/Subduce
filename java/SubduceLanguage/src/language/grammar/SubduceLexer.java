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
		NUMBER=10, BOOLEAN=11, IDENTIFIER=12, WS=13, QUOTE=14, NORMAL_CHARACTER=15, 
		ESCAPED_CHARACTER=16;
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
			"RETURN", "NUMBER", "DIGIT", "BOOLEAN", "IDENTIFIER", "IDENTIFIER_START_CHARACTER", 
			"IDENTIFIER_REST_CHARACTER", "WS", "WS_C", "COMMENT", "MULTILINE_COMMENT", 
			"QUOTE", "NORMAL_CHARACTER", "ESCAPED_CHARACTER", "QUOTE_IN_STRING"
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
			null, "EQ", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LAM", "DEF", "PRINT", 
			"RETURN", "NUMBER", "BOOLEAN", "IDENTIFIER", "WS", "QUOTE", "NORMAL_CHARACTER", 
			"ESCAPED_CHARACTER"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\22\u00ad\b\1\b\1"+
		"\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t"+
		"\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4"+
		"\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3"+
		"\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\5\13S\n\13\3"+
		"\13\6\13V\n\13\r\13\16\13W\3\13\3\13\6\13\\\n\13\r\13\16\13]\5\13`\n\13"+
		"\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\rm\n\r\3\16\3\16\7\16q"+
		"\n\16\f\16\16\16t\13\16\3\17\3\17\3\20\3\20\5\20z\n\20\3\21\6\21}\n\21"+
		"\r\21\16\21~\3\21\3\21\5\21\u0083\n\21\3\21\3\21\3\22\3\22\3\23\3\23\3"+
		"\23\3\23\7\23\u008d\n\23\f\23\16\23\u0090\13\23\3\23\3\23\3\24\3\24\3"+
		"\24\3\24\7\24\u0098\n\24\f\24\16\24\u009b\13\24\3\24\3\24\3\24\3\25\3"+
		"\25\3\25\3\25\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\4\u008e"+
		"\u0099\2\31\4\3\6\4\b\5\n\6\f\7\16\b\20\t\22\n\24\13\26\f\30\2\32\r\34"+
		"\16\36\2 \2\"\17$\2&\2(\2*\20,\21.\22\60\2\4\2\3\7\3\2\62;\b\2,-//>AC"+
		"\\aac|\5\2\13\f\17\17\"\"\4\2\f\f\17\17\6\2\f\f\17\17$$^^\2\u00b1\2\4"+
		"\3\2\2\2\2\6\3\2\2\2\2\b\3\2\2\2\2\n\3\2\2\2\2\f\3\2\2\2\2\16\3\2\2\2"+
		"\2\20\3\2\2\2\2\22\3\2\2\2\2\24\3\2\2\2\2\26\3\2\2\2\2\32\3\2\2\2\2\34"+
		"\3\2\2\2\2\"\3\2\2\2\2*\3\2\2\2\3,\3\2\2\2\3.\3\2\2\2\3\60\3\2\2\2\4\62"+
		"\3\2\2\2\6\64\3\2\2\2\b\66\3\2\2\2\n8\3\2\2\2\f:\3\2\2\2\16<\3\2\2\2\20"+
		"@\3\2\2\2\22D\3\2\2\2\24J\3\2\2\2\26R\3\2\2\2\30a\3\2\2\2\32l\3\2\2\2"+
		"\34n\3\2\2\2\36u\3\2\2\2 y\3\2\2\2\"\u0082\3\2\2\2$\u0086\3\2\2\2&\u0088"+
		"\3\2\2\2(\u0093\3\2\2\2*\u009f\3\2\2\2,\u00a3\3\2\2\2.\u00a5\3\2\2\2\60"+
		"\u00a8\3\2\2\2\62\63\7?\2\2\63\5\3\2\2\2\64\65\7*\2\2\65\7\3\2\2\2\66"+
		"\67\7+\2\2\67\t\3\2\2\289\7}\2\29\13\3\2\2\2:;\7\177\2\2;\r\3\2\2\2<="+
		"\7n\2\2=>\7c\2\2>?\7o\2\2?\17\3\2\2\2@A\7f\2\2AB\7g\2\2BC\7h\2\2C\21\3"+
		"\2\2\2DE\7r\2\2EF\7t\2\2FG\7k\2\2GH\7p\2\2HI\7v\2\2I\23\3\2\2\2JK\7t\2"+
		"\2KL\7g\2\2LM\7v\2\2MN\7w\2\2NO\7t\2\2OP\7p\2\2P\25\3\2\2\2QS\7/\2\2R"+
		"Q\3\2\2\2RS\3\2\2\2SU\3\2\2\2TV\5\30\f\2UT\3\2\2\2VW\3\2\2\2WU\3\2\2\2"+
		"WX\3\2\2\2X_\3\2\2\2Y[\7\60\2\2Z\\\5\30\f\2[Z\3\2\2\2\\]\3\2\2\2][\3\2"+
		"\2\2]^\3\2\2\2^`\3\2\2\2_Y\3\2\2\2_`\3\2\2\2`\27\3\2\2\2ab\t\2\2\2b\31"+
		"\3\2\2\2cd\7v\2\2de\7t\2\2ef\7w\2\2fm\7g\2\2gh\7h\2\2hi\7c\2\2ij\7n\2"+
		"\2jk\7u\2\2km\7g\2\2lc\3\2\2\2lg\3\2\2\2m\33\3\2\2\2nr\5\36\17\2oq\5 "+
		"\20\2po\3\2\2\2qt\3\2\2\2rp\3\2\2\2rs\3\2\2\2s\35\3\2\2\2tr\3\2\2\2uv"+
		"\t\3\2\2v\37\3\2\2\2wz\5\36\17\2xz\t\2\2\2yw\3\2\2\2yx\3\2\2\2z!\3\2\2"+
		"\2{}\5$\22\2|{\3\2\2\2}~\3\2\2\2~|\3\2\2\2~\177\3\2\2\2\177\u0083\3\2"+
		"\2\2\u0080\u0083\5&\23\2\u0081\u0083\5(\24\2\u0082|\3\2\2\2\u0082\u0080"+
		"\3\2\2\2\u0082\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085\b\21\2\2"+
		"\u0085#\3\2\2\2\u0086\u0087\t\4\2\2\u0087%\3\2\2\2\u0088\u0089\7\61\2"+
		"\2\u0089\u008a\7\61\2\2\u008a\u008e\3\2\2\2\u008b\u008d\n\5\2\2\u008c"+
		"\u008b\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008f\3\2\2\2\u008e\u008c\3\2"+
		"\2\2\u008f\u0091\3\2\2\2\u0090\u008e\3\2\2\2\u0091\u0092\7\f\2\2\u0092"+
		"\'\3\2\2\2\u0093\u0094\7\61\2\2\u0094\u0095\7,\2\2\u0095\u0099\3\2\2\2"+
		"\u0096\u0098\13\2\2\2\u0097\u0096\3\2\2\2\u0098\u009b\3\2\2\2\u0099\u009a"+
		"\3\2\2\2\u0099\u0097\3\2\2\2\u009a\u009c\3\2\2\2\u009b\u0099\3\2\2\2\u009c"+
		"\u009d\7,\2\2\u009d\u009e\7\61\2\2\u009e)\3\2\2\2\u009f\u00a0\7$\2\2\u00a0"+
		"\u00a1\3\2\2\2\u00a1\u00a2\b\25\3\2\u00a2+\3\2\2\2\u00a3\u00a4\n\6\2\2"+
		"\u00a4-\3\2\2\2\u00a5\u00a6\7^\2\2\u00a6\u00a7\n\5\2\2\u00a7/\3\2\2\2"+
		"\u00a8\u00a9\7$\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ab\b\30\4\2\u00ab\u00ac"+
		"\b\30\5\2\u00ac\61\3\2\2\2\17\2\3RW]_lry~\u0082\u008e\u0099\6\b\2\2\7"+
		"\3\2\t\20\2\6\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
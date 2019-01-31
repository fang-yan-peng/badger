// Generated from BadgerSql.g4 by ANTLR 4.7.2

package org.jfaster.badger.sql.parse;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BadgerSqlLexer extends Lexer {
    static {
        RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, INT = 4, DEC = 5, MARK = 6, AD = 7, OD = 8, IN = 9, BA = 10,
            LK = 11, IS = 12, NOT = 13, NUL = 14, QuoteString = 15, ID = 16, EQ = 17, LT = 18, GT = 19,
            NE = 20, LE = 21, GE = 22, WS = 23;
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };

    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    private static String[] makeRuleNames() {
        return new String[]{
                "T__0", "T__1", "T__2", "INT", "DEC", "MARK", "AD", "OD", "IN", "BA",
                "LK", "IS", "NOT", "NUL", "QuoteString", "ESCqs", "ID", "EQ", "LT", "GT",
                "NE", "LE", "GE", "WS", "Digit"
        };
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'('", "','", "')'", null, null, "'?'", null, null, null, null,
                null, null, null, null, null, null, "'='", "'<'", "'>'", null, "'<='",
                "'>='"
        };
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, null, null, null, "INT", "DEC", "MARK", "AD", "OD", "IN", "BA",
                "LK", "IS", "NOT", "NUL", "QuoteString", "ID", "EQ", "LT", "GT", "NE",
                "LE", "GE", "WS"
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


    StringBuilder sqlBuilder = new StringBuilder(128);
    List<Object> values = new ArrayList<Object>(16);
    List<String> fieldList = new ArrayList<String>(16);
    List<Object> condition = new ArrayList<Object>(16);
    int indexLevel = 1;

    StringBuilder updateBuilder = new StringBuilder(64);
    List<Object> updateValues = new ArrayList<Object>(16);
    List<String> updateFieldList = new ArrayList<String>(16);

    public String getSql() {
        return sqlBuilder.toString();
    }

    public List<String> getField() {
        return fieldList;
    }

    public List<Object> getSqlTree() {
        return condition;
    }

    public List<Object> getValues() {
        return values;
    }

    public String getUpdateStatement() {
        if (updateBuilder.length() > 0) {
            updateBuilder.deleteCharAt(updateBuilder.length() - 1);
        }
        return updateBuilder.toString();
    }

    public List<String> getUpdateField() {
        return updateFieldList;
    }

    public List<Object> getUpdateValues() {
        return updateValues;
    }


    public BadgerSqlLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @Override
    public String getGrammarFileName() {
        return "BadgerSql.g4";
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

    @Override
    public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
        switch (ruleIndex) {
            case 14:
                QuoteString_action((RuleContext) _localctx, actionIndex);
                break;
        }
    }

    private void QuoteString_action(RuleContext _localctx, int actionIndex) {
        switch (actionIndex) {
            case 0:

                setText(getText().substring(1, getText().length() - 1).replaceAll("''", "'"));

                break;
        }
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\31\u00a3\b\1\4\2" +
                    "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
                    "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
                    "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
                    "\t\31\4\32\t\32\3\2\3\2\3\3\3\3\3\4\3\4\3\5\5\5=\n\5\3\5\6\5@\n\5\r\5" +
                    "\16\5A\3\6\5\6E\n\6\3\6\6\6H\n\6\r\6\16\6I\3\6\3\6\6\6N\n\6\r\6\16\6O" +
                    "\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3" +
                    "\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16" +
                    "\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\7\20z\n\20\f\20\16\20}\13\20" +
                    "\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\7\22\u0087\n\22\f\22\16\22\u008a" +
                    "\13\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\26\5\26\u0096\n" +
                    "\26\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32\2\2\33" +
                    "\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20" +
                    "\37\21!\2#\22%\23\'\24)\25+\26-\27/\30\61\31\63\2\3\2\24\4\2CCcc\4\2P" +
                    "Ppp\4\2FFff\4\2QQqq\4\2TTtt\4\2KKkk\4\2DDdd\4\2GGgg\4\2VVvv\4\2YYyy\4" +
                    "\2NNnn\4\2MMmm\4\2UUuu\4\2WWww\3\2))\5\2C\\aac|\6\2\62;C\\aac|\5\2\13" +
                    "\f\17\17\"\"\2\u00a9\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2" +
                    "\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3" +
                    "\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2" +
                    "\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2" +
                    "\2/\3\2\2\2\2\61\3\2\2\2\3\65\3\2\2\2\5\67\3\2\2\2\79\3\2\2\2\t<\3\2\2" +
                    "\2\13D\3\2\2\2\rQ\3\2\2\2\17S\3\2\2\2\21W\3\2\2\2\23Z\3\2\2\2\25]\3\2" +
                    "\2\2\27e\3\2\2\2\31j\3\2\2\2\33m\3\2\2\2\35q\3\2\2\2\37v\3\2\2\2!\u0081" +
                    "\3\2\2\2#\u0084\3\2\2\2%\u008b\3\2\2\2\'\u008d\3\2\2\2)\u008f\3\2\2\2" +
                    "+\u0095\3\2\2\2-\u0097\3\2\2\2/\u009a\3\2\2\2\61\u009d\3\2\2\2\63\u00a1" +
                    "\3\2\2\2\65\66\7*\2\2\66\4\3\2\2\2\678\7.\2\28\6\3\2\2\29:\7+\2\2:\b\3" +
                    "\2\2\2;=\7/\2\2<;\3\2\2\2<=\3\2\2\2=?\3\2\2\2>@\5\63\32\2?>\3\2\2\2@A" +
                    "\3\2\2\2A?\3\2\2\2AB\3\2\2\2B\n\3\2\2\2CE\7/\2\2DC\3\2\2\2DE\3\2\2\2E" +
                    "G\3\2\2\2FH\5\63\32\2GF\3\2\2\2HI\3\2\2\2IG\3\2\2\2IJ\3\2\2\2JK\3\2\2" +
                    "\2KM\7\60\2\2LN\5\63\32\2ML\3\2\2\2NO\3\2\2\2OM\3\2\2\2OP\3\2\2\2P\f\3" +
                    "\2\2\2QR\7A\2\2R\16\3\2\2\2ST\t\2\2\2TU\t\3\2\2UV\t\4\2\2V\20\3\2\2\2" +
                    "WX\t\5\2\2XY\t\6\2\2Y\22\3\2\2\2Z[\t\7\2\2[\\\t\3\2\2\\\24\3\2\2\2]^\t" +
                    "\b\2\2^_\t\t\2\2_`\t\n\2\2`a\t\13\2\2ab\t\t\2\2bc\t\t\2\2cd\t\3\2\2d\26" +
                    "\3\2\2\2ef\t\f\2\2fg\t\7\2\2gh\t\r\2\2hi\t\t\2\2i\30\3\2\2\2jk\t\7\2\2" +
                    "kl\t\16\2\2l\32\3\2\2\2mn\t\3\2\2no\t\5\2\2op\t\n\2\2p\34\3\2\2\2qr\t" +
                    "\3\2\2rs\t\17\2\2st\t\f\2\2tu\t\f\2\2u\36\3\2\2\2v{\7)\2\2wz\5!\21\2x" +
                    "z\n\20\2\2yw\3\2\2\2yx\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|~\3\2\2\2" +
                    "}{\3\2\2\2~\177\7)\2\2\177\u0080\b\20\2\2\u0080 \3\2\2\2\u0081\u0082\7" +
                    ")\2\2\u0082\u0083\7)\2\2\u0083\"\3\2\2\2\u0084\u0088\t\21\2\2\u0085\u0087" +
                    "\t\22\2\2\u0086\u0085\3\2\2\2\u0087\u008a\3\2\2\2\u0088\u0086\3\2\2\2" +
                    "\u0088\u0089\3\2\2\2\u0089$\3\2\2\2\u008a\u0088\3\2\2\2\u008b\u008c\7" +
                    "?\2\2\u008c&\3\2\2\2\u008d\u008e\7>\2\2\u008e(\3\2\2\2\u008f\u0090\7@" +
                    "\2\2\u0090*\3\2\2\2\u0091\u0092\7#\2\2\u0092\u0096\7?\2\2\u0093\u0094" +
                    "\7>\2\2\u0094\u0096\7@\2\2\u0095\u0091\3\2\2\2\u0095\u0093\3\2\2\2\u0096" +
                    ",\3\2\2\2\u0097\u0098\7>\2\2\u0098\u0099\7?\2\2\u0099.\3\2\2\2\u009a\u009b" +
                    "\7@\2\2\u009b\u009c\7?\2\2\u009c\60\3\2\2\2\u009d\u009e\t\23\2\2\u009e" +
                    "\u009f\3\2\2\2\u009f\u00a0\b\31\3\2\u00a0\62\3\2\2\2\u00a1\u00a2\4\62" +
                    ";\2\u00a2\64\3\2\2\2\f\2<ADIOy{\u0088\u0095\4\3\20\2\b\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
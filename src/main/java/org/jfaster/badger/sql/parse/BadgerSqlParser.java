// Generated from BadgerSql.g4 by ANTLR 4.7.2

package org.jfaster.badger.sql.parse;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jfaster.badger.query.sql.SqlTree;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BadgerSqlParser extends Parser {
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
    public static final int
            RULE_logicalExpression = 0, RULE_relationalExpression = 1, RULE_updateExpression = 2,
            RULE_updateField = 3, RULE_logicalOperate = 4, RULE_leftBracket = 5, RULE_rightBracket = 6,
            RULE_atom = 7, RULE_updateAtom = 8, RULE_aton = 9, RULE_operate = 10;

    private static String[] makeRuleNames() {
        return new String[]{
                "logicalExpression", "relationalExpression", "updateExpression", "updateField",
                "logicalOperate", "leftBracket", "rightBracket", "atom", "updateAtom",
                "aton", "operate"
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
    public ATN getATN() {
        return _ATN;
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


    public BadgerSqlParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class LogicalExpressionContext extends ParserRuleContext {
        public RelationalExpressionContext relationalExpression() {
            return getRuleContext(RelationalExpressionContext.class, 0);
        }

        public List<LogicalOperateContext> logicalOperate() {
            return getRuleContexts(LogicalOperateContext.class);
        }

        public LogicalOperateContext logicalOperate(int i) {
            return getRuleContext(LogicalOperateContext.class, i);
        }

        public List<LogicalExpressionContext> logicalExpression() {
            return getRuleContexts(LogicalExpressionContext.class);
        }

        public LogicalExpressionContext logicalExpression(int i) {
            return getRuleContext(LogicalExpressionContext.class, i);
        }

        public LeftBracketContext leftBracket() {
            return getRuleContext(LeftBracketContext.class, 0);
        }

        public RightBracketContext rightBracket() {
            return getRuleContext(RightBracketContext.class, 0);
        }

        public LogicalExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_logicalExpression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).enterLogicalExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).exitLogicalExpression(this);
        }
    }

    public final LogicalExpressionContext logicalExpression() throws RecognitionException {
        LogicalExpressionContext _localctx = new LogicalExpressionContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_logicalExpression);
        try {
            int _alt;
            setState(42);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case INT:
                case ID:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(22);
                    relationalExpression();
                    setState(28);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 0, _ctx);
                    while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                        if (_alt == 1) {
                            {
                                {
                                    setState(23);
                                    logicalOperate();
                                    setState(24);
                                    logicalExpression();
                                }
                            }
                        }
                        setState(30);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 0, _ctx);
                    }
                }
                break;
                case T__0:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(31);
                    leftBracket();
                    setState(32);
                    logicalExpression();
                    setState(33);
                    rightBracket();
                    setState(39);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 1, _ctx);
                    while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                        if (_alt == 1) {
                            {
                                {
                                    setState(34);
                                    logicalOperate();
                                    setState(35);
                                    logicalExpression();
                                }
                            }
                        }
                        setState(41);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 1, _ctx);
                    }
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

    public static class RelationalExpressionContext extends ParserRuleContext {
        public Token fieldName;
        public OperateContext op;
        public AtonContext value;

        public TerminalNode IN() {
            return getToken(BadgerSqlParser.IN, 0);
        }

        public List<AtomContext> atom() {
            return getRuleContexts(AtomContext.class);
        }

        public AtomContext atom(int i) {
            return getRuleContext(AtomContext.class, i);
        }

        public TerminalNode ID() {
            return getToken(BadgerSqlParser.ID, 0);
        }

        public TerminalNode BA() {
            return getToken(BadgerSqlParser.BA, 0);
        }

        public TerminalNode AD() {
            return getToken(BadgerSqlParser.AD, 0);
        }

        public TerminalNode LK() {
            return getToken(BadgerSqlParser.LK, 0);
        }

        public OperateContext operate() {
            return getRuleContext(OperateContext.class, 0);
        }

        public TerminalNode INT() {
            return getToken(BadgerSqlParser.INT, 0);
        }

        public AtonContext aton() {
            return getRuleContext(AtonContext.class, 0);
        }

        public TerminalNode IS() {
            return getToken(BadgerSqlParser.IS, 0);
        }

        public TerminalNode NUL() {
            return getToken(BadgerSqlParser.NUL, 0);
        }

        public TerminalNode NOT() {
            return getToken(BadgerSqlParser.NOT, 0);
        }

        public RelationalExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_relationalExpression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).enterRelationalExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).exitRelationalExpression(this);
        }
    }

    public final RelationalExpressionContext relationalExpression() throws RecognitionException {
        RelationalExpressionContext _localctx = new RelationalExpressionContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_relationalExpression);
        int _la;
        try {
            setState(93);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 4, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    int count = 0;
                    setState(45);
                    ((RelationalExpressionContext) _localctx).fieldName = match(ID);
                    setState(46);
                    match(IN);
                    setState(47);
                    match(T__0);
                    setState(48);
                    atom();
                    count++;
                    setState(56);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    while (_la == T__1) {
                        {
                            {
                                setState(50);
                                match(T__1);
                                setState(51);
                                atom();
                                count++;
                            }
                        }
                        setState(58);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    }
                    setState(59);
                    match(T__2);

                    condition.add(new SqlTree((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null), "in", indexLevel, count));
                    sqlBuilder.append((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null))
                            .append(" IN (?");
                    fieldList.add((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null));
                    for (; count > 1; count--) {
                        sqlBuilder.append(", ?");
                        fieldList.add((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null));
                    }
                    sqlBuilder.append(")");

                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(62);
                    ((RelationalExpressionContext) _localctx).fieldName = match(ID);
                    setState(63);
                    match(BA);
                    setState(64);
                    atom();
                    setState(65);
                    match(AD);
                    setState(66);
                    atom();

                    {
                        sqlBuilder.append((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null)).append(" BETWEEN ? AND ? ");
                        fieldList.add((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null));
                        fieldList.add((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null));
                        condition.add(new SqlTree((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null), "between and", indexLevel, 2));
                    }

                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(69);
                    ((RelationalExpressionContext) _localctx).fieldName = match(ID);
                    setState(70);
                    match(LK);
                    setState(71);
                    atom();

                    {
                        sqlBuilder.append((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null)).append(" LIKE ?");
                        fieldList.add((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null));
                        condition.add(new SqlTree((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null), "like", indexLevel, 1));
                    }

                }
                break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(74);
                    ((RelationalExpressionContext) _localctx).fieldName = match(ID);
                    setState(75);
                    ((RelationalExpressionContext) _localctx).op = operate();
                    setState(76);
                    atom();

                    sqlBuilder.append((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null))
                            .append(' ')
                            .append((((RelationalExpressionContext) _localctx).op != null ? _input.getText(((RelationalExpressionContext) _localctx).op.start, ((RelationalExpressionContext) _localctx).op.stop) : null))
                            .append(' ')
                            .append("?");
                    fieldList.add((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null));
                    condition.add(new SqlTree((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null), (((RelationalExpressionContext) _localctx).op != null ? _input.getText(((RelationalExpressionContext) _localctx).op.start, ((RelationalExpressionContext) _localctx).op.stop) : null), indexLevel, 1));

                }
                break;
                case 5:
                    enterOuterAlt(_localctx, 5);
                {
                    setState(79);
                    ((RelationalExpressionContext) _localctx).fieldName = match(INT);
                    setState(80);
                    ((RelationalExpressionContext) _localctx).op = operate();
                    setState(81);
                    ((RelationalExpressionContext) _localctx).value = aton();

                    sqlBuilder.append((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null))
                            .append(' ')
                            .append((((RelationalExpressionContext) _localctx).op != null ? _input.getText(((RelationalExpressionContext) _localctx).op.start, ((RelationalExpressionContext) _localctx).op.stop) : null))
                            .append(' ')
                            .append((((RelationalExpressionContext) _localctx).value != null ? _input.getText(((RelationalExpressionContext) _localctx).value.start, ((RelationalExpressionContext) _localctx).value.stop) : null));

                }
                break;
                case 6:
                    enterOuterAlt(_localctx, 6);
                {
                    setState(84);
                    ((RelationalExpressionContext) _localctx).fieldName = match(ID);
                    setState(85);
                    match(IS);
                    setState(86);
                    match(NUL);

                    sqlBuilder.append((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null))
                            .append(' ')
                            .append("IS NULL");

                }
                break;
                case 7:
                    enterOuterAlt(_localctx, 7);
                {
                    setState(88);
                    ((RelationalExpressionContext) _localctx).fieldName = match(ID);
                    setState(89);
                    match(IS);
                    setState(90);
                    match(NOT);
                    setState(91);
                    match(NUL);

                    sqlBuilder.append((((RelationalExpressionContext) _localctx).fieldName != null ? ((RelationalExpressionContext) _localctx).fieldName.getText() : null))
                            .append(' ')
                            .append("IS NOT NULL");

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

    public static class UpdateExpressionContext extends ParserRuleContext {
        public List<UpdateFieldContext> updateField() {
            return getRuleContexts(UpdateFieldContext.class);
        }

        public UpdateFieldContext updateField(int i) {
            return getRuleContext(UpdateFieldContext.class, i);
        }

        public UpdateExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_updateExpression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).enterUpdateExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).exitUpdateExpression(this);
        }
    }

    public final UpdateExpressionContext updateExpression() throws RecognitionException {
        UpdateExpressionContext _localctx = new UpdateExpressionContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_updateExpression);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(95);
                updateField();
                setState(100);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == T__1) {
                    {
                        {
                            setState(96);
                            match(T__1);
                            setState(97);
                            updateField();
                        }
                    }
                    setState(102);
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

    public static class UpdateFieldContext extends ParserRuleContext {
        public Token fieldName;

        public TerminalNode EQ() {
            return getToken(BadgerSqlParser.EQ, 0);
        }

        public UpdateAtomContext updateAtom() {
            return getRuleContext(UpdateAtomContext.class, 0);
        }

        public TerminalNode ID() {
            return getToken(BadgerSqlParser.ID, 0);
        }

        public UpdateFieldContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_updateField;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).enterUpdateField(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).exitUpdateField(this);
        }
    }

    public final UpdateFieldContext updateField() throws RecognitionException {
        UpdateFieldContext _localctx = new UpdateFieldContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_updateField);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(103);
                ((UpdateFieldContext) _localctx).fieldName = match(ID);
                setState(104);
                match(EQ);
                setState(105);
                updateAtom();

                updateBuilder
                        .append((((UpdateFieldContext) _localctx).fieldName != null ? ((UpdateFieldContext) _localctx).fieldName.getText() : null))
                        .append(" = ")
                        .append("?")
                        .append(",");
                updateFieldList.add((((UpdateFieldContext) _localctx).fieldName != null ? ((UpdateFieldContext) _localctx).fieldName.getText() : null));

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

    public static class LogicalOperateContext extends ParserRuleContext {
        public TerminalNode AD() {
            return getToken(BadgerSqlParser.AD, 0);
        }

        public TerminalNode OD() {
            return getToken(BadgerSqlParser.OD, 0);
        }

        public LogicalOperateContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_logicalOperate;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).enterLogicalOperate(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).exitLogicalOperate(this);
        }
    }

    public final LogicalOperateContext logicalOperate() throws RecognitionException {
        LogicalOperateContext _localctx = new LogicalOperateContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_logicalOperate);
        try {
            setState(112);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case AD:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(108);
                    match(AD);
                    sqlBuilder.append(" AND ");
                    condition.add("and");
                }
                break;
                case OD:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(110);
                    match(OD);
                    sqlBuilder.append(" OR ");
                    condition.add("or");
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

    public static class LeftBracketContext extends ParserRuleContext {
        public LeftBracketContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_leftBracket;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).enterLeftBracket(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).exitLeftBracket(this);
        }
    }

    public final LeftBracketContext leftBracket() throws RecognitionException {
        LeftBracketContext _localctx = new LeftBracketContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_leftBracket);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(114);
                match(T__0);
                sqlBuilder.append("(");
                indexLevel++;
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

    public static class RightBracketContext extends ParserRuleContext {
        public RightBracketContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_rightBracket;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).enterRightBracket(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).exitRightBracket(this);
        }
    }

    public final RightBracketContext rightBracket() throws RecognitionException {
        RightBracketContext _localctx = new RightBracketContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_rightBracket);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(117);
                match(T__2);
                sqlBuilder.append(")");
                indexLevel--;
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

    public static class AtomContext extends ParserRuleContext {
        public Token INT;
        public Token DEC;
        public Token QuoteString;
        public Token MARK;

        public TerminalNode INT() {
            return getToken(BadgerSqlParser.INT, 0);
        }

        public TerminalNode DEC() {
            return getToken(BadgerSqlParser.DEC, 0);
        }

        public TerminalNode QuoteString() {
            return getToken(BadgerSqlParser.QuoteString, 0);
        }

        public TerminalNode MARK() {
            return getToken(BadgerSqlParser.MARK, 0);
        }

        public AtomContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_atom;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).enterAtom(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).exitAtom(this);
        }
    }

    public final AtomContext atom() throws RecognitionException {
        AtomContext _localctx = new AtomContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_atom);
        try {
            setState(128);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case INT:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(120);
                    ((AtomContext) _localctx).INT = match(INT);
                    values.add(Long.parseLong((((AtomContext) _localctx).INT != null ? ((AtomContext) _localctx).INT.getText() : null)));
                }
                break;
                case DEC:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(122);
                    ((AtomContext) _localctx).DEC = match(DEC);
                    values.add(Double.parseDouble((((AtomContext) _localctx).DEC != null ? ((AtomContext) _localctx).DEC.getText() : null)));
                }
                break;
                case QuoteString:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(124);
                    ((AtomContext) _localctx).QuoteString = match(QuoteString);
                    values.add((((AtomContext) _localctx).QuoteString != null ? ((AtomContext) _localctx).QuoteString.getText() : null));
                }
                break;
                case MARK:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(126);
                    ((AtomContext) _localctx).MARK = match(MARK);
                    values.add((((AtomContext) _localctx).MARK != null ? ((AtomContext) _localctx).MARK.getText() : null));
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

    public static class UpdateAtomContext extends ParserRuleContext {
        public Token INT;
        public Token DEC;
        public Token QuoteString;
        public Token MARK;

        public TerminalNode INT() {
            return getToken(BadgerSqlParser.INT, 0);
        }

        public TerminalNode DEC() {
            return getToken(BadgerSqlParser.DEC, 0);
        }

        public TerminalNode QuoteString() {
            return getToken(BadgerSqlParser.QuoteString, 0);
        }

        public TerminalNode MARK() {
            return getToken(BadgerSqlParser.MARK, 0);
        }

        public UpdateAtomContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_updateAtom;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).enterUpdateAtom(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).exitUpdateAtom(this);
        }
    }

    public final UpdateAtomContext updateAtom() throws RecognitionException {
        UpdateAtomContext _localctx = new UpdateAtomContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_updateAtom);
        try {
            setState(138);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case INT:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(130);
                    ((UpdateAtomContext) _localctx).INT = match(INT);
                    updateValues.add(Long.parseLong((((UpdateAtomContext) _localctx).INT != null ? ((UpdateAtomContext) _localctx).INT.getText() : null)));
                }
                break;
                case DEC:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(132);
                    ((UpdateAtomContext) _localctx).DEC = match(DEC);
                    updateValues.add(Double.parseDouble((((UpdateAtomContext) _localctx).DEC != null ? ((UpdateAtomContext) _localctx).DEC.getText() : null)));
                }
                break;
                case QuoteString:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(134);
                    ((UpdateAtomContext) _localctx).QuoteString = match(QuoteString);
                    updateValues.add((((UpdateAtomContext) _localctx).QuoteString != null ? ((UpdateAtomContext) _localctx).QuoteString.getText() : null));
                }
                break;
                case MARK:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(136);
                    ((UpdateAtomContext) _localctx).MARK = match(MARK);
                    updateValues.add((((UpdateAtomContext) _localctx).MARK != null ? ((UpdateAtomContext) _localctx).MARK.getText() : null));
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

    public static class AtonContext extends ParserRuleContext {
        public TerminalNode INT() {
            return getToken(BadgerSqlParser.INT, 0);
        }

        public AtonContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_aton;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).enterAton(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).exitAton(this);
        }
    }

    public final AtonContext aton() throws RecognitionException {
        AtonContext _localctx = new AtonContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_aton);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(140);
                match(INT);
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

    public static class OperateContext extends ParserRuleContext {
        public TerminalNode EQ() {
            return getToken(BadgerSqlParser.EQ, 0);
        }

        public TerminalNode LT() {
            return getToken(BadgerSqlParser.LT, 0);
        }

        public TerminalNode GT() {
            return getToken(BadgerSqlParser.GT, 0);
        }

        public TerminalNode NE() {
            return getToken(BadgerSqlParser.NE, 0);
        }

        public TerminalNode LE() {
            return getToken(BadgerSqlParser.LE, 0);
        }

        public TerminalNode GE() {
            return getToken(BadgerSqlParser.GE, 0);
        }

        public OperateContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_operate;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).enterOperate(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof BadgerSqlListener)
                ((BadgerSqlListener) listener).exitOperate(this);
        }
    }

    public final OperateContext operate() throws RecognitionException {
        OperateContext _localctx = new OperateContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_operate);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(142);
                _la = _input.LA(1);
                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << LT) | (1L << GT) | (1L << NE) | (1L << LE) | (1L << GE))) != 0))) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
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

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\31\u0093\4\2\t\2" +
                    "\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\3\2\3\2\3\2\3\2\7\2\35\n\2\f\2\16\2 \13\2\3\2\3\2\3\2\3" +
                    "\2\3\2\3\2\7\2(\n\2\f\2\16\2+\13\2\5\2-\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3" +
                    "\3\3\3\3\3\3\3\7\39\n\3\f\3\16\3<\13\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3" +
                    "\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3" +
                    "\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3`\n\3\3\4\3\4\3\4\7\4e\n\4\f\4\16" +
                    "\4h\13\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\5\6s\n\6\3\7\3\7\3\7\3\b" +
                    "\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u0083\n\t\3\n\3\n\3\n\3\n" +
                    "\3\n\3\n\3\n\3\n\5\n\u008d\n\n\3\13\3\13\3\f\3\f\3\f\2\2\r\2\4\6\b\n\f" +
                    "\16\20\22\24\26\2\3\3\2\23\30\2\u0099\2,\3\2\2\2\4_\3\2\2\2\6a\3\2\2\2" +
                    "\bi\3\2\2\2\nr\3\2\2\2\ft\3\2\2\2\16w\3\2\2\2\20\u0082\3\2\2\2\22\u008c" +
                    "\3\2\2\2\24\u008e\3\2\2\2\26\u0090\3\2\2\2\30\36\5\4\3\2\31\32\5\n\6\2" +
                    "\32\33\5\2\2\2\33\35\3\2\2\2\34\31\3\2\2\2\35 \3\2\2\2\36\34\3\2\2\2\36" +
                    "\37\3\2\2\2\37-\3\2\2\2 \36\3\2\2\2!\"\5\f\7\2\"#\5\2\2\2#)\5\16\b\2$" +
                    "%\5\n\6\2%&\5\2\2\2&(\3\2\2\2\'$\3\2\2\2(+\3\2\2\2)\'\3\2\2\2)*\3\2\2" +
                    "\2*-\3\2\2\2+)\3\2\2\2,\30\3\2\2\2,!\3\2\2\2-\3\3\2\2\2./\b\3\1\2/\60" +
                    "\7\22\2\2\60\61\7\13\2\2\61\62\7\3\2\2\62\63\5\20\t\2\63:\b\3\1\2\64\65" +
                    "\7\4\2\2\65\66\5\20\t\2\66\67\b\3\1\2\679\3\2\2\28\64\3\2\2\29<\3\2\2" +
                    "\2:8\3\2\2\2:;\3\2\2\2;=\3\2\2\2<:\3\2\2\2=>\7\5\2\2>?\b\3\1\2?`\3\2\2" +
                    "\2@A\7\22\2\2AB\7\f\2\2BC\5\20\t\2CD\7\t\2\2DE\5\20\t\2EF\b\3\1\2F`\3" +
                    "\2\2\2GH\7\22\2\2HI\7\r\2\2IJ\5\20\t\2JK\b\3\1\2K`\3\2\2\2LM\7\22\2\2" +
                    "MN\5\26\f\2NO\5\20\t\2OP\b\3\1\2P`\3\2\2\2QR\7\6\2\2RS\5\26\f\2ST\5\24" +
                    "\13\2TU\b\3\1\2U`\3\2\2\2VW\7\22\2\2WX\7\16\2\2XY\7\20\2\2Y`\b\3\1\2Z" +
                    "[\7\22\2\2[\\\7\16\2\2\\]\7\17\2\2]^\7\20\2\2^`\b\3\1\2_.\3\2\2\2_@\3" +
                    "\2\2\2_G\3\2\2\2_L\3\2\2\2_Q\3\2\2\2_V\3\2\2\2_Z\3\2\2\2`\5\3\2\2\2af" +
                    "\5\b\5\2bc\7\4\2\2ce\5\b\5\2db\3\2\2\2eh\3\2\2\2fd\3\2\2\2fg\3\2\2\2g" +
                    "\7\3\2\2\2hf\3\2\2\2ij\7\22\2\2jk\7\23\2\2kl\5\22\n\2lm\b\5\1\2m\t\3\2" +
                    "\2\2no\7\t\2\2os\b\6\1\2pq\7\n\2\2qs\b\6\1\2rn\3\2\2\2rp\3\2\2\2s\13\3" +
                    "\2\2\2tu\7\3\2\2uv\b\7\1\2v\r\3\2\2\2wx\7\5\2\2xy\b\b\1\2y\17\3\2\2\2" +
                    "z{\7\6\2\2{\u0083\b\t\1\2|}\7\7\2\2}\u0083\b\t\1\2~\177\7\21\2\2\177\u0083" +
                    "\b\t\1\2\u0080\u0081\7\b\2\2\u0081\u0083\b\t\1\2\u0082z\3\2\2\2\u0082" +
                    "|\3\2\2\2\u0082~\3\2\2\2\u0082\u0080\3\2\2\2\u0083\21\3\2\2\2\u0084\u0085" +
                    "\7\6\2\2\u0085\u008d\b\n\1\2\u0086\u0087\7\7\2\2\u0087\u008d\b\n\1\2\u0088" +
                    "\u0089\7\21\2\2\u0089\u008d\b\n\1\2\u008a\u008b\7\b\2\2\u008b\u008d\b" +
                    "\n\1\2\u008c\u0084\3\2\2\2\u008c\u0086\3\2\2\2\u008c\u0088\3\2\2\2\u008c" +
                    "\u008a\3\2\2\2\u008d\23\3\2\2\2\u008e\u008f\7\6\2\2\u008f\25\3\2\2\2\u0090" +
                    "\u0091\t\2\2\2\u0091\27\3\2\2\2\13\36),:_fr\u0082\u008c";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
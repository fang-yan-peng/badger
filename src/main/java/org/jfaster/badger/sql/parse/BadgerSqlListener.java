// Generated from BadgerSql.g4 by ANTLR 4.7.2

package org.jfaster.badger.sql.parse;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BadgerSqlParser}.
 */
public interface BadgerSqlListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link BadgerSqlParser#logicalExpression}.
     * @param ctx the parse tree
     */
    void enterLogicalExpression(BadgerSqlParser.LogicalExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link BadgerSqlParser#logicalExpression}.
     * @param ctx the parse tree
     */
    void exitLogicalExpression(BadgerSqlParser.LogicalExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link BadgerSqlParser#relationalExpression}.
     * @param ctx the parse tree
     */
    void enterRelationalExpression(BadgerSqlParser.RelationalExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link BadgerSqlParser#relationalExpression}.
     * @param ctx the parse tree
     */
    void exitRelationalExpression(BadgerSqlParser.RelationalExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link BadgerSqlParser#updateExpression}.
     * @param ctx the parse tree
     */
    void enterUpdateExpression(BadgerSqlParser.UpdateExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link BadgerSqlParser#updateExpression}.
     * @param ctx the parse tree
     */
    void exitUpdateExpression(BadgerSqlParser.UpdateExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link BadgerSqlParser#updateField}.
     * @param ctx the parse tree
     */
    void enterUpdateField(BadgerSqlParser.UpdateFieldContext ctx);

    /**
     * Exit a parse tree produced by {@link BadgerSqlParser#updateField}.
     * @param ctx the parse tree
     */
    void exitUpdateField(BadgerSqlParser.UpdateFieldContext ctx);

    /**
     * Enter a parse tree produced by {@link BadgerSqlParser#logicalOperate}.
     * @param ctx the parse tree
     */
    void enterLogicalOperate(BadgerSqlParser.LogicalOperateContext ctx);

    /**
     * Exit a parse tree produced by {@link BadgerSqlParser#logicalOperate}.
     * @param ctx the parse tree
     */
    void exitLogicalOperate(BadgerSqlParser.LogicalOperateContext ctx);

    /**
     * Enter a parse tree produced by {@link BadgerSqlParser#leftBracket}.
     * @param ctx the parse tree
     */
    void enterLeftBracket(BadgerSqlParser.LeftBracketContext ctx);

    /**
     * Exit a parse tree produced by {@link BadgerSqlParser#leftBracket}.
     * @param ctx the parse tree
     */
    void exitLeftBracket(BadgerSqlParser.LeftBracketContext ctx);

    /**
     * Enter a parse tree produced by {@link BadgerSqlParser#rightBracket}.
     * @param ctx the parse tree
     */
    void enterRightBracket(BadgerSqlParser.RightBracketContext ctx);

    /**
     * Exit a parse tree produced by {@link BadgerSqlParser#rightBracket}.
     * @param ctx the parse tree
     */
    void exitRightBracket(BadgerSqlParser.RightBracketContext ctx);

    /**
     * Enter a parse tree produced by {@link BadgerSqlParser#atom}.
     * @param ctx the parse tree
     */
    void enterAtom(BadgerSqlParser.AtomContext ctx);

    /**
     * Exit a parse tree produced by {@link BadgerSqlParser#atom}.
     * @param ctx the parse tree
     */
    void exitAtom(BadgerSqlParser.AtomContext ctx);

    /**
     * Enter a parse tree produced by {@link BadgerSqlParser#updateAtom}.
     * @param ctx the parse tree
     */
    void enterUpdateAtom(BadgerSqlParser.UpdateAtomContext ctx);

    /**
     * Exit a parse tree produced by {@link BadgerSqlParser#updateAtom}.
     * @param ctx the parse tree
     */
    void exitUpdateAtom(BadgerSqlParser.UpdateAtomContext ctx);

    /**
     * Enter a parse tree produced by {@link BadgerSqlParser#aton}.
     * @param ctx the parse tree
     */
    void enterAton(BadgerSqlParser.AtonContext ctx);

    /**
     * Exit a parse tree produced by {@link BadgerSqlParser#aton}.
     * @param ctx the parse tree
     */
    void exitAton(BadgerSqlParser.AtonContext ctx);

    /**
     * Enter a parse tree produced by {@link BadgerSqlParser#operate}.
     * @param ctx the parse tree
     */
    void enterOperate(BadgerSqlParser.OperateContext ctx);

    /**
     * Exit a parse tree produced by {@link BadgerSqlParser#operate}.
     * @param ctx the parse tree
     */
    void exitOperate(BadgerSqlParser.OperateContext ctx);
}
package org.jfaster.badger.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.query.shard.ShardTableInfo;
import org.jfaster.badger.query.sql.SqlTree;
import org.jfaster.badger.sql.ParseResult;
import org.jfaster.badger.sql.parse.BadgerSqlLexer;
import org.jfaster.badger.sql.parse.BadgerSqlParser;

/**
 * sql转化工具
 * @author yanpengfang
 * @create 2019-01-14 6:15 PM
 */
public class SQLParseUtils {

    private static final Map<Pair, ParseResult> expressions = new ConcurrentHashMap<>();

    public static ParseResult parse(Class<?> clazz, String expression)
            throws IOException {
        Pair pair = Pair.of(clazz, expression);
        ParseResult cache = expressions.get(pair);
        if (cache != null) {
            return cache;
        }
        cache = new ParseResult();
        InputStream is = new ByteArrayInputStream(expression.getBytes());
        CharStream cs = CharStreams.fromStream(is);
        BadgerSqlLexer esl = new BadgerSqlLexer(cs);
        TokenStream ts = new CommonTokenStream(esl);
        BadgerSqlParser esp = new BadgerSqlParser(ts);
        esp.logicalExpression();
        cache.setSql(esp.getSql());
        cache.setFields(esp.getField());
        cache.setValues(esp.getValues());
        cache.setSqlTree(esp.getSqlTree());
        cache.setDynamicFields(new ArrayList<>());
        ShardTableInfo shardTableInfo = SqlUtils.getShardTableInfo(clazz);
        String shardColumn = shardTableInfo != null ? shardTableInfo.getColumn() : null;
        int index = 0;
        int realIndex = 0;
        boolean hasIn = false;
        for (Object obj : cache.getSqlTree()) {
            if (obj instanceof SqlTree) {
                SqlTree st = (SqlTree) obj;
                if ("in".equals(st.getOption())) {
                    hasIn = true;
                }
                if (shardTableInfo != null && st.getFieldName().equals(shardColumn)) { //如果有分库分表字段，则检查是否是单值表达式
                    if (st.getCount() > 1) {
                        throw new BadgerException("column:%s shard only support '=,!=,<>,>,>=,<,<=,in(单值)' ,not support" +
                                " %s", st.getFieldName(), st.getOption());
                    }
                    Object val = cache.getValues().get(index++);
                    if (mark(val)) {
                        cache.setShardParameterIndex(realIndex++);
                        cache.getDynamicFields().add(SqlUtils.getFieldByColumn(clazz, st.getFieldName()));
                    } else {
                        //分库分表字段为固定值，不推荐
                        cache.setShardValue(val);
                    }
                    continue;
                }
                for (int i = 0; i < st.getCount(); i++) {
                    Object val = cache.getValues().get(index++);
                    if (mark(val)) {
                        realIndex++;
                        cache.getDynamicFields().add(SqlUtils.getFieldByColumn(clazz, st.getFieldName()));
                    }
                }
            }
        }
        if (!hasIn && expressions.size() < 10000) { //如果表达式有in则，不缓存，因为sql会跟着in的内的参数改变，后续优化in(?,?,?) 为in(?)
            expressions.put(pair, cache);
        }
        return cache;
    }

    public static ParseResult parseUpdateStatment(Class<?> clazz, String updateStatement)
            throws IOException {
        Pair pair = Pair.of(clazz, updateStatement);
        ParseResult cache = expressions.get(pair);
        if (cache != null) {
            return cache;
        }
        cache = new ParseResult();
        InputStream is = new ByteArrayInputStream(updateStatement.getBytes());
        CharStream cs = CharStreams.fromStream(is);
        BadgerSqlLexer esl = new BadgerSqlLexer(cs);
        TokenStream ts = new CommonTokenStream(esl);
        BadgerSqlParser esp = new BadgerSqlParser(ts);
        esp.updateExpression();
        List<String> fields = esp.getUpdateField();
        List<Object> values = esp.getUpdateValues();
        cache.setSql(esp.getUpdateStatement());
        cache.setFields(fields);
        cache.setValues(values);
        cache.setDynamicFields(new ArrayList<>());
        int size = fields.size();
        for (int i = 0; i < size; ++i) {
            Object val = values.get(i);
            if (mark(val)) {
                cache.getDynamicFields().add(SqlUtils.getFieldByColumn(clazz, fields.get(i)));
            }
        }
        if (expressions.size() < 10000) {
            expressions.put(pair, cache);
        }
        return cache;
    }

    private static boolean mark(Object value) {
        if (value instanceof String) {
            String valStr = (String) value;
            return "?".equals(valStr);
        }
        return false;
    }
}

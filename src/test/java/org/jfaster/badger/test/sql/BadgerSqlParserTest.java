package org.jfaster.badger.test.sql;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.jfaster.badger.sql.parse.BadgerSqlLexer;
import org.jfaster.badger.sql.parse.BadgerSqlParser;
import org.junit.Test;

/**
 * sql 解析测试
 * @author yanpengfang
 * @create 2019-01-03 5:15 PM
 */
public class BadgerSqlParserTest {

    @Test
    public void parse() throws Exception{
		String expression = "1=1 and (state!=1 And money<>9.0) Or name='ddd' aNd status in (1,2,3) or create_time between '2016-01-01' and '2017-01-01' and addr lIke '%中国%' and addr is null and addr is not null";
        //String expression = "1=1 and driver_tel = 15575919419 and ext like '2017-04%' and ext>='2017-04-04' and ext <= '2017-04-11' and work_time > 0";
        InputStream is = new ByteArrayInputStream(expression.getBytes());
        CharStream cs = CharStreams.fromStream(is);
        BadgerSqlLexer esl = new BadgerSqlLexer(cs);
        TokenStream ts = new CommonTokenStream(esl);
        BadgerSqlParser esp = new BadgerSqlParser(ts);
        esp.logicalExpression();
        System.out.println(esp.getSql());
        System.out.println(esp.getField());
        System.out.println(esp.getValues());
        System.out.println(esp.getSqlTree());
    }

    @Test
    public void parseUpdateStatement() throws Exception{
        String expression = "age=1,name='cat',address='中国',type=?";
        //String expression = "1=1 and driver_tel = 15575919419 and ext like '2017-04%' and ext>='2017-04-04' and ext <= '2017-04-11' and work_time > 0";
        InputStream is = new ByteArrayInputStream(expression.getBytes());
        CharStream cs = CharStreams.fromStream(is);
        BadgerSqlLexer esl = new BadgerSqlLexer(cs);
        TokenStream ts = new CommonTokenStream(esl);
        BadgerSqlParser esp = new BadgerSqlParser(ts);
        esp.updateExpression();
        System.out.println(esp.getUpdateStatement());
        System.out.println(esp.getUpdateField());
        System.out.println(esp.getUpdateValues());
    }
}

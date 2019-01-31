package org.jfaster.badger.dialect;

import org.jfaster.badger.spi.Spi;

/**
 * 数据库sql 统一接口
 * @author yanpengfang
 * @create 2019-01-03 9:42 PM
 */
@Spi
public interface Dialect {
    /*
     * 获取inser一条记录的sql，字段为null的不添加
     */
    InsertResult insertSqlNotNull(Object t, boolean ignore) throws Exception;

    /*
     * 获取inser一条记录的sql
     */
    InsertResult insertSql(Object t, boolean ignore) throws Exception;

    /*
     * 获取inser一条记录的sql，字段为null的不添加
     */
    InsertResult insertSqlNotNull(Object t, String tableName, boolean ignore) throws Exception;

    /*
     * 获取inser一条记录的sql
     */
    InsertResult insertSql(Object t, String tableName, boolean ignore) throws Exception;

    /*
     * 获取get单个对象的sql
     */
    String getSql(Class<?> clazz) throws Exception;

    String getSql(Class<?> clazz, String tableName) throws Exception;

    /*
     * 获取删除单个对象的语句
     */
    String deleteEntitySql(Class<?> clazz) throws Exception;

    String deleteEntitySql(Class<?> clazz, String tableName) throws Exception;

    String deleteConditionSql(Class<?> clazz, String condition) throws Exception;

    String deleteConditionSql(Class<?> clazz, String condition, String tableName) throws Exception;

    /*
     * 获取update语句
     */
    String updateEntitySql(Class<?> clazz) throws Exception;

    String updateEntitySql(Class<?> clazz, String tableName) throws Exception;

    String updateSql(Class<?> clazz, String updateStatement, String condition) throws Exception;

    String updateSql(Class<?> clazz, String updateStatement, String condition, String tableName) throws Exception;

    /**
     * 查询所有
     */
    String selectAllSql(Class<?> clazz) throws Exception;

    String selectAllSql(Class<?> clazz, String tableName) throws Exception;

    /**
     * 查询
     */
    String selectSql(Class<?> clazz, String columns) throws Exception;

    String selectSql(Class<?> clazz, String columns, String tableName) throws Exception;


    /**
     * count 操作
     */
    String countSql(Class<?> clazz) throws Exception;

    String countSql(Class<?> clazz, String condition) throws Exception;

    String countSql(Class<?> clazz, String tableName, String condition) throws Exception;

    String getPageSql(String sql, int pageIndex, int pageSize) throws Exception;
}

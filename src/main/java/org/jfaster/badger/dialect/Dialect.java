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
    InsertResult insertSqlNotNull(Object t, boolean ignore);

    /*
     * 获取inser一条记录的sql
     */
    InsertResult insertSql(Object t, boolean ignore);

    /*
     * 获取inser一条记录的sql，字段为null的不添加
     */
    InsertResult insertSqlNotNull(Object t, String tableName, boolean ignore);

    /*
     * 获取inser一条记录的sql
     */
    InsertResult insertSql(Object t, String tableName, boolean ignore);

    /*
     * 获取get单个对象的sql
     */
    String getSql(Class<?> clazz);

    String getSql(Class<?> clazz, String tableName);

    /*
     * 获取删除单个对象的语句
     */
    String deleteEntitySql(Class<?> clazz);

    String deleteEntitySql(Class<?> clazz, String tableName);

    String deleteConditionSql(Class<?> clazz, String condition);

    String deleteConditionSql(Class<?> clazz, String condition, String tableName);

    /*
     * 获取update语句
     */
    String updateEntitySql(Class<?> clazz);

    String updateEntitySql(Class<?> clazz, String tableName);

    String updateSql(Class<?> clazz, String updateStatement, String condition);

    String updateSql(Class<?> clazz, String updateStatement, String condition, String tableName);

    /**
     * 查询所有
     */
    String selectAllSql(Class<?> clazz);

    String selectAllSql(Class<?> clazz, String tableName);

    /**
     * 查询
     */
    String selectSql(Class<?> clazz, String columns);

    String selectSql(Class<?> clazz, String columns, String tableName);


    /**
     * count 操作
     */
    String countSql(Class<?> clazz);

    String countSql(Class<?> clazz, String condition);

    String countSql(Class<?> clazz, String tableName, String condition);

    String getPageSql(String sql, int pageIndex, int pageSize);
}

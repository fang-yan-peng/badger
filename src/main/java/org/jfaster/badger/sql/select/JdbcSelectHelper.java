package org.jfaster.badger.sql.select;

import java.util.List;

import org.jfaster.badger.Badger;
import org.jfaster.badger.dialect.Dialect;
import org.jfaster.badger.jdbc.extractor.support.ListResultSetExtractor;
import org.jfaster.badger.jdbc.extractor.support.ObjectResultSetExtractor;
import org.jfaster.badger.jdbc.mapper.RowMapplerRegistry;
import org.jfaster.badger.query.shard.ShardResult;
import org.jfaster.badger.spi.ExtensionLoader;
import org.jfaster.badger.sql.JdbcHelper;
import org.jfaster.badger.util.CheckConditions;
import org.jfaster.badger.util.ShardUtils;

/**
 * 查询辅助类
 * @author yanpengfang
 * @create 2019-01-27 1:54 PM
 */
public class JdbcSelectHelper {
    /**
     * 查询所有列列表
     * @param clazz
     * @param condition
     * @param paramList
     * @param badger
     * @param useMaster
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> find(Class<T> clazz, String condition, List<Object> paramList, Badger badger, boolean useMaster) throws Exception {
        return find(clazz, "*", condition, paramList, badger, useMaster);
    }

    /**
     * 查询指定列列表
     * @param clazz
     * @param columns
     * @param condition
     * @param paramList
     * @param badger
     * @param useMaster
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> find(Class<T> clazz, String columns, String condition, List<Object> paramList, Badger badger, boolean useMaster) throws Exception {
        CheckConditions.checkNotNull(columns, "查询列不能为空");
        CheckConditions.checkNotNull(condition, "查询条件不能为空");
        StringBuilder sql = new StringBuilder();
        Dialect dialect = ExtensionLoader.get(Dialect.class).getExtension(badger.getDialect());
        ShardResult shardResult = ShardUtils.shard(clazz, condition, paramList, badger);
        List<String> dynamicFields = shardResult.getDynamicFields();
        String tableName = shardResult.getTableName();
        String dbName = shardResult.getDataSourceName();
        if (tableName == null) {
            sql.append(("*".equals(columns.trim())) ? (dialect.selectAllSql(clazz)) : (dialect.selectSql(clazz, columns)));
        } else {
            sql.append(("*".equals(columns.trim())) ? (dialect.selectAllSql(clazz, tableName)) : (dialect.selectSql(clazz, columns, tableName)));
        }
        sql.append(" WHERE ").append(condition);
        return JdbcHelper.executeQuery(badger, clazz, dynamicFields, dbName,
                paramList, sql.toString(),
                new ListResultSetExtractor<>(RowMapplerRegistry.getRowMapper(clazz)), useMaster, true);
    }

    /**
     * 分页查询指定列
     * @param clazz
     * @param condition
     * @param paramList
     * @param pageIndex
     * @param pageSize
     * @param badger
     * @param useMaster
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> findByPage(Class<T> clazz, String condition,
            List<Object> paramList, int pageIndex, int pageSize, Badger badger, boolean useMaster) throws Exception {
        return findByPage(clazz, "*", condition, paramList, pageIndex, pageSize, badger, useMaster);
    }

    /**
     * 分页查询指定列
     * @param clazz
     * @param columns
     * @param condition
     * @param paramList
     * @param pageIndex
     * @param pageSize
     * @param badger
     * @param useMaster
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> findByPage(Class<T> clazz, String columns, String condition,
            List<Object> paramList, int pageIndex, int pageSize, Badger badger, boolean useMaster) throws Exception {
        CheckConditions.checkNotNull(columns, "查询列不能为空");
        CheckConditions.checkNotNull(condition, "查询条件不能为空");
        CheckConditions.checkPageSize(pageSize, badger.getPageSizeLimit());
        StringBuilder sql = new StringBuilder();
        Dialect dialect = ExtensionLoader.get(Dialect.class).getExtension(badger.getDialect());
        ShardResult shardResult = ShardUtils.shard(clazz, condition, paramList,badger);
        List<String> dynamicFields = shardResult.getDynamicFields();
        String tableName = shardResult.getTableName();
        String dbName = shardResult.getDataSourceName();
        int start = (pageIndex - 1) * pageSize;
        if (start < 0) {
            start = 0;
        }
        if (tableName == null) {
            sql.append(("*".equals(columns.trim())) ? (dialect.selectAllSql(clazz)) : (dialect.selectSql(clazz, columns)));
        } else {
            sql.append(("*".equals(columns.trim())) ? (dialect.selectAllSql(clazz, tableName)) : (dialect.selectSql(clazz, columns, tableName)));
        }
        sql.append(" WHERE ").append(condition);
        String resSql = dialect.getPageSql(sql.toString(), start, pageSize);
        return JdbcHelper.executeQuery(badger, clazz, dynamicFields, dbName,
                paramList, resSql,
                new ListResultSetExtractor<>(RowMapplerRegistry.getRowMapper(clazz)), useMaster, true);
    }

    /**
     * 根据条件count
     * @param clazz
     * @param condition
     * @param paramList
     * @param badger
     * @param useMaster
     * @param <T>
     * @return
     */
    public static <T> long count(Class<T> clazz, String condition, List<Object> paramList, Badger badger, boolean useMaster) throws Exception {
        CheckConditions.checkNotNull(condition, "查询条件不能为空");
        Dialect dialect = ExtensionLoader.get(Dialect.class).getExtension(badger.getDialect());
        ShardResult shardResult = ShardUtils.shard(clazz, condition, paramList, badger);
        List<String> dynamicFields = shardResult.getDynamicFields();
        String tableName = shardResult.getTableName();
        String dbName = shardResult.getDataSourceName();
        String sql = tableName == null ? dialect.countSql(clazz, condition) : dialect.countSql(clazz, tableName, condition);
        return JdbcHelper.executeQuery(badger, clazz, dynamicFields, dbName,
                paramList, sql,
                new ObjectResultSetExtractor<>(RowMapplerRegistry.getRowMapper(long.class)), useMaster, true);
    }
}

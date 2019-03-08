package org.jfaster.badger.sql.delete;

import java.util.Collections;
import java.util.List;

import org.jfaster.badger.Badger;
import org.jfaster.badger.dialect.Dialect;
import org.jfaster.badger.query.shard.ShardResult;
import org.jfaster.badger.spi.ExtensionLoader;
import org.jfaster.badger.sql.JdbcHelper;
import org.jfaster.badger.util.CheckConditions;
import org.jfaster.badger.util.ManualShardUtils;
import org.jfaster.badger.util.ShardUtils;
import org.jfaster.badger.util.SqlUtils;

/**
 * delete 辅助类
 * @author yanpengfang
 * create 2019-01-13 10:11 PM
 */
public class JdbcDeleteHelper {

    /**
     * 根据主键删除
     * @param clazz
     * @param id
     * @param badger
     * @return
     */
    public static <T> int deleteEntity(Class<T> clazz, Object id, Badger badger) {
        ShardResult shardResult = ShardUtils.shard(clazz, id);
        return execDeleteEntity(clazz, id, shardResult, badger);
    }

    /**
     * 根据条件删除
     * @param clazz
     * @param condition
     * @param parameters
     * @param badger
     * @return
     */
    public static <T> int deleteByCondition(Class<T> clazz, String condition, List<Object> parameters, Badger badger) {
        ShardResult shardResult = ShardUtils.shard(clazz, condition, parameters, badger);
        return execDeleteByCondition(clazz, condition, parameters, shardResult, badger);
    }

    /**
     * 根据主键删除
     * @param clazz
     * @param id
     * @param badger
     * @param shardValue
     * @return
     */
    public static <T> int deleteEntity(Class<T> clazz, Object id, Object shardValue, Badger badger) {
        ShardResult shardResult = ManualShardUtils.shard(clazz, shardValue);
        return execDeleteEntity(clazz, id, shardResult, badger);
    }

    /**
     * 根据条件删除
     * @param clazz
     * @param condition
     * @param parameters
     * @param shardValue
     * @param badger
     * @return
     */
    public static <T> int deleteByCondition(Class<T> clazz, String condition, List<Object> parameters, Object shardValue, Badger badger) {
        ShardResult shardResult = ManualShardUtils.shard(clazz, condition, parameters, shardValue, badger);
        return execDeleteByCondition(clazz, condition, parameters, shardResult, badger);
    }

    private static <T> int execDeleteEntity(Class<T> clazz, Object id, ShardResult shardResult, Badger badger) {
        Dialect dialect = ExtensionLoader.get(Dialect.class).getExtension(badger.getDialect());
        String tableName = shardResult.getTableName();
        String dbName = shardResult.getDataSourceName();
        String sql = tableName == null ? dialect.deleteEntitySql(clazz) : dialect.deleteEntitySql(clazz, tableName);
        return JdbcHelper.executeUpdate(badger, clazz, SqlUtils.getIdFields(clazz), dbName,
                Collections.singletonList(id), sql, true);
    }

    private static <T> int execDeleteByCondition(Class<T> clazz, String condition, List<Object>
            parameters,
            ShardResult shardResult, Badger badger) {
        CheckConditions.checkNotNull(condition, "查询条件不能为空");
        Dialect dialect = ExtensionLoader.get(Dialect.class).getExtension(badger.getDialect());
        String tableName = shardResult.getTableName();
        String dbName = shardResult.getDataSourceName();
        List<String> dynamicFields = shardResult.getDynamicFields();
        String sql = tableName == null ? dialect.deleteConditionSql(clazz, condition) : dialect.deleteConditionSql(clazz, condition, tableName);
        return JdbcHelper.executeUpdate(badger, clazz, dynamicFields, dbName, parameters, sql, true);
    }
}

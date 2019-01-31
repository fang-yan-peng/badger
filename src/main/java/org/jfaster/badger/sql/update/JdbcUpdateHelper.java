package org.jfaster.badger.sql.update;

import java.util.List;

import org.jfaster.badger.Badger;
import org.jfaster.badger.dialect.Dialect;
import org.jfaster.badger.query.shard.ShardResult;
import org.jfaster.badger.spi.ExtensionLoader;
import org.jfaster.badger.sql.JdbcHelper;
import org.jfaster.badger.util.CheckConditions;
import org.jfaster.badger.util.Lists;
import org.jfaster.badger.util.ShardUtils;
import org.jfaster.badger.util.SqlUtils;

/**
 * update 辅助类
 * @author yanpengfang
 * @create 2019-01-19 5:02 PM
 */
public class JdbcUpdateHelper {

    /**
     * 根据id 更新所有字段
     * @param t
     * @param badger
     * @return
     */
    public static int updateEntity(Object t, Badger badger) throws Exception {
        Class<?> clazz = t.getClass();
        ShardResult shardResult = ShardUtils.shard(t, true);
        Dialect dialect = ExtensionLoader.get(Dialect.class).getExtension(badger.getDialect());
        String tableName = shardResult.getTableName();
        String dbName = shardResult.getDataSourceName();
        String sql = tableName == null ? dialect.updateEntitySql(clazz) : dialect.updateEntitySql(clazz, tableName);
        List<String> fields = SqlUtils.getFields(clazz);
        return JdbcHelper.executeUpdate(badger, clazz, fields, dbName, Lists.transform(fields, (f) -> SqlUtils.getValueByField(t, f)), sql);
    }

    /**
     * 根据条件更新指定字段
     * @param clazz
     * @param updateStatement
     * @param condition
     * @param parameters
     * @return
     */
    public static <T> int updateByCondition(Class<T> clazz, String updateStatement,
            String condition, List<Object> parameters, Badger badger) throws Exception {
        CheckConditions.checkNotNull(condition, "查询条件不能为空");
        Dialect dialect = ExtensionLoader.get(Dialect.class).getExtension(badger.getDialect());
        ShardResult shardResult = ShardUtils.shard(clazz, updateStatement, condition, parameters);
        List<String> dynamicFields = shardResult.getDynamicFields();
        String tableName = shardResult.getTableName();
        String dbName = shardResult.getDataSourceName();
        String sql = tableName == null ? dialect.updateSql(clazz, updateStatement, condition) : dialect.updateSql(clazz, updateStatement, condition, tableName);
        return JdbcHelper.executeUpdate(badger, clazz, dynamicFields, dbName, parameters, sql);
    }
}

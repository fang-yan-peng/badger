package org.jfaster.badger.sql.get;

import java.util.Collections;

import org.jfaster.badger.Badger;
import org.jfaster.badger.dialect.Dialect;
import org.jfaster.badger.jdbc.extractor.support.ObjectResultSetExtractor;
import org.jfaster.badger.jdbc.mapper.RowMapplerRegistry;
import org.jfaster.badger.query.shard.ShardResult;
import org.jfaster.badger.spi.ExtensionLoader;
import org.jfaster.badger.sql.JdbcHelper;
import org.jfaster.badger.util.ManualShardUtils;
import org.jfaster.badger.util.ShardUtils;
import org.jfaster.badger.util.SqlUtils;

/**
 * 根据id查询
 * @author yanpengfang
 * create 2019-01-25 9:28 PM
 */
public class JdbcGetHelper {

    private static Integer int0 = 0;

    private static Long long0 = 0L;

    /**
     *
     * @param clazz
     * @param id
     * @param badger
     * @param useMaster
     * @param <T>
     * @return
     */
    public static <T> T get(Class<T> clazz, Object id, Badger badger, boolean useMaster) {
        if (id == null) {
            return null;
        }
        if ((id instanceof Long) && id.equals(long0)) {
            return null;
        } else if ((id instanceof Integer) && id.equals(int0)) {
            return null;
        }
        ShardResult shardResult = ShardUtils.shard(clazz, id);
        return execGet(clazz, id, shardResult, badger, useMaster);
    }

    /**
     *
     * @param clazz
     * @param id
     * @param badger
     * @param useMaster
     * @param <T>
     * @return
     */
    public static <T> T get(Class<T> clazz, Object id, Object shardValue, Badger badger, boolean useMaster) {
        if (id == null) {
            return null;
        }
        if ((id instanceof Long) && id.equals(long0)) {
            return null;
        } else if ((id instanceof Integer) && id.equals(int0)) {
            return null;
        }
        ShardResult shardResult = ManualShardUtils.shard(clazz, shardValue);
        return execGet(clazz, id, shardResult, badger, useMaster);
    }

    private static <T> T execGet(Class<T> clazz, Object id, ShardResult shardResult, Badger badger, boolean useMaster) {
        Dialect dialect = ExtensionLoader.get(Dialect.class).getExtension(badger.getDialect());
        String tableName = shardResult.getTableName();
        String dbName = shardResult.getDataSourceName();
        String sql = tableName == null ? dialect.getSql(clazz) : dialect.getSql(clazz, tableName);
        return JdbcHelper.executeQuery(badger, clazz, SqlUtils.getIdFields(clazz),
                dbName, Collections.singletonList(id), sql,
                new ObjectResultSetExtractor<>(RowMapplerRegistry.getRowMapper(clazz)),
                useMaster, true);
    }
}

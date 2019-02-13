package org.jfaster.badger.sql.select;

import java.util.List;

import org.jfaster.badger.Badger;
import org.jfaster.badger.jdbc.extractor.support.ListResultSetExtractor;
import org.jfaster.badger.jdbc.mapper.RowMapplerRegistry;
import org.jfaster.badger.sql.JdbcHelper;

/**
 * 原生查询sql
 * @author yanpengfang
 * @create 2019-01-29 11:16 PM
 */
public class JdbcSqlSelectHelper {

    public static <T> List<T> find(Class<T> clazz, String sql,
            String dbName, List<Object> paramList,
            Badger badger, boolean useMaster) {
        return JdbcHelper.executeQuery(badger, dbName, paramList, sql, new ListResultSetExtractor<>(RowMapplerRegistry.getRowMapper(clazz)), useMaster);
    }
}

package org.jfaster.badger.jdbc.mapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jfaster.badger.jdbc.mapper.support.BeanPropertyRowMapper;
import org.jfaster.badger.jdbc.mapper.support.SingleColumnRowMapper;
import org.jfaster.badger.jdbc.type.TypeHandlerRegistry;
import org.jfaster.badger.util.SqlUtils;

/**
 *
 * @author yanpengfang
 * @create 2019-01-27 12:16 PM
 */
public class RowMapplerRegistry {

    private static ConcurrentHashMap<Class<?>, RowMapper<?>> mappers = new ConcurrentHashMap<>();

    @SuppressWarnings("ALL")
    public static <T> RowMapper<T> getRowMapper(Class<T> clazz) {
        return (RowMapper<T>) mappers.computeIfAbsent(clazz, c -> {
            if (TypeHandlerRegistry.hasTypeHandler(c)) { // 单列mapper
                return new SingleColumnRowMapper(c);
            }
            // 类属性mapper
            return new BeanPropertyRowMapper(c, SqlUtils.getProperties2Columns(c));
        });
    }
}

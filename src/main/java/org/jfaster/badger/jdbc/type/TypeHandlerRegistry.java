package org.jfaster.badger.jdbc.type;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jfaster.badger.jdbc.JdbcType;

/**
 *
 * @author yanpengfang
 * @create 2019-01-06 11:58 AM
 */
public class TypeHandlerRegistry {
    private static final Map<Type, Map<JdbcType, TypeHandler<?>>> TYPE_HANDLER_MAP = new HashMap<Type, Map<JdbcType, TypeHandler<?>>>();
    static {
        register(Boolean.class, new BooleanTypeHandler());
        register(Byte.class, new ByteTypeHandler());
        register(Short.class, new ShortTypeHandler());
        register(Integer.class, new IntegerTypeHandler());
        register(Long.class, new LongTypeHandler());
        register(Float.class, new FloatTypeHandler());
        register(Double.class, new DoubleTypeHandler());
        register(Character.class, new CharacterTypeHandler());

        register(boolean.class, new BooleanTypeHandler());
        register(byte.class, new ByteTypeHandler());
        register(short.class, new ShortTypeHandler());
        register(int.class, new IntegerTypeHandler());
        register(long.class, new LongTypeHandler());
        register(float.class, new FloatTypeHandler());
        register(double.class, new DoubleTypeHandler());
        register(char.class, new CharacterTypeHandler());

        register(String.class, new StringTypeHandler());
        register(String.class, JdbcType.CHAR, new StringTypeHandler());
        register(String.class, JdbcType.CLOB, new ClobTypeHandler());
        register(String.class, JdbcType.VARCHAR, new StringTypeHandler());
        register(String.class, JdbcType.LONGVARCHAR, new ClobTypeHandler());
        register(String.class, JdbcType.NVARCHAR, new NStringTypeHandler());
        register(String.class, JdbcType.NCHAR, new NStringTypeHandler());
        register(String.class, JdbcType.NCLOB, new NClobTypeHandler());

        register(BigInteger.class, new BigIntegerTypeHandler());
        register(BigDecimal.class, new BigDecimalTypeHandler());

        register(Byte[].class, new ByteObjectArrayTypeHandler());
        register(Byte[].class, JdbcType.BLOB, new BlobByteObjectArrayTypeHandler());
        register(Byte[].class, JdbcType.LONGVARBINARY, new BlobByteObjectArrayTypeHandler());
        register(byte[].class, new ByteArrayTypeHandler());
        register(byte[].class, JdbcType.BLOB, new BlobTypeHandler());
        register(byte[].class, JdbcType.LONGVARBINARY, new BlobTypeHandler());

        register(Date.class, new DateTypeHandler());
        register(Date.class, JdbcType.DATE, new DateOnlyTypeHandler());
        register(Date.class, JdbcType.TIME, new TimeOnlyTypeHandler());

        register(java.sql.Date.class, new SqlDateTypeHandler());
        register(java.sql.Time.class, new SqlTimeTypeHandler());
        register(java.sql.Timestamp.class, new SqlTimestampTypeHandler());
    }

    public static boolean hasTypeHandler(Class<?> javaType) {
        return getNullableTypeHandler(javaType) != null;
    }

    public static <T> TypeHandler<T> getNullableTypeHandler(Class<T> type) {
        return getNullableTypeHandler((Type) type, null);
    }

    public static <T> TypeHandler<T> getTypeHandler(Class<T> type) {
        return getTypeHandler(type, null);
    }

    public static <T> TypeHandler<T> getNullableTypeHandler(Class<T> type, JdbcType jdbcType) {
        return getNullableTypeHandler((Type) type, jdbcType);
    }

    public static <T> TypeHandler<T> getTypeHandler(Class<T> type, JdbcType jdbcType) {
        TypeHandler<T> typeHandler = getNullableTypeHandler((Type) type, jdbcType);
        if (typeHandler == null) {
            throw new TypeException("Can't get type handle, java type is '" + type + "', jdbc type is '" + jdbcType + "'");
        }
        return typeHandler;
    }

    @SuppressWarnings("unchecked")
    private static <T> TypeHandler<T> getNullableTypeHandler(Type type, JdbcType jdbcType) {
        Map<JdbcType, TypeHandler<?>> jdbcHandlerMap = TYPE_HANDLER_MAP.get(type);
        TypeHandler<?> handler = null;
        if (jdbcHandlerMap != null) {
            handler = jdbcHandlerMap.get(jdbcType);
            if (handler == null) {
                handler = jdbcHandlerMap.get(null);
            }
        }
        return (TypeHandler<T>) handler;
    }

    public static <T> void register(Class<T> javaType, TypeHandler<? extends T> typeHandler) {
        register((Type) javaType, typeHandler);
    }

    private static <T> void register(Type javaType, TypeHandler<? extends T> typeHandler) {
        register(javaType, null, typeHandler);
    }

    public static <T> void register(Class<T> type, JdbcType jdbcType, TypeHandler<? extends T> handler) {
        register((Type) type, jdbcType, handler);
    }

    private static void register(Type javaType, JdbcType jdbcType, TypeHandler<?> handler) {
        Map<JdbcType, TypeHandler<?>> map = TYPE_HANDLER_MAP.computeIfAbsent(javaType, k -> new HashMap<>());
        map.put(jdbcType, handler);
    }
}

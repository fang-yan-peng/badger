package org.jfaster.badger.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.exception.MappingException;
import org.jfaster.badger.query.annotations.Extension;
import org.jfaster.badger.query.bean.invoker.GetterInvoker;
import org.jfaster.badger.query.bean.invoker.SetterInvoker;
import org.jfaster.badger.query.bean.invoker.support.InvokerCache;
import org.jfaster.badger.query.shard.ShardTableInfo;
import org.jfaster.badger.query.shard.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yanpengfang
 * create 2019-01-09 5:40 PM
 */
public class SqlUtils {

    private static final Logger logger = LoggerFactory.getLogger(SqlUtils.class);

    private static Map<Class<?>, ClassInfo> classInfoCache = new ConcurrentHashMap<>();

    private static Map<Class<?>, List<String>> mapColumns = new ConcurrentHashMap<>();

    private static Map<Class<?>, List<String>> mapFields = new ConcurrentHashMap<>();

    private static Map<Class<?>, SetterInvoker> idSetters = new ConcurrentHashMap<>();

    private static final Object lock = new Object();


    public static TableInfo getTableInfo(Class<?> clazz) {
        return getClassInfo(clazz).getTableInfo();
    }

    public static ShardTableInfo getShardTableInfo(Class<?> clazz) {
        return getClassInfo(clazz).getShardTableInfo();
/*
        checkNotNull(tableInfo.getColumn(), "annotated @ShardTable should annotated @ShardColumn");

        return tableInfo;
        */
    }

    public static List<String> getAllColumns(Class<?> clazz) {
        List<String> columns = mapColumns.get(clazz);
        if (columns != null) {
            return columns;
        }
        ClassInfo classInfo = getClassInfo(clazz);
        List<String> idColumns = classInfo.getIdColumns();
        List<String> otherColumns = classInfo.getColumns();
        columns = new ArrayList<>(idColumns.size() + otherColumns.size());
        columns.addAll(idColumns);
        columns.addAll(otherColumns);
        mapColumns.put(clazz, columns);
        return columns;
    }

    public static List<String> getColumns(Class<?> clazz) {
        return getClassInfo(clazz).getColumns();
    }

    public static List<String> getFields(Class<?> clazz) {
        return getClassInfo(clazz).getFields();
    }

    public static List<String> getAllFields(Class<?> clazz) {
        List<String> fields = mapFields.get(clazz);
        if (fields != null) {
            return fields;
        }
        ClassInfo classInfo = getClassInfo(clazz);
        List<String> idFields = classInfo.getIdFields();
        List<String> otherFields = classInfo.getFields();
        fields = new ArrayList<>(idFields.size() + otherFields.size());
        fields.addAll(otherFields);
        fields.addAll(idFields);
        mapFields.put(clazz, fields);
        return fields;
    }


    public static List<String> getIdFields(Class<?> clazz) {
        return getClassInfo(clazz).getIdFields();
    }

    public static List<String> getIdColumns(Class<?> clazz) {
        return getClassInfo(clazz).getIdColumns();
    }

    public static Object getPKValue(Object t) {
        Class<?> clazz = t.getClass();
        List<String> idFieldList = getIdFields(clazz);
        if (!idFieldList.isEmpty()) {
            if (idFieldList.size() == 1) {
                GetterInvoker invoker = InvokerCache.getGetterInvoker(clazz, idFieldList.get(0));
                if (invoker == null) {
                    throw new MappingException(idFieldList.get(0) + " getter method not fund");
                }
                return invoker.invoke(t);
            } else {
                throw new BadgerException("不支持联合主键，只能支持单主键");
            }
        }
        return null;
    }

    public static boolean hasPKValue(Object t) {
        Object id = getPKValue(t);
        if (id != null) {
            if ((id instanceof Long) && id.equals(0L)) {
                return false;
            }
            return !(id instanceof Integer) || !id.equals(0);
        }
        return false;
    }

    public static Object getValueByColumn(Object t, String column) {
        Class<?> clazz = t.getClass();
        String field = getClassInfo(clazz).getFieldByColumn(column);
        if (Strings.isNullOrEmpty(field)) {
            throw new MappingException("column:" + column + " not match field of " + clazz.getName());
        }
        GetterInvoker invoker = InvokerCache.getGetterInvoker(clazz, field);
        if (invoker == null) {
            throw new MappingException(field + " getter not exist");
        }
        return invoker.invoke(t);
    }

    public static Object getValueByField(Object t, String fieldName) {
        Class<?> clazz = t.getClass();
        GetterInvoker invoker = InvokerCache.getGetterInvoker(clazz, fieldName);
        if (invoker == null) {
            throw new MappingException(fieldName + " getter not exist");
        }
        return invoker.invoke(t);
    }

    public static SetterInvoker getIdSetterInvoker(Class<?> clazz) {
        SetterInvoker invoker = idSetters.get(clazz);
        if (invoker != null) {
            return invoker;
        }
        List<String> ids = getClassInfo(clazz).getIdFields();
        if (!ids.isEmpty()) {
            if (ids.size() == 1) {
                invoker = InvokerCache.getSetterInvoker(clazz, ids.get(0));
                if (invoker == null) {
                    throw new MappingException(ids.get(0) + " setter method not fund");
                }
                idSetters.put(clazz, invoker);
                return invoker;
            } else {
                throw new BadgerException("不支持联合主键，只能支持单主键");
            }
        }
        return null;
    }

    public static SetterInvoker getSetterInvoker(Class<?> clazz, String column) {
        String field = getClassInfo(clazz).getFieldByColumn(column);
        if (Strings.isNullOrEmpty(field)) {
            throw new MappingException("column:" + column + " not match field of " + clazz.getName());
        }
        SetterInvoker invoker = InvokerCache.getSetterInvoker(clazz, field);
        if (invoker == null) {
            throw new MappingException(field + " setter method not fund");
        }
        return invoker;
    }

    public static GetterInvoker getGetterInvoker(Class<?> clazz, String field) {
        GetterInvoker invoker = InvokerCache.getGetterInvoker(clazz, field);
        if (invoker == null) {
            while (clazz.isAnnotationPresent(Extension.class)) {
                Extension extension = clazz.getAnnotation(Extension.class);
                Class<?> eClazz = extension.extend();
                if (!eClazz.equals(Void.class)) {
                    invoker = InvokerCache.getGetterInvoker(eClazz, field);
                    if (invoker != null) {
                        return invoker;
                    }
                }
                clazz = eClazz;
            }
            throw new MappingException(field + " getter method not fund");
        }
        return invoker;
    }

    public static String getFieldByColumn(Class<?> clazz, String column) {
        String field = getClassInfo(clazz).getFieldByColumn(column);
        if (Strings.isNullOrEmpty(field)) {
            while (clazz.isAnnotationPresent(Extension.class)) {
                Extension extension = clazz.getAnnotation(Extension.class);
                Class<?> eClazz = extension.extend();
                if (!eClazz.equals(Void.class)) {
                    field = getClassInfo(eClazz).getFieldByColumn(column);
                    if (!Strings.isNullOrEmpty(field)) {
                        return field;
                    }
                }
                clazz = eClazz;
            }
            throw new MappingException("column:" + column + " not match field of " + clazz.getName());

        }
        return field;
    }

    public static Map<String, String> getProperties2Columns(Class<?> clazz) {
        return getClassInfo(clazz).getAllColumns();
    }

    public static Map<String, String> getColumns2Properties(Class<?> clazz) {
        return getClassInfo(clazz).getAllReverseColumns();
    }

    private static ClassInfo getClassInfo(Class<?> clazz) {
        ClassInfo ci = classInfoCache.get(clazz);
        if (ci != null) {
            return ci;
        }
        synchronized (lock) {
            ci = classInfoCache.get(clazz);
            if (ci != null) {
                return ci;
            }
            try {
                ci = new ClassInfo(clazz);
                classInfoCache.put(clazz, ci);
            } catch (Exception e) {
                logger.error("getClassInfo", e);
                throw new BadgerException(e);
            }
        }
        return ci;
    }
}

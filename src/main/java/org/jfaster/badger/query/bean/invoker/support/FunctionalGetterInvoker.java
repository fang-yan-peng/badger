package org.jfaster.badger.query.bean.invoker.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.jdbc.type.convert.NotConverter;
import org.jfaster.badger.jdbc.type.convert.TypeConverter;
import org.jfaster.badger.query.annotations.Column;
import org.jfaster.badger.query.bean.PropertyMeta;
import org.jfaster.badger.query.bean.invoker.GetterInvoker;

/**
 * 函数式getter方法调用器
 */
public class FunctionalGetterInvoker implements GetterInvoker {

    private TypeConverter function;
    private Class<?> rawType;
    private Class<?> jdbcType;
    private String name;
    private Method method;

    private FunctionalGetterInvoker(String name, PropertyMeta meta, Method method) {
        this.method = method;
        this.name = name;
        this.rawType = meta.getType();
        this.jdbcType = meta.getType();
        Column col = meta.getPropertyAnno(Column.class);
        if (col != null) {
            Class<? extends TypeConverter> convertClz = col.convert();
            if (!convertClz.equals(NotConverter.class)) {
                try {
                    ParameterizedType genericType = (ParameterizedType) convertClz.getGenericInterfaces()[0];
                    jdbcType = (Class<?>) genericType.getActualTypeArguments()[1];
                    function = convertClz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new BadgerException(e);
                }
            }
        }
    }

    public static FunctionalGetterInvoker create(String name, PropertyMeta meta, Method method) {
        return new FunctionalGetterInvoker(name, meta, method);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object obj) {
        try {
            Object r = method.invoke(obj);
            if (function != null) {
                r = function.convert(r);
            }
            return r;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BadgerException(e);
        }
    }

    @Override
    public Class<?> getRawType() {
        return rawType;
    }

    @Override
    public Class<?> getJdbcType() {
        return jdbcType;
    }

    @Override
    public TypeConverter getConverter() {
        return function;
    }

}

package org.jfaster.badger.query.bean.invoker.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.jdbc.type.convert.NotConverter;
import org.jfaster.badger.jdbc.type.convert.TypeConverter;
import org.jfaster.badger.query.annotations.Column;
import org.jfaster.badger.query.bean.PropertyMeta;
import org.jfaster.badger.query.bean.invoker.SetterInvoker;

public class FunctionalSetterInvoker implements SetterInvoker {

    private TypeConverter function;
    private Class<?> rawType;
    private Class<?> jdbcType;
    private String name;
    private Method method;

    private FunctionalSetterInvoker(String name, PropertyMeta meta, Method method) {
        this.method = method;
        this.name = name;
        this.rawType = meta.getType();
        this.jdbcType = meta.getType();
        Column col = meta.getPropertyAnno(Column.class);
        if (col != null) {
            Class<? extends TypeConverter> convertClz = col.convert();
            if (!convertClz.equals(NotConverter.class)) {
                try {
                    ParameterizedType genericType = (ParameterizedType)convertClz.getGenericInterfaces()[0];
                    jdbcType = (Class<?>) genericType.getActualTypeArguments()[1];
                    function = convertClz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new BadgerException(e);
                }
            }
        }
    }

    public static FunctionalSetterInvoker create(String name, PropertyMeta meta, Method method) {
        return new FunctionalSetterInvoker(name, meta, method);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void invoke(Object object, Object parameter) {
        try {
            if (function != null) {
                parameter = function.reverse(parameter, rawType);

            }
            if (parameter == null && rawType.isPrimitive()) {
                throw new NullPointerException("property " + name + " of " +
                        object.getClass() + " is primitive, can not be assigned to null");
            }
            /*if (parameter != null && !rawType.isAssignableFrom(parameter.getClass())) {
                throw new ClassCastException("cannot convert value of type [" + parameter.getClass().getName() +
                        "] to required type [" + rawType.getName() + "] " +
                        "for property '" + name + "' of " + object.getClass());
            }*/
            method.invoke(object, parameter);
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

    @Override
    public String name() {
        return name;
    }

}

package org.jfaster.badger.query.bean.invoker.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.jdbc.type.convert.NotConverter;
import org.jfaster.badger.jdbc.type.convert.TypeConverter;
import org.jfaster.badger.query.annotations.Column;
import org.jfaster.badger.query.bean.invoker.GetterInvoker;

/**
 * 函数式getter方法调用器
 */
public class FunctionalGetterInvoker implements GetterInvoker {

    private TypeConverter function;
    private Class<?> rawType;
    private String name;
    private Method method;

    private FunctionalGetterInvoker(String name, Class<?> rawType, Method method) {
        this.method = method;
        this.name = name;
        this.rawType = rawType;
        if (rawType.isAnnotationPresent(Column.class)) {
            Column col = rawType.getAnnotation(Column.class);
            Class<? extends TypeConverter> convertClz = col.convert();
            if (!convertClz.equals(NotConverter.class)) {
                try {
                    function = convertClz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new BadgerException(e);
                }
            }
        }
    }

    public static FunctionalGetterInvoker create(String name, Class<?> cls, Method method) {
        return new FunctionalGetterInvoker(name, cls, method);
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

}

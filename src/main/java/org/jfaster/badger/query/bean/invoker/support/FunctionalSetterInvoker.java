package org.jfaster.badger.query.bean.invoker.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.jdbc.type.convert.NotConverter;
import org.jfaster.badger.jdbc.type.convert.TypeConverter;
import org.jfaster.badger.query.annotations.Column;
import org.jfaster.badger.query.bean.invoker.SetterInvoker;

public class FunctionalSetterInvoker implements SetterInvoker {

  private TypeConverter function;
  private Class<?> rawType;
  private String name;
  private Method method;

  private FunctionalSetterInvoker(String name, Class<?> rawType, Method method) {
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

  public static FunctionalSetterInvoker create(String name, Class<?> cls, Method method) {
    return new FunctionalSetterInvoker(name, cls, method);
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
      if (parameter != null && !rawType.isAssignableFrom(parameter.getClass())) {
        throw new ClassCastException("cannot convert value of type [" + parameter.getClass().getName() +
                "] to required type [" + rawType.getName() + "] " +
                "for property '" + name + "' of " + object.getClass());
      }
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
  public String name() {
    return name;
  }

}

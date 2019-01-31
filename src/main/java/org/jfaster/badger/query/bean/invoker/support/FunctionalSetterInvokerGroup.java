package org.jfaster.badger.query.bean.invoker.support;


import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.jdbc.PropertyTokenizer;
import org.jfaster.badger.query.bean.invoker.GetterInvoker;
import org.jfaster.badger.query.bean.invoker.SetterInvoker;
import org.jfaster.badger.query.bean.invoker.SetterInvokerGroup;

public class FunctionalSetterInvokerGroup implements SetterInvokerGroup {

  private final Class<?> originalType;
  private final String propertyPath;
  private final Class<?> targetType;

  private FunctionalSetterInvokerGroup(Class<?> originalType, String propertyPath) {
    this.originalType = originalType;
    this.propertyPath = propertyPath;
    PropertyTokenizer prop = new PropertyTokenizer(propertyPath);
    if (!prop.hasNext()) {
      throw new IllegalStateException("property path '" + propertyPath + "' error");
    }
    Class<?> currentType = originalType;
    while (prop.hasCurrent()) {
      String propertyName = prop.getName();
      SetterInvoker setter = InvokerCache.getSetterInvoker(currentType, propertyName);
      if (prop.hasNext()) { // 后续还有属性则需检测set方法
        GetterInvoker getter = InvokerCache.getGetterInvoker(currentType, propertyName);
        if (!setter.getRawType().equals(getter.getRawType())) { // 有set方法，但get方法不对，抛出异常
          throw new BadgerException("Inconsistent setter/getter type for property named '" +
              propertyName + "' in '" + currentType + "'");
        }
      }
      currentType = setter.getRawType();
      prop = prop.next();
    }
    targetType = currentType;
  }

  public static FunctionalSetterInvokerGroup create(Class<?> originalType, String propertyPath) {
    return new FunctionalSetterInvokerGroup(originalType, propertyPath);
  }

  @Override
  public Class<?> getOriginalType() {
    return originalType;
  }

  @Override
  public Class<?> getTargetType() {
    return targetType;
  }

  @Override
  public void invoke(Object obj, Object value) {
    MetaObject mo = MetaObject.forObject(obj);
    mo.setValue(propertyPath, value);
  }

}

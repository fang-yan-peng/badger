package org.jfaster.badger.query.bean.invoker.support;


import org.jfaster.badger.jdbc.PropertyTokenizer;
import org.jfaster.badger.query.bean.invoker.GetterInvoker;
import org.jfaster.badger.query.bean.invoker.SetterInvoker;
import org.jfaster.badger.query.bean.invoker.SystemMetaObject;
import org.jfaster.badger.util.Reflections;

public class MetaObject {

  private Object originalObject;

  private Class<?> originalClass;

  private MetaObject(Object object) {
    this.originalObject = object;
    this.originalClass = object.getClass();
  }

  public static MetaObject forObject(Object object) {
    if (object == null) {
      return SystemMetaObject.NULL_META_OBJECT;
    } else {
      return new MetaObject(object);
    }
  }

  public Object getOriginalObject() {
    return originalObject;
  }

  private Object getValue(String propertyName) {
    GetterInvoker invoker = InvokerCache.getGetterInvoker(originalClass, propertyName);
    return invoker.invoke(originalObject);
  }

  public void setValue(String propertyPath, Object value) {
    PropertyTokenizer prop = new PropertyTokenizer(propertyPath);
    if (prop.hasNext()) {
      MetaObject metaValue = metaObjectForProperty(prop.getName());
      if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
        if (value == null && prop.getChildren() != null) {
          // don't instantiate child path if value is null
          return;
        } else {
          SetterInvoker invoker = InvokerCache.getSetterInvoker(originalClass, prop.getName());
          Class<?> clazz = invoker.getRawType();
          Object newObject = Reflections.instantiate(clazz);
          metaValue = MetaObject.forObject(newObject);
          invoker.invoke(originalObject, newObject);
        }
      }
      metaValue.setValue(prop.getChildren(), value);
    } else {
      SetterInvoker invoker = InvokerCache.getSetterInvoker(originalClass, propertyPath);
      invoker.invoke(originalObject, value);
    }
  }

  public MetaObject metaObjectForProperty(String name) {
    Object value = getValue(name);
    return MetaObject.forObject(value);
  }

}

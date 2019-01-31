package org.jfaster.badger.query.bean.invoker;

public interface SetterInvoker {

    void invoke(Object object, Object parameter);

    Class<?> getRawType();

    String name();

}

package org.jfaster.badger.query.bean.invoker;

public interface SetterInvokerGroup {

    Class<?> getOriginalType();

    Class<?> getTargetType();

    void invoke(Object obj, Object value);

}

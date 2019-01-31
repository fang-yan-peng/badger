package org.jfaster.badger.query.bean.invoker;

public interface GetterInvoker {

    Object invoke(Object obj);

    Class<?> getRawType();

}

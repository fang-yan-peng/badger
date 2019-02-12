package org.jfaster.badger.query.bean.invoker;

import org.jfaster.badger.jdbc.type.convert.TypeConverter;

public interface GetterInvoker {

    Object invoke(Object obj);

    Class<?> getRawType();

    Class<?> getJdbcType();

    TypeConverter getConverter();

}

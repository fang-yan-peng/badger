package org.jfaster.badger.query.bean.invoker;

import org.jfaster.badger.jdbc.type.convert.TypeConverter;

public interface SetterInvoker {

    void invoke(Object object, Object parameter);

    Class<?> getRawType();

    Class<?> getJdbcType();

    TypeConverter getConverter();

    String name();

}

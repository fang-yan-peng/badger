package org.jfaster.badger.query.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jfaster.badger.jdbc.datasource.support.AbstractDataSourceFactory;

/**
 * 表名
 * @author yanpengfang
 * @create 2019-01-04 9:09 PM
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    String tableName() default "";

    String dataSourceName() default AbstractDataSourceFactory.DEFULT_NAME;
}

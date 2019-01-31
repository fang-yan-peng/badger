package org.jfaster.badger.query.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jfaster.badger.jdbc.type.convert.NotConverter;
import org.jfaster.badger.jdbc.type.convert.TypeConverter;

/**
 *
 * @author yanpengfang
 * @create 2019-01-04 8:45 PM
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    String name() default "";

    String setFuncName() default "";

    String getFuncName() default "";

    boolean defaultValue() default false;

    Class<? extends TypeConverter> convert() default NotConverter.class;
}

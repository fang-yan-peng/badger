package org.jfaster.badger.query.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 根据其他类定义好的表结构，进行一些其他操作，比如sum，min等等产生的字段，这些字段并不属于原来的表，但是是根据原来的表结构生成的。
 * 通常用于查询操作，如果是insert或者update可以使用真正的类继承。
 * @author yanpengfang
 * create 2019-02-11 4:34 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Extension {
    Class<?> extend() default Void.class;
}

package org.jfaster.badger.jdbc.type.convert;

/**
 * bean类型和数据库类型转换器
 * @author yanpengfang
 * @create 2019-01-07 11:33 AM
 */
public interface TypeConverter<I, O>{
    O convert(I i); //bean property to jdbc type
    I reverse(O o, Class clz); //jdbc type to bean property

    String SEPARATOR = ",";

}

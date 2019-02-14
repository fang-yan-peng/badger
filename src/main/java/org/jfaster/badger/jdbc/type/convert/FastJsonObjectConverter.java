package org.jfaster.badger.jdbc.type.convert;

import com.alibaba.fastjson.JSON;

/**
 *
 * @author yanpengfang
 * create 2019-01-07 2:50 PM
 */
public class FastJsonObjectConverter implements TypeConverter<Object, String> {

    @Override
    public String convert(Object s) {
        return s == null ? "" : JSON.toJSONString(s);
    }

    @Override
    public Object reverse(String o, Class clz) {
        return o == null ? null : JSON.parseObject(o, clz);
    }
}

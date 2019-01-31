package org.jfaster.badger.jdbc.type.convert;

import java.util.EnumSet;

import org.jfaster.badger.cache.LoadingCache;
import org.jfaster.badger.cache.support.DoubleCheckCache;

/**
 * 枚举与整型间相互转换
 * @author yanpengfang
 * @create 2019-01-07 2:09 PM
 */
public class EnumIntegerConverter implements TypeConverter<Enum, Integer> {

    private final static LoadingCache<Class, EnumSet> cache = new DoubleCheckCache<Class, EnumSet>(
            EnumSet::allOf);

    @Override
    public Integer convert(Enum input) {
        return input == null ? null : input.ordinal();
    }

    @Override
    public Enum reverse(Integer o, Class clz) {
        if (o == null) {
            return null;
        }
        EnumSet<?> es = cache.get(clz);
        for (Enum<?> e : es) {
            if (e.ordinal() == o) {
                return e;
            }
        }
        throw new IllegalStateException("cant' trans Integer(" + o + ") to " + clz);
    }
}

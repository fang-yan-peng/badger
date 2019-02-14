package org.jfaster.badger.jdbc.type.convert;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author yanpengfang
 * create 2019-01-07 12:03 PM
 */
@SuppressWarnings("ALL")
public class IntegerSetStringConverter implements TypeConverter<Set<Integer>, String> {

    @Override
    public String convert(Set<Integer> integers) {
        if (integers == null) {
            return null;
        }
        if (integers.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Integer i : integers) {
            builder.append(i).append(SEPARATOR);
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    @Override
    public Set<Integer> reverse(String o, Class clz) {
        if (o == null) {
            return null;
        }
        if (o.length() == 0) {
            return new HashSet<>();
        }
        String[] ss = o.split(SEPARATOR);
        Set<Integer> r = new HashSet<>();
        for (String s : ss) {
            r.add(Integer.parseInt(s));
        }
        return r;
    }
}

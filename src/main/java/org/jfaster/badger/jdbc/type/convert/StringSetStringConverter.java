package org.jfaster.badger.jdbc.type.convert;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author yanpengfang
 * create 2019-01-07 12:03 PM
 */
@SuppressWarnings("ALL")
public class StringSetStringConverter implements TypeConverter<Set<String>, String> {

    @Override
    public String convert(Set<String> strings) {
        if (strings == null) {
            return null;
        }
        if (strings.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String i : strings) {
            builder.append(i).append(SEPARATOR);
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    @Override
    public Set<String> reverse(String o, Class clz) {
        if (o == null) {
            return null;
        }
        if (o.length() == 0) {
            return new HashSet<>();
        }
        String[] ss = o.split(SEPARATOR);
        Set<String> r = new HashSet<>();
        for (String s : ss) {
            r.add(s);
        }
        return r;
    }
}

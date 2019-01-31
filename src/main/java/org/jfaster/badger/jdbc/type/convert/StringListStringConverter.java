package org.jfaster.badger.jdbc.type.convert;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yanpengfang
 * @create 2019-01-07 12:03 PM
 */
@SuppressWarnings("ALL")
public class StringListStringConverter implements TypeConverter<List<String>, String> {

    @Override
    public String convert(List<String> strings) {
        if (strings == null) {
            return null;
        }
        if (strings.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(strings.get(0));
        for (int i = 1; i < strings.size(); i++) {
            builder.append(SEPARATOR).append(strings.get(i));
        }
        return builder.toString();
    }

    @Override
    public List<String> reverse(String o, Class clz) {
        if (o == null) {
            return null;
        }
        if (o.length() == 0) {
            return new ArrayList<>();
        }
        String[] ss = o.split(SEPARATOR);
        List<String> r = new ArrayList<>();
        for (String s : ss) {
            r.add(s);
        }
        return r;
    }
}

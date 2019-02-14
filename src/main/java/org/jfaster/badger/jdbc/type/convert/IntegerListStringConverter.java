package org.jfaster.badger.jdbc.type.convert;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yanpengfang
 * create 2019-01-07 12:03 PM
 */
@SuppressWarnings("ALL")
public class IntegerListStringConverter implements TypeConverter<List<Integer>, String> {

    @Override
    public String convert(List<Integer> integers) {
        if (integers == null) {
            return null;
        }
        if (integers.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(integers.get(0));
        for (int i = 1; i < integers.size(); i++) {
            builder.append(SEPARATOR).append(integers.get(i));
        }
        return builder.toString();
    }

    @Override
    public List<Integer> reverse(String o, Class clz) {
        if (o == null) {
            return null;
        }
        if (o.length() == 0) {
            return new ArrayList<>();
        }
        String[] ss = o.split(SEPARATOR);
        List<Integer> r = new ArrayList<>();
        for (String s : ss) {
            r.add(Integer.parseInt(s));
        }
        return r;
    }
}

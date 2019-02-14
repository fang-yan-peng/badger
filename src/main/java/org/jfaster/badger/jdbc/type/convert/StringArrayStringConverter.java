package org.jfaster.badger.jdbc.type.convert;

/**
 *
 * @author yanpengfang
 * create 2019-01-07 1:48 PM
 */
@SuppressWarnings("ALL")
public class StringArrayStringConverter implements TypeConverter<String[], String> {

    @Override
    public String convert(String[] strings) {
        if (strings == null) {
            return null;
        }
        if (strings.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(strings[0]);
        for (int i = 1; i < strings.length; ++i) {
            sb.append(SEPARATOR).append(strings[i]);
        }
        return sb.toString();
    }

    @Override
    public String[] reverse(String o, Class clz) {
        if (o == null) {
            return null;
        }
        if (o.length() == 0) {
            return new String[0];
        }
        return o.split(SEPARATOR);
    }
}

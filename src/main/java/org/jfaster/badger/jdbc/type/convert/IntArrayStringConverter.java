package org.jfaster.badger.jdbc.type.convert;

/**
 * int数组和string之间转化
 * @author yanpengfang
 * @create 2019-01-07 11:47 AM
 */
@SuppressWarnings("ALL")
public class IntArrayStringConverter implements TypeConverter<int[], String> {

    @Override
    public String convert(int[] ints) {
        if (ints == null) {
            return null;
        }
        if (ints.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(ints[0]);
        for (int i = 1; i < ints.length; ++i) {
            sb.append(SEPARATOR).append(ints[i]);
        }
        return sb.toString();
    }

    @Override
    public int[] reverse(String o, Class clz) {
        if (o == null) {
            return null;
        }
        if (o.length() == 0) {
            return new int[0];
        }
        String[] ss = o.split(SEPARATOR);
        int[] r = new int[ss.length];
        for (int i = 0; i < ss.length; i++) {
            r[i] = Integer.parseInt(ss[i]);
        }
        return r;
    }
}

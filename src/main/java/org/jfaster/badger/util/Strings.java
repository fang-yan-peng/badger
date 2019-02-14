package org.jfaster.badger.util;

/**
 * string 相关工具类
 * @author yanpengfang
 * create 2019-01-03 8:53 PM
 */
public class Strings {

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static String emptyToNull(String string) {
        return isNullOrEmpty(string) ? null : string;
    }

    public static String firstLetterToUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String underscoreName(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(name.substring(0, 1).toLowerCase());
        for (int i = 1; i < name.length(); i++) {
            String s = name.substring(i, i + 1);
            String slc = s.toLowerCase();
            if (!s.equals(slc)) {
                result.append("_").append(slc);
            } else {
                result.append(s);
            }
        }
        return result.toString();
    }
}

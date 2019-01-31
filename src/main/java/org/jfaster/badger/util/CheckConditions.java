package org.jfaster.badger.util;

/**
 *
 * @author yanpengfang
 * @create 2019-01-07 11:53 AM
 */
public class CheckConditions {

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    public static void checkPageSize(int pageSize, int limit) {
        if (pageSize > limit) {
            throw new RuntimeException("pageSize must not > " + limit);
        }
    }

}

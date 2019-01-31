package org.jfaster.badger.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author yanpengfang
 * @create 2019-01-19 4:39 PM
 */
@Data
@RequiredArgsConstructor
public class Pair {
    private final Class<?> clazz;
    private final String condition;

    public static Pair of(Class<?> clazz, String condition) {
        return new Pair(clazz, condition);
    }
}

package org.jfaster.badger.util;

import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author yanpengfang
 * create 2019-01-29 11:47 AM
 */
public class Iterators {
    static <T> ListIterator<T> cast(Iterator<T> iterator) {
        return (ListIterator<T>) iterator;
    }
}

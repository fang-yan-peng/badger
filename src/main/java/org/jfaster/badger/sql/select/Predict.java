package org.jfaster.badger.sql.select;

/**
 *
 * @author yanpengfang
 * @create 2019-04-19 6:14 PM
 */
@FunctionalInterface
public interface Predict<T> {
    boolean apply(T t);
}

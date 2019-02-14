package org.jfaster.badger.query.id;

/**
 * id产生器
 * @author yanpengfang
 * create 2019-01-04 8:51 PM
 */
public interface IdGenerator<T> {
    String getId(T value);
}

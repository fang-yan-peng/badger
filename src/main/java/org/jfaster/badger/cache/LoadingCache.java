package org.jfaster.badger.cache;

@FunctionalInterface
public interface LoadingCache<K, V> {

    V get(K key);

}

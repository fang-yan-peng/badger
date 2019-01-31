package org.jfaster.badger.cache;

@FunctionalInterface
public interface CacheLoader<K, V> {

    V load(K key);

}

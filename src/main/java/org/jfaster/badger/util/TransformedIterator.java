package org.jfaster.badger.util;

import static org.jfaster.badger.util.CheckConditions.checkNotNull;

import java.util.Iterator;

/**
 *
 * @author yanpengfang
 * create 2019-01-29 11:44 AM
 */
abstract class TransformedIterator<F, T> implements Iterator<T> {
    final Iterator<? extends F> backingIterator;

    TransformedIterator(Iterator<? extends F> backingIterator) {
        this.backingIterator = checkNotNull(backingIterator);
    }

    abstract T transform(F from);

    @Override
    public final boolean hasNext() {
        return backingIterator.hasNext();
    }

    @Override
    public final T next() {
        return transform(backingIterator.next());
    }

    @Override
    public final void remove() {
        backingIterator.remove();
    }
}

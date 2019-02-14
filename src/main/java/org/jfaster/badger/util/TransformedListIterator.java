package org.jfaster.badger.util;

import java.util.ListIterator;

/**
 *
 * @author yanpengfang
 * create 2019-01-29 11:44 AM
 */
abstract class TransformedListIterator<F, T> extends TransformedIterator<F, T>
        implements ListIterator<T> {
    TransformedListIterator(ListIterator<? extends F> backingIterator) {
        super(backingIterator);
    }

    private ListIterator<? extends F> backingIterator() {
        return Iterators.cast(backingIterator);
    }

    @Override
    public final boolean hasPrevious() {
        return backingIterator().hasPrevious();
    }

    @Override
    public final T previous() {
        return transform(backingIterator().previous());
    }

    @Override
    public final int nextIndex() {
        return backingIterator().nextIndex();
    }

    @Override
    public final int previousIndex() {
        return backingIterator().previousIndex();
    }

    @Override
    public void set(T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(T element) {
        throw new UnsupportedOperationException();
    }
}


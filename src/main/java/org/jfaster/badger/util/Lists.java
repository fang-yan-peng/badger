package org.jfaster.badger.util;

import static org.jfaster.badger.util.CheckConditions.checkNotNull;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.function.Function;

/**
 * list 工具类
 * @author yanpengfang
 * @create 2019-01-29 11:40 AM
 */
public class Lists {

    public static <F, T> List<T> transform(List<F> fromList, Function<? super F, ? extends T> function) {
        return (fromList instanceof RandomAccess)
                ? new TransformingRandomAccessList<F, T>(fromList, function)
                : new TransformingSequentialList<F, T>(fromList, function);
    }

    private static class TransformingRandomAccessList<F, T>
            extends AbstractList<T> implements RandomAccess, Serializable {
        final List<F> fromList;
        final Function<? super F, ? extends T> function;

        TransformingRandomAccessList(
                List<F> fromList, Function<? super F, ? extends T> function) {
            this.fromList = checkNotNull(fromList);
            this.function = checkNotNull(function);
        }

        @Override
        public void clear() {
            fromList.clear();
        }

        @Override
        public T get(int index) {
            return function.apply(fromList.get(index));
        }

        @Override
        public Iterator<T> iterator() {
            return listIterator();
        }

        @Override
        public ListIterator<T> listIterator(int index) {
            return new TransformedListIterator<F, T>(fromList.listIterator(index)) {
                @Override
                T transform(F from) {
                    return function.apply(from);
                }
            };
        }

        @Override
        public boolean isEmpty() {
            return fromList.isEmpty();
        }

        @Override
        public T remove(int index) {
            return function.apply(fromList.remove(index));
        }

        @Override
        public int size() {
            return fromList.size();
        }

        private static final long serialVersionUID = 0;
    }

    private static class TransformingSequentialList<F, T>
            extends AbstractSequentialList<T> implements Serializable {
        final List<F> fromList;
        final Function<? super F, ? extends T> function;

        TransformingSequentialList(
                List<F> fromList, Function<? super F, ? extends T> function) {
            this.fromList = checkNotNull(fromList);
            this.function = checkNotNull(function);
        }

        @Override
        public void clear() {
            fromList.clear();
        }

        @Override
        public int size() {
            return fromList.size();
        }

        @Override
        public ListIterator<T> listIterator(final int index) {
            return new TransformedListIterator<F, T>(fromList.listIterator(index)) {
                @Override
                T transform(F from) {
                    return function.apply(from);
                }
            };
        }

        private static final long serialVersionUID = 0;
    }

}

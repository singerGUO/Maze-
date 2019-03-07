package datastructures.concrete;

import datastructures.interfaces.IDictionary;
import datastructures.interfaces.ISet;
import misc.exceptions.NotYetImplementedException;

import java.util.Iterator;

/**
 * TODO: Replace this class with your ChainedHashSet implementation from the second project.
 */
public class ChainedHashSet<T> implements ISet<T> {
    // This should be the only field you need
    private IDictionary<T, Boolean> map;

    public ChainedHashSet() {
        throw new NotYetImplementedException();
    }

    @Override
    public void add(T item) {
        throw new NotYetImplementedException();
    }

    @Override
    public void remove(T item) {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean contains(T item) {
        throw new NotYetImplementedException();
    }

    @Override
    public int size() {
        throw new NotYetImplementedException();
    }

    @Override
    public Iterator<T> iterator() {
        return new SetIterator<>(this.map.iterator());
    }

    private static class SetIterator<T> implements Iterator<T> {
        // This should be the only field you need
        private Iterator<KVPair<T, Boolean>> iter;

        public SetIterator(Iterator<KVPair<T, Boolean>> iter) {
            // No need to change this method.
            this.iter = iter;
        }

        @Override
        public boolean hasNext() {
            throw new NotYetImplementedException();
        }

        @Override
        public T next() {
            throw new NotYetImplementedException();
        }
    }
}

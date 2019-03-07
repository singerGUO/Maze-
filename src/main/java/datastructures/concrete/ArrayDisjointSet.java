package datastructures.concrete;

import datastructures.interfaces.IDisjointSet;
import misc.exceptions.NotYetImplementedException;

/**
 * @see IDisjointSet for more details.
 */
public class ArrayDisjointSet<T> implements IDisjointSet<T> {
    // Note: do NOT rename or delete this field. We will be inspecting it
    // directly within our private tests.
    private int[] pointers;

    // However, feel free to add more methods and private helper methods.
    // You will probably need to add one or two more fields in order to
    // successfully implement this class.

    public ArrayDisjointSet() {
        // TODO: your code here
    }

    @Override
    public void makeSet(T item) {
        throw new NotYetImplementedException();
    }

    @Override
    public int findSet(T item) {
        throw new NotYetImplementedException();
    }

    @Override
    public void union(T item1, T item2) {
        throw new NotYetImplementedException();
    }
}

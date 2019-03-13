package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
//import misc.exceptions.NoSuchKeyException;
//import misc.exceptions.NoSuchKeyException;
//import misc.exceptions.NotYetImplementedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @see IDictionary and the assignment page for more details on what each method should do
 */
public class ChainedHashDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private IDictionary<K, V>[] chains;
    private final int overallSize = 10;
    private double loadfactor;
    private int size;
    private int length;


    // You're encouraged to add extra fields (and helper methods) though!

    public ChainedHashDictionary() {
        length = overallSize;
        loadfactor = 0.75;

        size = 0;

        chains = makeArrayOfChains();
        for (int i = 0; i < overallSize; i++) {
            chains[i] = new ArrayDictionary<>();
        }
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain IDictionary<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private IDictionary<K, V>[] makeArrayOfChains() {
        // Note: You do not need to modify this method.
        // See ArrayDictionary's makeArrayOfPairs(...) method for
        // more background on why we need this method.

        return (IDictionary<K, V>[]) new IDictionary[length];
    }

    @Override
    public V get(K key) {
        if (key == null) {
            for (KVPair<K, V> pair : chains[0]) {
                if (pair.getKey() == null) {
                    return pair.getValue();
                }
            }
            throw new NullPointerException();
        }
        int hashCode = key.hashCode();
        int index = Math.abs(hashCode % length);

        for (KVPair<K, V> pair : chains[index]) {
            if (pair.getKey().equals(key)) {
                return pair.getValue();
            }
        }

        throw new NoSuchKeyException();

    }

    @Override
    public void put(K key, V value) {

        if (size >= length * 0.75) {
            length *= 2;
            IDictionary<K, V>[] larger = makeArrayOfChains();
            for (int i = 0; i < length; i++) {
                larger[i] = new ArrayDictionary<>();
            }
            for (int i = 0; i < length / 2; i++) {

                for (KVPair<K, V> pair : chains[i]) {
                    if (pair.getKey() == null) {

                        larger[0].put(key, value);

                    }
                    int newAddress = Math.abs(pair.getKey().hashCode() % larger.length);
                    larger[newAddress].put(pair.getKey(), pair.getValue());

                }
            }

            chains = larger;
        }

        if (key == null) {
            if (!chains[0].containsKey(key)) {
                size++;
            }
            chains[0].put(key, value);
            return;
        }
        int hashCode = key.hashCode();
        int index = Math.abs(hashCode % length);
        if (!chains[index].containsKey(key)) {
            size++;
        }
        chains[index].put(key, value);

    }



    @Override
    public V remove(K key) {
        if (key == null) {
            return chains[0].remove(null);
        }


        int hashCode = key.hashCode();
        int index = Math.abs(hashCode % length);
        // try {
        //     chains[index].remove(key);
        //
        // } catch (NoSuchKeyException ex) {
        //     throw new NoSuchElementException();
        // }
        size--;
        return chains[index].remove(key);

    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            return chains[0].containsKey(null);
        }
        int hashCode = key.hashCode();
        int index = Math.abs(hashCode % length);
        return chains[index].containsKey(key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // Note: you do not need to change this method
        return new ChainedIterator<>(this.chains);
    }

    /**
     * Hints:
     *
     * 1. You should add extra fields to keep track of your iteration
     *    state. You can add as many fields as you want. If it helps,
     *    our reference implementation uses three (including the one we
     *    gave you).
     *
     * 2. Before you try and write code, try designing an algorithm
     *    using pencil and paper and run through a few examples by hand.
     *
     *    We STRONGLY recommend you spend some time doing this before
     *    coding. Getting the invariants correct can be tricky, and
     *    running through your proposed algorithm using pencil and
     *    paper is a good way of helping you iron them out.
     *
     * 3. Think about what exactly your *invariants* are. As a
     *    reminder, an *invariant* is something that must *always* be
     *    true once the constructor is done setting up the class AND
     *    must *always* be true both before and after you call any
     *    method in your class.
     *
     *    Once you've decided, write them down in a comment somewhere to
     *    help you remember.
     *
     *    You may also find it useful to write a helper method that checks
     *    your invariants and throws an exception if they're violated.
     *    You can then call this helper method at the start and end of each
     *    method if you're running into issues while debugging.
     *
     *    (Be sure to delete this method once your iterator is fully working.)
     *
     * Implementation restrictions:
     *
     * 1. You **MAY NOT** create any new data structures. Iterators
     *    are meant to be lightweight and so should not be copying
     *    the data contained in your dictionary to some other data
     *    structure.
     *
     * 2. You **MAY** call the `.iterator()` method on each IDictionary
     *    instance inside your 'chains' array, however.
     */
    private static class ChainedIterator<K, V> implements Iterator<KVPair<K, V>> {
        private IDictionary<K, V>[] chains;
        private Iterator<KVPair<K, V>> cur;
        private int index;

        public ChainedIterator(IDictionary<K, V>[] chains) {
            this.chains = chains;
            index = 0;
            this.cur = chains[index].iterator();
        }

        @Override
        public boolean hasNext() {
            if (cur.hasNext()) {
                return true;
            } else {
                //for skipping the empty Array dictionary.
                while (index < chains.length - 1 && !cur.hasNext()) {
                    cur = chains[++index].iterator();
                }
                return (index == chains.length - 1 && !cur.hasNext()) ? false : true;
            }
        }

        @Override
        public KVPair<K, V> next() {
            if (hasNext()) {
                return cur.next();
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}

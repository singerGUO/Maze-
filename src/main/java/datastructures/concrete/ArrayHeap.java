package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;


/**
 * @see IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    private int overallSize;
    private int size;

    // Feel free to add more fields and constants.

    public ArrayHeap() {
        overallSize = 10;
        size = 0;
        heap = makeArrayOfT(overallSize);
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int arraySize) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[arraySize]);
    }

    @Override
    public T removeMin() {
        if (size == 0) {
            throw new EmptyContainerException();
        }
        T min = heap[0];

        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        int parent = 0;
        int minIndex = parent;
        int child = 0;

        while (child < size) {

            for (int i = child; i < child + NUM_CHILDREN + 1; i++) {
                if (i < size && lessThan(heap[i], heap[minIndex])) {
                    minIndex = i;
                }
            }
            if (minIndex == parent) {
                break;
            }
            swap(minIndex, parent);
            parent = minIndex;
            child = NUM_CHILDREN * parent + 1;
        }
        return min;

    }

    @Override
    public T peekMin() {
        if (size == 0) {
            throw new EmptyContainerException();
        }
        return heap[0];

    }


    @Override
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == overallSize) {
            extendCapacity();
        }
        heap[size] = item;
        int tempSize = size;

        while (tempSize != 0) {

            if (greater(heap[(tempSize - 1) / NUM_CHILDREN], heap[tempSize])) {
                // T temp=heap[(tempSize-1)/NUM_CHILDREN];
                // heap[(tempSize-1)/NUM_CHILDREN]=heap[tempSize];
                // heap[tempSize]=temp;
                swap((tempSize - 1) / NUM_CHILDREN, tempSize);
                tempSize = (tempSize - 1) / NUM_CHILDREN;
            } else {
                break;
            }
        }
        size++;

    }

    public void swap(int a, int b) {
        T temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    public boolean greater(T a, T b) {
        return a.compareTo(b) > 0;
    }

    public boolean lessThan(T a, T b) {
        return a.compareTo(b) < 0;
    }


    public void extendCapacity() {
        T[] newheap = makeArrayOfT(2 * overallSize);
        for (int i = 0; i < size; i++) {
            newheap[i] = heap[i];
        }
        overallSize *= 2;
        heap = newheap;
    }

    @Override
    public int size() {
        return size;
    }
}

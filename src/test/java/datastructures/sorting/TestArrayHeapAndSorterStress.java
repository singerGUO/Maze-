package datastructures.sorting;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;
import misc.BaseTest;
import misc.Sorter;
import misc.exceptions.EmptyContainerException;
import org.junit.Test;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapAndSorterStress extends BaseTest {

    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout = 10 * SECOND)
    public void testStressUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }
        IList<Integer> top = Sorter.topKSort(5000, list);
        assertEquals(5000, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(1000000 - 5000 + i, top.get(i));
        }
    }

    @Test(timeout = 10 * SECOND)
    public void testManyinsert() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 10000; i++) {
            heap.insert(i);
            assertEquals(0, heap.peekMin());
        }
    }

    @Test(timeout = 10 * SECOND)
    public void testManyRemove() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 10000; i++) {
            heap.insert(i);
        }
        for (int i = 0; i < 9999; i++) {
            heap.removeMin();
        }
        assertEquals(9999, heap.removeMin());
        try {
            heap.removeMin();
            fail("emptyContainerException expected");
        } catch (EmptyContainerException ex) {
            //do nothing
        }
    }

    @Test(timeout = 10 * SECOND)
    public void testManyCombinaton() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 10000; i++) {
            heap.insert(i);
            assertEquals(0, heap.peekMin());
        }
        for (int i = 0; i < 10000; i++) {

            assertEquals(0, heap.peekMin());

        }

    }


    @Test(timeout = 10 * SECOND)
    public void testPlaceholder() {
        assertTrue(true);
    }

    @Test(timeout = 10 * SECOND)
    public void testHeapefficiency() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 200000; i++) {
            heap.insert(i);
        }
        for (int i = 0; i < 200000; i++) {
            assertEquals(i, heap.peekMin());
            assertEquals(i, heap.removeMin());
        }

    }

    @Test(timeout = 10 * SECOND)
    public void testSorterEfficiency() {

        IList<Integer> list = new DoubleLinkedList<>();

        for (int i = 0; i < 300000; i++) {
            list.add(i);
        }

        IList<Integer> top = Sorter.topKSort(150000, list);
        assertEquals(150000, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(299999 - i, top.remove());
        }
    }




}

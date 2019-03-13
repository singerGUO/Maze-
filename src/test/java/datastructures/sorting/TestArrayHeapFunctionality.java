package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import misc.BaseTest;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;
import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }

    @Test(timeout = SECOND)
    public void testinsertEmptyheap() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(3, heap.peekMin());
    }

    @Test(timeout = SECOND)
    public void peekMininEmptyheap() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.peekMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // This is ok; deliberately empty
        }
    }

    @Test(timeout = SECOND)
    public void peekMinregular() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(3, heap.peekMin());

    }

    @Test(timeout = SECOND)
    public void testInsertNull() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.insert(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // This is ok: do nothing
        }
    }

    @Test(timeout = SECOND)
    public void testRemoveSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(2);
        heap.removeMin();
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());

    }


    @Test(timeout = SECOND)
    public void removefromEmpty() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.removeMin();
            fail("EmptyConatiner Exection");
        } catch (EmptyContainerException ex) {
            //do nothing

        }

    }

    @Test
    public void testRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        int n = 1500;

        for (int i = 0; i <= n; i++) {
            heap.insert(i);
            assertEquals(0, heap.peekMin());
        }

        for (int i = 0; i <= n; i++) {
            int min = heap.removeMin();
            assertEquals(i, min);
        }

        assertEquals(0, heap.size());
    }

    @Test(timeout = SECOND)
    public void testrestize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 100; i++) {
            heap.insert(i);
        }
        assertEquals(100, heap.size());
    }

    @Test(timeout = SECOND)
    public void testbasicinsertandremoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(2);
        assertEquals(2, heap.peekMin());
        assertEquals(2, heap.size());
        assertEquals(2, heap.removeMin());
    }

    @Test(timeout = SECOND)
    public void testremovetwice() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(2);
        heap.insert(4);
        heap.insert(5);

        assertEquals(2, heap.removeMin());
        assertEquals(3, heap.removeMin());
    }

    @Test(timeout = SECOND)
    public void testInsertRandomly() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(5);
        heap.insert(10);
        heap.insert(20);
        heap.insert(15);
        heap.insert(7);
        heap.insert(2);

        assertEquals(2, heap.peekMin());
        assertEquals(6, heap.size());
    }

    @Test//(timeout = SECOND)
    public void testInsertAndRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 10; i++) {
            heap.insert(i);
        }

        for (int i = 0; i < 10; i++) {
            assertEquals(i, heap.removeMin());
        }
        assertTrue(heap.isEmpty());
    }


}

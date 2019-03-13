package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Sorter;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;



/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSorterFunctionality extends BaseTest {
    @Test(timeout=SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Sorter.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
    }



    @Test(timeout = SECOND)
    public void negativeK() {
        IList<Integer> list = new DoubleLinkedList<>();
        try {
            Sorter.topKSort(-1, list);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Do nothing: this is OK
        }
    }


    @Test(timeout = SECOND)
    public void largeK() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(3);
        list.add(5);
        IList<Integer> top = Sorter.topKSort(4, list);
        assertEquals(2, top.size());
        assertEquals(3, top.get(0));
        assertEquals(5, top.get(1));
    }

    @Test(timeout = SECOND)
    public void testDuplicates() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(1);
        }
        IList<Integer> sorted = Sorter.topKSort(5, list);
        assertEquals(5, sorted.size());
        for (int i = 0; i < sorted.size(); i++) {
            assertEquals(1, sorted.get(i));
        }
    }

    @Test(timeout = SECOND)
    public void testZeroK() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(0);
        list.add(1);
        IList<Integer> top = Sorter.topKSort(0, list);
        assertTrue(top.isEmpty());
    }

    @Test(timeout = SECOND)
    public void testTopOneValue() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        IList<Integer> top = Sorter.topKSort(1, list);
        assertEquals(1, top.size());
        assertEquals(4, top.get(0));
    }


    @Test(timeout = SECOND)
    public void testReversed() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 20; i > 0; i--) {
            list.add(i);
        }
        IList<Integer> top = Sorter.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(16 + i, top.get(i));
        }
    }

    @Test(timeout = SECOND)
    public void testRandom() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(3);
        list.add(5);
        list.add(100);
        list.add(22);
        list.add(33);
        IList<Integer> top = Sorter.topKSort(4, list);
        assertEquals(4, top.size());
        assertEquals(5, top.get(0));
        assertEquals(22, top.get(1));
        assertEquals(33, top.get(2));
        assertEquals(100, top.get(3));
    }
}


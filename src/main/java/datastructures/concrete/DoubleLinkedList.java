package datastructures.concrete;

import datastructures.interfaces.IList;
import misc.exceptions.EmptyContainerException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Note: For more info on the expected behavior of your methods:
 *
 * @see datastructures.interfaces.IList
 * (You should be able to control/command+click "IList" above to open the file from IntelliJ.)
 */
public class DoubleLinkedList<T> implements IList<T> {
    // You may not rename these fields or change their types.
    // We will be inspecting these in our private tests.
    // You also may not add any additional fields.
    private Node<T> front;
    private Node<T> back;
    private int size;

    public DoubleLinkedList() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    @Override
    public void add(T item) {
        Node<T> n = new Node<>(item);
        if (size == 0) {
            front = n;
            back = n;
        } else {
            back.next = n;
            n.prev = back;
            back = back.next;
        }
        size++;
    }

    @Override
    public T remove() {
        if (size == 0) {
            throw new EmptyContainerException();
        }
        if (size == 1) {
            T cur = front.data;
            front = null;
            back = null;
            size--;
            return cur;
        }
        T cur = back.data;
        back = back.prev;
        back.next = null;
        size--;
        return cur;
    }

    @Override
    public T get(int index) {
        // we
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> head = front;
        while (head != null && index > 0) {
            head = head.next;
            index--;
        }

        return head.data;
    }

    @Override
    public void set(int index, T item) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (item == null) {
            return;
        }
        Node<T> replace = new Node<>(item);
        if (size == 1) {
            remove();
            add(item);
        } else if (index == 0) {
            replace.next = front.next;
            front.next.prev = replace;
            front = replace;
        } else if (index == size - 1) {
            replace.prev = back.prev;
            back.prev.next = replace;
            back = replace;
        } else {
            Node<T> head = front;
            while (head != null && index > 0) {
                head = head.next;
                index--;
            }
            Node<T> prev = head.prev;
            Node<T> next = head.next;
            prev.next = replace;
            next.prev = replace;
            replace.prev = prev;
            replace.next = next;
        }

    }

    @Override
    public void insert(int index, T item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(item);
            return;
        }
        Node<T> replace = new Node<>(item);
        if (index == 0) {
            replace.next = front;
            front.prev = replace;
            front = replace;
        } else {
            Node<T> head;
            if (index >= size / 2) {
                head = back;
                int distance = size - index - 1;
                while (head != null && distance > 0) {
                    head = head.prev;
                    distance--;
                }
            } else {
                head = front;
                while (head != null && index > 0) {
                    head = head.next;
                    index--;
                }
            }
            head.prev.next = replace;
            replace.prev = head.prev;
            replace.next = head;
            head.prev = replace;
        }
        size++;
    }

    @Override
    public T delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 1) {

            return remove();
        } else {
            Node<T> head;
            if (index == 0) {
                head = front;
                front = front.next;
                front.prev = null;
                size--;
                return head.data;
            } else if (index == size - 1) {
                head = back;
                back = back.prev;
                back.next = null;
                size--;
                return head.data;
            } else {
                //this is for the stress test
                if (index >= size / 2) {
                    int distance = size - index - 1;
                    head = back;
                    while (back != null && distance > 0) {
                        head = head.prev;
                        distance--;
                    }

                } else {
                    head = front;
                    while (head != null && index > 0) {
                        head = head.next;
                        index--;
                    }
                }

                head.prev.next = head.next;
                head.next.prev = head.prev;
                size--;
                return head.data;
            }
        }
    }

    @Override
    public int indexOf(T item) {
        Node<T> head = front;
        int index = 0;
        if (item == null) {
            // we might compare object, we need to use .equals()
            //therefore we need to have if statements for whether item is null

            while (head != null && head.data != null) {
                head = head.next;
                index++;
            }
        }
        //2->3->null->5
        //assertEquals(3, list.indexOf(3)) is not checked in the junit test
        else {
            while (head != null) {
                if (head.data != null && head.data.equals(item)) {
                    break;
                }
                head = head.next;
                index++;
            }

        }
        return (head != null) ? index : -1;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    //if we don't have indexof it the return flase;
    public boolean contains(T other) {
        return indexOf(other) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        // Note: we have provided a part of the implementation of
        // an iterator for you. You should complete the methods stubs
        // in the DoubleLinkedListIterator inner class at the bottom
        // of this file. You do not need to change this method.
        return new DoubleLinkedListIterator<>(this.front);
    }


    private static class Node<E> {
        // You may not change the fields in this node or add any new fields.
        public final E data;
        public Node<E> prev;
        public Node<E> next;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public Node(E data) {
            this(null, data, null);
        }

        // Feel free to add additional constructors or methods to this class.
    }

    private static class DoubleLinkedListIterator<T> implements Iterator<T> {
        // You should not need to change this field, or add any new fields.
        private Node<T> current;

        public DoubleLinkedListIterator(Node<T> current) {
            // You do not need to make any changes to this constructor.
            this.current = current;
        }

        /**
         * Returns 'true' if the iterator still has elements to look at;
         * returns 'false' otherwise.
         */
        // current is the node we are pointing at while calling next method.
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the next item in the iteration and internally updates the
         * iterator to advance one element forward.
         *
         * @throws NoSuchElementException if we have reached the end of the iteration and
         *                                there are no more elements to look at.
         */


        public T next() {
            if (hasNext()) {
                T data = current.data;
                current = current.next;
                return data;
            }
            throw new NoSuchElementException();
        }
    }
}
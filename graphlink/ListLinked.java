package graphlink;

import java.util.Iterator;

public class ListLinked<T> implements Iterable<T> {
    private Node<T> first;
    private Node<T> last;

    public void insertLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void remove(T data) {
        if (isEmpty()) {
            return;
        }

        if (first.data.equals(data)) {
            first = first.next;
            if (first == null) {
                last = null;
            }
            return;
        }

        Node<T> current = first;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                if (current.next == null) {
                    last = current;
                }
                return;
            }
            current = current.next;
        }
    }

    public Iterator<T> iterator() {
        return new ListIterator();
    }
    public void clear() {
        first = null;
        last = null;
    }

    private class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    private class ListIterator implements Iterator<T> {
        private Node<T> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            T data = current.data;
            current = current.next;
            return data;
        }
    }
}

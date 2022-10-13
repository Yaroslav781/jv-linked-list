package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
        } else if (tail == null) {
            tail = new Node<>(head, value, null);
            head.next = tail;
        } else {
            Node<T> element = new Node<>(tail, value, null);
            tail.next = element;
            tail = element;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            head = new Node<>(null, value, getNode(index));
            getNode(index).prev = head;
            size++;
        } else {
            Node<T> previous = getNode(index).prev;
            Node<T> newNode = new Node<>(previous, value, getNode(index));
            previous.next = newNode;
            getNode(index).prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkBounds(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkBounds(index);
        Node<T> node = getNode(index);
        T old = node.value;
        node.value = value;
        return old;
    }

    @Override
    public T remove(int index) {
        checkBounds(index);
        T node = getNode(index).value;
        unlink(getNode(index));
        return node;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if ((node.value == null && object == null)
                    || (node.value != null && node.value.equals(object))) {
                remove(i);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        Node<T> exchange = head;
        int count = 0;
        while (count < index) {
            exchange = exchange.next;
            count++;
        }
        return exchange;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        node.value = null;
        size--;
    }

    private void checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
}

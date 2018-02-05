import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node pre;
    }

    private Node head = null;
    private Node tail = null;
    private int counts = 0;

    private class ListIterator implements Iterator<Item> {
        private Node currentHead = head;

        public boolean hasNext() {
            return currentHead != null;
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }

            Node oldHead = currentHead;
            currentHead = currentHead.next;

            return oldHead.item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void initFirstNode(Node n) {
        n.pre = null;
        n.next = null;
        tail = n;
        head = n;
    }

    private void checkIfIsEmpty() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private void validateInputItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isEmpty() {
        return head == null && tail == null;
    }

    public int size() {
        return counts;
    }

    public void addFirst(Item item) {
        validateInputItem(item);

        Node n = new Node();

        n.item = item;

        if (head == null) {
            initFirstNode(n);
        } else {
            Node oldFirst = head;
            head = n;
            n.next = oldFirst;
            oldFirst.pre = n;
        }

        counts++;
    }

    public void addLast(Item item) {
        validateInputItem(item);

        Node n = new Node();
        n.item = item;

        if (tail == null) {
            initFirstNode(n);
        } else {
            tail.next = n;
            n.pre = tail;
            n.next = null;
            tail = n;
        }

        counts++;
    }

    public Item removeFirst() {
        this.checkIfIsEmpty();

        Node n = head;
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.pre = null;
        }

        counts--;
        return n.item;
    }

    public Item removeLast() {
        this.checkIfIsEmpty();

        Node n = tail;
        tail = tail.pre;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }

        counts--;
        return n.item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();

        d.addLast(0);
        d.addLast(1);
        d.addLast(4);

        System.out.println(d.removeLast());
    }

}
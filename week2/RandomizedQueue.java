import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] list;
    private int count = 0;
    private int headIndex = 0;

    public RandomizedQueue() {
        list = (Item[]) new Object[1];
    }

    private class ListIterator implements Iterator<Item> {
        private int currentIndex = 0;

        public boolean hasNext() {
            return currentIndex != list.length;
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }

            int i = currentIndex;
            for (; i < list.length; i++) {
                if (list[i] != null) {
                    break;
                }
            }

            currentIndex = i + 1;
            return list[i];
        }
    }

    private void resizeList(int n) {
        Item[] copy = (Item[]) new Object[n];

        for (int i = 0, j = 0; i < list.length; i++, j++) {
            if (list[i] != null) {
                copy[j] = list[i];
            }
        }

        list = copy;
    }

    private void checkIfIsEmpty() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (headIndex >= list.length) {
            this.resizeList((list.length + 1) * 2);
        }

        list[headIndex] = item;
        headIndex += 1;
        count += 1;
    }

    public Item dequeue() {
        this.checkIfIsEmpty();

        int index = StdRandom.uniform(count);
        Item item = list[index];

        while (item == null) {
            index += 1;
            if (index == list.length) {
                index = 0;
            }

            item = list[index];
        }

        list[index] = null;
        count -= 1;

        if (count == list.length / 4) {
            this.resizeList(list.length / 2);
        }

        return item;
    }

    public Item sample() {
        this.checkIfIsEmpty();

        int index = StdRandom.uniform(count);
        Item item = list[index];

        while (item == null) {
            index += 1;
            if (index == list.length) {
                index = 0;
            }

            item = list[index];
        }

        return item;
    }

    public Iterator<Item> iterator() {
        StdRandom.shuffle(list);
        return new ListIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(48);
        rq.dequeue();
        rq.enqueue(43);
        rq.enqueue(15);

        System.out.println(rq.sample());
    }
}
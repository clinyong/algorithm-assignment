import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        if (count > 0) {
            RandomizedQueue<String> randomList = new RandomizedQueue<String>();
            while (!StdIn.isEmpty()) {
                randomList.enqueue(StdIn.readString());
            }

            Iterator<String> iterator = randomList.iterator();
            for (int i = 0; i < count; i++) {
                String s = iterator.next();
                StdOut.println(s);
            }
        }
    }
}
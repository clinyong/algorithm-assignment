
// the row and column indices are integers between 1 and n
import edu.princeton.cs.algs4.In;

public class Percolation {
    private int[] id;
    private int[] sz;
    private boolean[] openList;
    private int countOpen;
    private final int maxN;
    private final int topVIndex;
    private final int bottomVIndex;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        int flatN = n * n;
        id = new int[flatN + 2];
        sz = new int[flatN + 2];
        openList = new boolean[flatN + 2];
        for (int i = 0; i < flatN + 2; i++) {
            id[i] = i;
            sz[i] = 1;
            openList[i] = false;
        }
        countOpen = 0;
        maxN = n;

        this.topVIndex = flatN;
        this.bottomVIndex = flatN + 1;

        this.sz[this.topVIndex] = n;
        this.sz[this.bottomVIndex] = n;
        this.openList[this.topVIndex] = true;
        this.openList[this.bottomVIndex] = true;

        for (int i = 0; i < n; i++) {
            int topIndex = this.getFlatIndex(1, i + 1);
            int bottomIndex = this.getFlatIndex(n, i + 1);

            this.id[topIndex] = this.topVIndex;
            this.id[bottomIndex] = this.bottomVIndex;
        }
    }

    private int getFlatIndex(int row, int col) {
        return (row - 1) * this.maxN + col - 1;
    }

    private int root(int p) {
        while (p != id[p])
            p = id[p];

        return p;
    }

    private boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);

        if (pRoot != qRoot) {
            if (this.sz[pRoot] < this.sz[qRoot]) {
                this.id[pRoot] = qRoot;
                this.sz[qRoot] += this.sz[qRoot];
            } else {
                this.id[qRoot] = pRoot;
                this.sz[pRoot] += this.sz[qRoot];
            }
        }
    }

    private void checkArguments(int row, int col) {
        if (row <= 0 || row > this.maxN || col <= 0 || col > this.maxN) {
            throw new IllegalArgumentException();
        }
    }

    public void open(int row, int col) {
        this.checkArguments(row, col);

        if (!isOpen(row, col)) {
            int p = getFlatIndex(row, col);
            this.openList[p] = true;

            if (maxN > 1) {
                if (row > 1 && isOpen(row - 1, col)) {
                    union(p, p - maxN);
                }

                if (row < maxN && isOpen(row + 1, col)) {
                    union(p, p + maxN);
                }

                if (col > 1 && isOpen(row, col - 1)) {
                    union(p, p - 1);
                }
                if (col < maxN && isOpen(row, col + 1)) {
                    union(p, p + 1);
                }
            } else {
                // maxN == 1
                union(this.topVIndex, this.bottomVIndex);
            }
            countOpen += 1;
        }
    }

    public boolean isOpen(int row, int col) {
        this.checkArguments(row, col);

        int p = getFlatIndex(row, col);
        return this.openList[p];
    }

    public int numberOfOpenSites() {
        return countOpen;
    }

    public boolean isFull(int row, int col) {
        this.checkArguments(row, col);

        int p = this.getFlatIndex(row, col);
        return this.isOpen(row, col) && this.connected(p, this.topVIndex);
    }

    public boolean percolates() {
        return this.connected(this.topVIndex, this.bottomVIndex);
    }

    public static void main(String[] args) {
        In in = new In(args[0]); // input file
        int n = in.readInt(); // n-by-n percolation system

        Percolation perc = new Percolation(n);

        int count = 0;
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            count += 1;
            System.out.printf("%d (%d, %d): %b, %d\n", count, i, j, perc.isFull(2, 1), perc.root(6));
        }
    }
}
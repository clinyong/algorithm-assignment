import edu.princeton.cs.algs4.StdRandom;

// the row and column indices are integers between 1 and n

public class Percolation {
    private int[] id;
    private int[] sz;
    private int[] openList;
    private int countOpen;
    private int maxN;
    private int topVIndex;
    private int bottomVIndex;

    public Percolation(int n) {
        int flatN = n * n;
        id = new int[flatN + 2];
        sz = new int[flatN + 2];
        openList = new int[flatN + 2];
        for (int i = 0; i < flatN + 2; i++) {
            id[i] = i;
            sz[i] = 1;
            openList[i] = 0;
        }
        countOpen = 0;
        maxN = n;

        this.topVIndex = flatN;
        this.bottomVIndex = flatN + 1;

        this.sz[this.topVIndex] = n;
        this.sz[this.bottomVIndex] = n;

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

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            int p = getFlatIndex(row, col);
            this.openList[p] = 1;

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
            countOpen += 1;
        }
    }

    public boolean isOpen(int row, int col) {
        int p = getFlatIndex(row, col);
        return this.openList[p] == 1;
    }

    public int numberOfOpenSites() {
        return countOpen;
    }

    public boolean isFull(int row, int col) {
        if (row == 1) {
            return true;
        }

        int p = getFlatIndex(row, col);
        boolean full = false;
        for (int i = 0; i < maxN; i++) {
            if (connected(p, i)) {
                full = true;
                break;
            }
        }

        return full;
    }

    public boolean percolates() {
        return this.connected(this.topVIndex, this.bottomVIndex);
    }

    // public static void main(String[] args) {
    //     int n = Integer.parseInt(args[0]);
    //     Percolation p = new Percolation(n);

    //     int count = 0;
    //     while (!p.percolates()) {
    //         int row = StdRandom.uniform(n) + 1;
    //         int col = StdRandom.uniform(n) + 1;
    //         if (!p.isOpen(row, col)) {
    //             p.open(row, col);
    //             count += 1;
    //         }
    //     }

    //     System.out.println(count);
    // }
}
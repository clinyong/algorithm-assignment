import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double FACTOR = 1.96;
    private final double[] fractionList;
    private final int trials;

    public PercolationStats(int n, int trials) {
        fractionList = new double[trials];
        this.trials = trials;

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }

            this.fractionList[i] = p.numberOfOpenSites() / (double) (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(this.fractionList);
    }

    public double stddev() {
        return StdStats.stddev(this.fractionList);
    }

    public double confidenceLo() {
        double i = PercolationStats.FACTOR * PercolationStats.FACTOR;
        return this.mean() - Math.sqrt(i * this.stddev() / this.trials);
    }

    public double confidenceHi() {
        double i = PercolationStats.FACTOR * PercolationStats.FACTOR;
        return this.mean() + Math.sqrt(i * this.stddev() / this.trials);
    }

    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats p = new PercolationStats(size, trials);
        System.out.printf("mean = %f\n", p.mean());
        System.out.printf("stddev = %f\n", p.stddev());
        System.out.printf("95%% confidence interval = [%2f, %2f]\n", p.confidenceLo(), p.confidenceHi());
    }
}
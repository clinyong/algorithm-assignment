import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] fractionList;
    private int trials;

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
        double total = 0;
        for (int i = 0; i < this.trials; i++) {
            total += this.fractionList[i];
        }

        return total / this.trials;
    }

    public double stddev() {
        double m = this.mean();

        double total = 0.0;
        for (int i = 0; i < this.trials; i++) {
            double tmp = (this.fractionList[i] - m);
            total = tmp * tmp + total;
        }

        return total / (this.trials - 1);
    }

    public double confidenceLo() {
        return this.mean() - Math.sqrt(1.96 * 1.96 * this.stddev() / this.trials);
    }

    public double confidenceHi() {
        return this.mean() + Math.sqrt(1.96 * 1.96 * this.stddev() / this.trials);
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
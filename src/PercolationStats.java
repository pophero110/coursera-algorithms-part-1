/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats {
    private double critialValue = 1.96;
    private double[] x;
    private int sampleSize;
    // perform independent trials on an n-by-n grids
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        sampleSize = trials;
        x = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                perc.open(row, col);
            }
            double numofOpensites = perc.numberOfOpenSites();
            double numofSites = n * n;
            double percThreshold = numofOpensites / numofSites;
            x[i] = percThreshold;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(x);

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double standardDeviation = StdStats.stddev(x);
        if (Double.isNaN(standardDeviation)) standardDeviation = 0;
        return standardDeviation;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double sterror = stddev() / (Math.sqrt(sampleSize));
        return mean - critialValue * sterror;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double sterror = stddev() / (Math.sqrt(sampleSize));
        return mean + critialValue * sterror;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = [" + percStats.confidenceLo() + ", "
                + percStats.confidenceHi() + "]");
    }
}

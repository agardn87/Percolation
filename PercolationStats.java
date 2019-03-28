package A01Percolation;


import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

/**
 * Created by Adam Gardner on 8/31/16.
 */
public class PercolationStats {

    private double[] threshold;
    private int runTimes, size;

    /*
        assigns threshold[] the percThreshold values for each instance of A01Percolation that is ran.
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("The first parameter value is the size of the  & T must be more than 0, " +
                    "preferably higher than 3.");
        }
        runTimes = T;
        size = N;
        threshold = new double[runTimes];
        for (int i = 0; i < T; i++) {
            threshold[i] = percThreshold();
        }
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold);
    }

    public double confidenceLow() {
        return mean() - ((1.96*stddev()/Math.sqrt(runTimes)));
    }

    public double confidenceHigh() {
        return mean() + ((1.96*stddev()/Math.sqrt(runTimes)));
    }

    /*
    Creates instances of A01Percolation and returns the ratio of the amount of sites that were open before percolation
    is true and divides it by the amount n X n grid locations.
     */
    public double percThreshold() {
        int row, col;
        int count = 0;
        Percolation percolation = new Percolation(size);
        while (!percolation.percolates()) {
            row = StdRandom.uniform(size) + 1;
            col = StdRandom.uniform(size) + 1;
            if(!percolation.isOpen(row, col)) {
                percolation.open(row, col);
                count++;
            }
        }
        return count / (size*size + 2);
    }

    public static void main(String[] args) {
        int size = 200;
        int runtimes = 100;
        PercolationStats percolationStats = new PercolationStats(size, runtimes);
        System.out.println("values after creating PercolationStats(size, runtimes)");
        System.out.printf("mean: %f\n", percolationStats.mean());
        System.out.printf("stddev: %f\n", percolationStats.stddev());
        System.out.printf("confidenceLow: %f\n", percolationStats.confidenceLow());
        System.out.printf("confidenceHigh: %f", percolationStats.confidenceHigh());

    }
}

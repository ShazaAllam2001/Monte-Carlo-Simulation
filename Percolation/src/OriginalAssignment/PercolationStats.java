package OriginalAssignment;

import GUI_Adaptation.Algorithm.Percolation;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_LEVEL = 1.96;
    private double[] p;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n > 0 && trials > 0) {
            this.p = new double[trials];
            for (int i = 0; i < trials; i++) {
                GUI_Adaptation.Algorithm.Percolation percolationTry = new Percolation(n);
                while (!percolationTry.percolates()) {
                    int row = StdRandom.uniformInt(1, n+1);
                    int col = StdRandom.uniformInt(1, n+1);
                    percolationTry.open(row, col);
                }
                p[i] = ((double) percolationTry.numberOfOpenSites()) / (n*n);
            }
            this.mean = StdStats.mean(p);
            this.stddev = StdStats.stddev(p);
            this.confidenceLo = mean - (CONFIDENCE_LEVEL * stddev / Math.sqrt(trials));
            this.confidenceHi = mean + (CONFIDENCE_LEVEL * stddev / Math.sqrt(trials));
        }
        else {
            throw new IllegalArgumentException("n or trials <= 0");
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trails);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + "," + stats.confidenceHi() + "]");
    }
}

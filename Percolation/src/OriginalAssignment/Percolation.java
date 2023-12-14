package OriginalAssignment;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF sites;
    private WeightedQuickUnionUF sitesWithoutBottom; // to avoid errors in IsFull
    private boolean[][] opened;
    private int openSites;
    private int n;
    private int virtualTop;
    private int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n > 0) {
            this.opened = new boolean[n][n];
            this.openSites = 0;
            this.n = n;
            this.virtualTop = n*n;
            this.virtualBottom = n*n + 1;
            this.sites = new WeightedQuickUnionUF(n*n + 2);
            this.sitesWithoutBottom = new WeightedQuickUnionUF(n*n + 1);
        }
        else {
            throw new IllegalArgumentException("n <= 0");
        }
    }

    private int calculateId(int row, int col) {
        return (row-1)*n + (col-1);
    }

    private boolean checkIndex(int row, int col) {
        return row >= 1 && row <= n && col >= 1 && col <= n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (checkIndex(row, col)) {
            if (!isOpen(row, col)) {
                int siteId = calculateId(row, col);

                // if site is in the first row connect to the top virtual site
                // or if in the last connect to the bottom
                if (row == 1) {
                    sites.union(siteId, virtualTop);
                    sitesWithoutBottom.union(siteId, virtualTop);
                }
                if (row == n) {
                    sites.union(siteId, virtualBottom);
                }

                // connect site to its open neighbours
                if (row-1 >= 1 && isOpen(row-1, col)) {
                    sites.union(siteId, calculateId(row-1, col));
                    sitesWithoutBottom.union(siteId, calculateId(row-1, col));
                }
                if (row+1 <= n && isOpen(row+1, col)) {
                    sites.union(siteId, calculateId(row+1, col));
                    sitesWithoutBottom.union(siteId, calculateId(row+1, col));
                }
                if (col-1 >= 1 && isOpen(row, col-1)) {
                    sites.union(siteId, calculateId(row, col-1));
                    sitesWithoutBottom.union(siteId, calculateId(row, col-1));
                }
                if (col+1 <= n && isOpen(row, col+1)) {
                    sites.union(siteId, calculateId(row, col+1));
                    sitesWithoutBottom.union(siteId, calculateId(row, col+1));
                }

                opened[row-1][col-1] = true;
                openSites++;
            }
        }
        else {
            throw new IllegalArgumentException("indices are outside the boundaries");
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (checkIndex(row, col)) {
            return opened[row-1][col-1];
        }
        else {
            throw new IllegalArgumentException("indices are outside the boundaries");
        }
    }

    // is the site (row, col) full "connected to the top"?
    public boolean isFull(int row, int col) {
        if (checkIndex(row, col)) {
            // return sites.connected(virtualTop, calculateId(row, col));
            return sitesWithoutBottom.find(virtualTop) == sitesWithoutBottom.find(calculateId(row, col));
        }
        else {
            throw new IllegalArgumentException("indices are outside the boundaries");
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // return sites.connected(virtualTop, virtualBottom);
        return sites.find(virtualTop) == sites.find(virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}

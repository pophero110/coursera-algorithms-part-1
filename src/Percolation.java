import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private byte[] siteStates;
    private int numOfOpenSites;
    private boolean isPercolated;
    private int gridSize;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        numOfOpenSites = 0;
        siteStates = new byte[n * n];
        gridSize = n;
        // extra 2 sites for virtual top and bottom
        uf = new WeightedQuickUnionUF(n * n);
    }
    // (1,1) = First site = index 0
    // n = 3, (0,1) and (1,0) out of range
    // (4,3) (4,3) out of range
    public void open(int row, int col) {
        validateRowandCol(row, col);
        // change row and col for calculating index
        int indexRow = row - 1;
        int indexCol = col - 1;

        //(0,0) = 0, (0,1) = 1, (1,0) = 3, (1,2) = 5
        int siteIndex = indexRow * gridSize + indexCol;
        if (siteStates[siteIndex] > 0) return;
        // 0 = block, 4 = open, 5 = open and connect with top row, 6 = open and connected to bottom row
        // connect the site to all of its adjacent open sites
        // 0 1 2
        // 3 4 5
        // 6 7 8
        int topSiteIndex = siteIndex - gridSize;
        int downSiteIndex = siteIndex + gridSize;
        int leftSiteIndex = siteIndex - 1;
        int rightSiteIndex = siteIndex + 1;
        int lastSiteIndex = gridSize * gridSize - 1;

        siteStates[siteIndex] = 4;
        int state  = 4;
        numOfOpenSites++;

        if (downSiteIndex > lastSiteIndex) {
            // part of top row
            siteStates[siteIndex] = 6;
            state = 6;
        } else if (topSiteIndex < 0) {
            // part of bottom row
            siteStates[siteIndex] = 5;
            state = 5;
        }

        // top
        if (topSiteIndex >= 0 && siteStates[topSiteIndex] > 0) {
            int topRoot = uf.find(topSiteIndex);
            state = state | siteStates[topRoot];
            uf.union(siteIndex, topSiteIndex);
        }
        // down
        if (downSiteIndex <= lastSiteIndex && siteStates[downSiteIndex] > 0) {
            int downRoot = uf.find(downSiteIndex);
            state = state | siteStates[downRoot];
            uf.union(siteIndex, downSiteIndex);
        }
        // left (0,0) (1,0)
        if ((indexCol - 1) >= 0 && siteStates[leftSiteIndex] > 0) {
            int leftRoot = uf.find(leftSiteIndex);
            state = state | siteStates[leftRoot];
            uf.union(siteIndex, leftSiteIndex);
        }
        // right (0,2) (1,2) (2,2)
        if ((indexCol + 1) < gridSize && siteStates[rightSiteIndex] > 0) {
            int rightRoot = uf.find(rightSiteIndex);
            state = state | siteStates[rightRoot];
            uf.union(siteIndex, rightSiteIndex);
        }
        int newRoot = uf.find(siteIndex);
        siteStates[newRoot] = (byte) state;
        if (state == 7) {
            isPercolated = true;
        }
        if (gridSize == 1) {
            isPercolated = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowandCol(row, col);
        // (2,2) => (1,1) => Index 4
        row--;
        col--;
        int site = row * gridSize + col;
        int root = uf.find(site);
        return siteStates[root] > 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRowandCol(row, col);
        // (2,2) => (1,1) => Index 4
        row--;
        col--;
        int site = row * gridSize + col;
        int root = uf.find(site);
        return siteStates[root] == 5 || siteStates[root] == 7;
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }


    // does the system percolate?
    public boolean percolates() {
        return isPercolated;
    }

    private void validateRowandCol(int row, int col) {
        if (row <= 0 || col <= 0) throw new IllegalArgumentException();
        if (row > gridSize || col > gridSize) throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        while (!StdIn.isEmpty()) {
            int n = StdIn.readInt();
//            int row = StdIn.readInt();
//            int col = StdIn.readInt();
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                StdOut.println("row: " + row + " col: " + col);
                perc.open(row, col);
            }
            StdOut.println(perc.numberOfOpenSites());
            StdOut.println(perc.percolates());
        }

    }
}

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] openSites;
    private int numOfOpenSites;
    private int gridSize;
    private int virtualTopSite;
    private int virtualBottomSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        numOfOpenSites = 0;
        virtualTopSite = n * n;
        virtualBottomSite = n * n + 1;
        if (n <= 0) throw new IllegalArgumentException();
        openSites = new boolean[n * n];
        gridSize = n;
        // extra 2 sites for virtual top and bottom
        uf = new WeightedQuickUnionUF(n * n + 2);

        // connect top row to virtualTopSite
        for (int i = 0; i < n; i++) {
            // top row
            uf.union(i, virtualTopSite);
        }
        // connect bottom row to virtualBottomSite
        for (int i = 0; i < n; i++) {
            // bottom row indexes
            // (2,0) = 6 = 3 * 2 + 0
            // (2,1) = 7 = 3 * 2 + 1
            // (2,2) = 8 = 3 * 2 + 2
            uf.union(gridSize * (gridSize - 1) + i, virtualBottomSite);
        }
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
        // 0 = block, 1 = open;
        if (openSites[siteIndex]) return;

        // open the site
        openSites[siteIndex] = true;
        // count open sites
        numOfOpenSites++;
        // connect the site to all of its adjacent open sites
        // 0 1 2
        // 3 4 5
        // 6 7 8
        int topSiteIndex = siteIndex - gridSize;
        int downSiteIndex = siteIndex + gridSize;
        int leftSiteIndex = siteIndex - 1;
        int rightSiteIndex = siteIndex + 1;
        int lastSiteIndex = gridSize * gridSize - 1;
        // top
        if (topSiteIndex >= 0 && openSites[topSiteIndex]) {
            uf.union(siteIndex, topSiteIndex);
        }
        // down index 6
        if (downSiteIndex <= lastSiteIndex && openSites[downSiteIndex]) {
            uf.union(siteIndex, downSiteIndex);
        }
        // left (0,0) (1,0)
        if ((indexCol - 1) >= 0 && openSites[leftSiteIndex]) {
            uf.union(siteIndex, leftSiteIndex);
        }
        // right (0,2) (1,2) (2,2)
        if ((indexCol + 1) < gridSize && openSites[rightSiteIndex]) {
            uf.union(siteIndex, rightSiteIndex);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowandCol(row, col);
        // (2,2) => (1,1) => Index 4
        row--;
        col--;
        int site = row * gridSize + col;
        return openSites[site];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRowandCol(row, col);
        // (2,2) => (1,1) => Index 4
        row--;
        col--;
        int site = row * gridSize + col;
        return openSites[site] && uf.find(site) == uf.find(virtualTopSite);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }


    // does the system percolate?
    public boolean percolates() {
        for (int i = 1; i <= gridSize; i++) {
            if (isFull(gridSize, i)) {
                return true;
            }
        }
        return false;
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
                perc.open(row, col);
            }
            StdOut.println(perc.numberOfOpenSites());
            StdOut.println(perc.percolates());
        }

    }
}

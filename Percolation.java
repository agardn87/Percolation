package A01Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by Adam Gardner on 8/31/16.
 */
public class Percolation {

    private boolean[][] grid;
    private int n;
    private WeightedQuickUnionUF wquf;

    /*
    Constructor checks for bounds, assigns fields, and creates a grid for the percolation experiment.
     */
    public Percolation(int n) {
        if (n <= 0) {
            indexOutOfBoundsException();
        }
        this.n = n;
        grid = new boolean[n][n];
        wquf = new WeightedQuickUnionUF((n * n)+2);
    }

    /*
        assigns row and col and checks to see if adjacent rows or columns are true(open). If they are open then
        the 1d number for the original location is union'd to the adjacent open location. Also the first two conditions
        after the "grid[row][col] = true;" connects any top row location to the imaginary location 0, and same for
        the bottom row to the N*N+1 location.
     */
    public void open(int i, int j) {
        int row = i - 1;
        int col = j - 1;

        if (i < 0 || i > n || j < 0 || j > n) {
            indexOutOfBoundsException();
        }
        grid[row][col] = true;
        if (row == 0) {
            wquf.union(xyTo1d(row, col), 0);
        }
        if (row == n - 1) {
            wquf.union(xyTo1d(row, col), (n * n) + 1);
        }
        if (row < n && isOpen(row + 1, col)) {
            wquf.union(xyTo1d(row, col), xyTo1d(row + 1, col));
        }
        if (row > 1 && isOpen(row - 1, col)) {
            wquf.union(xyTo1d(row, col), xyTo1d(row - 1, col));
        }
        if (col < n && isOpen(row, col + 1)) {
            wquf.union(xyTo1d(row, col), xyTo1d(row, col + 1));
        }
        if (col > 1 && isOpen(row, col-1)) {
            wquf.union(xyTo1d(row, col), xyTo1d(row, col - 1));
        }
    }

    /*
        takes in the row and column location and creates a non-duplicated number to represent its location so that it
         can be connected via union().
     */
    public int xyTo1d(int x, int y) {
        if (x < 0 || x > n || y < 0 || y > n) {
            throw new IndexOutOfBoundsException("Values passed are outside the grid");
        }
        return ((x * n) + y) + 1;
    }

    public boolean isOpen(int i, int j) {
        return !(i <= 0 || i >= n || j <= 0 || j >= n) && grid[i][j];
    }

    public boolean isFull(int i, int j) {
        if (i > 0 && i <= n && j > 0 && j <= n) {
            return wquf.connected(xyTo1d(i - 1, j - 1), 0);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
    /*
        If the two imaginary locations are connected then program percolates.
     */
    public boolean percolates() {
        return wquf.connected(0, (n * n)+ 1);
    }

    public void indexOutOfBoundsException() {
        throw new IndexOutOfBoundsException("The number(s) passed as argument is outside the grid");
    }
}

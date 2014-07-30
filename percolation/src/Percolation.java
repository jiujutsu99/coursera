/***---------------------------------------------------------------
 *  Author:        Raymond Zhang
 *  Written:       6/25/2014
 *  Last updated:  6/25/2014
 *
 *  Compilation:   javac Percolation.java
 *  Execution:     java Percolation
 *  
 *  Models a percolation system using the WeightedQuickUnionUF
 *  data structure.
 * 
 ***---------------------------------------------------------------*/

public class Percolation {
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private boolean[] siteOpen;            // site open if true or closed if false
    private int N;                         // N will hold the width of grid
    private int top;                       // set our virtual top equal to zeroth node           
    private int bottom;                    // top and bottom are our virtual top and bottom 

    public Percolation(int n) {            // create N-by-N grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException("n cannot be less than 1");
        N        = n;
        top      = n*n;
        bottom   = n*n+1;                   // set virtual bottom equal to last node
        uf       = new WeightedQuickUnionUF(n*n+2); // create additional 2 sites for top and bottom
        uf2      = new WeightedQuickUnionUF(n*n+2);
        siteOpen = new boolean[n*n];
        for (int i = 0; i < n*n; i++)       // initialize all sites blocked
            siteOpen[i] = false;
    }

    public void open(int i, int j) {     // open site (row i, column j) if it is not already
        validateIndices(i, j);
        int pos = getPos(i, j);           // set converted 2D site to local var
        if (!isOpen(i, j)) {
            siteOpen[pos] = true;          // if the site is not open then set it open
            if (N == 1 || N == 2) {
                specialUnion(i, j, pos);
            }
            else
            {
                if (i == 1)
                    unionTop(i, j, pos);
                if (i == N)
                    unionBot(i, j, pos);
                if (j == 1 && i > 1 && i < N)
                    unionLeft(i, j, pos);
                if (j == N && i > 1 && i < N)
                    unionRight(i, j, pos);
                if (i > 1 && i < N && j > 1 && j < N)
                    unionAll(i, j, pos);
            }
        }
    }

    // checks if site (row i, column j) open
    public boolean isOpen(int i, int j) {
        validateIndices(i, j);
        return siteOpen[getPos(i, j)];
    }

    // checks if site (row i, column j) full
    public boolean isFull(int i, int j) {
        validateIndices(i, j);
        return isOpen(i, j) && uf2.connected(getPos(i, j), top);
    }

    // checks if system percolates
    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    // convert 2D coordinates to 1D coordinates
    private int getPos(int i, int j) {
        validateIndices(i, j);
        return (i-1)*N+(j-1);
    }

    // validating indices and throwing exception if out of bounds
    private void validateIndices(int i, int j) {
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("Row index i is out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("Column index j is out of bounds");
    }
   
    // union with neighboring sites on bottom
    private void unionBot(int i, int j, int pos) {
        uf.union(pos, bottom);               // union with virtual bottom
        if (isOpen(i-1, j)) {
            uf.union(pos, getPos(i-1, j));    // union above node if open
            uf2.union(pos, getPos(i-1, j));
        }
        if (j > 1) {
            if (isOpen(i, j-1)) {
                uf.union(pos, getPos(i, j-1)); // union left node if open
                uf2.union(pos, getPos(i, j-1));
            }
        }
        if (j < N) {
            if (isOpen(i, j+1)) {
                uf.union(pos, getPos(i, j+1)); // union right node if open
                uf2.union(pos, getPos(i, j+1));
            }
        }
    }
   
    // union with neighboring sites on bottom
    private void unionTop(int i, int j, int pos) {
        uf.union(pos, top);                   // union with virtual top
        uf2.union(pos, top);
        if (isOpen(i+1, j)) {
            uf.union(pos, getPos(i+1, j));    // union below node if open
            uf2.union(pos, getPos(i+1, j));
        }
        if (j > 1) {
            if (isOpen(i, j-1)) {
                uf.union(pos, getPos(i, j-1)); // union left node if open
                uf2.union(pos, getPos(i, j-1));
            }
        }
        if (j < N) {
            if (isOpen(i, j+1)) {
                uf.union(pos, getPos(i, j+1)); // union right node if open
                uf2.union(pos, getPos(i, j+1));
            }
        }
    }
   
    // union with neighboring sites on left column
    private void unionLeft(int i, int j, int pos) {
        uf.union(pos, getPos(i, j+1));     // union right node
        uf2.union(pos, getPos(i, j+1));
        if (isOpen(i+1, j)) {
            uf.union(pos, getPos(i+1, j));  // union below node if open
            uf2.union(pos, getPos(i+1, j));
        }
        if (isOpen(i-1, j)) {
            uf.union(pos, getPos(i-1, j));  // union above node if open
            uf2.union(pos, getPos(i-1, j));
        }
    }
   
    // union with neighboring sites on right column
    private void unionRight(int i, int j, int pos) {
        uf.union(pos, getPos(i, j-1));      // union left node
        uf2.union(pos, getPos(i, j-1));
        if (isOpen(i+1, j)) {
            uf.union(pos, getPos(i+1, j));   // union below node if open
            uf2.union(pos, getPos(i+1, j));
        }
        if (isOpen(i-1, j)) {
            uf.union(pos, getPos(i-1, j));   // union above node if open
            uf2.union(pos, getPos(i-1, j));
        }
    }
   
    // union with all 4 neighboring sites
    private void unionAll(int i, int j, int pos) {
        if (isOpen(i-1, j)) {
            uf.union(pos, getPos(i-1, j));   // union above node if open
            uf2.union(pos, getPos(i-1, j));
        }
        if (isOpen(i+1, j)) {
            uf.union(pos, getPos(i+1, j));   // union below node if open
            uf2.union(pos, getPos(i+1, j));
        }
        if (isOpen(i, j+1)) {
            uf.union(pos, getPos(i, j+1));   // union right node if open
            uf2.union(pos, getPos(i, j+1));
        }
        if (isOpen(i, j-1)) {
            uf.union(pos, getPos(i, j-1));   // union left node if open
            uf2.union(pos, getPos(i, j-1));
        }
    }
    
    // special case N = 1, N = 1 union with neighboring sites
    private void specialUnion(int i, int j, int pos) {
        if (N == 1) {                // if N=1 then union the only node with
            uf.union(pos, top);      // virtual top and bottom
            uf.union(pos, bottom);
            uf2.union(pos, top);
        }
        if (N == 2) {
            if (i == 1) {            // if first row then union with virtual
                uf.union(pos, top);  // top and the node below if open
                uf2.union(pos, top);
                if (isOpen(i+1, j)) {
                    uf.union(pos, getPos(i+1, j));
                    uf2.union(pos, getPos(i+1, j));
                }
            }
            if (i == 2) {               // if second row then union with virtual
                uf.union(pos, bottom); // bottom and node above it if open
                uf2.union(pos, bottom);
                if (isOpen(i-1, j)) {
                    uf.union(pos, getPos(i-1, j));
                    uf2.union(pos, getPos(i-1, j));
                }
            }
        }
    }
}
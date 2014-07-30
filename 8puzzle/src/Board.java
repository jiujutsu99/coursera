/*----------------------------------------------------------------
 *  Author:        Raymond Zhang
 *  Written:       7/18/2014
 *  Last updated:  7/18/2014
 * 
 *  An immutable data type Board
 * 
 *----------------------------------------------------------------*/

public class Board {
    private final int[][] board;
    private final int dim;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j
    public Board(int[][] blocks) {
        dim = blocks.length;
        board = copy(blocks);
    }
 
    // copies and return a 2D array
    private int[][] copy(int[][] copyThis) {
        int size = copyThis.length;
        int[][] newCopy = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                newCopy[i][j] = copyThis[i][j];
        }
        return newCopy;
    }

    // board dimension N
    public int dimension() {
        return dim;
    }

    // number of blocks out of place
    public int hamming() {
        int count = 0;
        int outOfPlace = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                count++;
                if (board[i][j] != count)
                    outOfPlace++;
            }
        }
        return outOfPlace - 1;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int value, row, col, dist = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                value = board[i][j];   // get the value of the square
                if (value == 0)        // skip if it's a zero
                    continue;
                row = (value - 1) / dim;  // calculate the value's correct position
                col = (value - 1) % dim;
            dist += Math.abs(i - row) + Math.abs(j - col);  // add the distance
            }
        }
        return dist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] boardcopy = copy(board);
        if (dim <= 1)
        return new Board(boardcopy);

        // set a firstNum to every loop of the array and if it's not equal to
        // zero
        // then end loop else set secNum equal to it
        int row = 0, col = 0, firstNum = 0, secNum = boardcopy[0][0];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                row = i;
                col = j;
                firstNum = boardcopy[i][j];
                if (firstNum != 0 && secNum != 0 && j > 0)
                    break;
                secNum = firstNum;
            }
        }
        boardcopy[row][col] = secNum;
        boardcopy[row][col - 1] = firstNum;
        return new Board(boardcopy);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)      // return true if referenced to same object
            return true;
        if (y == null)      // make sure it's not null
            return false;
        if (y.getClass() != this.getClass())   // make sure it's the same class
            return false;
        Board aBoard = (Board) y;
        if (aBoard.dimension() != dim)   // make sure it has same dimensions
            return false;
        for (int i = 0; i < dim; i++) {  // check each square matches
            for (int j = 0; j < dim; j++) {
                if (board[i][j] != aBoard.board[i][j])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();

        // search for zero's row and column position
        int zeroRow = 0, zeroCol = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break;
                }
            }
        }

        // test each condition of where zero could be then create a neighbor
        // board and add onto queue
        if (zeroRow > 0)
            q.enqueue(new Board(swap(zeroRow, zeroCol, zeroRow - 1, zeroCol)));
        if (zeroRow < dim - 1)
            q.enqueue(new Board(swap(zeroRow, zeroCol, zeroRow + 1, zeroCol)));
        if (zeroCol > 0)
            q.enqueue(new Board(swap(zeroRow, zeroCol, zeroRow, zeroCol - 1)));
        if (zeroCol < dim - 1)
            q.enqueue(new Board(swap(zeroRow, zeroCol, zeroRow, zeroCol + 1)));
        return q;
    }
 
    // swap zero with a square to create a neighbor board
    private int[][] swap(int zeroRow, int zeroCol, int newZeroRow, int newZeroCol) {
        int[][] neighbor = copy(board);
        int temp = neighbor[newZeroRow][newZeroCol];
        neighbor[newZeroRow][newZeroCol] = 0;
        neighbor[zeroRow][zeroCol] = temp;
        return neighbor;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
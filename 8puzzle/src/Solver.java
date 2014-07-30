/*----------------------------------------------------------------
 *  Author:        Raymond Zhang
 *  Written:       7/18/2014
 *  Last updated:  7/18/2014
 * 
 *  An immutable data type Solver
 * 
 *----------------------------------------------------------------*/

public class Solver {
    private SearchNode result;

    // SearchNode is a property of the A* search algorithm and has 4 properties:
    // a board, number of moves made so far to reach this board, the previous
    // search node, and a priority function (Hamming / Manhattan)
    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode previous;
        private final int priority;

        private SearchNode(Board myBoard, SearchNode node) {
            board = myBoard;
            previous = node;
            if (previous == null) // initial search node has a null previous
                                  // search node and zero moves
                moves = 0;
            else
                moves = previous.moves + 1;
            // Manhattan priority function is the sum of the Manhattan distances
            // and the number of moves made so far to get to the search node
            priority = board.manhattan() + moves;
        }

        public int compareTo(SearchNode node) {
            return priority - node.priority;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    // returns null if twin board is solved
    public Solver(Board initial) {
        if (initial.isGoal())
            result = new SearchNode(initial, null);
        else
            result = solve(initial, initial.twin());
    }

    private SearchNode step(MinPQ<SearchNode> pq) {
        SearchNode leastPriority = pq.delMin();
        for (Board neighbor : leastPriority.board.neighbors()) {
            if (leastPriority.previous == null || !neighbor.equals(leastPriority.previous.board))
                pq.insert(new SearchNode(neighbor, leastPriority));
        }
        return leastPriority;
    }

    private SearchNode solve(Board a, Board twin) {
        SearchNode last;
        MinPQ<SearchNode> myPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
        myPQ.insert(new SearchNode(a, null));
        twinPQ.insert(new SearchNode(twin, null));

        while (true) {
            last = step(myPQ);
            if (last.board.isGoal())
                return last;
            if (step(twinPQ).board.isGoal())
                return null;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return result != null;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (result != null)
            return result.moves;
        return -1;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        SearchNode solu = result;
        Stack<Board> iter = new Stack<Board>();
        if (solu == null)
            return null;
        while (solu.previous != null) {
            iter.push(solu.board);
            solu = solu.previous;
        }
        return iter;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
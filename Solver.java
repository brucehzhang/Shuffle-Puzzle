import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private class Node implements Comparable<Node> {
        private int count;
        private Board board;
        private Node previous;
        private int priority;

        private Node(int count, Board board, Node previous) {
            this.count = count;
            this.board = board;
            this.previous = previous;
            this.priority = board.manhattan() + count;
        }

        public int compareTo(Node n) {
            int priorityDifference = priority - n.priority;
            if (priorityDifference == 0) {
                return count - n.count;
            } else {
                return priorityDifference;
            }
        }
    }

    private MinPQ<Node> solutionPQ;
    private MinPQ<Node> solutionPQTwin;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Null argument");
        }
        Node currentNode = new Node(0, initial, null);
        solutionPQ = new MinPQ<>();
        solutionPQ.insert(currentNode);

        Node currentNodeTwin = new Node(0, initial.twin(), null);
        solutionPQTwin = new MinPQ<>();
        solutionPQTwin.insert(currentNodeTwin);

        while(!currentNode.board.isGoal() && !currentNodeTwin.board.isGoal()) {
            Node previousNode = solutionPQ.delMin();
            for (Board b : previousNode.board.neighbors()) {
                currentNode = new Node(previousNode.count + 1, b, previousNode);
                if (previousNode.previous != null) {
                    if (!b.equals(previousNode.previous.board)) {
                        solutionPQ.insert(currentNode);
                    }
                } else {
                    solutionPQ.insert(currentNode);
                }
            }

            Node previousNodeTwin = solutionPQTwin.delMin();
            for (Board b : previousNodeTwin.board.neighbors()) {
                currentNodeTwin = new Node(previousNodeTwin.count +1, b, previousNodeTwin);
                if (previousNodeTwin.previous != null) {
                    if (!b.equals(previousNodeTwin.previous.board)) {
                        solutionPQTwin.insert(currentNodeTwin);
                    }
                } else {
                    solutionPQTwin.insert(currentNodeTwin);
                }
            }
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (solutionPQ.min().board.isGoal()) {
            return true;
        } else {
            return false;
        }
    }

    // min number of moves to solve initial board
    public int moves() {
        if (isSolvable()) {
            return solutionPQ.min().count;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> winningMoves = new Stack<>();
        Node currentNode = solutionPQ.min();
        while (currentNode.previous != null) {
            winningMoves.push(currentNode.board);
            currentNode = currentNode.previous;
        }
        return winningMoves;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
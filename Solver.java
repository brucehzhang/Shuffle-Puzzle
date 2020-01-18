import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private class Node {
        private final int count;
        private final Board board;
        private final Node previous;
        private final int priority;

        private Node(int count, Board board, Node previous) {
            this.count = count;
            this.board = board;
            this.previous = previous;
            this.priority = board.manhattan() + count;
        }
    }

    private Node node;
    private Node nodeTwin;
    private final MinPQ<Node> solutionPQ;
    private final MinPQ<Node> solutionPQTwin;
    private final Comparator<Node> comparator = new Comparator<Node>() {
        @Override
        public int compare(Node n1, Node n2) {
            int priorityDifference = n1.priority - n2.priority;
            if (priorityDifference == 0) {
                return n1.count - n2.count;
            } else {
                return priorityDifference;
            }
        }
    };

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Null argument");
        }

        boolean solved = initial.isGoal();

        Node currentNode = new Node(0, initial, null);
        solutionPQ = new MinPQ<>(comparator);
        solutionPQ.insert(currentNode);

        if (solved) {
            node = currentNode;
        }

        Node currentNodeTwin = new Node(0, initial.twin(), null);
        solutionPQTwin = new MinPQ<>(comparator);
        solutionPQTwin.insert(currentNodeTwin);

        while (!solved) {
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
                if (currentNode.board.isGoal()) {
                    node = currentNode;
                }
            }
            if (node != null) {
                break;
            }

            Node previousNodeTwin = solutionPQTwin.delMin();
            for (Board b : previousNodeTwin.board.neighbors()) {
                currentNodeTwin = new Node(previousNodeTwin.count + 1, b, previousNodeTwin);
                if (previousNodeTwin.previous != null) {
                    if (!b.equals(previousNodeTwin.previous.board)) {
                        solutionPQTwin.insert(currentNodeTwin);
                    }
                } else {
                    solutionPQTwin.insert(currentNodeTwin);
                }
                if (currentNodeTwin.board.isGoal()) {
                    nodeTwin = currentNodeTwin;
                }
            }
            if (nodeTwin != null) {
                break;
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return node != null;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (isSolvable()) {
            return node.count;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> winningMoves = new Stack<>();
        Node currentNode = node;
        winningMoves.push(currentNode.board);
        while (currentNode.previous != null) {
            currentNode = currentNode.previous;
            winningMoves.push(currentNode.board);
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
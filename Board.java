import java.util.Arrays;
import java.util.ArrayList;

public class Board {
  private int dimension;
  private int size;
  private int[] board;

  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {
    dimension = tiles.length;
    board = Math.pow(dimension, 2);
    size = Math.pow(dimension, 2)
    int index = 0;
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        board[index] = tiles[i][j];
        index++;
      }
    }
  }

  // string representation of this board
  public String toString() {
    String boardRep = "" + dimension + "\n " + board[0];
    int index = 1;
    while (index < size) {
      if (index % dimension == 0) {
        boardRep += "\n " + board[index];
      } else {
        boardRep += "  " + board[index];
      }
      index++;
    }
    return boardRep;
  }

  // board dimension n
  public int dimension() {
    return dimension;
  }

  // number of tiles out of place
  public int hamming() {
    int wrongTiles = 0;
    for (int i = 0; i < board.length - 1; i++) {
      if (board[i] != i + 1) {
        wrongTiles++;
      }
    }
    if (board[board.length - 1] != 0) {
      wrongTiles++;
    }
    return wrongTiles;
  }

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    int distance = 0;
    for (int i = 0; i < board.length; i++) {
      if (board[i] != i + 1 && board[i] != 0) {
        // column distance
        distance += Math.abs((board[i] / dimension + 1) - ((i + 1) / dimension + 1));
        // row distance
        if (board[i] % dimension == 0) {
          distance += Math.abs(dimension - (i + 1) % dimension);
        } else if ((i + 1) % dimension == 0) {
          distance += Math.abs(board[i] % dimension - dimension);
        } else {
          distance += Math.abs(board[i] % dimension - (i + 1) % dimension);
        }
      }
    }
    return distance;
  }

  // is this board the goal board?
  public boolean isGoal() {
    for (int i = 0; i < dimension - 1; i++) {
      if (board[i] != i + 1) {
        return false;
      }
    }
    return true;
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (y instanceof Board) {
      Board yBoard = (Board) y;
      return Arrays.equals(board, yBoard.board);
    }
    return false;
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    int index = 0;
    for (int i = 0; i < dimension; i++) {
      if (board[i] == 0) {
        index = i;
        break;
      }
    }

  }

  // a board that is obtained by exchanging any pair of tiles
  public Board twin()

  // unit testing (not graded)
  public static void main(String[] args)

}
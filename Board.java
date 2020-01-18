import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.ArrayList;

public class Board {
  private final int dimension;
  private int size;
  private int[] board;

  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {
    dimension = tiles.length;
    board = new int[dimension * dimension];
    size = dimension * dimension;
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
    for (int i = 0; i < size - 1; i++) {
      if (board[i] != i + 1) {
        wrongTiles++;
      }
    }
    return wrongTiles;
  }

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    int distance = 0;
    for (int i = 0; i < size; i++) {
      if (board[i] != i + 1 && board[i] != 0) {
        // column distance
        distance += (int) Math.abs((board[i] - 1) / dimension - i / dimension);
        // row distance
        distance += Math.abs((board[i] - 1) % dimension - i % dimension);
      }
    }
    return distance;
  }

  // is this board the goal board?
  public boolean isGoal() {
    for (int i = 0; i < dimension; i++) {
      if (board[i] != i + 1 && board[i] != i + 1) {
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
    for (index = 0; index < size; index++) {
      if (board[index] == 0) {
        break;
      }
    }
    ArrayList<Board> neighborList = new ArrayList<>();
    if (index == 0) {
      neighborList.add(swap(index, index + 1));
      neighborList.add(swap(index, index + dimension));
    } else if (index % dimension == 0) {
      neighborList.add(swap(index, index + 1));
      neighborList.add(swap(index, index - dimension));
      if (size - index != dimension) {
        neighborList.add(swap(index, index + dimension));
      }
    } else if (index + 1 == dimension) {
      neighborList.add(swap(index, index - 1));
      neighborList.add(swap(index, index + dimension));
    } else if ((index + 1) % dimension == 0) {
      neighborList.add(swap(index, index - 1));
      neighborList.add(swap(index, index - dimension));
      if (index + 1 != size) {
        neighborList.add(swap(index, index + dimension));
      }
    } else if (index < dimension) {
      neighborList.add(swap(index, index - 1));
      neighborList.add(swap(index, index + 1));
      neighborList.add(swap(index, index + dimension));
    } else if (index >= size - dimension) {
      neighborList.add(swap(index, index - 1));
      neighborList.add(swap(index, index + 1));
      neighborList.add(swap(index, index - dimension));
    } else {
      neighborList.add(swap(index, index - 1));
      neighborList.add(swap(index, index + 1));
      neighborList.add(swap(index, index - dimension));
      neighborList.add(swap(index, index + dimension));
    }
    return neighborList;
  }

  private Board swap(int a, int b) {
    int[] clone = board.clone();
    int first = clone[a];
    int second = clone[b];
    clone[a] = second;
    clone[b] = first;
    int[][] twoDBoard = new int[dimension][dimension];
    int index = 0;
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        twoDBoard[i][j] = clone[index];
        index++;
      }
    }
    return new Board(twoDBoard);
  }

  // a board that is obtained by exchanging any pair of tiles
  public Board twin() {
    int randomTile = StdRandom.uniform(size);

    while (board[randomTile] == 0) {
      randomTile = StdRandom.uniform(size);
    }

    if (randomTile == 0) {
      if (board[randomTile + 1] != 0) {
        return swap(randomTile, randomTile + 1);
      } else {
        return swap(randomTile, randomTile + dimension);
      }
    } else if (randomTile % dimension == 0) {
      if (board[randomTile + 1] != 0) {
        return swap(randomTile, randomTile + 1);
      } else if (size - randomTile != dimension) {
        return swap(randomTile, randomTile + dimension);
      } else {
        return swap(randomTile, randomTile - dimension);
      }
    } else if (randomTile + 1 == dimension) {
      if (board[randomTile - 1] != 0) {
        return swap(randomTile, randomTile - 1);
      } else {
        return swap(randomTile, randomTile + dimension);
      }
    } else if ((randomTile + 1) % dimension == 0) {
      if (board[randomTile - 1] != 0) {
        return swap(randomTile, randomTile - 1);
      } else if ((randomTile + 1) != size) {
        return swap(randomTile, randomTile + dimension);
      } else {
        return swap(randomTile, randomTile - dimension);
      }
    } else {
      if (board[randomTile - 1] != 0) {
        return swap(randomTile, randomTile - 1);
      } else {
        return swap(randomTile, randomTile + 1);
      }
    }
  }

  // unit testing (not graded)
  public static void main(String[] args){

  }

}
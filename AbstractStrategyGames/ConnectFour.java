// Lewis Going
// CSE 123
// C0: Abstract Strategy Games
// 4/12/2023
//
// A class to represent a game of Connect Four that implements the
// AbstractStrategyGame interface.
import java.util.*;

public class ConnectFour implements AbstractStrategyGame {
    private char[][] board;
    private boolean isXTurn;

    // Constructs a new ConnectFour game.
    public ConnectFour() {
        board = new char[][]{{'-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-'},
        };
        isXTurn = true;
    }

    // Returns a String containing instructions to play the game.
    public String instructions() {
        String result = "";
        result += "Player 1 is R (red) and goes first. Player 2 is Y (yellow). Choose a column\n";
        result += "to play by entering a column number (0-6), 0 being the leftmost column.\n";
        result += "Pieces fall to the lowest available space in the selected column. Spaces shown\n";
        result += "as '-' are empty. The game ends when one player connects four of their pieces\n";
        result += "in a row, either horizontally, vertically, or diagonally, in which case that\n";
        result += "player wins, or when the board is full, in which case the game ends in a tie.";
        return result;
    }

    // Returns a String representation of the current state of the game.
    public String toString() {
        String result = "";
        for (char[] chars : board) {
            for (int j = 0; j < chars.length; j++) {
                result += chars[j] + " ";
            }
            result += "\n";
        }
        return result;
    }

    // Returns whether the game is over.
    public boolean isGameOver() {
        return getWinner() >= 0;
    }

    // Returns the index of the winner of the game.
    // 1 if player 1 (X), 2 if player 2 (O), 0 if a tie occurred,
    // and -1 if the game is not over. Checks for vertical, horizontal
    // and diagonal streaks.
    public int getWinner() {
        int streak = 4;

        // Checks for horizontal & vertical streaks
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length - streak + 1; j++) { // stays in bounds
                // check row i
                if (board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] &&
                        board[i][j] == board[i][j + 3] && board[i][j] != '-') {
                    return board[i][j] == 'R' ? 1 : 2;
                }
                // check col j (within bounds)
                if (i < board.length - streak + 1 && board[i][j] == board[i + 1][j] &&
                        board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j] &&
                        board[i][j] != '-') {
                    return board[i][j] == 'R' ? 1 : 2;
                }
            }
        }

        // Checks for diagonal streaks
        for (int i = 0; i < board.length - streak + 1; i++) {
            for (int j = 0; j < board[0].length - streak + 1; j++) {
                if (board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] &&
                        board[i][j] == board[i + 3][j + 3] && board[i][j] != '-') {
                    return board[i][j] == 'R' ? 1 : 2;
                }

                if (board[i + 3][j] == board[i + 2][j + 1] && board[i + 3][j] == board[i + 1][j + 2] &&
                        board[i + 3][j] == board[i][j + 3] && board[i + 3][j] != '-') {
                    return board[i + 3][j] == 'R' ? 1 : 2;
                }
            }
        }

        // Checks for game not over / tie
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '-') {
                    // unfilled space; game not over
                    return -1;
                }
            }
        }
        return 0; // returns tie
    }

    // Returns the index of the with the next turn.
    // Returns 1 if player 1 (R), 2 if player 2 (Y)
    public int getNextPlayer() {
        return isXTurn ? 1 : 2;
    }

    // Takes input from the parameter to specify the move the player
    // with the next turn wishes to make, then executes that move.
    // If any part of the move is illegal, throws an IllegalArgumentException.
    public void makeMove(Scanner input) {
        char currPlayer = isXTurn ? 'R' : 'Y';

        System.out.print("Column (0-6)? ");
        int col = input.nextInt();

        makeMove(col, currPlayer);
        isXTurn = !isXTurn;
    }

    // Private helper method for makeMove.
    // Given a column and player index places an
    // R or Y at the lowest available space in the
    // selected column. Throws and IllegalArgumentException
    // if the position is invalid, whether that be the column
    // is full or the index is out of bounds.
    // Bounds are (0-6) for columns
    private void makeMove(int col, char player) {
        if (col < 0 || col >= board[0].length) {
            throw new IllegalArgumentException("Invalid column: " + col);
        }

        if (board[0][col] != '-') {
            throw new IllegalArgumentException("Column already occupied: " + col);
        }

        int row = board.length - 1;
        while (board[row][col] != '-') {
            row--;
        }

        board[row][col] = player;
    }
}

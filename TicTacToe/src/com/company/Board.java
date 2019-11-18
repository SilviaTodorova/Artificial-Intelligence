package com.company;

import java.util.HashSet;

public class Board {
    private Player[][] board;
    private Player playersTurn;
    private Player winner;
    private int movesCount;
    private boolean isGameOver;
    private HashSet<Integer> movesAvailable;

    public Board() {
        playersTurn = Player.UNDEFINED;
        winner = Player.UNDEFINED;
        movesCount = 0;
        isGameOver = false;
        movesAvailable = new HashSet<>();
        initializeBoard();
    }

    public Player getPlayersTurn() {
        return playersTurn;
    }

    public void setPlayersTurn(Player playersTurn) {
        this.playersTurn = playersTurn;
    }

    public HashSet<Integer> getAvailableMoves() {
        return movesAvailable;
    }

    public Player getWinner() {
        return winner;
    }

    public Board copy() {
        Board board = new Board();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.board[i][j] = this.board[i][j];
            }
        }

        board.playersTurn = this.playersTurn;
        board.winner = this.winner;
        board.movesAvailable = new HashSet<>();
        board.movesAvailable.addAll(this.movesAvailable);
        board.movesCount = this.movesCount;
        board.isGameOver = this.isGameOver;

        return board;
    }

    private void initializeBoard() {
        board = new Player[3][3];

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                board[i][j] = Player.UNDEFINED;

            }
        }

        movesAvailable.clear();

        int size = 3 * 3;

        for (int i = 0; i < size; i++) {
            movesAvailable.add(i);
        }
    }

    public boolean move(int index) {
        return makeMove(index / 3, index % 3);
    }

    public boolean makeMove(int row, int col) {
        if (board[row][col] != Player.UNDEFINED) {
            // System.out.println("This place is not empty!");
            return false;
        }

        board[row][col] = playersTurn;
        ++movesCount;
        movesAvailable.remove(row * 3 + col);

        isGameOver = isWinner();

        playersTurn = Player.YOU == playersTurn ? Player.COMPUTER : Player.YOU;

        return true;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    private boolean isWinner() {
        for (int index = 0; index < 3; index++) {
            //Column winner
            if (board[0][index] == board[1][index] && board[0][index] == board[2][index] && board[0][index] != Player.UNDEFINED) {
                winner = board[0][index];
                return true;
            }

            //Row winner
            if (board[index][0] == board[index][1] && board[index][0] == board[index][2] && board[index][0] != Player.UNDEFINED) {
                winner = board[index][0];
                return true;
            }
        }

        //Main diagonal winner
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != Player.UNDEFINED) {
            winner = board[0][0];
            return true;
        }

        // Second diagonal winner
        if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != Player.UNDEFINED) {
            winner = board[0][2];
            return true;
        }

        // Draw or None
        return (movesCount == 9 && winner == Player.UNDEFINED) ? true : false;
    }

    public void displayBoard() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                System.out.printf("%s ", board[i][j].getPlayer());
            }
            System.out.println();
        }

        System.out.println();
    }

}

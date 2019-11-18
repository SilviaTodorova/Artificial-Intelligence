package com.company;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
    private Player[][] board;
    private Player turn;
    private Player winner;
    private Set<Point> movesAvailable;

    public Board(Player turn) {
        board = new Player[3][3];
        Arrays.stream(board).forEach(elem -> Arrays.fill(elem, Player.UNDEFINED));

        this.turn = turn;
        this.winner = Player.UNDEFINED;

        this.movesAvailable = new HashSet<>(9);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Point point = new Point(row, col);
                movesAvailable.add(point);
            }
        }
    }

    public void displayBoard() {
        System.out.println();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                String player = board[row][col].getPlayer();
                System.out.print(player + " ");
            }
            System.out.println();
        }
    }

    public Player getTurn() {
        return this.turn;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public boolean move(Player turn, Point point) {
        int x = point.getX();
        int y = point.getY();
        Player step = this.board[x][y];

        this.turn = (turn.equals(Player.YOU)) ? Player.COMPUTER : Player.YOU;

        if (step.equals(Player.UNDEFINED)) {
            this.board[x][y] = turn;
            this.movesAvailable.remove(point);
            return true;
        } else {
            return false;
        }
    }

    public Player checkWinner() {
        this.checkRows();
        this.checkColumns();
        this.checkMainDiagonal();
        this.checkReversedDiagonal();

        return this.winner;
    }

    public boolean isGameOver() {
        Player player = checkWinner();

        if (this.movesAvailable.size() > 0) {
            return player.equals(Player.UNDEFINED) ? false : true;
        } else {
            this.winner = Player.UNDEFINED;
            return true;
        }
    }

    private void checkRows() {
        int countComputer = 0;
        int countYou = 0;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].equals(Player.YOU)) {
                    countYou++;
                }

                if (board[row][col].equals(Player.COMPUTER)) {
                    countComputer++;
                }
            }

            if (countYou == 3) {
                this.winner = Player.YOU;
                break;
            }

            if (countComputer == 3) {
                this.winner = Player.COMPUTER;
                break;
            }

            countComputer = 0;
            countYou = 0;
        }
    }

    private void checkColumns() {
        int countComputer = 0;
        int countYou = 0;

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                if (board[row][col].equals(Player.YOU)) {
                    countYou++;
                }

                if (board[row][col].equals(Player.COMPUTER)) {
                    countComputer++;
                }
            }

            if (countYou == 3) {
                this.winner = Player.YOU;
                break;
            }

            if (countComputer == 3) {
                this.winner = Player.COMPUTER;
                break;
            }

            countComputer = 0;
            countYou = 0;
        }
    }

    private void checkMainDiagonal() {
        int countComputer = 0;
        int countYou = 0;

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                if (row == col) {
                    if (board[row][col].equals(Player.YOU)) {
                        countYou++;
                    }

                    if (board[row][col].equals(Player.COMPUTER)) {
                        countComputer++;
                    }
                }
            }
        }

        if (countYou == 3) {
            this.winner = Player.YOU;
            return;
        }

        if (countComputer == 3) {
            this.winner = Player.COMPUTER;
            return;
        }

    }

    private void checkReversedDiagonal() {
        int countComputer = 0;
        int countYou = 0;

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                if ((row + col) == 2) {
                    if (board[row][col].equals(Player.YOU)) {
                        countYou++;
                    }

                    if (board[row][col].equals(Player.COMPUTER)) {
                        countComputer++;
                    }
                }
            }
        }

        if (countYou == 3) {
            this.winner = Player.YOU;
            return;
        }

        if (countComputer == 3) {
            this.winner = Player.COMPUTER;
            return;
        }
    }

    public Set<Point> getAvailableMoves() {
        return movesAvailable;
    }

    public Board getDeepCopy() {
        Board board = new Board(Player.COMPUTER);

        for (int i = 0; i < board.board.length; i++) {
            board.board[i] = this.board[i].clone();
        }

        board.turn = this.turn;
        board.winner = this.winner;
        board.movesAvailable = new HashSet<>();
        board.movesAvailable.addAll(this.movesAvailable);
        board.winner = this.winner;
        return board;
    }
}

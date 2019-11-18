package com.company;

import java.util.Scanner;

public class TicTacToe {
    private Board board;
    private MinMaxAlgorithm minMaxAlgorithm;

    public Player play(Player turn, Scanner in) {
        this.board = new Board(turn);
        minMaxAlgorithm = new MinMaxAlgorithm();

        do {
            board.displayBoard();
            this.nextStep(in);
        } while (!board.isGameOver());

        board.displayBoard();

        return board.getWinner();
    }

    private void nextStep(Scanner in) {
        if(board.isGameOver()){
            return;
        }

        Player turn = this.board.getTurn();

        if (turn.equals(Player.YOU)) {
            System.out.printf("%nIt's your turn X:%n");
            this.getPlayerStep(Player.YOU, in);
        } else {
            System.out.printf("%nIt's computer turn 0:%n");
            minMaxAlgorithm.alphaBetaDecision(Player.COMPUTER, board);
            //this.getPlayerStep(Player.COMPUTER, in);
        }

        Player winner = board.checkWinner();
        if (!board.isGameOver()) {
            board.setWinner(winner);
        }
    }

    private void getPlayerStep(Player player, Scanner in) {
        boolean correctInput = false;
        do {
            System.out.print("row = ");
            int x = in.nextInt() - 1;
            while (x < 0 || x > 2) {
                System.out.println("Incorrect option! Please row index between 1 and 3: ");
                System.out.print("row = ");
                x = in.nextInt() - 1;
            }

            System.out.print("column = ");
            int y = in.nextInt() - 1;
            while (y < 0 || y > 2) {
                System.out.println("Incorrect option! Please choose column between 1 and 3: ");
                System.out.print("column = ");
                y = in.nextInt() - 1;
            }

            Point point = new Point(x, y);
            correctInput = board.move(player, point);

            if (!correctInput) {
                System.out.printf("Incorrect input!%n");
            }

        } while (!correctInput);
    }
}

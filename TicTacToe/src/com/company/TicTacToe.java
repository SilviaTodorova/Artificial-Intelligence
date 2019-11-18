package com.company;

import java.util.Random;
import java.util.Scanner;

class TicTacToe {
    private final Board board = new Board();
    private final MinMaxAlgorithm algorithm = new MinMaxAlgorithm();

    public Player play(Player turn, Scanner in) {
        board.setPlayersTurn(Player.YOU);

        Random random = new Random();
        if(turn.equals(Player.COMPUTER)){
            board.setPlayersTurn(Player.COMPUTER);
            board.makeMove(random.nextInt(3), random.nextInt(3));
        }

        do{
            board.displayBoard();

            this.nextStep(in, board, Player.YOU);
            board.displayBoard();

            this.nextStep(in, board, Player.COMPUTER);
        }while(!board.isGameOver());

        board.displayBoard();

        return board.getWinner();
    }

    private void nextStep(Scanner in, Board board, Player turn) {
        if(turn.equals(Player.YOU)){
            System.out.printf("It's your turn X:%n");
            this.getPlayerStep(in, board);
        }else{
            System.out.printf("It's computer turn 0:%n");
            algorithm.start(board, board.getPlayersTurn(), 0);
        }
    }

    private void getPlayerStep(Scanner in, Board board) {
        boolean correctInput = false;
        do {
            System.out.print("row = ");
            int x = in.nextInt() - 1;
            while (x < 0 || x > 2) {
                System.out.printf("%nIncorrect option! Please row index between 1 and 3:%n");
                System.out.print("row = ");
                x = in.nextInt() - 1;
            }

            System.out.print("column = ");
            int y = in.nextInt() - 1;
            while (y < 0 || y > 2) {
                System.out.printf("%nIncorrect option! Please choose column between 1 and 3:%n");
                System.out.print("column = ");
                y = in.nextInt() - 1;
            }

            correctInput = board.makeMove(x,y);

            if (!correctInput) {
                System.out.printf("%nIncorrect input!%n");
            }

        } while (!correctInput);
    }
}

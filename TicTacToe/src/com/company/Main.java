package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.printf(String.format("Choose which player is first: %n(1) You%n(2) Computer%n%nPlease, enter option: "));

        int turn = in.nextInt();

        while (turn != 1 && turn != 2) {
            System.out.print("Incorrect option! Please choose between 1 and 2: ");
            turn = in.nextInt();
        }

        TicTacToe game = new TicTacToe();
        Player player = Player.getEnumPlayer(turn);
        Player winner = game.play(player, in);

        if(winner.equals(Player.COMPUTER)){
            System.out.printf(String.format("%nGAME OVER!%n"));
        }else if(winner.equals(Player.YOU)){
            System.out.printf(String.format("%nCONGRATULATIONS, YOU WON!%n"));
        }else{
            System.out.printf(String.format("%nTHERE AREN'T WINNER!%n"));
        }

    }
}

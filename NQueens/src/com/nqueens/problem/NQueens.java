package com.nqueens.problem;

import java.util.Scanner;

public class NQueens {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter positive number of queens: ");
        int numberOfQueens = in.nextInt();
        while (numberOfQueens < 0 || (numberOfQueens > 1 && numberOfQueens < 4 )) {
            System.out.print("Incorrect number of queens. Please, enter 1, 4, 5, ....: ");
            numberOfQueens = in.nextInt();
        }

        MinConflictAlgorithm nqueens = new MinConflictAlgorithm(numberOfQueens);

        long startTime = System.currentTimeMillis();
        nqueens.findSolution();
        long endTime = System.currentTimeMillis();

        long time = endTime - startTime;
        System.out.printf("Time for execution (in millis): %d%n", time);
        System.out.printf("Time for execution (in seconds): %.2f%n", (double) time / 1000 );
        System.out.println("Would you like to see the result?");
        System.out.print("Yes / No :");
        String printResult = in.next().toLowerCase();
        in.close();

        if(printResult.equals("yes")){
            nqueens.printSolution();
        }

    }
}

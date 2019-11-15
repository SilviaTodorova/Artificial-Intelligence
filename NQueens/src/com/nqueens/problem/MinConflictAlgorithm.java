package com.nqueens.problem;

import java.util.*;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MinConflictAlgorithm {
    private static int MAX_ITERATIONS;
    private static IntPredicate SMALLER_THAN_TWO;

    private Random random;

    private int numberOfQueens;
    private int[] queens;
    private int[] countQueensRow;
    private int[] countQueensMainDiagonal;
    private int[] countQueensSecondaryDiagonal;

    public MinConflictAlgorithm(int numberOfQueens) {
        MAX_ITERATIONS = 2 * numberOfQueens;
        SMALLER_THAN_TWO = elem -> elem > -1 && elem < 2;

        random = new Random();

        this.numberOfQueens = numberOfQueens;
        queens = new int[this.numberOfQueens];

        countQueensRow = new int[this.numberOfQueens];
        countQueensMainDiagonal = new int[2 * this.numberOfQueens - 1];
        countQueensSecondaryDiagonal = new int[2 * this.numberOfQueens - 1];

        initializeBoard();
    }

    public void findSolution() {
        int i = 0;
        while (true) {
            int column = getColumnWithMaxConflicts();

            if (column == -1) {
                break;
            }

            int row = getRowWithMinConflicts(column);

            this.moveQueen(column, row);

            i++;

            if (i == MAX_ITERATIONS && !hasConflicts()) {
                //Random restart
                this.initializeBoard();
                i = 0;
            }
        }
    }

    public void printSolution() {
        for (int row = 0; row < this.numberOfQueens; row++) {
            for (int col = 0; col < this.numberOfQueens; col++) {
                char sign = queens[col] == row ? 'X' : '0';
                System.out.print(String.format("%c ", sign));
            }
            System.out.println();
        }
    }

    private void initializeBoard() {
        List<Integer> queensList = IntStream.range(0, this.numberOfQueens).boxed().collect(Collectors.toList());
        Collections.shuffle(queensList);
        queens = queensList.stream().mapToInt(Integer::intValue).toArray();

        Arrays.fill(countQueensRow, 0);
        Arrays.fill(countQueensMainDiagonal, 0);
        Arrays.fill(countQueensSecondaryDiagonal, 0);

        for (int col = 0; col < numberOfQueens; col++) {
            int currentRow = queens[col];
            countQueensRow[col] = (int) Arrays.stream(this.queens).filter(row -> row == currentRow).count();

            int positionInMainDiagonal = col - currentRow + numberOfQueens - 1;
            ++countQueensMainDiagonal[positionInMainDiagonal];

            int positionInReversedDiagonal = col + currentRow;
            ++countQueensSecondaryDiagonal[positionInReversedDiagonal];
        }
    }

    //Move queen which is on 'column' on 'row'
    private void moveQueen(int column, int row) {
        int previousRow = queens[column];

        queens[column] = row;

        --countQueensRow[previousRow];
        ++countQueensRow[row];

        --countQueensMainDiagonal[column - previousRow + this.numberOfQueens - 1];
        ++countQueensMainDiagonal[column - row + this.numberOfQueens - 1];

        --countQueensSecondaryDiagonal[column + previousRow];
        ++countQueensSecondaryDiagonal[column + row];
    }

    private boolean hasConflicts() {
        boolean rowNoConflicts = Arrays.stream(countQueensRow).allMatch(SMALLER_THAN_TWO);
        boolean rowNoMainDiagonalConflicts = Arrays.stream(countQueensMainDiagonal).allMatch(SMALLER_THAN_TWO);
        boolean rowNoSecondaryDiagonalConflicts = Arrays.stream(countQueensSecondaryDiagonal).allMatch(SMALLER_THAN_TWO);

        return !rowNoConflicts || !rowNoMainDiagonalConflicts || !rowNoSecondaryDiagonalConflicts;
    }

    private int getColumnWithMaxConflicts() {
        List<Integer> queensWithMaxConflicts = new ArrayList<>();
        int maxConflicts = 0;

        for (int col = 0; col < this.numberOfQueens; col++) {
            int conflicts = countQueensRow[queens[col]]
                    + countQueensMainDiagonal[col - queens[col] + this.numberOfQueens - 1]
                    + countQueensSecondaryDiagonal[col + queens[col]] - 3;

            if (conflicts == maxConflicts) {
                queensWithMaxConflicts.add(col);
            } else if (conflicts > maxConflicts) {
                maxConflicts = conflicts;
                queensWithMaxConflicts.clear();
                queensWithMaxConflicts.add(col);
            }
        }

        if (maxConflicts == 0) {
            return -1;
        }
        return queensWithMaxConflicts.get(random.nextInt(queensWithMaxConflicts.size()));
    }

    private int getRowWithMinConflicts(int column) {
        List<Integer> queensWithMinConflicts = new ArrayList<>();
        int minConflicts = this.numberOfQueens;

        for (int row = 0; row < this.numberOfQueens; row++) {
            if (row != queens[column]) {
                int conflicts = countQueensRow[row] + countQueensMainDiagonal[column - row + this.numberOfQueens - 1]
                        + countQueensSecondaryDiagonal[column + row];

                if (conflicts == minConflicts) {
                    queensWithMinConflicts.add(row);
                } else if (conflicts < minConflicts) {
                    minConflicts = conflicts;
                    queensWithMinConflicts.clear();
                    queensWithMinConflicts.add(row);
                }
            }
        }

        return queensWithMinConflicts.get(random.nextInt(queensWithMinConflicts.size()));
    }
}

package com.company;


public class MinMaxAlgorithm {
    private static final int MAX_DEPTH = 10;

    void alphaBetaDecision(Player player, Board board) {
        alphaBetaDecision(player, board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
    }

    private int alphaBetaDecision(Player player, Board board, int alpha, int beta, int depth) {
        if (depth++ == MAX_DEPTH || board.isGameOver()) {
            return score(player, board, depth);
        }

        if (board.getTurn() == player) {
            return MAX_VALUE(player, board, alpha, beta, depth);
        } else {
            return MIN_VALUE(player, board, alpha, beta, depth);
        }
    }

    private int MAX_VALUE(Player player, Board board, int alpha, int beta, int depth) {
        Point bestMove = null;

        for (Point currentMove : board.getAvailableMoves()) {
            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(Player.COMPUTER, currentMove);

            int score = alphaBetaDecision(player, modifiedBoard, alpha, beta, depth);

            if (score > alpha) {
                alpha = score;
                bestMove = currentMove;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (bestMove != null) {
            board.move(Player.COMPUTER, bestMove);
        }

        return alpha;
    }

    private int MIN_VALUE(Player player, Board board, int alpha, int beta, int depth) {
        Point bestMove = null;

        for (Point move : board.getAvailableMoves()) {
            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(Player.COMPUTER, move);

            int score = alphaBetaDecision(player, modifiedBoard, alpha, beta, depth);

            if (score < beta) {
                beta = score;
                bestMove = move;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (bestMove != null) {
            board.move(Player.COMPUTER, bestMove);
        }
        return beta;
    }

    private int score(Player player, Board board, int depth) {
        Player opponent = Player.COMPUTER;

        if (board.isGameOver() && board.getWinner() == player) {
            return 10 - depth;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return depth - 10;
        } else {
            return 0;
        }
    }

}

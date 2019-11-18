package com.company;

class MinMaxAlgorithm {
    private final int NEGATIVE_INFINITY;
    private final int POSITIVE_INFINITY;
    private final int currentDepth;

    public MinMaxAlgorithm() {
        NEGATIVE_INFINITY = Integer.MIN_VALUE;
        POSITIVE_INFINITY = Integer.MAX_VALUE;
        currentDepth = 0;
    }

    public int start(Board board, Player player, int currentDepth){
        currentDepth++;
        if(board.isGameOver()){
            return score(board, player, currentDepth);
        }

        if(player == board.getPlayersTurn()){
            return getMax(board, player, NEGATIVE_INFINITY, POSITIVE_INFINITY, currentDepth);
        } else {
            return getMin(board, player, NEGATIVE_INFINITY, POSITIVE_INFINITY, currentDepth);
        }
    }

    private int getMax(Board board, Player player, int alpha, int beta, int currentDepth){
        int indexOfBestMove = -1;

        for (Integer move : board.getAvailableMoves()) {
            Board modifiedBoard = board.copy();
            modifiedBoard.move(move);
            int score = start(modifiedBoard, player, currentDepth);

            if(score > alpha){
                alpha = score;
                indexOfBestMove = move;
            }

            if(alpha >= beta){
                break;
            }

        }

        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }
        return alpha;
    }

    private int getMin(Board board, Player player, int alpha, int beta, int currentDepth){
        int indexOfBestMove = -1;

        for (Integer move : board.getAvailableMoves()) {
            Board modifiedBoard = board.copy();
            modifiedBoard.move(move);
            int score = start(modifiedBoard, player, currentDepth);

            if(score < beta){
                beta = score;
                indexOfBestMove = move;
            }

            if(alpha >= beta){
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }
        return beta;
    }

    private int score(Board board, Player player, int currentDepth){
        Player opponent = (player == Player.YOU) ? Player.COMPUTER : Player.YOU;

        if(board.getWinner() == player){
            return 10 - currentDepth;
        } else if(board.getWinner() == opponent){
            return -10 + currentDepth;
        } else{
            return 0;
        }
    }
}

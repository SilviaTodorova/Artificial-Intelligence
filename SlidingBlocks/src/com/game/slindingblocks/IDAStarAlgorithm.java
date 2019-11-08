package com.game.slindingblocks;

import java.util.ArrayList;
import java.util.List;

public class IDAStarAlgorithm {
    private Node initialState;
    private Node finalState;
    private int size;

    public IDAStarAlgorithm(int[] initialState, int size, int zeroPosition) {
        this.initialState = new Node(initialState,size,null);
        this.size = size;
        this.finalState = this.makeFinalState(zeroPosition);
    }

    private Node makeFinalState(int zeroPosition){
        int[] state = new int[size*size];

        int counter = 1;
        for(int index = 0; index < size*size;index++){
            if(index == zeroPosition){
                state[index]=0;
            }else{
                state[index] = counter;
                counter++;
            }
        }

        return new Node(state,size,null);
    }

    public Node findSolution() {
        int heuristic = this.manhattanSum(this.initialState, this.finalState);

        ArrayList<Node> path = new ArrayList<>();
        path.add(0, this.initialState);

        int threshold;
        do {
            threshold = limitedSearch(path, 0, heuristic);

            if (threshold == 0) {
                return path.get(path.size() - 1);
            }

            heuristic = threshold;
        } while (heuristic != Double.MAX_VALUE);

        return null;
    }

    private int limitedSearch(ArrayList<Node> path, int cost, int totalValue) {
        Node currentNode = path.get(path.size() - 1);
        currentNode.setHeuristic(this.manhattanSum(currentNode, this.finalState));
        currentNode.setCost(cost);
        currentNode.setTotalValue(cost + currentNode.getHeuristic());

        if (currentNode.getTotalValue() > totalValue) {
            return currentNode.getTotalValue();
        }

        if (currentNode.equals(this.finalState)) {
            return 0;
        }

        int threshold = Integer.MAX_VALUE;

        List<Node> children = currentNode.findStates();

        for (Node child : children) {
            if (!path.contains(child)) {
                path.add(child);
                int limit = limitedSearch(path, currentNode.getCost() + 1, totalValue);
                if (limit == 0) {
                    return 0;
                }

                if (limit < threshold) {
                    threshold = limit;
                }

                path.remove(path.size() - 1);
            }
        }

        return threshold;
    }

    public List<Node> getPath(Node state) {
        ArrayList<Node> path = new ArrayList<>();
        path.add(state);

        while (state.getParent() != null && state.getParent().getDirection() != null) {
            path.add(0, state.getParent());
            state = state.getParent();
        }

        return path;
    }

    public int manhattanSum(Node searchNode, Node finalNode) {
        int[] finalBoard = finalNode.getState();
        int[] currentBoard = searchNode.getState();

        int score = 0;
        for (int index = 0; index < finalBoard.length; index++) {
            int finalNodeIndex = 0;

            for (int swapNodeIndex = 0; swapNodeIndex < finalBoard.length; swapNodeIndex++) {
                if (finalBoard[index] == currentBoard[swapNodeIndex]) {
                    finalNodeIndex = swapNodeIndex;
                }
            }

            Position currentIndices = new Position(finalNodeIndex / size, finalNodeIndex % size);
            Position finalIndices = new Position(index / size, index % size);

            score += Math.abs(currentIndices.getCol() - finalIndices.getCol()) + Math.abs(currentIndices.getRow() - finalIndices.getRow());
        }

        return score;
    }

    public void printSolution(Node endPathNode){
        List<Node> path = this.getPath(endPathNode);

        for(Node node: path){
            System.out.println(node.getDirection().getPosition());
        }

    }
}


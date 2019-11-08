package com.game.slindingblocks;

import java.util.*;

public class Node implements Comparator<Node> {
    private int heuristic;
    private int cost;
    //heuristic + cost
    private int totalValue;

    private Node parent;
    private int zeroPosition;
    private int size;
    private int[] state;
    private Move direction;

    public Node() {
        this.setHeuristic(Integer.MIN_VALUE);
        this.setTotalValue(0);
        this.setCost(0);
    }

    public Node(int[] state, int size, Move step) {
        this();

        this.setDirection(step);
        this.setSize(size);
        this.setState(state);

        for (int currentSpace = 0; currentSpace < state.length; currentSpace++) {
            if (state[currentSpace] == 0) {
                this.setZeroPosition(currentSpace);
            }
        }
    }

    public Node(int[] state, int size, int zeroPosition, Node parentState, Move move) {
        this(state, size, move);
        this.setParent(parentState);
    }

    public int getHeuristic() {
        return this.heuristic;
    }

    public void setHeuristic(int hScore) {
        this.heuristic = hScore;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int gScore) {
        this.cost = gScore;
    }

    public int getTotalValue() {
        return this.totalValue;
    }

    public void setTotalValue(int fScore) {
        this.totalValue = fScore;
    }

    public Move getDirection() {
        return direction;
    }

    public void setDirection(Move direction) {
        this.direction = direction;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int[] getState() {
        return this.state;
    }

    public void setState(int[] state) {
        this.state = state;
    }

    @Override
    public int compare(Node node1, Node node2) {
        return node1.getTotalValue() - node2.getTotalValue();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getZeroPosition() {
        return this.zeroPosition;
    }

    public void setZeroPosition(int zeroPosition) {
        this.zeroPosition = zeroPosition;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.getState());
    }

    @Override
    public boolean equals(Object otherState) {
        if (otherState instanceof Node) {
            return Arrays.equals(((Node) otherState).getState(), this.getState());
        }

        return false;
    }

    public ArrayList<Node> findStates() {
        Map<Integer, Move> possibleNextMoves = new HashMap<>();

        int row = this.zeroPosition / size;
        int col = this.zeroPosition % size;

        if (col > 0) {
            possibleNextMoves.put(this.zeroPosition - 1, Move.RIGHT);
        }

        if (col < size - 1) {
            possibleNextMoves.put(this.zeroPosition + 1, Move.LEFT);
        }

        if (row > 0) {
            possibleNextMoves.put(this.zeroPosition - size, Move.DOWN);
        }

        if (row < size - 1) {
            possibleNextMoves.put(this.zeroPosition + size, Move.UP);
        }

        ArrayList<Node> successors = new ArrayList<>();
        int[] successorBoard;
        for (Integer nextSpace : possibleNextMoves.keySet()) {
            successorBoard = Arrays.copyOf(this.getState(), this.getState().length);
            successorBoard[this.zeroPosition] = this.getState()[nextSpace];
            successorBoard[nextSpace] = 0;

            Move move = possibleNextMoves.get(nextSpace);
            successors.add(new Node(successorBoard, size, this.zeroPosition, this, move));
        }

        return successors;
    }


}
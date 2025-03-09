package edu.washburn.cis.ichabot.implementation.tictactoe;

public class TicTacToeAction {
    public final int ROW;
    public final int COL;

    public TicTacToeAction(int row, int col) {
        this.ROW = row;
        this.COL = col; 
    }

    public String toString() {
        return "(move: " + ROW + ", " + COL + ")";
    }
}
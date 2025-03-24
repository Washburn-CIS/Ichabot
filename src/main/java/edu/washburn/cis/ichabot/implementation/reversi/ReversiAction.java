package edu.washburn.cis.ichabot.implementation.reversi;

public class ReversiAction {
    public final int ROW;
    public final int COL;
    public final boolean SKIP;

    public ReversiAction(int row, int col) {
        this.ROW = row;
        this.COL = col; 
        this.SKIP = false;
    }

    public ReversiAction() {
        this.SKIP = true;
        this.ROW = 0;
        this.COL = 0;
    }

    public String toString() {
        return SKIP ? "(move: skip)" : "(move: " + ROW + ", " + COL + ")";
    }
}
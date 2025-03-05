package edu.washburn.cis.ichabot.implementation.tictactoe;

public class TicTacToeAction {
    public final char PLAYER;
    public final int ROW;
    public final int COL;

    public TicTacToeAction(char player, int row, int col) {
        this.PLAYER = player;
        this.ROW = row;
        this.COL = col; 
    }
}
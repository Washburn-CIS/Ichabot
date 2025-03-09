package edu.washburn.cis.ichabot.implementation.tictactoe;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import edu.washburn.cis.ichabot.search.*;

public class TicTacToeState extends MinimaxSearchState<TicTacToeAction,TicTacToeState> {
    
    public static int expansions = 0;

    public final char[][] BOARD;
    public final char PLAYER;
    public final char TURN;

    public TicTacToeState(int boardSize, char player) {
        this.BOARD = new char[boardSize][boardSize];
        this.PLAYER = player;
        this.TURN = 'X';
    }

    public TicTacToeState(char[][] board, char player, char turn) {
        this.BOARD = board;
        this.PLAYER = player;
        this.TURN = turn;
        expansions++;
    }

    public TicTacToeState(TicTacToeState parent, TicTacToeAction action) {
        super(parent);
        BOARD = new char[parent.BOARD.length][parent.BOARD.length];
        for(int r=0; r<BOARD.length; r++) {
            for(int c=0; c<BOARD.length; c++) {
                BOARD[r][c] = parent.BOARD[r][c];
            }
        }

        BOARD[action.ROW][action.COL] = parent.TURN;
        this.PLAYER = parent.PLAYER;
        this.TURN = parent.TURN == 'X' ? 'O' : 'X';
        expansions++;
    }
    
    public Set<TicTacToeAction> actions() {
        var actions = new HashSet<TicTacToeAction>();
        for(int r=0; r<BOARD.length; r++) 
            for(int c=0; c<BOARD.length; c++) 
                if(BOARD[r][c] == 0) 
                    actions.add(new TicTacToeAction(r, c));
        return actions;
    }

    public TicTacToeState nextState(TicTacToeAction action) {
        return new TicTacToeState(this, action);
    }

    public double evaluate() {
        Character winner = null;
        boolean match;

        for(int r=0; r<BOARD.length; r++) { // check rows
            if(winner != null) return winner == PLAYER ? 1 : -1;
            match = true;
            for(int c=1; c<BOARD.length; c++) {
                if(BOARD[r][c] != BOARD[r][0]) {
                    match = false;
                    break;
                }
            }
            if(match) return BOARD[r][0] == PLAYER ? 1 : -1;
        }

        for(int c=0; c<BOARD.length; c++) { // check cols
            if(winner != null) return winner == PLAYER ? 1 : -1;
            match = true;
            for(int r=1; r<BOARD.length; r++) {
                if(BOARD[r][c] != BOARD[0][c]) {
                    match = false;
                    break;
                }
            }
            if(match) return BOARD[0][c] == PLAYER ? 1 : -1;
        }

        match = true;
        for(int i=1; i<BOARD.length; i++) {
            if(BOARD[i][i] != BOARD[0][0]) {
                match = false;
            }
        }
        if(match) return BOARD[0][0] == PLAYER ? 1 : -1;

        match = true;
        for(int i=1; i<BOARD.length; i++) {
            if(winner != null) break;
            if(BOARD[BOARD.length-i-1][i] != 
               BOARD[BOARD.length-1][0]) {
                match = false;
            }
        }
        if(match) return BOARD[0][BOARD.length-1] == PLAYER ? 1 : -1;
        
        return 0;
    }


    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Player: " + PLAYER + "\n");
        b.append("Turn: " + TURN + "\n");
        for(int r=0; r<BOARD.length; r++) {
            if(r>0) {
                for(int c=0; c<2*BOARD.length-1; c++)
                    b.append('-');
                b.append('\n');
            }
            for(int c=0; c<BOARD.length; c++) {
                if(c>0) b.append('|');
                b.append(BOARD[r][c] == 0 ? '.' : BOARD[r][c]);
            }
            b.append('\n');
        }
        return b.toString();
    }
}
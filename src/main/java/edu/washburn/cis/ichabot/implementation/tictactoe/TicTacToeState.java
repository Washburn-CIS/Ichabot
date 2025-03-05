package edu.washburn.cis.ichabot.implementation.tictactoe;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import edu.washburn.cis.ichabot.search.*;

public class TicTacToeState implements SearchState<TicTacToeAction,TicTacToeState> {
    public final char[][] BOARD;
    public final boolean MAX_TURN;

    public TicTacToeState(char[][] board, boolean maxTurn) {
        this.BOARD = board;
        this.MAX_TURN = maxTurn;
    }
    
    public Set<TicTacToeAction> actions() {
        return null; // stub
    }

    public TicTacToeState nextState(TicTacToeAction action) {
        return null; //stub
    }
}
package edu.washburn.cis.ichabot.implementation.reversi;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import edu.washburn.cis.ichabot.search.*;

public class ReversiState extends MinimaxSearchState<ReversiAction,ReversiState> {
    
    public static int expansions = 0;

    public final char[][] BOARD;
    public final char PLAYER;
    public final char TURN;

    public ReversiState() {
        this.BOARD = new char[8][8];
        BOARD[3][3] = 'X';
        BOARD[3][4] = 'O';
        BOARD[4][3] = 'O';
        BOARD[4][4] = 'X';
        this.PLAYER = 'X';
        this.TURN = 'X';
    }

    public ReversiState(char[][] board, char player, char turn) {
        this.BOARD = board;
        this.PLAYER = player;
        this.TURN = turn;
        expansions++;
    }

    public ReversiState(ReversiState parent, ReversiAction action) {
        super(parent);
        BOARD = new char[parent.BOARD.length][parent.BOARD.length];
        for(int r=0; r<BOARD.length; r++) {
            for(int c=0; c<BOARD.length; c++) {
                BOARD[r][c] = parent.BOARD[r][c];
            }
        }

        this.PLAYER = parent.PLAYER;
        this.TURN = parent.TURN == 'X' ? 'O' : 'X';

        if(!action.SKIP) {
            BOARD[action.ROW][action.COL] = parent.TURN;
            int dr,dc;
            for(int i=0; i<9; i++) {
                dr = i/3-1;
                dc = i%3-1;
                int nr = action.ROW + dr;
                int nc = action.COL + dc;
                if(nr > 7 || nc > 7 || nr < 0 || nc < 0) continue;
                while(BOARD[nr][nc] == TURN) {
                    nr += dr;
                    nc += dc;
                    if(nr > 7 || nc > 7 || nr < 0 || nc < 0) break;
                    
                    if(BOARD[nr][nc] == parent.TURN) {
                        while(nr != action.ROW || nc != action.COL) {
                            BOARD[nr][nc] = parent.TURN;
                            nr -= dr;
                            nc -= dc;
                        }
                        break;
                    }
                }
            }
        }
        expansions++;
    }

    public boolean legalMove(int r, int c) {
        int dr,dc;
        if(BOARD[r][c] != 0) return false;
        char OTHER = TURN == 'X' ? 'O' : 'X';

        for(int i=0; i<9; i++) {
            dr = i/3-1;
            dc = i%3-1;
            int nr = r + dr;
            int nc = c + dc;

            if(nr > 7 || nc > 7 || nr < 0 || nc < 0) continue;
            while(BOARD[nr][nc] == OTHER) {
                nr += dr;
                nc += dc;
                if(nr > 7 || nc > 7 || nr < 0 || nc < 0) break;
                if(BOARD[nr][nc] == TURN) return true;
            }
        }
        return false;
    }
    
    public Set<ReversiAction> actions() {
        var actions = new HashSet<ReversiAction>();
        for(int r=0; r<8; r++) for(int c=0; c<8; c++) 
            if(legalMove(r, c)) 
                actions.add(new ReversiAction(r, c));
        if(actions.size() == 0) 
            actions.add(new ReversiAction()); // add skip move
        return actions;
    }

    public ReversiState nextState(ReversiAction action) {
        return new ReversiState(this, action);
    }

    public double evaluate() {
        double score = 0;
        for(int i=0; i<64; i++) {
            if(BOARD[i/8][i%8] == PLAYER)
                score += 1;
            else if(BOARD[i/8][i%8] != 0)
                score -= 1;
        }
        return score;
    }


    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Player: " + PLAYER + "\n");
        b.append("Turn: " + TURN + "\n");
        b.append("  0 1 2 3 4 5 6 7\n");
        for(int r=0; r<BOARD.length; r++) {
            if(r>0) {
                b.append("  ");
                for(int c=0; c<2*BOARD.length-1; c++)
                    b.append('-');
                b.append('\n');
            }
            b.append(r + " "); 
            for(int c=0; c<BOARD.length; c++) {
                if(c>0) b.append('|');
                b.append(BOARD[r][c] == 0 ? '.' : BOARD[r][c]);
            }
            b.append('\n');
        }
        return b.toString();
    }
}
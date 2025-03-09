package edu.washburn.cis.ichabot.implementation.tictactoe;

import edu.washburn.cis.ichabot.search.*;

public class TicTacToePlayground {

    public static void main(String[] args) {
        Searcher.verbose = true;
        char[][] board = new char[3][3];
        String bd = args[0]; //input is 9 board spots in order (col then row)
                             //then player, then turn
        for(int i=0;i<9;i++) {
            board[i/3][i%3] = bd.charAt(i) == '.' ? 0 : bd.charAt(i);
        }
        var state = new TicTacToeState(board, bd.charAt(9), bd.charAt(10));
        System.out.println("STARTING: " + state);
        
        long startTime = System.currentTimeMillis();
        
        //var action = Searcher.minimaxSearch(state);
        var action = Searcher.alphaBetaSearch(state);

        long endTime = System.currentTimeMillis();
        System.out.println("Expansions: " + TicTacToeState.expansions);
        System.out.println("Time: " + (endTime - startTime));
        System.out.println("Move: " + action);
    }

}

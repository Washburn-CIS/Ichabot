package edu.washburn.cis.ichabot.implementation.reversi;

import java.util.Scanner;
import java.util.function.Function;
import edu.washburn.cis.ichabot.search.*;

public class ReversiSim {

    public static Function<ReversiState,Double> pXh = s->0.0;
    public static Function<ReversiState,Double> pOh = s->0.0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Player X search depth (0 for human): ");
        int pXdepth = input.nextInt();
        System.out.print("Player O search depth (0 for human): ");
        int pOdepth = input.nextInt();

        ReversiState s = new ReversiState();
        while(s != null) {
            System.out.println(s);
            ReversiAction action = null;
            if(s.PLAYER == 'X') {
                if(pXdepth > 0) {
                    action = Searcher.alphaBetaSearch(s, pXh, pXdepth);
                } else {
                    System.out.print("Enter row: ");
                    int row = input.nextInt();
                    System.out.print("Enter col: ");
                    int col = input.nextInt();
                    action = new ReversiAction(row, col);
                }
            } else {
                if(pOdepth > 0) {
                    action = Searcher.alphaBetaSearch(s, pOh, pOdepth);
                } else {
                    System.out.print("Enter row: ");
                    int row = input.nextInt();
                    System.out.print("Enter col: ");
                    int col = input.nextInt();
                    action = new ReversiAction(row, col);
                }
            }
            s = new ReversiState(s, action);
            s = new ReversiState(s.BOARD, s.PLAYER=='X'?'O':'X',s.TURN);
        }
    }
}
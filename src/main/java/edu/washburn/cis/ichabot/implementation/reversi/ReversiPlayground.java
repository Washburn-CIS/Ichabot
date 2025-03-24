package edu.washburn.cis.ichabot.implementation.reversi;

import edu.washburn.cis.ichabot.search.*;

public class ReversiPlayground {

    public static void main(String[] args) {
        Searcher.verbose = true;
        var state = new ReversiState();
        System.out.println("STARTING: " + state);
        
        long startTime = System.currentTimeMillis();
        
        //var action = Searcher.minimaxSearch(state);
        var action = Searcher.alphaBetaSearch(state, s->0.0, 14);

        /*
        while(state != null) {
            System.out.println(state);
            boolean a = false;
            for(var s2: state.children()) {
                state = s2; 
                a=true;
                break;
            }
            if(!a) break;
        }
 */
        long endTime = System.currentTimeMillis();
        System.out.println("Expansions: " + ReversiState.expansions);
        System.out.println("Time: " + (endTime - startTime));
        System.out.println("Move: " + action);
    }
}

package edu.washburn.cis.ichabot.implementation.reversi;

import edu.washburn.cis.ichabot.search.MCTS;
import edu.washburn.cis.ichabot.search.Searcher;

public class ReversiPlayground {

    public static void main(String[] args) {
        Searcher.verbose = true;
        var state = new ReversiState();
        System.out.println("STARTING: " + state);
        
        long startTime = System.currentTimeMillis();
        
        //var action = Searcher.minimaxSearch(state);
        //var action = Searcher.alphaBetaSearch(state, s->0.0, 14);

        MCTS<ReversiAction, ReversiState> searcher = new MCTS<>(
            s->s.evaluate(), 
            1.414, 
            1000
            );
        var action = searcher.findBestAction(state);

        /* 
        while(state != null && !state.isLeaf()) {
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

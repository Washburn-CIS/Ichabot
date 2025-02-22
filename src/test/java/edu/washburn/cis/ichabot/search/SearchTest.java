package edu.washburn.cis.ichabot.search;

import edu.washburn.cis.ichabot.implementation.npuzzle.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class SearchTest extends TestCase {

    public static Test suite() {
        return new TestSuite( SearchTest.class );
    }

    public void testNPuzzle() {

        int[][] statearr =  {{1,2,3},{4,5,0},{6,7,8}};
        var state = new NPuzzleState(3,statearr);
        var s = new Searcher<NPuzzleAction, NPuzzleState>(
            state,
            NPuzzleState.makeGoal(3),
            Searcher.generateAStarQueue(ns -> 
                NPuzzleHeuristics.manhattanDistance(ns.puzzleState, NPuzzleHeuristics.generateBasicGoal(3))));
    
        long startTime = System.currentTimeMillis();
        var sol = s.findFirst();
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
        System.out.println(s.getExpansions());
        System.out.println(sol.STATE);
        while(sol.PRIOR != null) {
            System.out.println(sol.ACTION);
            sol = sol.PRIOR;
            System.out.println(sol.STATE);
        }
        assertTrue( true );
        
    }

}

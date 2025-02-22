import edu.washburn.cis.ichabot.search.*;
import edu.washburn.cis.ichabot.implementation.npuzzle.*;

public class PuzzlePlayground {

    public static void main(String[] args) {
        int[][] statearr =  {{1,2,3},{4,5,0},{6,7,8}};
        var state = new NPuzzleState(3,statearr);
        var s = new Searcher<NPuzzleAction, NPuzzleState>(
            state,
            NPuzzleState.makeGoal(3),
            //Searcher.generateAStarQueue(ns -> NPuzzleHeuristics.manhattanDistance(ns.puzzleState, NPuzzleHeuristics.generateBasicGoal(3))));
            Searcher.generateBFSQueue());
    
        long startTime = System.currentTimeMillis();
        var sol = s.findFirst();
        long endTime = System.currentTimeMillis();
        System.out.println(s.getExpansions());
        System.out.println(sol.STATE);
        while(sol.PRIOR != null) {
            System.out.println(sol.ACTION);
            sol = sol.PRIOR;
            System.out.println(sol.STATE);
        }
        System.out.println("Time: " + (endTime - startTime));
        
    }

}

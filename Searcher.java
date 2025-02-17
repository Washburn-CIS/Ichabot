import java.util.function.Predicate;
import java.util.Queue;
import java.util.HashSet;
import java.util.Set;

public class Searcher<ActionT, SearchStateT extends SearchState<ActionT, SearchStateT>> {
    private Predicate<SearchStateT> goalTest;
    private Queue<SearchPath<SearchStateT,ActionT>> frontier;
    private int expansions = 0;
    private Set<SearchPath<SearchStateT,ActionT>> goals = new HashSet<SearchPath<SearchStateT,ActionT>>();

    public Searcher(SearchStateT initialState, 
                    Predicate<SearchStateT> goalTest,
                    Queue<SearchPath<SearchStateT,ActionT>> frontier) {
        this.goalTest = goalTest;
        this.frontier = frontier;
        frontier.add(new SearchPath(initialState));
    }

    public SearchPath<SearchStateT,ActionT> expandOne() {
        expansions++;
        var path = frontier.poll();
        if(path != null) {
            for(ActionT action : path.STATE.actions()) {
                var newState = path.STATE.nextState(action);
                var newPath = new SearchPath(path, action, newState);
                if(goalTest.test(newState)) goals.add(newPath);
                frontier.add(newPath);
            }
        }
        return path;
    }

    public SearchPath<SearchStateT,ActionT> findFirst() {
        while(goals.size() == 0 && frontier.size() > 0) expandOne();
        for(SearchPath<SearchStateT,ActionT> path : goals) return path;
        return null;
    }

    public int getExpansions() { return expansions; }
}
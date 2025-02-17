import java.util.function.Predicate;
import java.util.Queue;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;

public class BreadthFirstSearcher<ActionT, SearchStateT extends SearchState<ActionT,SearchStateT>>  
        extends Searcher<ActionT,SearchStateT>  {
    public BreadthFirstSearcher(SearchStateT initialState, 
                    Predicate<SearchStateT> goalTest) {
            super(initialState,
                  goalTest,
                  new LinkedList<SearchPath<SearchStateT,ActionT>>());  // FIFO queue
    }
}
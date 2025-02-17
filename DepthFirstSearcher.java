import java.util.function.Predicate;
import java.util.Queue;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.util.Stack;

public class DepthFirstSearcher<ActionT, SearchStateT extends SearchState<ActionT,SearchStateT>>  
        extends Searcher<ActionT,SearchStateT>  {
    public DepthFirstSearcher(SearchStateT initialState, 
                    Predicate<SearchStateT> goalTest) {
            super(initialState,
                  goalTest,
                  new SearchStack<SearchPath<SearchStateT,ActionT>>());  // FIFO queue
    }
}

class SearchStack<E> extends Stack<E> implements Queue<E> {

    @Override
    public E element() {
        E obj = peek();
        if(obj == null) throw new EmptyStackException();
        return obj;
    }

    @Override
    public E poll() {
        if(size() > 0) return pop();
        return null;
    }

    @Override
    public boolean offer(E obj) {
        push(obj);
        return true;
    }

    @Override
    public E remove() { return pop(); }

}
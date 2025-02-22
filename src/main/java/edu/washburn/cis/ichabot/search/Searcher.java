package edu.washburn.cis.ichabot.search;

import java.util.function.Predicate;
import java.util.Queue;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.Stack;
import java.util.EmptyStackException;

public class Searcher<ActionT, SearchStateT extends SearchState<ActionT, SearchStateT>> {
    private Predicate<SearchStateT> goalTest;
    private Queue<SearchPath<SearchStateT,ActionT>> frontier;
    private int expansions = 0;
    private Set<SearchPath<SearchStateT,ActionT>> goals = new HashSet<SearchPath<SearchStateT,ActionT>>();

    public static <SearchStateT,ActionT> Queue<SearchPath<SearchStateT,ActionT>> generateDFSQueue() {
        return new SearchStack<SearchPath<SearchStateT,ActionT>>();
    }

    public static <SearchStateT,ActionT> Queue<SearchPath<SearchStateT,ActionT>> generateBFSQueue() {
        return new LinkedList<SearchPath<SearchStateT,ActionT>>();
    }

    public static <SearchStateT,ActionT> Queue<SearchPath<SearchStateT,ActionT>> 
            generateAStarQueue(Function<SearchStateT, Double> heuristic) {
        return new PriorityQueue<SearchPath<SearchStateT, ActionT>>(
                            (path1, path2) -> Double.compare(
                                  path1.COST + heuristic.apply(path1.STATE), 
                                  path2.COST + heuristic.apply(path2.STATE)));
    }

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
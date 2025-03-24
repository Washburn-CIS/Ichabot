package edu.washburn.cis.ichabot.search;

import java.util.function.*;
import java.util.*;

public class Searcher<ActionT, SearchStateT extends SearchState<ActionT, SearchStateT>> {

    public static boolean verbose = false;

    public static <I,O extends Comparable> I argmax(Collection<? extends I> inputs,
            Function<? super I, O> f) {
                O maxOutput = null;
                I maxInput = null;
                for(I input : inputs) {
                    O output = f.apply(input);
                    if(verbose) System.out.println(input + "->" + output);
                    if(maxOutput == null || maxOutput.compareTo(output) < 0) {
                        maxInput = input;
                        maxOutput = output;
                    }
                }
                return maxInput;
    }

    public static <ActionT, SearchStateT extends MinimaxSearchState<ActionT, SearchStateT>>
            ActionT minimaxSearch(SearchStateT state) {
                return argmax(state.actions(), 
                   action -> minimaxSearchMin(state.nextState(action)));
    }

    public static <ActionT, SearchStateT extends MinimaxSearchState<ActionT, SearchStateT>>
            double minimaxSearchMin(SearchStateT state) {
                return state.actions().stream()
                    .map(a -> state.nextState(a))
                    .mapToDouble(s -> s.isLeaf() ? s.evaluate() : minimaxSearchMax(s))
                    .min().getAsDouble();
    }

    public static <ActionT, SearchStateT extends MinimaxSearchState<ActionT, SearchStateT>>
            double minimaxSearchMax(SearchStateT state) {
                return state.actions().stream()
                    .map(a -> state.nextState(a))
                    .mapToDouble(s -> s.isLeaf() ? s.evaluate() : minimaxSearchMin(s))
                    .max().getAsDouble();
    }

    public static <ActionT, SearchStateT extends MinimaxSearchState<ActionT, SearchStateT>>
            ActionT alphaBetaSearch(SearchStateT state) {
        return alphaBetaSearch(state, 
                s -> s.evaluate(), 
                Double.POSITIVE_INFINITY);
    }

    public static <ActionT, SearchStateT extends MinimaxSearchState<ActionT, SearchStateT>>
            ActionT alphaBetaSearch(SearchStateT state, 
                    Function<? super SearchStateT,Double> h,
                    double depthLimit) {
                double α = Double.NEGATIVE_INFINITY;
                double β = Double.POSITIVE_INFINITY;
                ActionT maxAction = null;
                for(var action : state.actions()) {
                    var s = state.nextState(action);
                    double utility = alphaBetaSearchMin(α,β,s,h,1,depthLimit);
                    if(verbose) System.out.println(action + " -> " + utility);
                    if(utility > α) {
                        α = utility;
                        maxAction = action;
                    }
                }
                return maxAction;
    }

    public static <ActionT, SearchStateT extends MinimaxSearchState<ActionT, SearchStateT>>
            double alphaBetaSearchMin(double α, double β, SearchStateT state,
                    Function<? super SearchStateT,Double> h,
                                      int depth, double depthLimit) {
                if(state.isLeaf()) return state.evaluate();
                if(depth >= depthLimit) return h.apply(state);
                for(ActionT action : state.actions()) {
                    var s = state.nextState(action);
                    double utility = alphaBetaSearchMax(α,β,s,h,depth+1,depthLimit);
                    if(utility <= α) return utility;
                    if(utility < β) β = utility;
                }
                return β;
    }

    public static <ActionT, SearchStateT extends MinimaxSearchState<ActionT, SearchStateT>>
            double alphaBetaSearchMax(double α, double β, SearchStateT state,
                    Function<? super SearchStateT,Double> h,
                                      int depth, double depthLimit) {
                if(state.isLeaf()) return state.evaluate();
                if(depth >= depthLimit) return h.apply(state);
                for(ActionT action : state.actions()) {
                    var s = state.nextState(action);
                    double utility = alphaBetaSearchMin(α,β,s,h,depth+1,depthLimit);
                    if(utility >= β) return utility;
                    if(utility > α) α = utility;
                }
                return α;
    }

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
        if(verbose) System.out.println("EXPANDING: '" + path+"'");
        if(path != null) {
            if(verbose) System.out.println("ACTIONS:");
            var actions = path.STATE.actions();
            if(actions.size() == 0) {
                if(verbose) System.out.println("LEAF NODE REACHED");
            } else for(ActionT action : actions) {
                if(verbose) System.out.println(action);
                var newState = path.STATE.nextState(action);
                var newPath = new SearchPath(path, action, newState);
                if(goalTest.test(newState)) {
                    if(verbose) System.out.println("GOAL DISCOVERED");
                    goals.add(newPath);
                }
                frontier.add(newPath);
            }
        }
        return path;
    }

    public SearchPath<SearchStateT,ActionT> findFirst() {
        if(verbose) System.out.println("STARTING SEARCH ");
        
        while(goals.size() == 0 && frontier.size() > 0) expandOne();
        for(SearchPath<SearchStateT,ActionT> path : goals) return path;
        return null;
    }


    public Set<SearchPath<SearchStateT,ActionT>> findAll() {
        if(verbose) System.out.println("STARTING SEARCH ");
        
        while(frontier.size() > 0) expandOne();
        return goals;
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
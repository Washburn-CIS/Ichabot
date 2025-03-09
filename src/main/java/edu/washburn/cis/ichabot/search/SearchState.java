package edu.washburn.cis.ichabot.search;

import java.util.Set;
import java.util.HashSet;

public interface SearchState<ActionT, SearchStateT extends SearchState<ActionT, SearchStateT>> {
    public Set<ActionT> actions();

    public SearchStateT nextState(ActionT action);

    public default boolean isLeaf() {
        return actions().size() == 0;
    }

    public default Set<SearchStateT> children() {
        var states = new HashSet<SearchStateT>();
        for(ActionT action : actions()) {
            states.add(nextState(action));
        }
        return states;
    }
}
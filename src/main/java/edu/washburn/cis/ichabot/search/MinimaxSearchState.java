package edu.washburn.cis.ichabot.search;

import java.util.*;
import java.util.function.*;

public abstract class MinimaxSearchState<ActionT, 
    SearchStateT extends MinimaxSearchState<ActionT, SearchStateT>>
            implements SearchState<ActionT, SearchStateT> {

    private final int DEPTH;
    private final MinimaxSearchState PARENT;

    public boolean isMax() { return DEPTH % 2 == 0; }
    public int getDepth() { return DEPTH + 1; }


    public MinimaxSearchState() {
        DEPTH = 0;
        PARENT = null;
    }

    public MinimaxSearchState(SearchStateT parent) {
        DEPTH = parent.getDepth() + 1;
        PARENT = parent;
    }

    public abstract double evaluate();

}
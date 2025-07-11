package edu.washburn.cis.ichabot.search;
/*
A breadth-first search queue using multi-path pruning
Solves the NPuzzle 300 times faster than a non-pruning breadth-first search queue
Developed by Ellie Hanson
*/
import java.util.*;

public class MPPQueue<SearchStateT extends SearchState,ActionT> extends 
        LinkedList<SearchPath<SearchStateT,ActionT>> {
    private Set<SearchStateT> explored = new HashSet<SearchStateT>();
    
    @Override
    public SearchPath<SearchStateT,ActionT> poll() {
        while(true) {
            var selected = super.poll();
            if(!explored.contains(selected.STATE)) {
                explored.add(selected.STATE);
                return selected;
            }
        }        
    }
}

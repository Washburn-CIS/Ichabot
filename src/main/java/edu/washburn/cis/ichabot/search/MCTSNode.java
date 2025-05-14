package edu.washburn.cis.ichabot.search;

import java.util.HashMap;
import java.util.Map;

public class MCTSNode<ActionT, SearchStateT extends SearchState<ActionT, SearchStateT>> {
    public final SearchStateT state;
    public final MCTSNode<ActionT, SearchStateT> parent;
    public final ActionT action; // Action leading to this node from parent
    public final Map<ActionT, MCTSNode<ActionT, SearchStateT>> children;
    public int visitCount;
    public double totalValue;

    // Root node constructor
    public MCTSNode(SearchStateT state) {
        this.state = state;
        this.parent = null;
        this.action = null;
        this.children = new HashMap<>();
        this.visitCount = 0;
        this.totalValue = 0.0;
    }

    // Child node constructor
    public MCTSNode(SearchStateT state, MCTSNode<ActionT, SearchStateT> parent, ActionT action) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.children = new HashMap<>();
        this.visitCount = 0;
        this.totalValue = 0.0;
    }

    public boolean isFullyExpanded() {
        return children.size() == state.actions().size();
    }

    public void addChild(ActionT action, MCTSNode<ActionT, SearchStateT> child) {
        children.put(action, child);
    }

    public double getAverageValue() {
        return visitCount == 0 ? 0 : totalValue / visitCount;
    }
}
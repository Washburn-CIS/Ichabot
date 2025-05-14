package edu.washburn.cis.ichabot.search;

import java.util.HashMap;
import java.util.Map;

/**
 * A node in the MCTS tree.
 * Each node has a state, a parent node, the action leading to this node from the parent, and a map of children nodes.
 * Each node also has a visit count and a total value,
 * which are used to calculate the average value of the node.
 * 
 * @author Hayden Eddy
 * @version 1.0
 * @see MCTS
 */
public class MCTSNode<ActionT, SearchStateT extends SearchState<ActionT, SearchStateT>> {
    public final SearchStateT state;
    public final MCTSNode<ActionT, SearchStateT> parent;
    public final ActionT action; // Action leading to this node from parent
    public final Map<ActionT, MCTSNode<ActionT, SearchStateT>> children;
    public int visitCount;
    public double totalValue;

    /**
     * Constructor for the root node.
     * 
     * @param state The state of the root node, or the initial state of the search problem.
     */
    public MCTSNode(SearchStateT state) {
        this.state = state;
        this.parent = null;
        this.action = null;
        this.children = new HashMap<>();
        this.visitCount = 0;
        this.totalValue = 0.0;
    }

    /**
     * Constructor for a child node.
     * @param state the state of the child node
     * @param parent the parent node
     * @param action the action leading to this node from the parent
     */
    public MCTSNode(SearchStateT state, MCTSNode<ActionT, SearchStateT> parent, ActionT action) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.children = new HashMap<>();
        this.visitCount = 0;
        this.totalValue = 0.0;
    }

    /**
     * Returns true if the node has been fully expanded, meaning that all possible
     * actions from the node have been explored. This is determined by checking if
     * the number of children is equal to the number of possible actions from the
     * node.
     * @return true if the node is fully expanded, false otherwise
     */
    public boolean isFullyExpanded() {
        return children.size() == state.actions().size();
    }

    /**
     * Adds a child node to the current node.
     * The child node is associated with the given action.
     * This is used to expand the current node, by generating a new child node for
     * each possible action from the current node.
     * @param action the action leading to the child node
     * @param child the child node to add
     */
    public void addChild(ActionT action, MCTSNode<ActionT, SearchStateT> child) {
        children.put(action, child);
    }

    /**
     * Returns the average value of the node.
     * This is calculated by dividing the total value of the node by the number of visits.
     * If the node has not been visited, the average value is 0.
     * @return the average value of the node
     */
    public double getAverageValue() {
        return visitCount == 0 ? 0 : totalValue / visitCount;
    }
}
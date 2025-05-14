package edu.washburn.cis.ichabot.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

/**
 * A simple implementation of the MCTS algorithm
 * Should work for any state and action that properly extends SearchState,
 * Including states that extend MinimaxSearchState.
 * 
 * @author Hayden Eddy
 * @version 1.0
 * @see MCTSNode
 */
public class MCTS<ActionT, SearchStateT extends SearchState<ActionT, SearchStateT>> {
    private final Function<SearchStateT, Double> evaluator;
    private final double explorationParameter;
    private final int maxIterations;

    /**
     * Constructor
     * @param evaluator A function that evaluates the value of a state
     * @param explorationParameter The exploration parameter. Ideally set to sqrt(2)
     * @param maxIterations The maximum number of iterations. Typically 1000
     */
    public MCTS(Function<SearchStateT, Double> evaluator, double explorationParameter, int maxIterations) {
        this.evaluator = evaluator;
        this.explorationParameter = explorationParameter;
        this.maxIterations = maxIterations;
    }

    /**
     * Executes the Monte Carlo Tree Search (MCTS) algorithm to find the best action
     * from a given initial state. The search is performed over a specified number 
     * of iterations, where each iteration consists of selection, expansion, simulation, 
     * and backpropagation phases. The function returns the action associated with 
     * the child node of the root having the highest average reward.
     *
     * @param initialState The initial state from which the search begins.
     * @return The best action found after the specified number of iterations.
     */

    public ActionT findBestAction(SearchStateT initialState) {
        MCTSNode<ActionT, SearchStateT> root = new MCTSNode<>(initialState);

        for (int i = 0; i < maxIterations; i++) {
            System.out.println("Iteration " + i);
            MCTSNode<ActionT, SearchStateT> selectedNode = select(root);
            MCTSNode<ActionT, SearchStateT> expandedNode = expand(selectedNode);
            double result = simulate(expandedNode);
            backpropagate(expandedNode, result);
        }

        return getBestAction(root);
    }

    /**
     * Selects a node to expand by traversing the tree from the given node.
     * The selection is based on the UCB1 value, moving towards the best child
     * until a leaf node or a non-fully expanded node is reached.
     *
     * @param node The starting node for the selection process.
     * @return The selected node for expansion.
     */

    private MCTSNode<ActionT, SearchStateT> select(MCTSNode<ActionT, SearchStateT> node) {
        System.out.println("Selecting...");
        while (!node.state.isLeaf()) {
            if (!node.isFullyExpanded()) {
                return node;
            } else {
                node = selectBestChild(node);
            }
        }
        return node;
    }

/**
 * Selects the best child of the given node based on the UCB1 value.
 * Iterates over all children and returns the one with the highest score.
 *
 * @param node The parent node whose children are to be evaluated.
 * @return The child node with the highest UCB1 value.
 * @throws NoSuchElementException if the node has no children.
 */

    private MCTSNode<ActionT, SearchStateT> selectBestChild(MCTSNode<ActionT, SearchStateT> node) {
        return node.children.values().stream()
                .max(Comparator.comparingDouble(child -> ucb1(node, child)))
                .orElseThrow();
    }

    /**
     * Calculates the UCB1 value for the given child node.
     * The UCB1 value is a heuristic that balances exploration and exploitation.
     * It is the average value of the child node plus a bonus term that depends
     * on the number of visits to the child and its parent node.
     *
     * The bonus term is a function of the logarithm of the number of visits to the
     * parent node divided by the number of visits to the child node, scaled by
     * the exploration parameter.
     *
     * If the child node has not been visited before, the UCB1 value is set to
     * positive infinity, so that the child is chosen as the best node.
     *
     * @param parent The parent node of the child node.
     * @param child The child node for which to calculate the UCB1 value.
     * @return The UCB1 value of the child node.
     */
    private double ucb1(MCTSNode<ActionT, SearchStateT> parent, MCTSNode<ActionT, SearchStateT> child) {
        if (child.visitCount == 0) return Double.POSITIVE_INFINITY;
        return child.getAverageValue() +
                explorationParameter * Math.sqrt(Math.log(parent.visitCount) / child.visitCount);
    }

    /**
     * Expands the given node by adding a new child node for a random unvisited
     * action, if such an action exists. If all actions have been visited, returns
     * the node as is.
     *
     * @param node The node to expand.
     * @return The expanded node, or the original node if all actions have been
     *         visited.
     */
    private MCTSNode<ActionT, SearchStateT> expand(MCTSNode<ActionT, SearchStateT> node) {
        System.out.println("Expanding...");
        if (node.state.isLeaf()) return node;

        for (ActionT action : node.state.actions()) {
            if (!node.children.containsKey(action)) {
                SearchStateT childState = node.state.nextState(action);
                MCTSNode<ActionT, SearchStateT> childNode = new MCTSNode<>(childState, node, action);
                node.addChild(action, childNode);
                return childNode;
            }
        }
        return node; // Shouldn't reach here if isFullyExpanded checked
    }

    /**
     * Simulates a random playout from the given node to a leaf node.
     * The simulation is done by randomly selecting an action from the
     * current state's actions until a leaf node is reached.
     * The value of the leaf node is then returned.
     *
     * @param node The node to start the simulation from.
     * @return The value of the leaf node reached by the simulation.
     */
    private double simulate(MCTSNode<ActionT, SearchStateT> node) {
        System.out.println("Simulating...");
        SearchStateT currentState = node.state;
        Random rand = new Random();

        while (!currentState.isLeaf()) {
            List<ActionT> actions = new ArrayList<>(currentState.actions());
            ActionT action = actions.get(rand.nextInt(actions.size()));
            currentState = currentState.nextState(action);
        }

        return evaluator.apply(currentState);
    }

    /**
     * Backpropagates the result of a simulation to the root node.
     * The value of the leaf node is propagated up the tree by
     * incrementing the visit count and adding the value to the total
     * value of each node.
     *
     * @param node The node to start the backpropagation from.
     * @param result The value of the leaf node.
     */
    private void backpropagate(MCTSNode<ActionT, SearchStateT> node, double result) {
        System.out.println("Backpropagating...");
        while (node != null) {
            node.visitCount++;
            node.totalValue += result;
            node = node.parent;
        }
    }

    /**
     * Returns the best action from the given root node.
     * The best action is determined by the number of visits to each child node.
     * If no child nodes exist, returns null.
     *
     * @param root The root node to find the best action from.
     * @return The best action from the given root node.
     */
    private ActionT getBestAction(MCTSNode<ActionT, SearchStateT> root) {
        return root.children.entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getValue().visitCount))
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
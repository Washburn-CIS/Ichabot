package edu.washburn.cis.ichabot.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class MCTS<ActionT, SearchStateT extends SearchState<ActionT, SearchStateT>> {
    private final Function<SearchStateT, Double> evaluator;
    private final double explorationParameter;
    private final int maxIterations;

    public MCTS(Function<SearchStateT, Double> evaluator, double explorationParameter, int maxIterations) {
        this.evaluator = evaluator;
        this.explorationParameter = explorationParameter;
        this.maxIterations = maxIterations;
    }

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

    private MCTSNode<ActionT, SearchStateT> selectBestChild(MCTSNode<ActionT, SearchStateT> node) {
        return node.children.values().stream()
                .max(Comparator.comparingDouble(child -> ucb1(node, child)))
                .orElseThrow();
    }

    private double ucb1(MCTSNode<ActionT, SearchStateT> parent, MCTSNode<ActionT, SearchStateT> child) {
        if (child.visitCount == 0) return Double.POSITIVE_INFINITY;
        return child.getAverageValue() +
                explorationParameter * Math.sqrt(Math.log(parent.visitCount) / child.visitCount);
    }

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

    private void backpropagate(MCTSNode<ActionT, SearchStateT> node, double result) {
        System.out.println("Backpropagating...");
        while (node != null) {
            node.visitCount++;
            node.totalValue += result;
            node = node.parent;
        }
    }

    private ActionT getBestAction(MCTSNode<ActionT, SearchStateT> root) {
        return root.children.entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getValue().visitCount))
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
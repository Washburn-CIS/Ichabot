package edu.washburn.cis.ichabot.logic;

import java.util.*;

public class Reasoner {

    // Bottom-Up Reasoning (Forward Chaining)
    public static Set<String> proveBottomUp(KnowledgeBase kb) {
        Set<String> inferred = new HashSet<>(kb.facts);
        boolean addedNew;

        do {
            addedNew = false;
            for (Rule rule : kb.rules) {
                if (inferred.contains(rule.conclusion)) continue;

                boolean allPremisesTrue = rule.premises.stream()
                        .allMatch(inferred::contains);

                if (allPremisesTrue) {
                    inferred.add(rule.conclusion);
                    addedNew = true;
                    System.out.println("Inferred: " + rule.conclusion + " from " + rule.premises);
                }
            }
        } while (addedNew);

        return inferred;
    }

    // Top-Down Reasoning (Backward Chaining)
    public static boolean proveTopDown(String goal, KnowledgeBase kb, Set<String> visited) {
        if (kb.facts.contains(goal)) {
            System.out.println(goal + " is a known fact.");
            return true;
        }

        if (visited.contains(goal)) {
            return false;
        }

        visited.add(goal);

        for (Rule rule : kb.rules) {
            if (rule.conclusion.equals(goal)) {
                System.out.println("Trying to prove " + goal + " using rule: " + rule.conclusion + " <- " + rule.premises);
                boolean allPremisesTrue = true;
                for (String premise : rule.premises) {
                    if (!proveTopDown(premise, kb, visited)) {
                        allPremisesTrue = false;
                        break;
                    }
                }
                if (allPremisesTrue) {
                    System.out.println("Proved " + goal);
                    return true;
                }
            }
        }

        return false;
    }
}


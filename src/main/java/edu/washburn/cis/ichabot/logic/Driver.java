package edu.washburn.cis.ichabot.logic;
import java.util.*;

public class Driver{
    public static void main(String[] args) {
        KnowledgeBase kb = new KnowledgeBase();

        KnowledgeBase kb2 = new KnowledgeBase(); //knowledge base for examples 5.10 + 5.11
	kb2.facts.add("d");
	kb2.facts.add("e");
		
	kb2.rules.add(new Rule(List.of("b", "c"), "a"));
	kb2.rules.add(new Rule(List.of("d", "e"), "b"));
	kb2.rules.add(new Rule(List.of("g", "e"), "b"));
	kb2.rules.add(new Rule(List.of("a", "g"), "f"));
	kb2.rules.add(new Rule(List.of("e"), "c"));
		
	System.out.println();
	System.out.println("-----------------Example proofs from book, 5.10 + 5.11-----------------");
	// Run forward chaining
        
	System.out.printf("%nFORWARD CHAINING: %n %n");
		
	System.out.printf("FACTS: %n");
	for (String s : kb2.facts) { System.out.println(s); }
	System.out.printf("RULES: %n");
	for (Rule r : kb2.rules){
		System.out.println(r.conclusion + " <- " + r.premises);
	}
	System.out.println();
		
	Set<String> inferred = Reasoner.proveBottomUp(kb2);
	System.out.println("Facts inferred (bottom-up): " + inferred);
		
	/// Run backward chaining
		
	System.out.printf("%nBACKWARD CHAINING: %n %n");
		
	System.out.printf("FACTS: %n");
	for (String s : kb2.facts) { System.out.println(s); }
	System.out.printf("RULES: %n");
	for (Rule r : kb2.rules)
	{
		System.out.println(r.conclusion + " <- " + r.premises);
	}
		System.out.println();
		
	boolean canProve = Reasoner.proveTopDown("a", kb2, new HashSet<>());
		
	
	    
	KnowledgeBase diagnosis = new KnowledgeBase(); //knowledge base for medical example
		
	diagnosis.facts.add("fever");
	diagnosis.facts.add("cough");
	diagnosis.facts.add("fatigue");
	diagnosis.facts.add("shortness_of_breath");
		
	diagnosis.rules.add(new Rule(List.of("fever", "cough"), "possible_infection"));
	diagnosis.rules.add(new Rule(List.of("possible_infection", "fatigue"), "possible_flu"));
	diagnosis.rules.add(new Rule(List.of("cough", "shortness_of_breath"), "possible_covid"));
	diagnosis.rules.add(new Rule(List.of("possible_covid", "fever"), "recommend_covid_test"));
	diagnosis.rules.add(new Rule(List.of("possible_flu"), "recommend_rest_and_fluids"));
		
		
	System.out.println();
	System.out.println("-----------------Medical Example-----------------");
	// Run forward chaining
	System.out.printf("%nFORWARD CHAINING: %n %n");
		
	System.out.printf("FACTS: %n");
	for (String s : diagnosis.facts) { System.out.println(s); }
	System.out.printf("RULES: %n");
	for (Rule r : diagnosis.rules){
		System.out.println(r.conclusion + " <- " + r.premises);
	}
	System.out.println();
		
	inferred = Reasoner.proveBottomUp(diagnosis);
	System.out.println("Facts inferred (bottom-up): " + inferred);
		
		
	KnowledgeBase smartHome = new KnowledgeBase(); //knowledge base for medical example
		
	smartHome.facts.add("night");
	smartHome.facts.add("motion_detected");
	smartHome.facts.add("temperature_under_18");
	smartHome.facts.add("windows_open");
	smartHome.facts.add("raining");
		
	smartHome.rules.add(new Rule(List.of("night", "motion_detected"), "turn_on_lights"));
	smartHome.rules.add(new Rule(List.of("temperature_under_18"), "turn_on_heater"));
	smartHome.rules.add(new Rule(List.of("raining", "windows_open"), "close_windows"));
	smartHome.rules.add(new Rule(List.of("heater_onk", "windows_open"), "alert_close_windows"));
		
				
	System.out.println();
	System.out.println("-----------------Smart Home Automation Example-----------------");
	// Run forward chaining
	System.out.printf("%nFORWARD CHAINING: %n %n");
		
	System.out.printf("FACTS: %n");
	for (String s : smartHome.facts) { System.out.println(s); }
	System.out.printf("RULES: %n");
	for (Rule r : smartHome.rules){
		System.out.println(r.conclusion + " <- " + r.premises);
	}
	System.out.println();
		
	inferred = Reasoner.proveBottomUp(smartHome);
	System.out.println("Facts inferred (bottom-up): " + inferred);
    }
}

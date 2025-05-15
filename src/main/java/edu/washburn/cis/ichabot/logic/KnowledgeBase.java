package edu.washburn.cis.ichabot.logic;

import java.util.*;

class KnowledgeBase {
    Set<String> facts = new HashSet<>();
    List<Rule> rules = new ArrayList<>();
	
	
    // Constructor
    public KnowledgeBase() {
        this.facts = new HashSet<>();
        this.rules = new ArrayList<>();
    }
}



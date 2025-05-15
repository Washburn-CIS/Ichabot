package edu.washburn.cis.ichabot.logic;

import java.util.*;

class Rule {
    List<String> premises; 
    String conclusion;  

	 // Constructor
    public Rule(List<String> premises, String conclusion) {
        this.premises = premises;
        this.conclusion = conclusion;
    }

    @Override
    public String toString() {
        return premises + " => " + conclusion;
    }
	
}

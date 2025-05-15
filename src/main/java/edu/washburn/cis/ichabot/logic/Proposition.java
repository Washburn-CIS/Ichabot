package edu.washburn.cis.ichabot.logic;

class Proposition {
    String name;
    boolean value; 

  public Proposition(String name) {
        this.name = name;
        this.value = true; // default: assumed to be true when added to facts
    }

  public Proposition(String name, boolean value) {
        this.name = name;
        this.value = value;
    }
}


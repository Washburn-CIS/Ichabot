package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import java.util.*;

public class SFModelAgent extends SFAgent {

    private final SFMap map;

    public SFModelAgent(SFMap map) {
        this.map = map;
    }

    public SFCommand percieve(SFPercept p) { 
        var loc = p.agentLocations().get(p.yourID());
        System.out.println(loc);
        var options = new ArrayList<SFCommand>();
        for(var cmd : SFCommand.values()) {
            var dest = loc.move(cmd);
            if(dest.row() >=0 && dest.col() >=0 &&
               dest.row() < map.height() && dest.col() < map.width())
               options.add(cmd);
        } 
        return options.get((int)(Math.random()*options.size()));
    }
}
package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import java.util.Set;
import java.util.Map;
import java.util.Collections;
import java.io.Serializable;

public record SFMap(
    int width, 
    int height, 
    Map<SFCoordinates,Integer> tileHeight,
    Map<SFCoordinates,Integer> gold,
    Set<SFCoordinates> spawnPoints,
    Set<SFCoordinates> exits,
    Map<SFCoordinates,Set<SFCoordinates>> treasureMaps) implements Serializable {
    
    /** removes elements of map only visible to simulator */
    public SFMap agentVisible() {
        return new SFMap(width, height, 
            tileHeight, 
            Collections.emptyMap(),
            spawnPoints, 
            exits,
            Collections.emptyMap());
    }
}
package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.io.Serializable;

public record SFPercept(
    SFMap visibleMap, 
    int waterLevel,
    int yourID,
    Map<Integer,SFCoordinates> agentLocations,
    Map<Integer,Integer> agentGold,
    List<Integer> turnOrder,
    Set<SFCoordinates> treasureMaps) implements Serializable {
    
}
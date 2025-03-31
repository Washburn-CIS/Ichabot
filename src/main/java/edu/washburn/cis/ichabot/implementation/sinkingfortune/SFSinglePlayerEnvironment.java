package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import edu.washburn.cis.ichabot.agent.Environment;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

public class SFSinglePlayerEnvironment extends Environment<SFCommand,SFPercept> {


    private final SFMap map;
    private SFCoordinates agentLocation;
    private int agentGold = 0;
    private int waterLevel = 0;
    private Map<SFCoordinates, Integer> gold;

    public SFSinglePlayerEnvironment(SFMap map, SFAgent agent) {
        this.map = map;
        gold = new HashMap<SFCoordinates, Integer>(map.gold());
        List<SFCoordinates> bids = agent.bidOnSpawn(map);
        agentLocation = bids.get(0);
    }
    
    public SFPercept accept(SFCommand cmd) {
        if(cmd != null) {
            agentLocation = agentLocation.move(cmd);
            if(agentLocation.row() < 0 ||
                agentLocation.col() < 0 ||
                agentLocation.row() >= map.height() ||
                agentLocation.col() >= map.width())
                    throw new IllegalArgumentException("illegal move");
        }    
        
        agentGold += gold.get(agentLocation);
        gold.put(agentLocation, 0);
        waterLevel++;

        if(map.exits().contains(agentLocation)) return null;
        if(map.tileHeight().get(agentLocation) < waterLevel) return null;

        return new SFPercept(
            map.agentVisible(),
            waterLevel,
            1,
            Map.of(agentLocation,1),
            Map.of(1,agentGold),
            List.of(1),
            map.treasureMaps().get(agentLocation)
        );
    }
}
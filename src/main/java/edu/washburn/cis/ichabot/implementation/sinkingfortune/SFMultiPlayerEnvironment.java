package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import edu.washburn.cis.ichabot.agent.Environment;

import java.util.*;

public class SFMultiPlayerEnvironment 
        extends Environment<SFCommand,SFPercept,SFAgent> {

    private final SFMap map;
    private Map<SFAgent,SFCoordinates> agentLocations;
    private Map<SFAgent,Integer> agentGold = new HashMap<SFAgent,Integer>();
    private int waterLevel = 0;
    private Map<SFCoordinates, Integer> gold;
    private List<SFAgent> turnOrder;
    private Map<SFAgent,Integer> agentIds = new HashMap<SFAgent,Integer>();
    private HashSet<SFAgent> finishedAgents = new HashSet<SFAgent>();
    private HashSet<SFAgent> deadAgents = new HashSet<SFAgent>();

    public SFMultiPlayerEnvironment(SFMap map, List<SFAgent> agentList) {
        this.turnOrder = agentList;
        this.map = map;
        var agents = new HashSet<SFAgent>(agentList);
        gold = new HashMap<SFCoordinates, Integer>(map.gold());
        var spawns = new HashSet<SFCoordinates>(map.spawnPoints());
        int i=1;
        for(SFAgent agent: agentList) {
            agentIds.put(agent, i);
            i++;
            agentGold.put(agent, 0);
            for(SFCoordinates bid : agent.bidOnSpawn(map)) {
                if(spawns.contains(bid)) {
                    agentLocations.put(agent, bid);
                    spawns.remove(bid);
                    agents.remove(agent);
                    break;
                }
            }
        }
        for(SFAgent agent: agents) {
            for(SFCoordinates spawn : spawns) {
                agentLocations.put(agent, spawn);
                spawns.remove(spawn);
                agents.remove(agent);
            }
        }
    }
    
    public Map<SFAgent,SFPercept> accept(Map<SFAgent,SFCommand> commands) {
        var percepts = new HashMap<SFAgent,SFPercept>();

        var newTurnOrder = new ArrayList<Integer>();
        for(int i=1; i<=turnOrder.size(); i++)
            newTurnOrder.add(agentIds.get(turnOrder.get(i%turnOrder.size())));

        var newAgentGold = new HashMap<Integer,Integer>();
        for(SFAgent agent : turnOrder) 
            newAgentGold.put(agentIds.get(agent), agentGold.get(agent));
        
        var newAgentLocations = new HashMap<Integer,SFCoordinates>();
        for(SFAgent agent : turnOrder) 
            newAgentLocations.put(agentIds.get(agent), agentLocations.get(agent));

        for(SFAgent agent : turnOrder) {
            if(finishedAgents.contains(agent)) continue;
            if(deadAgents.contains(agent)) continue;
            var cmd = commands.get(agent);
            var agentLocation = agentLocations.get(agent);
            if(cmd != null) {
                agentLocations.put(agent, agentLocation.move(cmd));
                if(agentLocation.row() < 0 ||
                    agentLocation.col() < 0 ||
                    agentLocation.row() >= map.height() ||
                    agentLocation.col() >= map.width())
                        throw new IllegalArgumentException("illegal move");
            }    
            
            agentGold.put(agent, gold.get(agentLocation));
            gold.put(agentLocation, 0);
            waterLevel++;

            if(map.exits().contains(agentLocation)) {
                agentLocations.remove(agent);
                finishedAgents.add(agent);
                continue;
            }
            if(map.tileHeight().get(agentLocation) < waterLevel) {
                agentLocations.remove(agent);
                deadAgents.add(agent);
            }

            SFPercept p = new SFPercept(
                map.agentVisible(),
                waterLevel,
                agentIds.get(agent),
                newAgentLocations,
                newAgentGold,
                newTurnOrder,
                map.treasureMaps().get(agentLocation)
            );


            percepts.put(agent, p);
        }
        return percepts;
    }
}
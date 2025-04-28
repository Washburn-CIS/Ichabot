package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import edu.washburn.cis.ichabot.agent.Environment;

import java.util.*;

public class SFEnvironment 
        extends Environment<SFCommand,SFPercept,SFAgent> {

    private final SFMap map;
    private Map<SFAgent,SFCoordinates> agentLocations = 
        new HashMap<SFAgent,SFCoordinates>();
    private Map<SFAgent,Integer> agentGold = 
        new HashMap<SFAgent,Integer>();
    private int waterLevel = 0;
    private Map<SFCoordinates, Integer> gold;
    private List<SFAgent> turnOrder;
    private Map<SFAgent,Integer> agentIds = 
        new HashMap<SFAgent,Integer>();
    private HashSet<SFAgent> finishedAgents = 
        new HashSet<SFAgent>();
    private HashSet<SFAgent> deadAgents = 
        new HashSet<SFAgent>();

    public SFEnvironment(SFMap map, List<SFAgent> agentList) {
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

    public Map<SFAgent,SFCoordinates> getAgentLocations() { 
        return agentLocations; 
    }
    public Set<SFAgent> getDeadAgents() {
        return deadAgents;
    }
    public Set<SFAgent> getFinishedAgents() { 
        return finishedAgents;
    }
    public SFMap getMap() { return map; }
    public Map<SFCoordinates, Integer> getGold() { return gold; }
    public int getWaterLevel() { return waterLevel; }
    public Map<SFAgent, Integer> getAgentIds() { return agentIds; }
    
    public Map<SFAgent,SFPercept> accept(Map<SFAgent,SFCommand> commands) {
        var percepts = new HashMap<SFAgent,SFPercept>();

        var newTurnOrder = new ArrayList<Integer>();
        for(int i=1; i<=turnOrder.size(); i++)
            newTurnOrder.add(agentIds.get(turnOrder.get(i%turnOrder.size())));

        for(SFAgent agent : turnOrder) {
            if(finishedAgents.contains(agent)) continue;
            if(deadAgents.contains(agent)) continue;
            var cmd = commands.get(agent);
            var agentLocation = agentLocations.get(agent);
            if(cmd != null) {
                var dest = agentLocations.get(agent).move(cmd);
                if(!agentLocations.values().contains(dest)) {
                    agentLocations.remove(agent);
                    agentLocation = agentLocation.move(cmd);
                    if(agentLocation.row() < 0 ||
                        agentLocation.col() < 0 ||
                        agentLocation.row() >= map.height() ||
                        agentLocation.col() >= map.width())
                            throw new IllegalArgumentException("illegal move");
                    
                    agentLocations.put(agent, agentLocation);
                }
            }    
            
            agentGold.put(agent, 
                agentGold.get(agent) + gold.get(agentLocation));
            gold.put(agentLocation, 0);

            if(map.exits().contains(agentLocation)) {
                agentLocations.remove(agent);
                finishedAgents.add(agent);
                continue;
            }
            if(map.tileHeight().get(agentLocation) < waterLevel) {
                agentLocations.remove(agent);
                deadAgents.add(agent);
            }
        }

        var newAgentLocations = new HashMap<Integer,SFCoordinates>();
        var newAgentGold = new HashMap<Integer,Integer>();
        for(SFAgent agent : turnOrder) {
            var id = agentIds.get(agent);
            newAgentGold.put(id, agentGold.get(agent));
            newAgentLocations.put(id, agentLocations.get(agent));
        }

        for(SFAgent agent : turnOrder) {
            var agentLocation = agentLocations.get(agent);
            if(agentLocation == null) continue;
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
        waterLevel++;
        return percepts;
    }
}
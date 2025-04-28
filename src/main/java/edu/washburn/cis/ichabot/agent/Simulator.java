package edu.washburn.cis.ichabot.agent;

import java.io.Serializable;
import java.util.*;

public class Simulator<CommandT extends Serializable, 
                       PerceptT extends Serializable,
                       AgentT extends Agent<CommandT, PerceptT>,
                       EnvironmentT extends Environment<CommandT,PerceptT,AgentT>> {

    public final List<AgentT> AGENTS;
    public final EnvironmentT ENV;

    private List<Map<AgentT,PerceptT>> perceptTrace = new ArrayList<Map<AgentT,PerceptT>>();
    private List<Map<AgentT,CommandT>> commandTrace = new ArrayList<Map<AgentT,CommandT>>();
    private int round = 1;
    
    public Simulator(EnvironmentT env, AgentT ... agents) {
        this.AGENTS = new ArrayList<AgentT>();
        for(var agent : agents) this.AGENTS.add(agent);
        this.ENV = env;
    }

    public boolean step() {
        System.out.println("Round: " + round);
        round++;
        var percept = ENV.accept(
            commandTrace.size() > 0 ? 
                commandTrace.get(commandTrace.size()-1) : Collections.emptyMap());
        if(percept == null || percept.size() == 0) {
            System.out.println("Simulation Ended");
            return false;
        }
        perceptTrace.add(percept);
        System.out.println("Percept: " + percept);

        var commands = new HashMap<AgentT,CommandT>();
        for(var agent: AGENTS) 
            if(percept.keySet().contains(agent))
                commands.put(agent, agent.percieve(percept.get(agent)));
        System.out.println("Commands Issued: " + commands);
        commandTrace.add(commands);

        
        return true;
    }

    public void simulate() {
        while(step());
    }

}
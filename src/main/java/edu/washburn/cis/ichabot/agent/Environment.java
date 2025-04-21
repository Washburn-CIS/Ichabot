package edu.washburn.cis.ichabot.agent;

import java.io.Serializable;
import java.util.Map;

public abstract class Environment<CommandT extends Serializable, 
        PerceptT extends Serializable, 
        AgentT extends Agent<CommandT,PerceptT>> {
    public abstract Map<AgentT,PerceptT> accept(Map<AgentT,CommandT> commands);
}
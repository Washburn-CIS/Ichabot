package edu.washburn.cis.ichabot.agent;

import java.io.Serializable;

public abstract class Agent<CommandT extends Serializable,
                            PerceptT extends Serializable> {

    public abstract CommandT percieve(PerceptT percept);
    
}
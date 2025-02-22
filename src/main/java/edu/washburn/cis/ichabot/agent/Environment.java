package edu.washburn.cis.ichabot.agent;

import java.io.Serializable;

public abstract class Environment<CommandT extends Serializable, PerceptT extends Serializable> {
    public abstract PerceptT accept(CommandT command);
}
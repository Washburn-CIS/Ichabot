package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import edu.washburn.cis.ichabot.agent.Agent;
import edu.washburn.cis.ichabot.agent.Environment;

import java.util.*;

public abstract class SFAgent extends Agent<SFCommand, SFPercept> {

    public List<SFCoordinates> bidOnSpawn(SFMap map) {
        return Collections.unmodifiableList(
            new ArrayList<SFCoordinates>(map.spawnPoints()));
    }
}

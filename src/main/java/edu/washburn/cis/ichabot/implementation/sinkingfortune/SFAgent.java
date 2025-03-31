package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import edu.washburn.cis.ichabot.agent.Agent;
import edu.washburn.cis.ichabot.agent.Environment;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public abstract class SFAgent<EnvironmentT extends Environment<SFCommand,SFPercept>>
    extends Agent<SFCommand, SFPercept, EnvironmentT> {

    public List<SFCoordinates> bidOnSpawn(SFMap map) {
        return Collections.unmodifiableList(
            new ArrayList<SFCoordinates>(map.spawnPoints()));
    }
}

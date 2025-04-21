package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import edu.washburn.cis.ichabot.agent.*;

import java.util.Map;
import java.util.Set;
import java.io.Serializable;

public class SFPlayGround {
    public static void main(String[] args) {

        SFAgent agent = new SFAgent() {
            public SFCommand percieve(SFPercept p) { // TODO: fix type bounds
                return SFCommand.values()[(int)(Math.random()*4)];
            }
        };

        System.out.println(map);
        SFSinglePlayerEnvironment env = new SFSinglePlayerEnvironment(SFMap.ONE_PLAYER_CHALLENGE1, agent);
        Simulator sim = new Simulator(env, agent);
        sim.simulate();
    }
}
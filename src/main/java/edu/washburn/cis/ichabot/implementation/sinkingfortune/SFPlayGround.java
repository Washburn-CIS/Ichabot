package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import edu.washburn.cis.ichabot.agent.*;

import java.util.Map;
import java.util.Set;
import java.io.Serializable;

public class SFPlayGround {
    public static void main(String[] args) {
        SFMap map = new SFMap(2, 2, 
            Map.of(new SFCoordinates(0, 0), 10,
                new SFCoordinates(0, 1), 10,
                new SFCoordinates(1, 0), 10,
                new SFCoordinates(1, 1), 10
            ),
            Map.of(new SFCoordinates(0, 0), 0,
                new SFCoordinates(0, 1), 0,
                new SFCoordinates(1, 0), 10,
                new SFCoordinates(1, 1), 0
            ),
            Set.of(new SFCoordinates(0, 0)),
            Set.of(new SFCoordinates(1, 1)),
            Map.of(new SFCoordinates(0, 1), Set.of(new SFCoordinates(1, 0)))
        );

        SFAgent agent = new SFAgent() {
            public SFCommand percieve(Serializable p) { // TODO: fix type bounds
                return SFCommand.values()[(int)(Math.random()*4)];
            }
        };

        System.out.println(map);
        SFSinglePlayerEnvironment env = new SFSinglePlayerEnvironment(map, agent);
        Simulator sim = new Simulator(env, agent);
        sim.simulate();
    }
}
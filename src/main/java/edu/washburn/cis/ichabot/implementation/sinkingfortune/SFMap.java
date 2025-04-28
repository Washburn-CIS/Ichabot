package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import java.util.*;
import java.io.Serializable;

import static edu.washburn.cis.ichabot.implementation.sinkingfortune.SFCoordinates.coordinates;

public record SFMap(
    int width, 
    int height, 
    Map<SFCoordinates,Integer> tileHeight,
    Map<SFCoordinates,Integer> gold,
    Set<SFCoordinates> spawnPoints,
    Set<SFCoordinates> exits,
    Map<SFCoordinates,Set<SFCoordinates>> treasureMaps) implements Serializable {
    
    /** removes elements of map only visible to simulator */
    public SFMap agentVisible() {
        return new SFMap(width, height, 
            tileHeight, 
            Collections.emptyMap(),
            spawnPoints, 
            exits,
            Collections.emptyMap());
    }

    public static Map<SFCoordinates,Integer> createIntMap(int[][] map) {
        var m = new HashMap<SFCoordinates,Integer>();
        for(int r=0; r<map.length; r++) {
            for(int c=0; c<map[r].length; c++) {
                m.put(coordinates(r,c), map[r][c]);
            }
        }
        return m;
    }

    public static final int[][] ONE_PLAYER_CHALLENGE1_HEIGHT = {
        {5, 5},
        {5, 5}
    };
    public static final int[][] ONE_PLAYER_CHALLENGE1_GOLD = {
        {00, 00},
        {10, 00}
    };
    public static final SFMap ONE_PLAYER_CHALLENGE1 = new SFMap(2, 2, 
        createIntMap(ONE_PLAYER_CHALLENGE1_HEIGHT),       
        createIntMap(ONE_PLAYER_CHALLENGE1_GOLD), 
        Set.of(coordinates(0, 0), coordinates(0, 1)),  //spawns
        Set.of(coordinates(1,1)),                     // exits
        Map.of(coordinates(0,1), Set.of(coordinates(1,0))));  // maps

    

    public static final int[][] CHALLENGE2_HEIGHT = {
        {20, 20, 20, 20, 20, 20, 20, 20},
        {20, 20, 20, 20, 20, 20, 20, 20},
        {20, 20, 20, 20, 20, 20, 20, 20},
        {20, 20, 20, 20, 20, 20, 20, 20},
        {20, 20, 20, 20, 20, 20, 20, 20},
        {20, 20, 20, 20, 20, 20, 20, 20},
        {20, 20, 20, 20, 20, 20, 20, 20},
        {20, 20, 20, 20, 20, 20, 20, 20}
    };
    public static final int[][] CHALLENGE2_GOLD = {
        {00, 00, 00, 10, 00, 00, 00, 00},
        {00, 00, 00, 00, 00, 10, 00, 00},
        {00, 00, 00, 00, 10, 00, 10, 00},
        {00, 00, 00, 00, 00, 00, 00, 00},
        {00, 00, 10, 00, 00, 00, 10, 00},
        {00, 00, 10, 00, 00, 00, 00, 00},
        {00, 00, 10, 00, 00, 00, 00, 00},
        {50, 00, 10, 00, 00, 00, 00, 00}
    };
    public static final SFMap CHALLENGE2 = new SFMap(8, 8, 
        createIntMap(CHALLENGE2_HEIGHT),       
        createIntMap(CHALLENGE2_GOLD), 
        Set.of(coordinates(1, 0), coordinates(0, 1)),  //spawns
        Set.of(coordinates(7,7)),                     // exits
        Map.of(coordinates(0,1), 
            Set.of(coordinates(7,0),
            coordinates(4,2),
            coordinates(5,2),
            coordinates(6,2),
            coordinates(7,2),
            coordinates(0,3),
            coordinates(2,4),
            coordinates(1,5),
            coordinates(2,6),
            coordinates(1,0)),
        coordinates(1,0), 
        Set.of(coordinates(7,0),
            coordinates(4,2),
            coordinates(5,2),
            coordinates(6,2),
            coordinates(7,2),
            coordinates(0,3),
            coordinates(2,4),
            coordinates(1,5),
            coordinates(2,6),
            coordinates(1,0))));  // maps
}
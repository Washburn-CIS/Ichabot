package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import edu.washburn.cis.ichabot.agent.*;

import javax.swing.*;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.io.Serializable;

import java.util.Scanner;

public class SFPlayGround {
    public static void main(String[] args) {
        final var map = SFMap.ONE_PLAYER_CHALLENGE1;
        SFAgent agent = new SFAgent() {
            public SFCommand percieve(SFPercept p) { 
                var loc = p.agentLocations().get(p.yourID());
                var options = new ArrayList<SFCommand>();
                for(var cmd : SFCommand.values()) {
                    var dest = loc.move(cmd);
                    if(dest.row() >=0 && dest.col() >=0 &&
                       dest.row() < map.height() && dest.col() < map.width())
                       options.add(cmd);
                }
                return options.get((int)(Math.random()*options.size()));
            }
        };

        //System.out.println(map);
        SFSinglePlayerEnvironment env = new SFSinglePlayerEnvironment(map, agent);
        Simulator sim = new Simulator(env, agent);
        Scanner input = new Scanner(System.in);
        JFrame frame = new JFrame("SF Map Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var mapViewer = new SFMapViewer(env, 100);
        frame.add(mapViewer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        while(input.hasNextLine() && sim.step()) {
            mapViewer.repaint();
            input.nextLine();
        }
    }
}
package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import edu.washburn.cis.ichabot.agent.*;

import javax.swing.*;
import java.util.*;
import java.util.ArrayList;
import java.io.Serializable;

import java.util.Scanner;

public class SFPlayGround {
    public static void main(String[] args) {
        final var map = SFMap.CHALLENGE2;

        SFEnvironment env = new SFEnvironment(map, 
            List.of(new SFModelAgent(map), new SFModelAgent(map)));
        Simulator sim = new Simulator(env, agent1, agent2);
        Scanner input = new Scanner(System.in);
        /*
        JFrame frame = new JFrame("SF Map Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var mapViewer = new SFMapViewer(env, 100);
        frame.add(mapViewer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        */
        while(input.hasNextLine() && sim.step()) {
            update(null, env);  // update to send mapViewer if using GUI
            input.nextLine();
        }
        update(null, env);   // update to send mapViewer if using GUI
    } 

    public static void update(SFMapViewer mapViewer, SFEnvironment env) {
        //mapViewer.repaint();
        var agentLocations = env.getAgentLocations();
        System.out.println("Simulation Status");
        for(var agent: agentLocations.keySet())
            System.out.println(env.getAgentIds().get(agent)+": " + 
            agentLocations.get(agent));
        for(var agent: env.getDeadAgents())
            System.out.println(env.getAgentIds().get(agent)+": DEAD");
        for(var agent: env.getFinishedAgents())
            System.out.println(env.getAgentIds().get(agent)+": FINISHED");
    }
}
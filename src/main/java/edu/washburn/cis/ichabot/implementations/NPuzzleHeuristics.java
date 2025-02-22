package edu.washburn.cis.ichabot.implementation.npuzzle;

import java.util.stream.IntStream;
import java.util.Map;
import java.util.HashMap;
import java.awt.Point; // TODO: replace with record

public class NPuzzleHeuristics {

    public static Map<Integer,Point> matrixToMap(int[][] matrix) {
        var map = new HashMap<Integer,Point>();
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix.length; c++) {
                map.put(matrix[r][c], new Point(r,c));
            }
        }
        return map;
    }

    public static double manhattanDistance(int[][] board, int[][] goal) {
        var size = board.length;
        var boardLocs = matrixToMap(board);
        var goalLocs = matrixToMap(goal);
        return IntStream.range(1, size*size)
            .map( i -> 
                Math.abs(boardLocs.get(i).x - goalLocs.get(i).x) + 
                Math.abs(boardLocs.get(i).y - goalLocs.get(i).y))
            .sum();
    }

    public static int[][] generateBasicGoal(int size) {
        int[][] goal = new int[size][size];
        for(int r=0; r<size; r++) {
            for(int c=0; c<size; c++) {
                goal[r][c] = r*size + c + 1;
            }
        }
        return goal;
    }
}

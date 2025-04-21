package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import java.io.Serializable;

public record SFCoordinates(int row, int col) implements Serializable {

    /** yeilds coordinates resulting from given agent command */
    public SFCoordinates move(SFCommand cmd) {
        return switch(cmd) {
            case WEST: yield new SFCoordinates(row, col-1);
            case EAST: yield new SFCoordinates(row, col+1);
            case SOUTH: yield new SFCoordinates(row+1, col);
            case NORTH: yield new SFCoordinates(row-1, col);
            case PASS: yield this;
        };
    }

    public static SFCoordinates coordinates(int row, int col) { return new SFCoordinates(row, col); }
}
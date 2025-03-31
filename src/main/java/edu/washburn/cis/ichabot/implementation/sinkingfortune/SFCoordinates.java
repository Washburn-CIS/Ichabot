public record SFCoordinates(int x, int y) {

    /** yeilds coordinates resulting from given agent command */
    public SFCoordinates move(SFCommand cmd) {
        return switch(cmd) {
            case NORTH: yield new SFCoordinates(x, y-1);
            case SOUTH: yield new SFCoordinates(x, y+1);
            case EAST: yield new SFCoordinates(x+1, y);
            case WEST: yield new SFCoordinates(x-1, y);
            case PASS: yield this;
        };
    }
}
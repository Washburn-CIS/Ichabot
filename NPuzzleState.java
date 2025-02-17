import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class NPuzzleState implements SearchState<NPuzzleAction,NPuzzleState> {
    private final int[][] puzzleState;
    public final int SIZE;
    public final int BLANK_ROW;
    public final int BLANK_COL;

    public static Predicate<NPuzzleState> makeGoal(int size) {
        return state -> {
            final int SIZE = state.SIZE;
            for(int r=0; r<SIZE; r++) {
                for(int c=0; c<SIZE; c++) {
                    if(r == SIZE-1 && c == SIZE-1) 
                        return state.puzzleState[r][c] == 0;
                    if(state.puzzleState[r][c] != r*SIZE + c + 1) 
                        return false;
                }
            }
            return false;
        };
    }


    public NPuzzleState(int size, int[][] stateValues) {
        puzzleState = stateValues;
        SIZE = size;
        int br = 0;
        int bc = 0;

        var viewed = new HashSet<Integer>();
        for(int r=0; r<SIZE; r++) {
            for(int c=0; c<SIZE; c++) {
                if(stateValues[r][c] >= SIZE*SIZE || stateValues[r][c] < 0) {
                    throw new IllegalArgumentException(stateValues[r][c] + " not a valid puzzle value");
                }
                if(stateValues[r][c] == 0) {
                    br = r;
                    bc = c;
                }
                viewed.add(stateValues[r][c]);
            }
        }
        if(viewed.size() != SIZE*SIZE) {
            throw new IllegalArgumentException("puzzle states should have unique values");
        }
        BLANK_COL = bc;
        BLANK_ROW = br;
    }

    @Override
    public Set<NPuzzleAction> actions() {
        var actions = new HashSet<NPuzzleAction>();
        if(BLANK_ROW < SIZE-1) actions.add(NPuzzleAction.UP);
        if(BLANK_ROW > 0) actions.add(NPuzzleAction.DOWN);
        if(BLANK_COL < SIZE-1) actions.add(NPuzzleAction.LEFT);
        if(BLANK_COL > 0) actions.add(NPuzzleAction.RIGHT);
        return actions;
    }

    @Override
    public NPuzzleState nextState(NPuzzleAction action) {
        int[][] newState = new int[SIZE][SIZE];
        for(int r=0;r<SIZE; r++) {
            for(int c=0; c<SIZE; c++) {
                newState[r][c] = puzzleState[r][c];
            }
        }
        switch(action) {
            case UP:
                if(BLANK_ROW >= SIZE-1) throw new IllegalArgumentException();
                newState[BLANK_ROW][BLANK_COL] = newState[BLANK_ROW+1][BLANK_COL];
                newState[BLANK_ROW+1][BLANK_COL] = 0;
                break;
            case DOWN:
                if(BLANK_ROW <= 0) throw new IllegalArgumentException();
                newState[BLANK_ROW][BLANK_COL] = newState[BLANK_ROW-1][BLANK_COL];
                newState[BLANK_ROW-1][BLANK_COL] = 0;
                break;
            case LEFT:
                if(BLANK_COL >= SIZE-1) throw new IllegalArgumentException();
                newState[BLANK_ROW][BLANK_COL] = newState[BLANK_ROW][BLANK_COL+1];
                newState[BLANK_ROW][BLANK_COL+1] = 0;
                break;
            case RIGHT:
                if(BLANK_COL <= 0) throw new IllegalArgumentException();
                newState[BLANK_ROW][BLANK_COL] = newState[BLANK_ROW][BLANK_COL-1];
                newState[BLANK_ROW][BLANK_COL-1] = 0;
                break;
        }
        return new NPuzzleState(SIZE, newState);
    }

    @Override
    public String toString() {
        String ret = "";
        for(int r=0; r<SIZE; r++) {
            for(int c=0; c<SIZE; c++) {
                ret += puzzleState[r][c] + " ";
            }
            ret += "\n";
        }
        return ret;
    }
}

enum NPuzzleAction { UP, DOWN, LEFT, RIGHT }
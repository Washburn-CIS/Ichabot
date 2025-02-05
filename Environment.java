import java.io.Serializable;

public abstract class Environment<COMMAND extends Serializable, PERCEPT extends Serializable> {
    public abstract PERCEPT doCommand(COMMAND command);
}
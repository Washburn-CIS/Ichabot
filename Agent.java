import java.io.Serializable;

public abstract class Agent<COMMAND extends Serializable,
                            PERCEPT extends Serializable, 
                            ENVIRONMENT extends Environment<COMMAND,PERCEPT>> {
    public abstract COMMAND percieveEnvironment(PERCEPT percept);
}
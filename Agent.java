import java.io.Serializable;

public abstract class Agent<CommandT extends Serializable,
                            PerceptT extends Serializable, 
                            EnvironmentT extends Environment<CommandT,PerceptT>> {

    public abstract CommandT percieve(PerceptT percept);
    
}
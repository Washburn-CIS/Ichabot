import java.io.Serializable;

public abstract class Environment<CommandT extends Serializable, PerceptT extends Serializable> {
    public abstract PerceptT doCommand(CommandT command);
}
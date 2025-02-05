import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Simulator<CommandT extends Serializable, 
                       PerceptT extends Serializable,
                       EnvironmentT extends Environment<CommandT,PerceptT>,
                       AgentT extends Agent<CommandT, PerceptT, EnvironmentT>> {

    public final AgentT AGENT;
    public final EnvironmentT ENV;

    private List<PerceptT> perceptTrace = new ArrayList<PerceptT>();
    private List<CommandT> commandTrace = new ArrayList<CommandT>();
    private int round = 1;
    
    public Simulator(EnvironmentT env, AgentT agent) {
        this.AGENT = agent;
        this.ENV = env;
    }

    public boolean step() {
        System.out.println("Round: " + round);
        var percept = ENV.accept(commandTrace.size() > 0 ? commandTrace.get(commandTrace.size()-1) : null);
        if(percept == null) {
            System.out.println("Simulation Ended");
            return false;
        }
        perceptTrace.add(percept);
        System.out.println("Percept: ");
        var command = AGENT.percieve(percept);
        System.out.println("Command Issued: " + command);
        commandTrace.add(command);
        return true;
    }

    public void simulate() {
        while(step());
    }

}
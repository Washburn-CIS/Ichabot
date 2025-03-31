import java.util.Map;
import java.util.Set;
import java.util.List;

public record SFPercept(SFMap visibleMap,
    SFCoordinates yourLocation,
    Map<SFCoordinates,Integer> agentLocations,
    int waterLevel,
    Map<Integer,Integer> agentGold,
    List<Integer> turnOrder,
    Set<SFCoordinates> treasureMaps) {
    
}
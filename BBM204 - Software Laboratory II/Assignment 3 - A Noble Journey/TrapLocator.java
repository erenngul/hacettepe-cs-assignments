import java.util.*;

public class TrapLocator {
    public List<Colony> colonies;

    private boolean[] marked;

    private int[] edgeTo;

    private boolean[] onStack;

    public TrapLocator(List<Colony> colonies) {
        this.colonies = colonies;
    }

    public List<List<Integer>> revealTraps() {

        // Trap positions for each colony, should contain an empty array if the colony is safe.
        // I.e.:
        // 0 -> [2, 15, 16, 31]
        // 1 -> [4, 13, 22]
        // 3 -> []
        // ...
        List<List<Integer>> traps = new ArrayList<>();
        int maxCities = 0;
        for (Colony colony : colonies) {
            int maxCityId = Collections.max(colony.cities);
            if (maxCities < maxCityId)
                maxCities = maxCityId;
        }

        // Identify the time traps and save them into the traps variable and then return it.
        for (Colony colony : colonies) {
            marked = new boolean[maxCities + 1];
            edgeTo = new int[maxCities + 1];
            onStack = new boolean[maxCities + 1];
            List<Integer> trap = new ArrayList<>();
            for (int cityId : colony.cities) {
                if (colony.hasCycle)
                    break;
                if (!marked[cityId])
                    depthFirstSearch(colony, trap, cityId);
            }
            traps.add(trap);
        }

        return traps;
    }

    public void printTraps(List<List<Integer>> traps) {
        // For each colony, if you have encountered a time trap, then print the cities that create the trap.
        // If you have not encountered a time trap in this colony, then print "Safe".
        // Print the findings conforming to the given output format.
        System.out.println("Danger exploration conclusions:");
        for (int i = 0; i < traps.size(); i++) {
            if (traps.get(i).isEmpty())
                System.out.println("Colony " + (i + 1) + ": Safe");
            else
                System.out.println("Colony " + (i + 1) + ": Dangerous. Cities on the dangerous path: " + traps.get(i));
        }
    }

    private void depthFirstSearch(Colony colony, List<Integer> trap, int cityId) {
        onStack[cityId] = true;
        marked[cityId] = true;
        for (int adjacentCityId : colony.roadNetwork.get(cityId)) {
            if (!marked[adjacentCityId]) {
                edgeTo[adjacentCityId] = cityId;
                depthFirstSearch(colony, trap, adjacentCityId);
            }
            else if (onStack[adjacentCityId]) {
                for (int i = cityId; i != adjacentCityId; i = edgeTo[i])
                    trap.add(i);
                trap.add(adjacentCityId);
                Collections.sort(trap);
                colony.hasCycle = true;
                return;
            }
        }
        onStack[cityId] = false;
    }

}

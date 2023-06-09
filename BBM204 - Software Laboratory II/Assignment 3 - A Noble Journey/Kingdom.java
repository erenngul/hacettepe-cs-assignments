import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Kingdom {

    public List<List<Integer>> adjacencyMatrix;

    public List<List<Integer>> adjacencyList;

    public HashMap<Integer, List<Integer>> roadHashMap;

    public boolean[] marked;

    public void initializeKingdom(String filename) {
        // Read the txt file and fill your instance variables
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        adjacencyMatrix = new ArrayList<>();
        String line;
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] inputArray = line.split(" ");
            List<Integer> matrixRow = new ArrayList<>();
            for (String number : inputArray)
                matrixRow.add(Integer.parseInt(number));
            adjacencyMatrix.add(matrixRow);
        }

        adjacencyList = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.size(); i++)
            adjacencyList.add(new ArrayList<>());

        roadHashMap = new HashMap<>();
        for (int i = 0; i < adjacencyMatrix.size(); i++) {
            List<Integer> roadToCities = new ArrayList<>();
            for (int j = 0; j < adjacencyMatrix.size(); j++) {
                if (adjacencyMatrix.get(i).get(j) == 1) {
                    roadToCities.add(j + 1);
                    adjacencyList.get(i).add(j);
                    adjacencyList.get(j).add(i);
                }
            }
            roadHashMap.put(i + 1, roadToCities);
        }
    }

    public List<Colony> getColonies() {
        List<Colony> colonies = new ArrayList<>();
        // Identify the colonies using the given input file.
        marked = new boolean[adjacencyList.size()];
        for (int i = 0; i < adjacencyList.size(); i++) {
            if (!marked[i]) {
                Colony colony = new Colony();
                depthFirstSearch(colony, i);
                Collections.sort(colony.cities);
                colonies.add(colony);
            }
        }
        return colonies;
    }

    public void printColonies(List<Colony> discoveredColonies) {
        // Print the given list of discovered colonies conforming to the given output format.
        System.out.println("Discovered colonies are: ");
        for (int i = 0; i < discoveredColonies.size(); i++)
            System.out.println("Colony " + (i + 1) + ": " + discoveredColonies.get(i).cities);
    }

    private void depthFirstSearch(Colony colony, int cityId) {
        marked[cityId] = true;
        colony.cities.add(cityId + 1);
        colony.roadNetwork.put(cityId + 1, roadHashMap.get(cityId + 1));
        for (int adjacentCity : adjacencyList.get(cityId)) {
            if (!marked[adjacentCity])
                depthFirstSearch(colony, adjacentCity);
        }
    }
}

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class TravelMap {

    // Maps a single Id to a single Location.
    public Map<Integer, Location> locationMap = new HashMap<>();

    // List of locations, read in the given order
    public List<Location> locations = new ArrayList<>();

    // List of trails, read in the given order
    public List<Trail> trails = new ArrayList<>();

    public void initializeMap(String filename) {
        // Read the XML file and fill the instance variables locationMap, locations and trails.
        File xmlFile = new File(filename);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc;
        try {
            doc = builder.parse(xmlFile);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        doc.getDocumentElement().normalize();

        NodeList locationList = doc.getElementsByTagName("Location");
        for (int i = 0; i < locationList.getLength(); i++) {
            Node location = locationList.item(i);
            if (location.getNodeType() == Node.ELEMENT_NODE) {
                Element locationElement = (Element) location;
                String locationName = locationElement.getElementsByTagName("Name").item(0).getTextContent();
                int locationId = Integer.parseInt(locationElement.getElementsByTagName("Id").item(0).getTextContent());
                Location locationObject = new Location(locationName, locationId);
                locationMap.put(locationId, locationObject);
                locations.add(locationObject);
            }
        }

        NodeList trailList = doc.getElementsByTagName("Trail");
        for (int i = 0; i < trailList.getLength(); i++) {
            Node trail = trailList.item(i);
            if (trail.getNodeType() == Node.ELEMENT_NODE) {
                Element trailElement = (Element) trail;
                int trailSource = Integer.parseInt(trailElement.getElementsByTagName("Source").item(0).getTextContent());
                int trailDestination = Integer.parseInt(trailElement.getElementsByTagName("Destination").item(0).getTextContent());
                int trailDanger = Integer.parseInt(trailElement.getElementsByTagName("Danger").item(0).getTextContent());
                Trail trailObject = new Trail(locationMap.get(trailSource), locationMap.get(trailDestination), trailDanger);
                trails.add(trailObject);
                locationMap.get(trailSource).adj.add(trailObject);
                locationMap.get(trailDestination).adj.add(trailObject);
            }
        }
    }

    public List<Trail> getSafestTrails() {
        List<Trail> safestTrails = new ArrayList<>();
        // Fill the safestTrail list and return it.
        // Select the optimal Trails from the Trail list that you have read.
        PriorityQueue<Trail> pq = new PriorityQueue<>();

        visit(pq, 0);
        while (!pq.isEmpty()) {
            Trail trail = pq.poll();
            int locationId1 = trail.either();
            int locationId2 = trail.other(locationId1);
            Location location1 = locationMap.get(locationId1);
            Location location2 = locationMap.get(locationId2);
            if (location1.marked && location2.marked)
                continue;
            safestTrails.add(trail);
            if (!location1.marked)
                visit(pq, locationId1);
            if (!location2.marked)
                visit(pq, locationId2);
        }

        return safestTrails;
    }

    public void printSafestTrails(List<Trail> safestTrails) {
        // Print the given list of safest trails conforming to the given output format.
        System.out.println("Safest trails are:");
        int totalDanger = 0;
        for (Trail safestTrail : safestTrails) {
            System.out.println("The trail from " + safestTrail.source.name + " to " + safestTrail.destination.name + " with danger " + safestTrail.danger);
            totalDanger += safestTrail.danger;
        }
        System.out.println("Total danger: " + totalDanger);
    }

    private void visit(PriorityQueue<Trail> pq, int locationId) {
        locationMap.get(locationId).marked = true;
        for (Trail trail : locationMap.get(locationId).adj) {
            if (!locationMap.get(trail.other(locationId)).marked)
                pq.add(trail);
        }
    }
}

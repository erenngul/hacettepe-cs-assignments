import java.util.HashMap;

public class RailRoad extends Property {
	private static HashMap<Integer, RailRoad> railRoadProperties = new HashMap<Integer, RailRoad>(2);

	public RailRoad(int propertyID, String propertyName, double propertyCost) {
		super(propertyID, propertyName, propertyCost);
	}
	
	// Takes railroad properties from property.json, creates railroad objects and makes pairs of id-object
	public static HashMap<Integer, RailRoad> createRailRoadProperties() {
		for (HashMap.Entry<Integer, String[]> entry : properties.getRailRoads().entrySet()) {
			Integer key = entry.getKey();
			String[] value = entry.getValue();
			
			RailRoad railRoad = new RailRoad(key, value[0], Integer.parseInt(value[1]));
			
			railRoadProperties.put(key, railRoad);
		}
		
		return railRoadProperties;
	}

	public static HashMap<Integer, RailRoad> getRailRoadProperties() {
		return railRoadProperties;
	}
}
import java.util.HashMap;

public class Land extends Property {
	private static HashMap<Integer, Land> landProperties = new HashMap<Integer, Land>(22);

	public Land(int propertyID, String propertyName, double propertyCost) {
		super(propertyID, propertyName, propertyCost);
		
		// Sets the rent of property according to its cost
		if (propertyCost <= 2000.0)
			setPropertyRent(propertyCost * (40.0/100.0));
		else if (propertyCost <= 3000.0)
			setPropertyRent(propertyCost * (30.0/100.0));
		else
			setPropertyRent(propertyCost * (35.0/100.0));
	}
	
	// Takes land properties from property.json, creates land objects and makes pairs of id-object
	public static HashMap<Integer, Land> createLandProperties() {
		for (HashMap.Entry<Integer, String[]> entry : properties.getLands().entrySet()) {
			Integer key = entry.getKey();
			String[] value = entry.getValue();
			
			Land land = new Land(key, value[0], Integer.parseInt(value[1]));
			
			landProperties.put(key, land);
		}
		
		return landProperties;
	}
	
	public static HashMap<Integer, Land> getLandProperties() {
		return landProperties;
	}
}
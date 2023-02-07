public abstract class Property {
	
	private int propertyID;
	private String propertyName;
	private double propertyCost;
	private double propertyRent;
	private boolean propertyOwned = false;
	private Player propertyOwner;
	
	public Property(int propertyID, String propertyName, double propertyCost) {
		this.propertyID = propertyID;
		this.propertyName = propertyName;
		this.propertyCost = propertyCost;
	}
	
	public static PropertyJsonReader properties = new PropertyJsonReader();

	public int getPropertyID() {
		return propertyID;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public double getPropertyCost() {
		return propertyCost;
	}

	public double getPropertyRent() {
		return propertyRent;
	}
	
	public void setPropertyRent(double propertyRent) {
		this.propertyRent = propertyRent;
	}

	public boolean isPropertyOwned() {
		return propertyOwned;
	}
	
	public void setPropertyOwned(boolean propertyOwned) {
		this.propertyOwned = propertyOwned;
	}
	
	public Player getPropertyOwner() {
		return propertyOwner;
	}
	
	public void setPropertyOwner(Player player) {
		this.propertyOwner = player;
	}
	
}
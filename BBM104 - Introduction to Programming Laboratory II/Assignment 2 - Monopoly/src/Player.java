import java.util.ArrayList;
import java.util.HashMap;

public class Player extends User {
	
	private int position = 1;
	private ArrayList<Property> ownedPropertyList = new ArrayList<Property>();
	private int ownedRailRoadNumber;
	private static HashMap<String, Player> players = new HashMap<String, Player>(2);
	private int jailCount;
	private boolean inJail = false;
	private int parkingCount;
	private boolean inParking = false;
	
	// Creates Player object which has 15000 TL
	public Player(String name) {
		super(name, 15000);
	}
	
	// Creates objects of 2 players and makes pairs of name-object
	public static HashMap<String, Player> createPlayers() {
		Player player1 = new Player("Player 1");
		Player.getPlayers().put("Player 1", player1);
		Player player2 = new Player("Player 2");
		Player.getPlayers().put("Player 2", player2);
		
		return players;
	}
	
	// Changes position depending on the dice. If player lands on or passes GO square, banker gives 200 TL to player
	public void changePosition(int dice) {
		position += dice;
		if (position > 40) {
			// To ensure position value remains between 1 and 40
			position -= 40;
			setMoney(200);
			Game.getBanker().setMoney(-200);
		}
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public ArrayList<Property> getOwnedPropertyList() {
		return ownedPropertyList;
	}
	
	public static HashMap<String, Player> getPlayers() {
		return players;
	}
	
	public void addOwnedRailRoadNumber() {
		++ownedRailRoadNumber;
	}
	
	public int getOwnedRailRoadNumbers() {
		return ownedRailRoadNumber;
	}
	
	public int getJailCount() {
		return jailCount;
	}
	
	public void setJailCount(int jailCount) {
		this.jailCount = jailCount;
	}
	
	public void addJailCount(int jailCount) {
		this.jailCount += jailCount;
	}

	public boolean isInJail() {
		return inJail;
	}

	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	public int getParkingCount() {
		return parkingCount;
	}
	
	public void setParkingCount(int parkingCount) {
		this.parkingCount = parkingCount;
	}
	
	public void addParkingCount(int parkingCount) {
		this.parkingCount += parkingCount;
	}

	public boolean isInParking() {
		return inParking;
	}

	public void setInParking(boolean inParking) {
		this.inParking = inParking;
	}

}
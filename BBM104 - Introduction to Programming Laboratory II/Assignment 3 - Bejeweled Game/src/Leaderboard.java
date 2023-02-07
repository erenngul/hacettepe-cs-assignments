import java.util.ArrayList;
import java.util.Collections;

public class Leaderboard {
	private static ArrayList<Player> players = new ArrayList<Player>();

	// Creates player objects and adds them to an arraylist
	public static ArrayList<Player> createPlayers(ArrayList<String[]> leaderboard) {
		
		for (String[] line : leaderboard) {
			players.add(new Player(line[0], Integer.parseInt(line[1])));
		}
		
		return players;
	}
	
	// Sorts the player according to overriden compare method in Players class
	public static ArrayList<Player> sortPlayers(ArrayList<Player> players) {
		Collections.sort(players);
		return players;
	}
}
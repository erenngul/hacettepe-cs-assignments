import java.util.HashMap;

public abstract class Card {

	private static HashMap<String, Player> players = Game.getPlayers();
	private static ListJsonReader cards = new ListJsonReader();
	
	public static HashMap<String, Player> getPlayers() {
		return players;
	}

	public static ListJsonReader getCards() {
		return cards;
	}
	
}
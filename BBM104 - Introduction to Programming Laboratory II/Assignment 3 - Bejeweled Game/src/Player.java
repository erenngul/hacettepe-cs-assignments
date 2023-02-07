public class Player implements Comparable<Player> {
	private String name;
	private int score;

	public Player(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}

	// Compares the scores of players
	@Override
	public int compareTo(Player player) {
		return player.score - this.score;
	}
}
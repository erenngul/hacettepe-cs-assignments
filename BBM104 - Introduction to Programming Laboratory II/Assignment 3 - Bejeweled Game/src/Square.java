public class Square extends Jewel {

	public Square() {
		super(15, "S");
	}

	// Checks horizontal direction for match
	@Override
	public void match(int row, int column) {
		
		while (true) {
			boolean horizontalMatchFound = horizontalMatch(row, column);
			if (horizontalMatchFound == true)
				break;
			
			else {
				Game.setCollectedScore(0);
				break;
			}
		}
		
	}

}
public class Wildcard extends Jewel {

	public Wildcard() {
		super(10, "W");
	}

	// Checks all directions for match
	@Override
	public void match(int row, int column) {
		
		while (true) {
			boolean verticalMatchFound = verticalMatch(row, column);
			if (verticalMatchFound == true)
				break;
	
			boolean horizontalMatchFound = horizontalMatch(row, column);
			if (horizontalMatchFound == true)
				break;
			
			boolean leftDiagonalMatchFound = leftDiagonalMatch(row, column);
			if (leftDiagonalMatchFound == true)
				break;
	
			boolean rightDiagonalMatchFound = rightDiagonalMatch(row, column);
			if (rightDiagonalMatchFound == true)
				break;
			
			else {
				Game.setCollectedScore(0);
				break;
			}
		}
		
	}

}
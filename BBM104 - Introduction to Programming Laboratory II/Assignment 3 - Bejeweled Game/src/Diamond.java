public class Diamond extends Jewel {

	public Diamond() {
		super(30, "D");
	}
	
	// Checks diagonal directions for match
	@Override
	public void match(int row, int column) {

		while (true) {
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
public class Triangle extends Jewel {

	public Triangle() {
		super(15, "T");
	}
	
	// Checks vertical direction for match
	@Override
	public void match(int row, int column) {
		
		while (true) {
			boolean verticalMatchFound = verticalMatch(row, column);
			if (verticalMatchFound == true)
				break;
			
			else {
				Game.setCollectedScore(0);
				break;
			}
		}
		
	}
	
}
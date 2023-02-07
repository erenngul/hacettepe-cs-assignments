public class MathematicalSymbol extends Jewel {
	private String symbol;

	public MathematicalSymbol(String symbol) {
		super(20, "M");
		this.symbol = symbol;
	}

	// Defines matching rules for all mathematical symbols
	@Override
	public void match(int row, int column) {
		MathematicalSymbol jewel = (MathematicalSymbol) Game.getMatrix().get(row).get(column);
		
		if (jewel.getSymbol().equals("/")) {
			while (true) {
				boolean rightDiagonalMatchFound = rightDiagonalMatch(row, column);
				if (rightDiagonalMatchFound == true)
					break;
				
				else {
					Game.setCollectedScore(0);
					break;
				}
			}
		}
		else if (jewel.getSymbol().equals("-")) {
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
		else if (jewel.getSymbol().equals("+")) {
			while (true) {
				boolean horizontalMatchFound = horizontalMatch(row, column);
				if (horizontalMatchFound == true)
					break;
					
				boolean verticalMatchFound = verticalMatch(row, column);
				if (verticalMatchFound == true)
					break;
				
				else {
					Game.setCollectedScore(0);
					break;
				}
			}
		}
		else if (jewel.getSymbol().equals("\\")) {
			while (true) {
				boolean leftDiagonalMatchFound = leftDiagonalMatch(row, column);
				if (leftDiagonalMatchFound == true)
					break;
				
				else {
					Game.setCollectedScore(0);
					break;
				}
			}
		}
		else if (jewel.getSymbol().equals("|")) {
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

	public String getSymbol() {
		return symbol;
	}
}
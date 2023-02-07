import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public abstract class Jewel {
	private int point;
	private String type;
	private boolean matchFound;
	
	public Jewel(int point, String type) {
		this.point = point;
		this.type = type;
	}
	
	public abstract void match(int row, int column);
	
	// Search in left diagonal direction
	public boolean leftDiagonalMatch(int row, int column) {
		ArrayList<ArrayList<Jewel>> matrix = Game.getMatrix();
		Jewel[] matchedJewels = new Jewel[3];
		HashSet<String> nonDuplicateJewels;

		while (true) {
			
			// Search in direction 1
			try {
				matchedJewels[0] = matrix.get(row).get(column);
				matchedJewels[1] = matrix.get(row - 1).get(column - 1);
				matchedJewels[2] = matrix.get(row - 2).get(column - 2);
				nonDuplicateJewels = new HashSet<String>(Arrays.asList(matchedJewels[0].getType(), matchedJewels[1].getType(), matchedJewels[2].getType()));
				
				if (matchedJewels[0].getType().equals("D") || (matchedJewels[0].getType().equals("M") ? ((MathematicalSymbol) matchedJewels[0]).getSymbol().equals("\\") : false)) {
					if (nonDuplicateJewels.size() == 1) {
						matrix.get(row).set(column, null);
						matrix.get(row - 1).set(column - 1, null);
						matrix.get(row - 2).set(column - 2, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				else if (matchedJewels[0].getType().equals("W")) {
					if (nonDuplicateJewels.size() == 1 || nonDuplicateJewels.size() == 2) {
						matrix.get(row).set(column, null);
						matrix.get(row - 1).set(column - 1, null);
						matrix.get(row - 2).set(column - 2, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				
			} catch (IndexOutOfBoundsException e) {}

			// Search in direction 9
			try {
				matchedJewels[0] = matrix.get(row).get(column);
				matchedJewels[1] = matrix.get(row + 1).get(column + 1);
				matchedJewels[2] = matrix.get(row + 2).get(column + 2);
				nonDuplicateJewels = new HashSet<String>(Arrays.asList(matchedJewels[0].getType(), matchedJewels[1].getType(), matchedJewels[2].getType()));
				
				if (matchedJewels[0].getType().equals("D") || (matchedJewels[0].getType().equals("M") ? ((MathematicalSymbol) matchedJewels[0]).getSymbol().equals("\\") : false)) {
					if (nonDuplicateJewels.size() == 1) {
						matrix.get(row).set(column, null);
						matrix.get(row + 1).set(column + 1, null);
						matrix.get(row + 2).set(column + 2, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				else if (matchedJewels[0].getType().equals("W")) {
					if (nonDuplicateJewels.size() == 1 || nonDuplicateJewels.size() == 2) {
						matrix.get(row).set(column, null);
						matrix.get(row + 1).set(column + 1, null);
						matrix.get(row + 2).set(column + 2, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				
			} catch (IndexOutOfBoundsException e) {break;}
			break;
		}
		
		return matchFound;
	}
	
	// Search in right diagonal direction
	public boolean rightDiagonalMatch(int row, int column) {
		ArrayList<ArrayList<Jewel>> matrix = Game.getMatrix();
		Jewel[] matchedJewels = new Jewel[3];
		HashSet<String> nonDuplicateJewels;
		
		while (true) {
			
			// Search in direction 3
			try {
				matchedJewels[0] = matrix.get(row).get(column);
				matchedJewels[1] = matrix.get(row - 1).get(column + 1);
				matchedJewels[2] = matrix.get(row - 2).get(column + 2);
				nonDuplicateJewels = new HashSet<String>(Arrays.asList(matchedJewels[0].getType(), matchedJewels[1].getType(), matchedJewels[2].getType()));
				
				if (matchedJewels[0].getType().equals("D") || (matchedJewels[0].getType().equals("M") ? ((MathematicalSymbol) matchedJewels[0]).getSymbol().equals("/") : false)) {
					if (nonDuplicateJewels.size() == 1) {
						matrix.get(row).set(column, null);
						matrix.get(row - 1).set(column + 1, null);
						matrix.get(row - 2).set(column + 2, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				else if (matchedJewels[0].getType().equals("W")) {
					if (nonDuplicateJewels.size() == 1 || nonDuplicateJewels.size() == 2) {
						matrix.get(row).set(column, null);
						matrix.get(row - 1).set(column + 1, null);
						matrix.get(row - 2).set(column + 2, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				
			} catch (IndexOutOfBoundsException e) {}
			
			// Search in direction 7
			try {
				matchedJewels[0] = matrix.get(row).get(column);
				matchedJewels[1] = matrix.get(row + 1).get(column - 1);
				matchedJewels[2] = matrix.get(row + 2).get(column - 2);
				nonDuplicateJewels = new HashSet<String>(Arrays.asList(matchedJewels[0].getType(), matchedJewels[1].getType(), matchedJewels[2].getType()));
				
				if (matchedJewels[0].getType().equals("D") || (matchedJewels[0].getType().equals("M") ? ((MathematicalSymbol) matchedJewels[0]).getSymbol().equals("/") : false)) {
					if (nonDuplicateJewels.size() == 1) {
						matrix.get(row).set(column, null);
						matrix.get(row + 1).set(column - 1, null);
						matrix.get(row + 2).set(column - 2, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				else if (matchedJewels[0].getType().equals("W")) {
					if (nonDuplicateJewels.size() == 1 || nonDuplicateJewels.size() == 2) {
						matrix.get(row).set(column, null);
						matrix.get(row + 1).set(column - 1, null);
						matrix.get(row + 2).set(column - 2, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				
			} catch (IndexOutOfBoundsException e) {break;}
			break;
		}
		
		return matchFound;
	}
	
	// Search in horizontal direction
	public boolean horizontalMatch(int row, int column) {
		ArrayList<ArrayList<Jewel>> matrix = Game.getMatrix();
		Jewel[] matchedJewels = new Jewel[3];
		HashSet<String> nonDuplicateJewels;

		while (true) {

			// Search in left horizontal direction 4
			try {
				matchedJewels[0] = matrix.get(row).get(column);
				matchedJewels[1] = matrix.get(row).get(column - 1);
				matchedJewels[2] = matrix.get(row).get(column - 2);
				nonDuplicateJewels = new HashSet<String>(Arrays.asList(matchedJewels[0].getType(), matchedJewels[1].getType(), matchedJewels[2].getType()));
				
				if (matchedJewels[0].getType().equals("S") || (matchedJewels[0].getType().equals("M") ? ((MathematicalSymbol) matchedJewels[0]).getSymbol().equals("-") : false) || (matchedJewels[0].getType().equals("M") ? ((MathematicalSymbol) matchedJewels[0]).getSymbol().equals("+") : false)) {
					if (nonDuplicateJewels.size() == 1) {
						matrix.get(row).set(column, null);
						matrix.get(row).set(column - 1, null);
						matrix.get(row).set(column - 2, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				else if (matchedJewels[0].getType().equals("W")) {
					if (nonDuplicateJewels.size() == 1 || nonDuplicateJewels.size() == 2) {
						matrix.get(row).set(column, null);
						matrix.get(row).set(column - 1, null);
						matrix.get(row).set(column - 2, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				
			} catch (IndexOutOfBoundsException e) {}

			// Search in right horizontal direction 6
			try {
				matchedJewels[0] = matrix.get(row).get(column);
				matchedJewels[1] = matrix.get(row).get(column + 1);
				matchedJewels[2] = matrix.get(row).get(column + 2);
				nonDuplicateJewels = new HashSet<String>(Arrays.asList(matchedJewels[0].getType(), matchedJewels[1].getType(), matchedJewels[2].getType()));
				
				if (matchedJewels[0].getType().equals("S") || (matchedJewels[0].getType().equals("M") ? ((MathematicalSymbol) matchedJewels[0]).getSymbol().equals("-") : false) || (matchedJewels[0].getType().equals("M") ? ((MathematicalSymbol) matchedJewels[0]).getSymbol().equals("+") : false)) {
					if (nonDuplicateJewels.size() == 1) {
						matrix.get(row).set(column, null);
						matrix.get(row).set(column + 1, null);
						matrix.get(row).set(column + 2, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				else if (matchedJewels[0].getType().equals("W")) {
					if (nonDuplicateJewels.size() == 1 || nonDuplicateJewels.size() == 2) {
						matrix.get(row).set(column, null);
						matrix.get(row).set(column + 1, null);
						matrix.get(row).set(column + 2, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				
			} catch (IndexOutOfBoundsException e) {break;}
			break;
		}
		
		return matchFound;
	}
	
	// Search in vertical direction
	public boolean verticalMatch(int row, int column) {
		ArrayList<ArrayList<Jewel>> matrix = Game.getMatrix();
		Jewel[] matchedJewels = new Jewel[3];
		HashSet<String> nonDuplicateJewels;

		while (true) {
			
			// Search in upward vertical direction 2
			try {
				matchedJewels[0] = matrix.get(row).get(column);
				matchedJewels[1] = matrix.get(row - 1).get(column);
				matchedJewels[2] = matrix.get(row - 2).get(column);
				nonDuplicateJewels = new HashSet<String>(Arrays.asList(matchedJewels[0].getType(), matchedJewels[1].getType(), matchedJewels[2].getType()));
				if (matchedJewels[0].getType().equals("T") || (matchedJewels[0].getType().equals("M") ? ((MathematicalSymbol) matchedJewels[0]).getSymbol().equals("|") : false) || (matchedJewels[0].getType().equals("M") ? ((MathematicalSymbol) matchedJewels[0]).getSymbol().equals("+") : false)) {
					if (nonDuplicateJewels.size() == 1) {
						matrix.get(row).set(column, null);
						matrix.get(row - 1).set(column, null);
						matrix.get(row - 2).set(column, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				else if (matchedJewels[0].getType().equals("W")) {
					if (nonDuplicateJewels.size() == 1 || nonDuplicateJewels.size() == 2) {
						matrix.get(row).set(column, null);
						matrix.get(row - 1).set(column, null);
						matrix.get(row - 2).set(column, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				
			} catch (IndexOutOfBoundsException e) {}
			
			// Search in downward vertical direction 8
			try {
				matchedJewels[0] = matrix.get(row).get(column);
				matchedJewels[1] = matrix.get(row + 1).get(column);
				matchedJewels[2] = matrix.get(row + 2).get(column);
				nonDuplicateJewels = new HashSet<String>(Arrays.asList(matchedJewels[0].getType(), matchedJewels[1].getType(), matchedJewels[2].getType()));
				
				if (matchedJewels[0].getType().equals("T") || (matchedJewels[0].getType().equals("M") ? ((MathematicalSymbol) matchedJewels[0]).getSymbol().equals("|") : false) || (matchedJewels[0].getType().equals("M") ? ((MathematicalSymbol) matchedJewels[0]).getSymbol().equals("+") : false)) {
					if (nonDuplicateJewels.size() == 1) {
						matrix.get(row).set(column, null);
						matrix.get(row + 1).set(column, null);
						matrix.get(row + 2).set(column, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				else if (matchedJewels[0].getType().equals("W")) {
					if (nonDuplicateJewels.size() == 1 || nonDuplicateJewels.size() == 2) {
						matrix.get(row).set(column, null);
						matrix.get(row + 1).set(column, null);
						matrix.get(row + 2).set(column, null);
						Game.addScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						Game.setCollectedScore(matchedJewels[0].getPoint() + matchedJewels[1].getPoint() + matchedJewels[2].getPoint());
						matchFound = true;
						break;
					}
				}
				
			} catch (IndexOutOfBoundsException e) {break;}
			break;
		}
		
		return matchFound;
	}
	
	public int getPoint() {
		return point;
	}
	
	public String getType() {
		return type;
	}
	
}
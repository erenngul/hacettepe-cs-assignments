import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
	private static ArrayList<ArrayList<Jewel>> matrix = new ArrayList<ArrayList<Jewel>>();
	private static String name;
	private static int totalScore;
	private static int collectedScore;
	
	// Reads the game grid arraylist and creates a matrix made of jewel objects
	public static ArrayList<ArrayList<Jewel>> createMatrix(ArrayList<ArrayList<String>> gameGrid) {
		
		for (ArrayList<String> row : gameGrid) {
			ArrayList<Jewel> matrixRow = new ArrayList<Jewel>();
			
			for (String jewel : row) {
				switch (jewel) {
				case "D":
					Jewel diamond = new Diamond();
					matrixRow.add(diamond);
					break;
				case "S":
					Jewel square = new Square();
					matrixRow.add(square);
					break;
				case "T":
					Jewel triangle = new Triangle();
					matrixRow.add(triangle);
					break;
				case "W":
					Jewel wildcard = new Wildcard();
					matrixRow.add(wildcard);
					break;
				default:
					MathematicalSymbol mathematicalSymbol = new MathematicalSymbol(jewel);
					matrixRow.add(mathematicalSymbol);
				}
			}
			
			matrix.add(matrixRow);
		}
		
		return matrix;
	}
	
	// Selects the coordinate and pops if there is a triple match
	public static void selectCoordinate(int row, int column, ArrayList<ArrayList<Jewel>> matrix) throws IndexOutOfBoundsException {
		Jewel jewel = matrix.get(row).get(column);
		
		switch (jewel.getType()) {
		case "D":
			jewel.match(row, column);
			break;
		case "S":
			jewel.match(row, column);
			break;
		case "T":
			jewel.match(row, column);
			break;
		case "W":
			jewel.match(row, column);
			break;
		default:
			jewel.match(row, column);
		}		
	}
	
	/* 
	 * After the jewels popped, remaining jewels slide down if there is empty space below
	 * The pseudocode of the method is:
	 * 1. Start from bottom row and check all columns in that row, find if coordinate is null
	 * 2. Get index of that coordinate
	 * 3. Count all nulls up to next jewel in the same column and add 1 to count
     * 4. Slide down the jewel that much count
     * 5. Repeat for all rows
	 */
	public static void slideDown() {
		for (int rowIndex = matrix.size() - 1; rowIndex > 0; rowIndex--) {
			int columnIndex = 0;
			for (Jewel jewel : matrix.get(rowIndex)) {
				if (jewel == null) {
					int rowCoordinate1, rowCoordinate2; // coordinate is row
					rowCoordinate1 = rowIndex;
					rowCoordinate2 = rowIndex;
					
					int count = 1;
					boolean foundNextJewel = false;
					while (foundNextJewel == false) {
						try {
							if (matrix.get(rowCoordinate1 - 1).get(columnIndex) == null) {
								count++;
								rowCoordinate1--;
							}
							else {
								foundNextJewel = true;
							}
						} catch (IndexOutOfBoundsException e) {
							break;
						}
					}
					
					try {
						Jewel fallingJewel = matrix.get(rowCoordinate2 - count).get(columnIndex);
						matrix.get(rowCoordinate2).set(columnIndex, fallingJewel);
						matrix.get(rowCoordinate2 - count).set(columnIndex, null);
					} catch (IndexOutOfBoundsException e) {}
				}
				columnIndex++;
			}
		}
	}
	
	// The game is organized in this method, it iterates over commands and plays each turn
	public static void playGame(FileWriter monitoringWriter, ArrayList<String[]> commands, ArrayList<ArrayList<String>> gameGrid) throws IOException {
		ArrayList<ArrayList<Jewel>> matrix = Game.createMatrix(gameGrid);
		
		monitoringWriter.write("Game grid:\n\n");
		printMatrix(monitoringWriter);

		for (String[] command : commands) {
			// If coordinates are given in the command
			if (command.length == 2) {
				try {
					monitoringWriter.write(String.format("Select coordinate or enter E to end the game: %s %s\n\n", command[0], command[1]));
					selectCoordinate(Integer.parseInt(command[0]), Integer.parseInt(command[1]), matrix);
					slideDown();
					printMatrix(monitoringWriter);
					monitoringWriter.write(String.format("Score: %d points\n\n", collectedScore));
				// If the selected coordinate is empty
				} catch (NullPointerException e) {
					monitoringWriter.write("Please enter a valid coordinate\n\n");
					continue;
				// If the selected coordinate is out of grid
				} catch (IndexOutOfBoundsException e) {
					monitoringWriter.write("Please enter a valid coordinate\n\n");
					continue;
				}
			}
			// If E command is given
			else if (command[0].equals("E")) {
				monitoringWriter.write(String.format("Select coordinate or enter E to end the game: %s\n\n", command[0]));
				monitoringWriter.write(String.format("Total score: %d points\n\n", totalScore));
			}
			// Name is given
			else {
				monitoringWriter.write(String.format("Enter name: %s\n\n", command[0]));
				
				// Leaderboard output is written in this part after the name is given
				ArrayList<String[]> playerList = FileIO.readLeaderboard("leaderboard.txt");
				// The user object is created and added to players arraylist
				Player user = new Player(command[0], totalScore);
				ArrayList<Player> players = Leaderboard.createPlayers(playerList);
				
				File leaderboardFile = new File("leaderboard.txt");
				FileWriter leaderboardWriter = new FileWriter(leaderboardFile);
				for (Player player : players) {
					leaderboardWriter.write(player.getName() + " " + player.getScore() + "\n");
				}
				leaderboardWriter.write(user.getName() + " " + user.getScore());
				leaderboardWriter.close();
				
				players.add(user);
				players = Leaderboard.sortPlayers(players);
				
				// User's rank is written in this part
				if (players.get(0).equals(user))
					monitoringWriter.write(String.format("Your rank is 1/%d, your score is %d points higher than %s\n\n", players.size(), user.getScore() - players.get(1).getScore(), players.get(1).getName()));
				else if (players.get(players.size() - 1).equals(user))
					monitoringWriter.write(String.format("Your rank is %d/%d, your score is %d points lower than %s\n\n", players.size(), players.size(), players.get(players.size() - 2).getScore() - user.getScore(), players.get(players.size() - 2).getName()));
				else {
					int rankIndex = Collections.binarySearch(players, user);
					monitoringWriter.write(String.format("Your rank is %d/%d, your score is %d points lower than %s and %d points higher than %s\n\n", rankIndex + 1, players.size(), players.get(rankIndex - 1).getScore() - user.getScore(), players.get(rankIndex - 1).getName(), user.getScore() - players.get(rankIndex + 1).getScore(), players.get(rankIndex + 1).getName()));
				}
				
				monitoringWriter.write("Good bye!");
			}
		}
		
	}
	
	// Writes the matrix to monitoring file
	public static void printMatrix(FileWriter monitoringWriter) throws IOException {
		for (ArrayList<Jewel> row : matrix) {
			for (Jewel jewel : row) {
				if (jewel == null) {
					monitoringWriter.write("  ");
				}
				else {
					monitoringWriter.write(jewel.getType().equals("M") ? ((MathematicalSymbol) jewel).getSymbol() + " " : jewel.getType() + " ");
				}
			}
			monitoringWriter.write("\n");
		}
		monitoringWriter.write("\n");
	}
	
	public static ArrayList<ArrayList<Jewel>> getMatrix() {
		return matrix;
	}
	
	public static int getTotalScore() {
		return totalScore;
	}
	
	public static void addScore(int points) {
		totalScore += points;
	}

	public static void setCollectedScore(int collectedScore) {
		Game.collectedScore = collectedScore;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		Game.name = name;
	}
}
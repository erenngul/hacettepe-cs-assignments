import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		// Reads input files gameGrid.txt and command.txt and makes arraylists of them to use in methods
		ArrayList<ArrayList<String>> gameGrid = FileIO.readGameGrid(args[0]);
		ArrayList<String[]> commands = FileIO.readCommand(args[1]);

		// Writes the output of game to monitoring.txt and leaderboard.txt
		FileIO.writeOutput(commands, gameGrid);
	}

}
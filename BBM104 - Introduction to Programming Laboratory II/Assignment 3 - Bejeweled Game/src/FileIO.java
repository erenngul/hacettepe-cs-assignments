import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileIO {
	
	// Reads command.txt file to add the commands to an arraylist
	public static ArrayList<String[]> readCommand(String file) {
		ArrayList<String[]> commands = new ArrayList<String[]>();
		File commandFile = new File(file);
		
		try {
			Scanner scanner = new Scanner(commandFile);
			
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().trim().split(" ");
				commands.add(line);
			}
			
			scanner.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return commands;
	}
	
	// Reads gameGrid.txt to add the game grid to an arraylist
	public static ArrayList<ArrayList<String>> readGameGrid(String file) {
		ArrayList<ArrayList<String>> gameGrid = new ArrayList<ArrayList<String>>();
		File gameGridFile = new File(file);
		
		try {
			Scanner scanner = new Scanner(gameGridFile);
			
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().trim().split(" ");
				ArrayList<String> row = new ArrayList<String>(Arrays.asList(line));
				gameGrid.add(row);
			}
			
			scanner.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return gameGrid;
	}
	
	// Reads leaderboard.txt to add the players to an arraylist
	public static ArrayList<String[]> readLeaderboard(String file) {
		ArrayList<String[]> leaderboard = new ArrayList<String[]>();
		File leaderboardFile = new File(file);
		
		try {
			Scanner scanner = new Scanner(leaderboardFile);
			
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().trim().split(" ");
				leaderboard.add(line);
			}
			
			scanner.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return leaderboard;
	}
	
	// Writes output of the game to monitoring.txt and leaderboard.txt
	public static void writeOutput(ArrayList<String[]> commands, ArrayList<ArrayList<String>> gameGrid) {
		File monitoringFile = new File("monitoring.txt");
		
		try {
			FileWriter monitoringWriter = new FileWriter(monitoringFile);
			Game.playGame(monitoringWriter, commands, gameGrid);
			monitoringWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
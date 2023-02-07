import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	
	public static File outputFile = new File("output.txt");
	public static FileWriter writer;
	
	public static void main(String[] args) {

		// Reads command.txt and creates all of the properties taken from property.json
		Game.readCommandFile(args[0]);
		Land.createLandProperties();
		RailRoad.createRailRoadProperties();
		Company.createCompanyProperties();
		
		try {
			writer = new FileWriter(outputFile);
			for (String[] command : Game.getCommands()) {
				
				if (command.length == 1) {
					// Gives information about the current situation of the game
					Game.show();
				}
				else {
					// Executes the command, each player plays their turn
					Game.playTurns(command[0], Integer.parseInt(command[1]));
					
					// If a player bankrupts, it will stop iterating over commands
					if (Game.isGameEnd() == true)
						break;
					
				}
			}
			
			// Shows the result of the game
			Game.show();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

// Sport class is used to read sport.txt and create a (sportID, Sport object) HashMap.
public class Sport {
	private static HashMap<Integer, Sport> sports = new HashMap<>(100);
	private int sportID, calorieBurned;
	private String nameOfSport;
	
	
	// Sport constructor to initialize the variables of object Sport.
	public Sport(String[] dataArray) {
		sportID = Integer.parseInt(dataArray[0]);
		nameOfSport = dataArray[1];
		calorieBurned = Integer.parseInt(dataArray[2]);
	}
	
	
	// Read sport.txt file and add (sportID, Sport object) pair to HashMap.
	public static void readSportFile(String file) {
		try {
			File sportFile = new File(file);
			Scanner reader = new Scanner(sportFile);
			
			while (reader.hasNext()) {
				String line = reader.nextLine();
				String[] dataArray = line.split("\t");
				
				sports.put(Integer.parseInt(dataArray[0]), new Sport(dataArray));
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	// Return sports HashMap.
	public static HashMap<Integer, Sport> getSports() {
		return sports;
	}
	
	// Return nameOfSport.
	public String getNameOfSport() {
		return nameOfSport;
	}
	
	// Return calorieBurned.
	public int getCalorieBurned() {
		return calorieBurned;
	}
}
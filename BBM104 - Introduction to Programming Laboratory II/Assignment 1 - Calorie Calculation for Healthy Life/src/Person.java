import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

// Person class is used to read people.txt and create a (personID, Person object) HashMap.
// It also calculates the daily calorie need and result for each Person object.
public class Person {
	private static HashMap<Integer, Person> people = new HashMap<>(100);
	private int personID, weight, height, dateOfBirth, dailyCalorieNeed, caloriesTaken = 0, caloriesBurned = 0, result = 0;
	private String name;
	private char gender;

	
	// Person constructor to initialize the variables of object Person.
	public Person(String[] dataArray) {
		
		personID = Integer.parseInt(dataArray[0]);
		name = dataArray[1];
		gender = dataArray[2].charAt(0);
		weight = Integer.parseInt(dataArray[3]);
		height = Integer.parseInt(dataArray[4]);
		dateOfBirth = Integer.parseInt(dataArray[5]);
		calculateDailyCalorieNeed(gender, 2022 - dateOfBirth, height, weight);
	}
	
	
	// Read people.txt file and add (personID, Person object) pair to HashMap.
	public static void readPeopleFile(String file) {
		try {
			File peopleFile = new File(file);
			Scanner reader = new Scanner(peopleFile);
			
			while (reader.hasNext()) {
				String line = reader.nextLine();
				String[] dataArray = line.split("\t");
				
				people.put(Integer.parseInt(dataArray[0]), new Person(dataArray));
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	// Calculates daily calorie needs for people.
	public void calculateDailyCalorieNeed(char gender, int age, int height, int weight) {
		switch (gender) {
		case 'm':
			dailyCalorieNeed = (int) Math.round(66 + (13.75 * weight) + (5 * height) - (6.8 * age));
			break;
		case 'f':
			dailyCalorieNeed = (int) Math.round(665 + (9.6 * weight) + (1.7 * height) - (4.7 * age));
			break;
		}
	}
	
	
	// Calculates the result value for people.
	public String calculateResult(int caloriesTaken, int caloriesBurned) {
		result = (caloriesTaken - caloriesBurned) - dailyCalorieNeed;
		if (result < 0)
			return "" + result;
		else if (result == 0)
			return "0";
		else
			return "+" + result;
	}
	
	
	// Return result.
	public int getResult() {
		return result;
	}

	// Get people HashMap.
	public static HashMap<Integer, Person> getPeople() {
		return people;
	}
	
	// Return caloriesTaken.
	public int getCaloriesTaken() {
		return caloriesTaken;
	}

	// Return caloriesBurned.
	public int getCaloriesBurned() {
		return caloriesBurned;
	}

	// Add new caloriesTaken value to existing caloriesTaken value of object.
	public void addCaloriesTaken(int caloriesTaken) {
		this.caloriesTaken += caloriesTaken;
	}

	// Add new caloriesBurned value to existing caloriesBurned value of object.
	public void addCaloriesBurned(int caloriesBurned) {
		this.caloriesBurned += caloriesBurned;
	}
	
	// Return dateOfBirth.
	public int getDateOfBirth() {
		return dateOfBirth;
	}

	// Return dailyCalorieNeed.
	public int getDailyCalorieNeed() {
		return dailyCalorieNeed;
	}

	// Return name.
	public String getName() {
		return name;
	}
}
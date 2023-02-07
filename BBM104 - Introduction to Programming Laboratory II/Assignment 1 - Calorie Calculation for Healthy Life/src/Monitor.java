import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// Monitor class is used to read the command.txt and write the necessary output to monitoring.txt.
public class Monitor {
	private static ArrayList<String[]> commands = new ArrayList<>();
	private static ArrayList<Integer> peopleList = new ArrayList<>();
	
	
	// Reads command.txt and adds the command lines to ArrayList commands.
	public static void readCommandFile(String file) {
		try {
			File commandFile = new File(file);
			Scanner reader = new Scanner(commandFile);
			
			while (reader.hasNext()) {
				String line = reader.nextLine();
				commands.add(line.split("\t"));
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	// Takes ArrayList commands as input and writes output according to 5 conditions.
	public static void writeMonitoringFile(ArrayList<String[]> commands) {
		try {
			File monitoringFile = new File("monitoring.txt");
			FileWriter writer = new FileWriter(monitoringFile);
			
			int currentIndex = 0;
			for (String[] command : commands) {
				
				// Code block for "printList" command. Gives information about people so far.
				if (command[0].equals("printList")) {
					if (currentIndex == commands.size() - 1) {
						int index = 0;
						for (Integer personID : peopleList) {
							Person person = Person.getPeople().get(personID);
							person.calculateResult(person.getCaloriesTaken(), person.getCaloriesBurned());
							if (index == peopleList.size() - 1)
								writer.write(person.getName() + "\t" + (2022 - person.getDateOfBirth()) + "\t" + person.getDailyCalorieNeed() + "kcal\t" + person.getCaloriesTaken() + "kcal\t" + person.getCaloriesBurned() + "kcal\t" + person.calculateResult(person.getCaloriesTaken(), person.getCaloriesBurned()) + "kcal");
							else
								writer.write(person.getName() + "\t" + (2022 - person.getDateOfBirth()) + "\t" + person.getDailyCalorieNeed() + "kcal\t" + person.getCaloriesTaken() + "kcal\t" + person.getCaloriesBurned() + "kcal\t" + person.calculateResult(person.getCaloriesTaken(), person.getCaloriesBurned()) + "kcal\n");
							++index;
						}
					}
					else {
						for (Integer personID : peopleList) {
							Person person = Person.getPeople().get(personID);
							person.calculateResult(person.getCaloriesTaken(), person.getCaloriesBurned());
							writer.write(person.getName() + "\t" + (2022 - person.getDateOfBirth()) + "\t" + person.getDailyCalorieNeed() + "kcal\t" + person.getCaloriesTaken() + "kcal\t" + person.getCaloriesBurned() + "kcal\t" + person.calculateResult(person.getCaloriesTaken(), person.getCaloriesBurned()) + "kcal\n");
						}	
					}
				}
				
				// Code block for "printWarn" command. Warns people who exceeded daily calorie need.
				else if (command[0].equals("printWarn")) {
					ArrayList<Boolean> positiveResults = new ArrayList<Boolean>(peopleList.size());
					for (Integer personID : peopleList) {
						positiveResults.add((Person.getPeople().get(personID).getResult() >= 0) ? true : false);
					}
					if (positiveResults.contains(true)) {
						if (currentIndex == commands.size() - 1) {
							int index = 0;
							for (Integer personID : peopleList) {
								Person person = Person.getPeople().get(personID);
								if (person.getResult() > 0) {
									if (index == Collections.frequency(positiveResults, true) - 1)
										writer.write(person.getName() + "\t" + (2022 - person.getDateOfBirth()) + "\t" + person.getDailyCalorieNeed() + "kcal\t" + person.getCaloriesTaken() + "kcal\t" + person.getCaloriesBurned() + "kcal\t" + person.calculateResult(person.getCaloriesTaken(), person.getCaloriesBurned()) + "kcal");
									else
										writer.write(person.getName() + "\t" + (2022 - person.getDateOfBirth()) + "\t" + person.getDailyCalorieNeed() + "kcal\t" + person.getCaloriesTaken() + "kcal\t" + person.getCaloriesBurned() + "kcal\t" + person.calculateResult(person.getCaloriesTaken(), person.getCaloriesBurned()) + "kcal\n");
									++index;
								}
							}
						}
						else {
							for (Integer personID : peopleList) {
								Person person = Person.getPeople().get(personID);
								if (person.getResult() > 0) {
									writer.write(person.getName() + "\t" + (2022 - person.getDateOfBirth()) + "\t" + person.getDailyCalorieNeed() + "kcal\t" + person.getCaloriesTaken() + "kcal\t" + person.getCaloriesBurned() + "kcal\t" + person.calculateResult(person.getCaloriesTaken(), person.getCaloriesBurned()) + "kcal\n");
								}
							}
						}
					}
					else {
						if (currentIndex == commands.size() - 1)
							writer.write("there\tis\tno\tsuch\tperson");
						else
							writer.write("there\tis\tno\tsuch\tperson\n");
					}
				}
				
				// Code block for "print(personID)" command. Gives information about specific person.
				else if (command[0].contains("print(")) {
					Integer personID = Integer.parseInt(command[0].substring(6, 11));
					Person person = Person.getPeople().get(personID);
					if (currentIndex == commands.size() - 1)
						writer.write(person.getName() + "\t" + (2022 - person.getDateOfBirth()) + "\t" + person.getDailyCalorieNeed() + "kcal\t" + person.getCaloriesTaken() + "kcal\t" + person.getCaloriesBurned() + "kcal\t" + person.calculateResult(person.getCaloriesTaken(), person.getCaloriesBurned()) + "kcal");
					else
						writer.write(person.getName() + "\t" + (2022 - person.getDateOfBirth()) + "\t" + person.getDailyCalorieNeed() + "kcal\t" + person.getCaloriesTaken() + "kcal\t" + person.getCaloriesBurned() + "kcal\t" + person.calculateResult(person.getCaloriesTaken(), person.getCaloriesBurned()) + "kcal\n");
				}
				
				// Code block for foods taken. Saves data of calorie taken.
				else if (command[1].charAt(0) == '1') {
					if (currentIndex == commands.size() - 1)
						writer.write(command[0] + "\thas\ttaken\t" + Food.getFoods().get(Integer.parseInt(command[1])).getCalorieCount() * Integer.parseInt(command[2]) + "kcal\tfrom\t" + Food.getFoods().get(Integer.parseInt(command[1])).getNameOfFood());
					else
						writer.write(command[0] + "\thas\ttaken\t" + Food.getFoods().get(Integer.parseInt(command[1])).getCalorieCount() * Integer.parseInt(command[2]) + "kcal\tfrom\t" + Food.getFoods().get(Integer.parseInt(command[1])).getNameOfFood() + "\n");
					Person.getPeople().get(Integer.parseInt(command[0])).addCaloriesTaken(Food.getFoods().get(Integer.parseInt(command[1])).getCalorieCount() * Integer.parseInt(command[2]));
					if (!(peopleList.contains(Integer.parseInt(command[0])))) {
						peopleList.add(Integer.parseInt(command[0]));
					}
					Person.getPeople().get(Integer.parseInt(command[0])).calculateResult(Person.getPeople().get(Integer.parseInt(command[0])).getCaloriesTaken(), 0);
				}
				
				// Code block for sports done. Saves data of calorie burned.
				else if (command[1].charAt(0) == '2') {
					if (currentIndex == commands.size() - 1)
						writer.write(command[0] + "\thas\tburned\t" + Sport.getSports().get(Integer.parseInt(command[1])).getCalorieBurned() * Integer.parseInt(command[2]) / 60 + "kcal\tthanks to\t" + Sport.getSports().get(Integer.parseInt(command[1])).getNameOfSport());
					else
						writer.write(command[0] + "\thas\tburned\t" + Sport.getSports().get(Integer.parseInt(command[1])).getCalorieBurned() * Integer.parseInt(command[2]) / 60 + "kcal\tthanks to\t" + Sport.getSports().get(Integer.parseInt(command[1])).getNameOfSport() + "\n");
					Person.getPeople().get(Integer.parseInt(command[0])).addCaloriesBurned(Sport.getSports().get(Integer.parseInt(command[1])).getCalorieBurned() * Integer.parseInt(command[2]) / 60);
					if (!(peopleList.contains(Integer.parseInt(command[0])))) {
						peopleList.add(Integer.parseInt(command[0]));
					}
					Person.getPeople().get(Integer.parseInt(command[0])).calculateResult(0, Person.getPeople().get(Integer.parseInt(command[0])).getCaloriesBurned());
				}
				
				++currentIndex;
				
				if (!(currentIndex == commands.size()))
					writer.write("***************\n");
			}
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// Returns commands ArrayList.
	public static ArrayList<String[]> getCommands() {
		return commands;
	}
}
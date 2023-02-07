public class Main {
	public static void main(String[] args) {
		
		// Each of them reads the fixed file and create HashMap to be used in Monitor class.
		Person.readPeopleFile("people.txt");
		Food.readFoodFile("food.txt");
		Sport.readSportFile("sport.txt");
		
		// Reads the command file and writes the output according to commands.
		Monitor.readCommandFile(args[0]);
		Monitor.writeMonitoringFile(Monitor.getCommands());
		
	}
}
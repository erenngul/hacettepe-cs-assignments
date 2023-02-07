import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

// Food class is used to read food.txt and create a (foodID, Food object) HashMap.
public class Food {
	private static HashMap<Integer, Food> foods = new HashMap<>(100);
	private int foodID, calorieCount;
	private String nameOfFood;
	
	
	// Food constructor to initialize the variables of object Food.
	public Food(String[] dataArray) {
		foodID = Integer.parseInt(dataArray[0]);
		nameOfFood = dataArray[1];
		calorieCount = Integer.parseInt(dataArray[2]);
	}
	
	
	// Read food.txt file and add (foodID, Food object) pair to HashMap.
	public static void readFoodFile(String file) {
		try {
			File foodFile = new File(file);
			Scanner reader = new Scanner(foodFile);
			
			while (reader.hasNext()) {
				String line = reader.nextLine();
				String[] dataArray = line.split("\t");
				
				foods.put(Integer.parseInt(dataArray[0]), new Food(dataArray));
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	// Return foods HashMap.
	public static HashMap<Integer, Food> getFoods() {
		return foods;
	}
	
	// Returns nameOfFood.
	public String getNameOfFood() {
		return nameOfFood;
	}
	
	// Returns calorieCount.
	public int getCalorieCount() {
		return calorieCount;
	}
}
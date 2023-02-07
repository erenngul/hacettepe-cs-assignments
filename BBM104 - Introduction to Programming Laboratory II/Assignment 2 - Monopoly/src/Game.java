import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
	private static ArrayList<String[]> commands = new ArrayList<String[]>();
	private static ArrayList<Integer> landIds = new ArrayList<Integer>(Arrays.asList(2, 4, 7, 9, 10, 12, 14, 15, 17, 19, 20, 22, 24, 25, 27, 28, 30, 32, 33, 35, 38, 40));
	private static ArrayList<Integer> railRoadIds = new ArrayList<Integer>(Arrays.asList(6, 16, 26, 36));
	private static ArrayList<Integer> companyIds = new ArrayList<Integer>(Arrays.asList(13, 29));
	private static ArrayList<Integer> communityChestIds = new ArrayList<Integer>(Arrays.asList(3, 18, 34));
	private static ArrayList<Integer> chanceIds = new ArrayList<Integer>(Arrays.asList(8, 23, 37));
	private static int maxRounds;
	private static int roundsPlayed;
	private static boolean gameEnd = false;
	
	// Reads the command file and creates a command ArrayList to iterate in Main
	public static void readCommandFile(String file) {
		try {
			File commandFile = new File(file);
			Scanner reader = new Scanner(commandFile);
			
			while (reader.hasNext()) {
				String line = reader.nextLine();
				commands.add(line.split(";"));	
			}
			maxRounds = commands.size();
			reader.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static Banker banker = new Banker();
	private static HashMap<String, Player> players = Player.createPlayers();
	
	// Player plays their turn with given dice
	public static void playTurns(String name, int dice) {
		
		Player player = players.get(name);
		
		// If player is not in jail, they can change position
		if (player.isInJail() == false && player.isInParking() == false)
			player.changePosition(dice);
		
		try {
			
			// Checks if player landed on one of the land properties
			if (landIds.contains(player.getPosition())) {
				Land land = Land.getLandProperties().get(player.getPosition());
				
				// Checks if land is owned or not and does action based on it
				if (land.isPropertyOwned() == false) {
					// If land is not owned and player has money, player buys it
					if (player.getMoney() >= land.getPropertyCost()) {
						player.setMoney(-(land.getPropertyCost()));
						player.getOwnedPropertyList().add(land);
						land.setPropertyOwner(player);
						land.setPropertyOwned(true);
						banker.setMoney(land.getPropertyCost());
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s bought %s\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name, land.getPropertyName()));
						// If player has 0 money, game ends
						if (player.getMoney() == 0)
							Game.setGameEnd(true);
					}
					// If player doesn't have enough money, they go bankrupt
					else {
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name));
						Game.setGameEnd(true);
					}
				}
				// If the land is owned by someone
				else {
					// Checks if player landed on another player's land and pays rent
					if (player != land.getPropertyOwner()) {
						// If player has enough money they pay rent
						if (player.getMoney() >= land.getPropertyRent()) {
							land.getPropertyOwner().setMoney(land.getPropertyRent());
							player.setMoney(-(land.getPropertyRent()));
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s paid rent for %s\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name, land.getPropertyName()));
							// If player has 0 money, game ends
							if (player.getMoney() == 0)
								Game.setGameEnd(true);
						}
						// If player doesn't have enough money for the rent, then they go bankrupt
						else {
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name));
							Game.setGameEnd(true);
						}
					}
					// If land belongs to the current player, nothing happens
					else {
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s has %s\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name, land.getPropertyName()));
					}
				}
			}
			
			// Checks if player landed on one of the railroad properties
			else if (railRoadIds.contains(player.getPosition())) {
				RailRoad railRoad = RailRoad.getRailRoadProperties().get(player.getPosition());
				
				// Checks if railroad is owned or not and does action based on it
				if (railRoad.isPropertyOwned() == false) {
					
					// If railroad is not owned and player has money, player buys it
					if (player.getMoney() >= railRoad.getPropertyCost()) {
						player.setMoney(-(railRoad.getPropertyCost()));
						player.getOwnedPropertyList().add(railRoad);
						railRoad.setPropertyOwner(player);
						player.addOwnedRailRoadNumber();
						railRoad.setPropertyOwned(true);
						banker.setMoney(railRoad.getPropertyCost());
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s bought %s\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name, railRoad.getPropertyName()));
						// If player has 0 money, game ends
						if (player.getMoney() == 0)
							Game.setGameEnd(true);
					}
					// If player doesn't have enough money, they go bankrupt
					else {
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name));
						Game.setGameEnd(true);
					}
				}
				// If the railroad is owned by someone
				else {
					// Checks if player landed on another player's railroad and pays rent
					if (player != railRoad.getPropertyOwner()) {
						railRoad.setPropertyRent(25 * railRoad.getPropertyOwner().getOwnedRailRoadNumbers());
						// If player has enough money, then they pay rent
						if (player.getMoney() >= railRoad.getPropertyRent()) {
							railRoad.getPropertyOwner().setMoney(railRoad.getPropertyRent());
							player.setMoney(-(railRoad.getPropertyRent()));
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s paid rent for %s\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name, railRoad.getPropertyName()));
							// If player has 0 money, game ends
							if (player.getMoney() == 0)
								Game.setGameEnd(true);
						}
						// If player doesn't have enough money for the rent, then they go bankrupt
						else {
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name));
							Game.setGameEnd(true);
						}
					}
					// If railroad belongs to the current player, nothing happens
					else {
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s has %s\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name, railRoad.getPropertyName()));
					}
				}
				
			}

			// Checks if player landed on one of the company properties
			else if (companyIds.contains(player.getPosition())) {
				Company company = Company.getCompanyProperties().get(player.getPosition());
				
				// Checks if company is owned or not and does action based on it
				if (company.isPropertyOwned() == false) {
					
					// If company is not owned and player has money, player buys it
					if (player.getMoney() >= company.getPropertyCost()) {
						player.setMoney(-(company.getPropertyCost()));
						player.getOwnedPropertyList().add(company);
						company.setPropertyOwner(player);
						company.setPropertyOwned(true);
						banker.setMoney(company.getPropertyCost());
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s bought %s\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name, company.getPropertyName()));
						// If player has 0 money, game ends
						if (player.getMoney() == 0)
							Game.setGameEnd(true);
					}
					// If player doesn't have enough money, they go bankrupt
					else {
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name));
						Game.setGameEnd(true);
					}
				}
				// If the company is owned by someone
				else {
					// Checks if player landed on another player's company and pays rent
					if (player != company.getPropertyOwner()) {
						company.setPropertyRent(4 * dice);
						// If player has enough money, then they pay rent
						if (player.getMoney() >= company.getPropertyRent()) {
							company.getPropertyOwner().setMoney(company.getPropertyRent());
							player.setMoney(-(company.getPropertyRent()));
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s paid rent for %s\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name, company.getPropertyName()));
							// If player has 0 money, game ends
							if (player.getMoney() == 0)
								Game.setGameEnd(true);
						}
						// If player doesn't have enough money for the rent, then they go bankrupt
						else {
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name));
							Game.setGameEnd(true);
						}
					}
					// If company belongs to the current player, nothing happens
					else {
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s has %s\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name, company.getPropertyName()));
					}
				}
				
			}
			
			// Checks if player landed on one of the community chest squares
			else if (communityChestIds.contains(player.getPosition())) {
				CommunityChest.drawCommunityChestCard(name, dice, player, banker);
			}

			// Checks if player landed on one of the chance squares 
			else if (chanceIds.contains(player.getPosition())) {
				Chance.drawChanceCard(name, dice, player, banker);
			}
			
			// Checks if player landed on GO square
			else if (player.getPosition() == 1) {
				Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s is in GO square\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name));
			}
			
			// Checks if player landed on one of the tax squares
			else if (player.getPosition() == 5 || player.getPosition() == 39) {
				// If player has more than 100 TL, they pay tax
				if (player.getMoney() >= 100) {
					player.setMoney(-100);
					banker.setMoney(100);
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s paid Tax\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name));
					// If player has 0 money, game ends
					if (player.getMoney() == 0)
						Game.setGameEnd(true);
				}
				// If player doesn't have enough money to pay tax, they go bankrupt
				else {
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name));
					Game.setGameEnd(true);
				}
			}
			
			// Checks if player landed on go to jail square
			else if (player.getPosition() == 31) {
				// Player goes to jail and waits for 3 rounds
				player.setPosition(11);
				player.setInJail(true);
				Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s went to jail\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name));
			}
			
			// Checks if player landed on jail square
			else if (player.getPosition() == 11) {
				// If player is in jail, increase jail count
				if (player.isInJail() == true) {
					player.addJailCount(1);
					if (player.getJailCount() == 3) {
						player.setInJail(false);
					}
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s in jail (count=%d)\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name, player.getJailCount()));
				}
				// If player is not in jail, go to jail
				else {
					player.setInJail(true);
					player.setJailCount(0);
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s went to jail\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name));
				}
			}
			
			// Checks if player landed on free parking square
			else if (player.getPosition() == 21) {
				// Player waits for 1 more round and will start to move next round
				if (player.isInParking() == true) {
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s is in Free Parking (count=1)\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name));
					player.setInParking(false);
				}
				// Player waits for 1 round
				else {
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s is in Free Parking\n", name, dice, player.getPosition(), (int) players.get("Player 1").getMoney(), (int) players.get("Player 2").getMoney(), name));
					player.setInParking(true);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		++roundsPlayed;
	}
	
	// Shows the current situation of the game
	public static void show() {
		
		// Makes the lists of owned property list to print in output for both players
		ArrayList<Property> player1Properties = players.get("Player 1").getOwnedPropertyList();
		String player1PropertyList = "";
		int index1 = 0;
		for (Property property : player1Properties) {
			if (index1 == player1Properties.size() - 1)
				player1PropertyList += property.getPropertyName();
			else
				player1PropertyList += property.getPropertyName() + ",";
			++index1;
		}
		
		ArrayList<Property> player2Properties = players.get("Player 2").getOwnedPropertyList();
		String player2PropertyList = "";
		int index2 = 0;
		for (Property property : player2Properties) {
			if (index2 == player2Properties.size() - 1)
				player2PropertyList += property.getPropertyName();
			else
				player2PropertyList += property.getPropertyName() + ",";
			++index2;
		}
		
		try {			
			
			Main.writer.write("-----------------------------------------------------------------------------------------------------------\n");
			Main.writer.write(String.format("Player 1\t%d\thave: %s\n", (int) players.get("Player 1").getMoney(), player1PropertyList));
			Main.writer.write(String.format("Player 2\t%d\thave: %s\n", (int) players.get("Player 2").getMoney(), player2PropertyList));
			Main.writer.write(String.format("Banker\t%d\n", (int) banker.getMoney()));
			
			if (players.get("Player 1").getMoney() > players.get("Player 2").getMoney())
				Main.writer.write("Winner Player 1\n");
			else if (players.get("Player 2").getMoney() > players.get("Player 1").getMoney())
				Main.writer.write("Winner Player 2\n");

			// This is just to make sure that there isn't a newline at the end of output
			if (roundsPlayed == maxRounds || Game.isGameEnd() == true)
				Main.writer.write("-----------------------------------------------------------------------------------------------------------");
			else
				Main.writer.write("-----------------------------------------------------------------------------------------------------------\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		++roundsPlayed;
	}

	public static ArrayList<String[]> getCommands() {
		return commands;
	}
	
	public static Banker getBanker() {
		return Game.banker;
	}

	public static boolean isGameEnd() {
		return gameEnd;
	}

	public static void setGameEnd(boolean gameEnd) {
		Game.gameEnd = gameEnd;
	}

	public static HashMap<String, Player> getPlayers() {
		return players;
	}
	
}
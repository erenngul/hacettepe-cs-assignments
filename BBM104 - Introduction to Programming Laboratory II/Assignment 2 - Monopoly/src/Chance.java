import java.io.IOException;

public class Chance extends Card {
	private static int chanceIndex = 0;
	
	public static void drawChanceCard(String name, int dice, Player player, Banker banker) {
		try {
			switch (chanceIndex) {
			
			// Advance to Go (Collect $200)
			case 0:
				player.setMoney(200);
				banker.setMoney(-200);
				player.setPosition(1);
				Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(0)));
				break;
			
			// Advance to Leicester Square
			case 1:
				if (player.getPosition() > 27) {
					player.setMoney(200);
					banker.setMoney(-200);
				}
				player.setPosition(27);
				
				// When the player reaches Leicester Square, game will act as if player landed on a land property
				Land land = Land.getLandProperties().get(player.getPosition());
				if (land.isPropertyOwned() == false) {
					if (player.getMoney() >= land.getPropertyCost()) {
						player.setMoney(-(land.getPropertyCost()));
						player.getOwnedPropertyList().add(land);
						land.setPropertyOwner(player);
						land.setPropertyOwned(true);
						banker.setMoney(land.getPropertyCost());
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s bought %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(1), name, land.getPropertyName()));
						// If player has 0 money, game ends
						if (player.getMoney() == 0)
							Game.setGameEnd(true);
					}
					else {
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(1), name));
						Game.setGameEnd(true);
					}
				}
				else {
					if (player != land.getPropertyOwner()) {
						if (player.getMoney() >= land.getPropertyRent()) {
							land.getPropertyOwner().setMoney(land.getPropertyRent());
							player.setMoney(-(land.getPropertyRent()));
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s paid rent for %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(1), name, land.getPropertyName()));
							// If player has 0 money, game ends
							if (player.getMoney() == 0)
								Game.setGameEnd(true);
						}
						else {
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(1), name));
							Game.setGameEnd(true);
						}
					}
					else {
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s has %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(1), name, land.getPropertyName()));
					}
				}
				break;
			
			// Go back 3 spaces
			case 2:
				player.changePosition(-3);
				
				if (player.getPosition() == 20) {
					// Game will act as if player landed on a land property
					Land land1 = Land.getLandProperties().get(player.getPosition());
					if (land1.isPropertyOwned() == false) {
						if (player.getMoney() >= land1.getPropertyCost()) {
							player.setMoney(-(land1.getPropertyCost()));
							player.getOwnedPropertyList().add(land1);
							land1.setPropertyOwner(player);
							land1.setPropertyOwned(true);
							banker.setMoney(land1.getPropertyCost());
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s bought %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, land1.getPropertyName()));
							// If player has 0 money, game ends
							if (player.getMoney() == 0)
								Game.setGameEnd(true);
						}
						else {
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name));
							Game.setGameEnd(true);
						}
					}
					else {
						if (player != land1.getPropertyOwner()) {
							if (player.getMoney() >= land1.getPropertyRent()) {
								land1.getPropertyOwner().setMoney(land1.getPropertyRent());
								player.setMoney(-(land1.getPropertyRent()));
								Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s paid rent for %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, land1.getPropertyName()));
								// If player has 0 money, game ends
								if (player.getMoney() == 0)
									Game.setGameEnd(true);
							}
							else {
								Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name));
								Game.setGameEnd(true);
							}
						}
						else {
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s has %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, land1.getPropertyName()));
						}
					}
				}
				
				// Game will act as if player landed on a tax square
				else if (player.getPosition() == 5) {
					if (player.getMoney() >= 100) {
						player.setMoney(-100);
						banker.setMoney(100);
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s paid Tax\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name));
						// If player has 0 money, game ends
						if (player.getMoney() == 0)
							Game.setGameEnd(true);
					}
					else {
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name));
						Game.setGameEnd(true);
					}
				}
				
				// Game will act as if player landed on a community chest square
				else if (player.getPosition() == 34) {
					switch (CommunityChest.getCommunityChestIndex()) {
					
					// Advance to Go (Collect $200)
					case 0:
						player.setMoney(200);
						banker.setMoney(-200);
						player.setPosition(1);
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, getCards().getCommunityChestArrayList().get(0)));
						break;
						
					// Bank error in your favor - collect $75
					case 1:
						player.setMoney(75);
						banker.setMoney(-75);
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, getCards().getCommunityChestArrayList().get(1)));
						break;
					
					// Doctor's fees - Pay $50
					case 2:
						if (player.getMoney() >= 50) {
							player.setMoney(-50);
							banker.setMoney(50);
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, getCards().getCommunityChestArrayList().get(2)));
							// If player has 0 money, game ends
							if (player.getMoney() == 0)
								Game.setGameEnd(true);
						}
						else {
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name));
							Game.setGameEnd(true);
						}
						break;
					
					// It is your birthday Collect $10 from each player
					case 3:
						player.setMoney(10);
						if (player.getName().equals("Player 1")) {
							if (getPlayers().get("Player 2").getMoney() >= 10) {
								getPlayers().get("Player 2").setMoney(-10);
								Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, getCards().getCommunityChestArrayList().get(3)));
								// If player 2 has 0 money, game ends
								if (getPlayers().get("Player 2").getMoney() == 0)
									Game.setGameEnd(true);
							}
							else {
								Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), "Player 2"));
								Game.setGameEnd(true);
							}
						}
						else {
							if (getPlayers().get("Player 1").getMoney() >= 10) {
								getPlayers().get("Player 1").setMoney(-10);
								Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, getCards().getCommunityChestArrayList().get(3)));
								// If player 1 has 0 money, game ends
								if (getPlayers().get("Player 1").getMoney() == 0)
									Game.setGameEnd(true);
							}
							else {
								Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), "Player 1"));
								Game.setGameEnd(true);
							}
						}
						break;
					
					// Grand Opera Night - collect $50 from every player for opening night seats
					case 4:
						player.setMoney(50);
						// If the current player is Player 1, take money from Player 2
						if (player.getName().equals("Player 1")) {
							if (getPlayers().get("Player 2").getMoney() >= 50) {
								getPlayers().get("Player 2").setMoney(-50);
								Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, getCards().getCommunityChestArrayList().get(4)));
								// If player 2 has 0 money, game ends
								if (getPlayers().get("Player 2").getMoney() == 0)
									Game.setGameEnd(true);
							}
							else {
								Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), "Player 2"));
								Game.setGameEnd(true);
							}
						}
						// If the current player is Player 2, take money from Player 1
						else {
							if (getPlayers().get("Player 1").getMoney() >= 50) {
								getPlayers().get("Player 1").setMoney(-50);
								Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, getCards().getCommunityChestArrayList().get(4)));
								// If player 1 has 0 money, game ends
								if (getPlayers().get("Player 1").getMoney() == 0)
									Game.setGameEnd(true);
							}
							else {
								Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), "Player 1"));
								Game.setGameEnd(true);
							}
						}
						break;
					
					// Income Tax refund - collect $20
					case 5:
						player.setMoney(20);
						banker.setMoney(-20);
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, getCards().getCommunityChestArrayList().get(5)));
						break;
					
					// Life Insurance Matures - collect $100
					case 6:
						player.setMoney(100);
						banker.setMoney(-100);
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, getCards().getCommunityChestArrayList().get(6)));
						break;
					
					// Pay Hospital Fees of $100
					case 7:
						if (player.getMoney() >= 100) {
							player.setMoney(-100);
							banker.setMoney(100);
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, getCards().getCommunityChestArrayList().get(7)));
							// If player has 0 money, game ends
							if (player.getMoney() == 0)
								Game.setGameEnd(true);
						}
						else {
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name));
							Game.setGameEnd(true);
						}
						break;
					
					// Pay School Fees of $50
					case 8:
						if (player.getMoney() >= 50) {
							player.setMoney(-50);
							banker.setMoney(50);
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, getCards().getCommunityChestArrayList().get(8)));
							// If player has 0 money, game ends
							if (player.getMoney() == 0)
								Game.setGameEnd(true);
						}
						else {
							Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name));
							Game.setGameEnd(true);
						}
						break;
					
					// You inherit $100
					case 9:
						player.setMoney(100);
						banker.setMoney(-100);
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, getCards().getCommunityChestArrayList().get(9)));
						break;
					
					// From sale of stock you get $50
					case 10:
						player.setMoney(50);
						banker.setMoney(-50);
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s %s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(2), name, getCards().getCommunityChestArrayList().get(10)));
						break;
					}
					// Each time a community chest card is drawn, the next card is ready
					CommunityChest.addCommunityChestIndex();
				}
				break;

			// Pay poor tax of $15
			case 3:
				if (player.getMoney() >= 15) {
					player.setMoney(-15);
					banker.setMoney(15);
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(3)));
					// If player has 0 money, game ends
					if (player.getMoney() == 0)
						Game.setGameEnd(true);
				}
				else {
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name));
					Game.setGameEnd(true);
				}
				break;
			
			// Your building loan matures - collect $150
			case 4:
				player.setMoney(150);
				banker.setMoney(-150);
				Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(4)));
				break;
			
			// You have won a crossword competition - collect $100
			case 5:
				player.setMoney(100);
				banker.setMoney(-100);
				Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getChanceArrayList().get(5)));
				break;
			}
			// Each time a chance card is drawn, the next card is ready
			++chanceIndex;
			
			// When all cards are drawn, it is reset to the first card
			if (chanceIndex == 6)
				chanceIndex = 0;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
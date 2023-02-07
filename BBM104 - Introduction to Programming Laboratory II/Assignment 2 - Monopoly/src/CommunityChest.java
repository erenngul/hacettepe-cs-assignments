import java.io.IOException;

public class CommunityChest extends Card {
	private static int communityChestIndex = 0;
	
	public static void drawCommunityChestCard(String name, int dice, Player player, Banker banker) {
		try {
			switch (communityChestIndex) {
			
			// Advance to Go (Collect $200)
			case 0:
				player.setMoney(200);
				banker.setMoney(-200);
				player.setPosition(1);
				Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getCommunityChestArrayList().get(0)));
				break;
				
			// Bank error in your favor - collect $75
			case 1:
				player.setMoney(75);
				banker.setMoney(-75);
				Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getCommunityChestArrayList().get(1)));
				break;
			
			// Doctor's fees - Pay $50
			case 2:
				if (player.getMoney() >= 50) {
					player.setMoney(-50);
					banker.setMoney(50);
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getCommunityChestArrayList().get(2)));
					// If player has 0 money, game ends
					if (player.getMoney() == 0)
						Game.setGameEnd(true);
				}
				else {
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name));
					Game.setGameEnd(true);
				}
				break;
			
			// It is your birthday Collect $10 from each player
			case 3:
				player.setMoney(10);
				if (player.getName().equals("Player 1")) {
					if (getPlayers().get("Player 2").getMoney() >= 10) {
						getPlayers().get("Player 2").setMoney(-10);
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getCommunityChestArrayList().get(3)));
						// If player 2 has 0 money, game ends
						if (getPlayers().get("Player 2").getMoney() == 0)
							Game.setGameEnd(true);
					}
					else {
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), "Player 2"));
						Game.setGameEnd(true);
					}
				}
				else {
					if (getPlayers().get("Player 1").getMoney() >= 10) {
						getPlayers().get("Player 1").setMoney(-10);
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getCommunityChestArrayList().get(3)));
						// If player 1 has 0 money, game ends
						if (getPlayers().get("Player 1").getMoney() == 0)
							Game.setGameEnd(true);
					}
					else {
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), "Player 1"));
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
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getCommunityChestArrayList().get(4)));
						// If player 2 has 0 money, game ends
						if (getPlayers().get("Player 2").getMoney() == 0)
							Game.setGameEnd(true);
					}
					else {
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), "Player 2"));
						Game.setGameEnd(true);
					}
				}
				// If the current player is Player 2, take money from Player 1
				else {
					if (getPlayers().get("Player 1").getMoney() >= 50) {
						getPlayers().get("Player 1").setMoney(-50);
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getCommunityChestArrayList().get(4)));
						// If player 1 has 0 money, game ends
						if (getPlayers().get("Player 1").getMoney() == 0)
							Game.setGameEnd(true);
					}
					else {
						Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), "Player 1"));
						Game.setGameEnd(true);
					}
				}
				break;
			
			// Income Tax refund - collect $20
			case 5:
				player.setMoney(20);
				banker.setMoney(-20);
				Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getCommunityChestArrayList().get(5)));
				break;
			
			// Life Insurance Matures - collect $100
			case 6:
				player.setMoney(100);
				banker.setMoney(-100);
				Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getCommunityChestArrayList().get(6)));
				break;
			
			// Pay Hospital Fees of $100
			case 7:
				if (player.getMoney() >= 100) {
					player.setMoney(-100);
					banker.setMoney(100);
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getCommunityChestArrayList().get(7)));
					// If player has 0 money, game ends
					if (player.getMoney() == 0)
						Game.setGameEnd(true);
				}
				else {
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name));
					Game.setGameEnd(true);
				}
				break;
			
			// Pay School Fees of $50
			case 8:
				if (player.getMoney() >= 50) {
					player.setMoney(-50);
					banker.setMoney(50);
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getCommunityChestArrayList().get(8)));
					// If player has 0 money, game ends
					if (player.getMoney() == 0)
						Game.setGameEnd(true);
				}
				else {
					Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s goes bankrupt\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name));
					Game.setGameEnd(true);
				}
				break;
			
			// You inherit $100
			case 9:
				player.setMoney(100);
				banker.setMoney(-100);
				Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getCommunityChestArrayList().get(9)));
				break;
			
			// From sale of stock you get $50
			case 10:
				player.setMoney(50);
				banker.setMoney(-50);
				Main.writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%s draw %s\n", name, dice, player.getPosition(), (int) getPlayers().get("Player 1").getMoney(), (int) getPlayers().get("Player 2").getMoney(), name, getCards().getCommunityChestArrayList().get(10)));
				break;
			}
			// Each time a community chest card is drawn, the next card is ready
			++communityChestIndex;
			
			// When all cards are drawn, it is reset to the first card
			if (communityChestIndex == 11)
				communityChestIndex = 0;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addCommunityChestIndex() {
		++communityChestIndex;
		if (communityChestIndex == 11)
			communityChestIndex = 0;
	}
	
	public static int getCommunityChestIndex() {
		return communityChestIndex;
	}
}
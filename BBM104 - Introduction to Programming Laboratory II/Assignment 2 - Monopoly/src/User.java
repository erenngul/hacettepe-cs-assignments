abstract public class User {
	
	private String name;
	private double money;
	
	public User(String name, double money) {
		this.name = name;
		this.money = money;
	}
	
	public double getMoney() {
		return money;
	}
	
	// Takes or gives money
	public void setMoney(double money) {
		this.money += money;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
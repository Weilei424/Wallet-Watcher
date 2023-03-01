package persistence;

public class Misc extends LedgerItem {
	public Misc(String name, double amount) {
		super(null, amount, name, null);
		// TODO Auto-generated constructor stub
	}
	
	public Misc(String date, double amount,String item) {
		super(date,amount,item,null);
	}
	@Override
	public String toString() {
		String message="Your "+this.itemName+"have amount of "+this.amount;
		return message;
	}
	
	public void setUp(String Name,double amount) {
		this.itemName=Name;
		this.amount=amount;
	}
	
}

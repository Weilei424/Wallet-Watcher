package main.java.persistence;

public class Gic extends Investment {
	private double rate;
	private String endDate;

	protected Gic(String date, double amount, String itemName, double rate,String endDate) {
		super(date, amount, itemName, null,rate);
		this.rate=rate;
		this.endDate=endDate;
		
	}
	
	
	
	public String dateRemaining() {
		String message = "";
		
		
		return message;	}
	
	public double calInterst() {
		return this.amount*rate;
	}
	

	public void cashout() {
		
	}
}

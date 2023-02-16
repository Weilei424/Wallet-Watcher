package main.java.persistence;

import java.util.List;

public class Stock_Fund extends Investment {
	
	private double current_amount;
	protected Stock_Fund(String date, double amount, String itemName, String note) {
		super(date, amount, itemName, note,0);
		this.current_amount=amount
		
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public String priceChange(String date,double amount) {
		String message="";
		this.current_amount=amount;
		message+="THe price change for the fund /stock is"+this.amount;
		
		return message;
	}




	@Override
	public String toString() {
		return "Stock_Fund bought" + date + ", amount=" + amount + ", itemName=" + itemName + "]";
	}
	
	
	
	

}

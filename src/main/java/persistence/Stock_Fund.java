package main.java.persistence;

import java.util.List;

public class Stock_Fund extends Investment {
	

	protected Stock_Fund(String date, double amount, String itemName, String note) {
		super(date, amount, itemName, note,0);
		
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public String priceChange(String date,double amount) {
		String message="";
		this.amount=amount;
		message+="THe price change for the fund /stock is"+this.amount;
		
		return message;
	}
	
	
	
	

}

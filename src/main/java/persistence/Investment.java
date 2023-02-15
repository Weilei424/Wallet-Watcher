package main.java.persistence;

import java.util.Date;

import main.java.persistence.Immediate_Income;

public abstract class  Investment extends LedgerItem{
	private double rate;

	protected Investment(String date, double amount, String itemName, String note,double rate) {
		
		//Create a expense object of the same amount and store it in database
		super(date, amount, itemName, note);
		this.rate=rate;
		
		
	}
	

	public void cashout(String date) {
		String message ="Sell"+this.getItemName();
		Immediate_Income income = new Immediate_Income(date,amount,message,null);
		//delete this investment from database
		//add this item to user ldgerItem
	}
	
}

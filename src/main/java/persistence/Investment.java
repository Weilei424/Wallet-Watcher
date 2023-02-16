package main.java.persistence;

import java.util.Date;

import main.java.persistence.Earning;
import main.java.persistence.EarningInputData;

public class  Investment extends LedgerItem{
	private double rate;
	private double interst;

	protected Investment(String date, double amount, String itemName, String note,double rate,ExpenseInputData data) {
		
		//Create a expense object of the same amount and store it in database
		super(date, amount, itemName, note);
		data.addExpense(date, amount, itemName, note);
		this.rate=rate;
		
		
	}
	

	public String cashout(String date,EarningInputData list) {
		String message ="withdraw"+this.getItemName()+"get"+this.getAmount();
		Earning earn = new Earning(date,amount,message,null);
		list.addEarning(earn);
		return message;
		//delete this investment from database
		//add this item to user ldgerItem
	}
	
}

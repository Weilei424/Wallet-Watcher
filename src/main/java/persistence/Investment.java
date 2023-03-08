package persistence;

import java.util.Date;

import persistence.Earning;
import persistence.EarningInputData;

public class  Investment extends LedgerItem{
	private double rate;
	private double interst;

	public Investment(String date, double amount, String itemName, String note,double rate,ExpenseInputData data) {
		
		//Create a expense object of the same amount and store it in database
		super(date, amount, itemName, note);
		data.addExpense(date, amount, itemName, note);
		this.rate=rate;
		
		
	}
	
	public double getRate() {
		return this.rate;
	}
	
	public double getInterest() {
		return this.interst;
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

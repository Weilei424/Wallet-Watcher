package main.java.persistence;

import java.util.List;
import main.java.persistence.*;
public class Stock_Fund extends Investment {
	
	
	
	private double current_amount;
	public Stock_Fund(String date, double amount, String itemName, String note,ExpenseInputData data) {
		super(date, amount, itemName, note,0,data);
		this.current_amount=amount;
		
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public String priceChange(double amount) {
		String message="";
		this.current_amount=amount;
		message+="THe price change for the fund /stock is"+this.amount;
		
		return message;
	}


	public double getCurrent() {
		return this.current_amount;
	}
	public double getDifference() {
		return current_amount-amount;
	}
	@Override
	public String cashout(String date,EarningInputData list) {
		String message ="Sell"+this.getItemName()+"and earn "+this.current_amount+"the different is"+getDifference();
		Earning earn = new Earning(date,current_amount,message,null);
		list.addEarning(earn);
		
		//delete this investment from database
		//add this item to user ldgerItem
		return message;
	}
	

	@Override
	public String toString() {
		return "Stock_Fund"+this.itemName+" bought on  " + date + ", amount=" + amount ;
	}
	
	
	
	

}

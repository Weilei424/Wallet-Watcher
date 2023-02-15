package main.java.persistence;

import java.util.List;

public class Stock_Fund extends Investment {
	protected List<Investpoint>list;

	protected Stock_Fund(String date, double amount, String itemName, String note) {
		super(date, amount, itemName, note);
		Investpoint first= new Investpoint(date,amount);
		list.add(first);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public String priceChange(String date,double amount) {
		String message="";
		double difference=amount-list.get(list.size()-1).getAmount();
		this.amount=amount;
		message+="THe price change for the fund /stock is"+this.amount+"The difference is "+difference;
		this.list.add(new Investpoint(date,amount));
		return message;
	}
	
	
	
	

}

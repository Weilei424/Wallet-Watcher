package main.java.persistence;

import java.util.Date;
import java.util.List;

public class Earning extends LedgerItem{
	
	
	public Earning(String date,double amount,String event,String note) {
		super(date,amount,event,note);
	}
	
	
	
	
	public static double getSumIncome(List<Earning>list) {
		double amount =0;
		for(Earning INCOME:list)
			amount+=INCOME.getAmount();
		return amount;
			
			
	}
	@Override
	public String toString() {
		String returns="";
		returns +=this.getAmount()+"earned on "+this.getDate().toString()+"by "+this.getItemName();
		return returns;
	}
	public double computeIncome(Date startDate,Date endDate,List<LedgerItem>list) {
		double amount =0;
		for(LedgerItem item:list)
		{
			if(!(item instanceof Earning))
			continue;
			if(item.getDate().compareTo(endDate)>0||item.getDate().compareTo(startDate)<0)
				continue;
			amount+=item.getAmount();
		}
		return amount;
	}
	
	
	
	
}

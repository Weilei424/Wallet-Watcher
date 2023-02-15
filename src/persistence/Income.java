package persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Income extends LedgerItem{
	
	
	public Income(String date,double amount,String event,String note) {
		super(date,amount,event,note);
	}
	
	
	
	
	public static double getSumIncome(List<Income>list) {
		double amount =0;
		for(Income INCOME:list)
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
			if(!(item instanceof Income))
			continue;
			if(item.getDate().compareTo(endDate)>0||item.getDate().compareTo(startDate)<0)
				continue;
			amount+=item.getAmount();
		}
		return amount;
	}
	
	
	
	
}

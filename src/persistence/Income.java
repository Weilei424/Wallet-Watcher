package persistence;

import java.util.Date;
import java.util.List;

public class Income {
	LedgerItem info;
	
	public Income(LedgerItem info) {
		this.info=info;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public Date getDate() {
		return this.info.getDate();
	}
	/**
	 * 
	 * @return
	 */
	public double getAmount() {
		return this.info.getAmount();
	}
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static double getSumIncome(List<Income>list) {
		double amount =0;
		for(Income INCOME:list)
			amount+=INCOME.getAmount();
		return amount;
			
			
	}
	@SuppressWarnings("deprecation")
	public static double getSumMOnth(int month,List<Income>list) {
		double amount =0;
		for (Income income:list)
			if(income.info.getDate().getMonth()==month)
				amount+=income.info.getAmount();
		return amount;
	}
	
	
	
	
}

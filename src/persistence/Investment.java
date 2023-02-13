package persistence;

import java.util.Date;

public abstract class  Investment extends LedgerItem{

	protected Investment(Date date, double amount, String itemName, String note) {
		super(date, amount, itemName, note);
		
		
	}
	

	public void cashout(Date date) {
		String message ="Sell"+this.getItemName();
		Immediate_Income income = (Immediate_Income) Immediate_Income.getInstanceOf(message);
		//delete this investment from database
		//add this item to user ldgerItem
	}
}

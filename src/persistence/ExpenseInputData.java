package persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseInputData {
	public List<LedgerItem> ledgerItems;
	public double cost; 
	
	public ExpenseInputData()
	{ 
		ledgerItems=new ArrayList<>();
		cost=0; 
		
	}
	public void addExpense(String date, double amount, String itemName, String note)
	{
		ledgerItems.add(new LedgerItem(date,amount, itemName, note));
		cost+=amount; 
	}
	public void addExpense(LedgerItem input)
	{
		ledgerItems.add(input);
		cost+=input.amount; 
	}
	public String generateReceipt()
	{ 
		String ret="";
		for(LedgerItem item: this.ledgerItems)
		{ 
			ret=ret.concat(item.toString());
		}
		return ret; 
	}
}


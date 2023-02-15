package main.java.persistence;



	
	import java.util.ArrayList;
	import java.util.List;

	public class IncomeInputData {
		public List<LedgerItem> incomes;
		public double income; 
		
		public IncomeInputData()
		{ 
			incomes=new ArrayList<>();
			income=0; 
			
		}
		public void addExpense(String date, double amount, String itemName, String note)
		{
			incomes.add(new LedgerItem(date,amount, itemName, note));
			income+=amount; 
		}
		public void addExpense(LedgerItem input)
		{
			incomes.add(input);
			income+=input.amount; 
		}
		public String generateReceipt()
		{ 
			String ret="";
			for(LedgerItem item: this.incomes)
			{ 
				ret=ret.concat(item.toString());
			}
			return ret; 
		}
	}


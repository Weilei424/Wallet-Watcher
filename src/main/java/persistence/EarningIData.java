package src.main.java.persistence;



	
	import java.util.ArrayList;
	import java.util.List;

	public class EarningIData {
		public List<LedgerItem> incomes;
		public double income; 
		
		public EarningIData()
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
		public String generateEarningrecord()
		{ 
			String ret="";
			for(LedgerItem item: this.incomes)
			{ 
				ret=ret.concat(item.toString());
			}
			return ret; 
		}
	}


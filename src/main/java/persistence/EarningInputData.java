package persistence;



	
	import java.util.ArrayList;
	import java.util.List;

	public class EarningInputData {
		public List<LedgerItem> incomes;
		public double income; 
		
		public EarningInputData()
		{ 
			incomes=new ArrayList<>();
			income=0; 
			
		}
		public void addEarning(String date, double amount, String itemName, String note)
		{
			incomes.add(new LedgerItem(date,amount, itemName, note));
			income+=amount; 
		}
		public void addEarning(LedgerItem input)
		{
			incomes.add(input);
			income+=input.amount; 
		}
		
		
		public double getIncome() {
			return income;
		}
		public void setIncome(double income) {
			this.income = income;
		}
		public List<LedgerItem> getIncomes() {
			return incomes;
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


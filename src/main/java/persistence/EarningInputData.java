package persistence;



	
	import java.util.ArrayList;
	import java.util.List;

	public class EarningInputData {
		public List<Earning> incomes;
		public double income; 
		
		public EarningInputData()
		{ 
			incomes=new ArrayList<>();
			income=0; 
			
		}
		public void addEarning(String date, double amount, String itemName, String note)
		{
			incomes.add(new Earning(date,amount, itemName, note));
			income+=amount; 
		}
		public void addEarning(Earning input)
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
		public List<Earning> getIncomes() {
			return incomes;
		}
		public String generateEarningrecord()
		{ 
			String ret="";
			for(Earning item: this.incomes)
			{ 
				ret=ret.concat(item.toString());
			}
			return ret; 
		}
		
		
	}


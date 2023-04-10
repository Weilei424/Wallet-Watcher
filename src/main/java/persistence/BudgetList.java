package persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class BudgetList {
	
	public List<BudgetData> budgets=new ArrayList<>();
	
	public BudgetList()
	{ 
	
	}
	public static List<BudgetData> getEntries(JTable table)
	{ 
	    List<BudgetData> objects = new ArrayList<>();

	    TableModel model = table.getModel();
	    int rowCount = model.getRowCount();

	    for (int i = 0; i < rowCount; i++) { 
	    	int ref=(Integer)model.getValueAt(i, 0);
	    	double amount=(Double)model.getValueAt(i,1);
	    	String start_date=model.getValueAt(i, 2).toString();
	    	String end_date=model.getValueAt(i, 3).toString();
	    	objects.add(new BudgetData(amount,start_date,end_date,ref));
	    }
		return objects;
	}
	
	public void sortByLocalDate()
	{ 
		int n = this.budgets.size();
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (this.budgets.get(j).endDate.compareTo(this.budgets.get(min_idx).endDate)<0)
                    min_idx = j;
 
            // Swap the found minimum element with the first
            // element
            BudgetData temp=this.budgets.get(min_idx);
            this.budgets.set(min_idx, this.budgets.get(i));
            this.budgets.set(i, temp); 
        }
	}
	public Map<String,Double> mapForEachBudget()
	{
		Map<String,Double> map=new LinkedHashMap<>();
		this.sortByLocalDate();
		
		for(int x=0; x<this.budgets.size();x++)
		{
			map.put(this.budgets.get(x).startDate+" - "+ this.budgets.get(x).endDate
						 , this.budgets.get(x).budget);
		}
		return map;	
	}
	public Map<String,Double> mapSurplus(LedgerList entries)
	{
		Map<String,Double> map=new LinkedHashMap<>();
		this.sortByLocalDate();
		double ret=0;
		for(int x=0; x< this.budgets.size(); x++)
		{ 
			ret+=this.budgets.get(x).budget;
			for(int y=0; y< entries.items.size(); y++)
			{
		        if(entries.items.get(y).date.compareTo(this.budgets.get(x).endDate) <= 0 && entries.items.get(y).date.compareTo(this.budgets.get(x).startDate) >= 0)
		        { 
		        	ret-=entries.items.get(y).amount;
		        }
			}
			map.put(this.budgets.get(x).startDate +" to "+ this.budgets.get(x).endDate, ret);
		}
		return map;
	}
	
	public Map<String,Double[]> mapExpensesAndEarningsWithBudget(LedgerList expenseList, LedgerList earningsList)
	{
		Map<String,Double[]> map=new LinkedHashMap<>();
		this.sortByLocalDate();
		double ret=0;
		for(int x=0; x< this.budgets.size(); x++)
		{ 
			Double []arr = new Double[3]; 
			arr[0]=this.budgets.get(x).budget;
			Double expenses=0.0;
			for(int y=0; y< expenseList.items.size(); y++)
			{
		        if(expenseList.items.get(y).date.compareTo(this.budgets.get(x).endDate) <= 0 && expenseList.items.get(y).date.compareTo(this.budgets.get(x).startDate) >= 0)
		        { 
		        	expenses+=expenseList.items.get(y).amount;
		        }

			}
	        Double earnings=0.0;
			for(int y=0; y< earningsList.items.size(); y++)
			{
		        if(earningsList.items.get(y).date.compareTo(this.budgets.get(x).endDate) <= 0 && earningsList.items.get(y).date.compareTo(this.budgets.get(x).startDate) >= 0)
		        { 
		        	earnings+=earningsList.items.get(y).amount;
		        }
			}
			arr[1]=expenses;
			arr[2]=earnings;
			map.put(this.budgets.get(x).startDate +" to "+ this.budgets.get(x).endDate, arr);
		}
		return map;
	}

	
}

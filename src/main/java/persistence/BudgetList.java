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

	
}

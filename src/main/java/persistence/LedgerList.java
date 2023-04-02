package persistence;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import businessLogic.Recurrence;

public class LedgerList {
	
	public List<LedgerItem> items=new ArrayList<>();
	public LedgerList()
	{ 
	}
	public static List<LedgerItem> getEntries(JTable table)
	{ 
		
	    List<LedgerItem> objects = new ArrayList<>();

	    TableModel model = table.getModel();
	    int rowCount = model.getRowCount();

	    for (int i = 0; i < rowCount; i++) { 
	    	String date=(String)model.getValueAt(i, 6);
	    	double amount=(Double)model.getValueAt(i,3);
	    	String itemname=(String)model.getValueAt(i,1);	
	    	String note=(String)model.getValueAt(i,2);
	    	String category=(String)model.getValueAt(i, 5);
	    	objects.add(new LedgerItem(date.toString(),amount,itemname,note,category));

	    }
		return objects;
	}
	
	public static void sortByLocalDate(LedgerList item)
	{ 
		int n = item.items.size();
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (item.items.get(j).date.compareTo(item.items.get(min_idx).date)<0)
                    min_idx = j;
 
            // Swap the found minimum element with the first
            // element
            LedgerItem temp=item.items.get(min_idx);
            item.items.set(min_idx, item.items.get(i));
            item.items.set(i, temp); 
        }
	}
	public Map<String,Double> categorize(String type)
	{ 
		Map<String,Double> map=new HashMap<>();
		if(type.compareTo("expense")==0)
		{
			map.put("Food", 0.0);
			map.put("Commute", 0.0);
			map.put("Entertainment", 0.0);
		}
		if(type.compareTo("earning")==0)
		{
			map.put("Salary", 0.0);
			map.put("Commission", 0.0);
			map.put("Side Gig", 0.0);
		}
		if(type.compareTo("cardpurse")==0)
		{
			map.put("Credit Card", 0.0);
			map.put("Points Card", 0.0);
			map.put("Debit Card", 0.0);
		}
		if(type.compareTo("investment")==0)
		{
			map.put("Stock", 0.0);
			map.put("Bond", 0.0);
			map.put("Saving account", 0.0);
		}
		for(LedgerItem item:this.items)
		{
			if(map.containsKey(item.category.getName()))
			{	
				map.put(item.category.getName(), map.get(item.category.getName())+item.amount);				
			}
		}
		return map;
	}
	

	 /* list of categories:
	  Expense: Food, Commute, Entertainment
	  Earning: Salary, Commission, Side Gig
	  CardPurse: Debit Card, Credit Card, Points Card
	  Investment: Stock, Bond, Saving Account
	 */
	
	
	public Map<String,Double> mapfor30days(String type)
	{
		Map<String,Double> map=new LinkedHashMap<>();
		LocalDate today=LocalDate.now();
		LocalDate monthLater=LocalDate.now();
		LedgerList.sortByLocalDate(this);
		
		for(int x=0; x<this.items.size();x++)
		{
			if(x==0)
			{ 
				 today=this.items.get(x).date;
				 monthLater=today.plusMonths(1).withDayOfMonth(1);
				 map.put(today.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()) + " "+
						 today.getYear(), this.items.get(x).amount);
			}
			else if(this.items.get(x).date.compareTo(monthLater)>0)
			{
				 today=monthLater;
				 monthLater=monthLater.plusMonths(1).withDayOfMonth(1);;
				 map.put(today.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()) +" "+ 
						 today.getYear(), this.items.get(x).amount);
			} 			
			else
			{ 
				 map.put(today.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()) +" "+ 
						 today.getYear(), 
						 map.get(today.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()) +" "+ 
								 today.getYear()) + this.items.get(x).amount);
			}
		}
		return map;
	}
	
	public Map<LocalDate,Double> mapforEachDay(String type)
	{
		Map<LocalDate,Double> map=new LinkedHashMap<>();
		LocalDate today=LocalDate.now();
		LedgerList.sortByLocalDate(this);
		
		for(int x=0; x<this.items.size();x++)
		{
			if(x==0)
			{ 
				 today=this.items.get(x).date;
				 map.put(today, this.items.get(x).amount);
			}
			else if(this.items.get(x).date.compareTo(today)>0)
			{
				 today=this.items.get(x).date;
				 map.put(today, this.items.get(x).amount);
			} 			
			else
			{ 
				 map.put(today, 
						 map.get(today) + this.items.get(x).amount);
			}
		}
		return map;	
		}
	public Map<String,Double> mapforeachcard()
	{
		Map<String,Double> map=new LinkedHashMap<>();
		for(int x=0; x<this.items.size(); x++)
		{ 
			map.put(this.items.get(x).itemName, this.items.get(x).amount);
		}
		return map;
	}	
	
}

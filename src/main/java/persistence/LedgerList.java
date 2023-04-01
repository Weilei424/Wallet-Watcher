package persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
	    	objects.add(new LedgerItem(date.toString(),amount,itemname,note));

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
	
	
}

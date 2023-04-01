package persistence;

import java.util.ArrayList;
import java.util.List;

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
	    	String start_date=(String)model.getValueAt(i, 2);
	    	String end_date=(String)model.getValueAt(i, 3);
	    	objects.add(new BudgetData(amount,start_date,end_date,ref));
	    }
		return objects;
	}
}

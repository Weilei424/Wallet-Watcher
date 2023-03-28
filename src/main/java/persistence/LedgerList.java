package persistence;

import java.util.List;

import javax.swing.JTable;

public class LedgerList {
	public static User currUser;
	public static LedgerList budgetList; 
	public static LedgerList earnings; 
	public static LedgerList expenses; 
	public static LedgerList cards; 
	
	public List<LedgerItem> items;
	public LedgerList()
	{ 
	}
	public static List<LedgerItem> getEntries(JTable table)
	{ 
		
	}

}

package persistence;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import java.io.*;
import java.sql.SQLException;


public class Outputform {
	public static final String EXPENSE = "expense";
	public static final String EARNING = "earning";
	public static final String INVESTMENT = "investment";
	public static final String STOCK = "stock";
	public static final String MISC = "misc";
	public static final String CARD = "card";
	
	public static final String ITEM = "item";
	public static final String NOTE = "note";
	public static final String TAG = "tag";
	public static final String AMOUNT = "amount";
	public static final String INTEREST_RATE = "interest_rate";
	public static final String INTEREST = "interest";
	public static final String RECUR = "recur";
	public static final String CATEGORY = "category";
	public static final String DATE_START = "date_start";
	
	private static String user;
	
	public JTable form;
	
	
	
	public Outputform(String username) throws SQLException {
		this.form=DBUtil.query(username, "tag", "all");
		this.user=username;
	}
	
	public Outputform(String user,String column, String value) throws SQLException {
		this.form=DBUtil.query(user, column, value);
		this.user=user;
		
	}
	
	
	
	
	
	
	
	public void outputFile(String filename)throws IOException {
		
		String Path = "./csvfile/"+filename;
		TableModel model = this.form.getModel();
		File file = new File(Path);
		FileWriter csvWriter = new FileWriter(file);

		// Export header row
	    for (int i = 0; i < model.getColumnCount(); i++) {
	      csvWriter.write(model.getColumnName(i) + ",");
	    }
	    csvWriter.write("\n");

	    // Export data rows
	    for (int i = 0; i < model.getRowCount(); i++) {
	      for (int j = 0; j < model.getColumnCount(); j++) {
	        String value = model.getValueAt(i, j).toString();
	        csvWriter.write(value + ",");
	      }
	      csvWriter.write("\n");
	    }

	    csvWriter.close();
	  
		
	}
	
	
	
	
	
	
	
	

}

package UI;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import persistence.DBUtil;

public class displayAllUsers {
	JFrame page; 
JTable table;
JScrollPane scrollPane;
	JPanel panel;
	public displayAllUsers()
	{ 
		try
		{ 
	       	table = DBUtil.query("ceojeff","category","default");
		}
		catch(SQLException er)
		{ 
		}
		 		scrollPane = new JScrollPane(table); // create the JScrollPane object and pass the JTable object to it
		 		panel=new JPanel();
		        // Create a new JScrollPane and add the JTable to it
			     page=new JFrame();
			     panel.add(scrollPane);
			     page.add(panel);
			     // Set the size of the JFrame
			     page.setSize(500, 200);

		        // Set the visibility of the JFrame to true
		        page.setVisible(true);
	}
	public static void main(String[] args) {
		new displayAllUsers();
	}
}

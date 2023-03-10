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
	       	table = DBUtil.query("ceojeff","tag","all");
		}
		catch(SQLException er)
		{ 
		}
		//invokes query method grabs JTabel and puts it on  the frame along with a scrollpane
		 		scrollPane = new JScrollPane(table); // create the JScrollPane object and pass the JTable object to it
		 		panel=new JPanel();
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

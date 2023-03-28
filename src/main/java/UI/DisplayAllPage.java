package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import DB.DBUtil;
import persistence.LedgerItem;
import persistence.User;

public class DisplayAllPage implements ActionListener {

	public JFrame displayFrame;
	private JPanel mainDisplayPanel;
	private JPanel mainPanel; // main panel to hold all other panels in the expense page form
	private JButton toMenu;
	private JLabel title;
	public JTable expenseTable;
	private JScrollPane displayScroller;
	private NavigatorPage navigation;

	public DisplayAllPage() {

		try {
			expenseTable = DBUtil.query(User.getLoginAs(), "tag", "all");
		} catch (SQLException er) {
		}
		displayScroller = new JScrollPane(expenseTable);

		displayFrame = new JFrame();
		displayFrame.setLocationRelativeTo(null);
		mainDisplayPanel = new JPanel();

		// Initialize main title on page, along with initializing button and layouts
		title = new JLabel("All Items");
		title.setSize(30, 30);
		title.setFont(new Font("Tahoma", Font.BOLD, 60));

		toMenu = new JButton(new AbstractAction("Main Menu") {

			@Override
			public void actionPerformed(ActionEvent e) {
				navigation = new NavigatorPage();
				displayFrame.dispose();
			}
		});

		// This panel holds the top elements including the title and the ability to add
		// another button
		mainDisplayPanel.setLayout(new GridLayout(1, 3));
		mainDisplayPanel.add(title);
		mainDisplayPanel.add(toMenu);
		mainDisplayPanel.setBackground(Color.green);

		// This panel holds all other elements in the frame
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.add(mainDisplayPanel);
		mainPanel.setBackground(Color.green);

		// This is the main frame which holds the main panel and all other elements
		// enclosed in it
		displayFrame.add(mainPanel, BorderLayout.NORTH);
		// mainEpFrame.add(ledgerInfo, BorderLayout.CENTER);
		displayFrame.add(displayScroller, BorderLayout.CENTER);
		displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		displayFrame.setTitle("Expenses");
		displayFrame.setSize(1000, 1000);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		displayFrame.setVisible(true);

	}

//	JFrame page; 
//JTable table;
//JScrollPane scrollPane;
//	JPanel panel;
//	public displayAllUsers()
//	{ 
//		try
//		{ 
//	       	table = DBUtil.query("ceojeff","tag","all");
//		}
//		catch(SQLException er)
//		{ 
//		}
//		//invokes query method grabs JTabel and puts it on  the frame along with a scrollpane
//		 		scrollPane = new JScrollPane(table); // create the JScrollPane object and pass the JTable object to it
//		 		panel=new JPanel();
//			     page=new JFrame();
//			     panel.add(scrollPane);
//			     page.add(panel);
//			     // Set the size of the JFrame
//			     page.setSize(500, 200);
//
//		        // Set the visibility of the JFrame to true
//		        page.setVisible(true);
//	}
	public static void main(String[] args) {
		new DisplayAllPage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

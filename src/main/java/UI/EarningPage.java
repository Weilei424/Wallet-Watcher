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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import DB.DBUtil;
import persistence.LedgerItem;
import persistence.User;

public class EarningPage implements ActionListener {

	// Initializing global variables
	public JFrame mainEpFrame;
	private JPanel mainEpPanel;
	private JPanel mainPanel; // main panel to hold all other panels in the expense page form
	// JPanel ledgerPanel;
	private JButton addEarning;
	private JButton removeEarning;
	private JButton toMenu;
	private JLabel title;
	//private JTextArea ledgerInfo;
	private EarningPageForm epForm;
	private LedgerItem tempLedgerItem;
	public JTable earningTable;
	private JScrollPane earningScroller;
	private boolean isRemoved;
	private navigatorPage navigation;
	public static volatile int numberOfEarnings = 0;

	public EarningPage() {
		
		try
		{ 
	       	earningTable = DBUtil.query(User.getLoginAs(),"tag","earning");
		}
		catch(SQLException er)
		{ 
		}
		earningScroller = new JScrollPane(earningTable);
		
		
		mainEpFrame = new JFrame();
		mainEpFrame.setLocationRelativeTo(null);
		mainEpPanel = new JPanel();
		this.isRemoved = false;

		// Initialize main title on page, along with initializing button and layouts
		title = new JLabel("Earnings");
		title.setSize(30, 30);
		title.setFont(new Font("Tahoma", Font.BOLD, 60));

		addEarning = new JButton("Add New Earnings");
		addEarning.setSize(40, 40);
		addEarning.addActionListener(this);
		
		toMenu = new JButton(new AbstractAction("Main Menu") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				navigation = new navigatorPage();
				mainEpFrame.dispose();
			}
		});

		// This panel holds the top elements including the title and the ability to add
		// another button
		mainEpPanel.setLayout(new GridLayout(1, 3));
		mainEpPanel.add(title);
		mainEpPanel.add(addEarning);
		mainEpPanel.add(toMenu);
		mainEpPanel.setBackground(Color.green);

		removeEarning = new JButton(new AbstractAction("Remove All Expenses") {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (numberOfEarnings > 0) {
					
				} else {
					JOptionPane.showMessageDialog(mainEpFrame, "You need to input expenses first!");
				}
			}

		});
		removeEarning.setForeground(Color.green);

		// This panel holds all other elements in the frame
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.add(mainEpPanel);
		mainPanel.setBackground(Color.green);

		// This is the main frame which holds the main panel and all other elements
		// enclosed in it
		mainEpFrame.add(mainPanel, BorderLayout.NORTH);
		//mainEpFrame.add(ledgerInfo, BorderLayout.CENTER);
		mainEpFrame.add(removeEarning, BorderLayout.SOUTH);
		mainEpFrame.add(earningScroller, BorderLayout.CENTER);
		mainEpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainEpFrame.setTitle("Earnings");
		mainEpFrame.setSize(1000, 1000);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		mainEpFrame.setVisible(true);

	}

	public LedgerItem getTempLedgerItem() {
		return tempLedgerItem;
	}

	public void setTempLedgerItem(LedgerItem tempLedgerItem) {
		this.tempLedgerItem = tempLedgerItem;
	}

	public JButton getAddEarning() {
		return addEarning;
	}

	public int getNumberOfEarning() {
		return numberOfEarnings;
	}

	public void setNumberOfEarning(int numberOfEarnings) {
		this.numberOfEarnings = numberOfEarnings;
	}

	public boolean isRemoved() {
		return isRemoved;
	}

	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public static void main(String[] args) {
		new ExpensePage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		epForm = new EarningPageForm();
		epForm.earningPageFrame.setVisible(true);
		mainEpFrame.dispose();
	}

}

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
import javax.swing.JTextArea;

import DB.DBUtil;
import persistence.LedgerItem;
import persistence.User;

public class BudgetPage implements ActionListener {

	// Initializing global variables
	public JFrame mainEpFrame;
	private JPanel mainEpPanel;
	private JPanel mainPanel; // main panel to hold all other panels in the expense page form
	// JPanel ledgerPanel;
	private JButton addBudget;
	private JButton removeExpense;
	private JButton toMenu;
	private JLabel title;
	//private JTextArea ledgerInfo;
	private BudgetPageForm bpForm;
	private LedgerItem tempLedgerItem;
	public JTable budgetTable;
	private JScrollPane budgetScroller;
	private boolean isRemoved;
	private navigatorPage navigation;
	public static volatile int numberOfExpenses = 0;

	public BudgetPage() {
		
		try
		{ 
	       	budgetTable = DBUtil.query(User.getLoginAs(),"tag","budget");
		}
		catch(SQLException er)
		{ 
		}
		budgetScroller = new JScrollPane(budgetTable);
		
		
		mainEpFrame = new JFrame();
		mainEpPanel = new JPanel();
		this.tempLedgerItem = tempLedgerItem;
		this.isRemoved = false;

		// Initialize main title on page, along with initializing button and layouts
		title = new JLabel("Budget");
		title.setSize(30, 30);
		title.setFont(new Font("Tahoma", Font.BOLD, 60));

		addBudget = new JButton("Add New Budgets");
		addBudget.setSize(40, 40);
		addBudget.addActionListener(this);
		
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
		mainEpPanel.add(addBudget);
		mainEpPanel.add(toMenu);
		mainEpPanel.setBackground(Color.green);

		removeExpense = new JButton(new AbstractAction("Remove All Expenses") {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (numberOfExpenses > 0) {
//					ledgerInfo.setText(null);
//					ledgerInfo.append("Name of Expense" + "\t");
//					ledgerInfo.append("Cost of Expense" + "\t");
//					ledgerInfo.append("Date Due" + "\t" + "\t" + "\t");
//					ledgerInfo.append("Special Notes");
				} else {
					JOptionPane.showMessageDialog(mainEpFrame, "You need to input expenses first!");
				}
			}

		});
		removeExpense.setForeground(Color.green);

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
		mainEpFrame.add(removeExpense, BorderLayout.SOUTH);
		mainEpFrame.add(budgetScroller, BorderLayout.CENTER);
		mainEpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainEpFrame.setTitle("Budget Plans");
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

	public JButton getAddBudget() {
		return addBudget;
	}

	public int getNumberOfExpenses() {
		return numberOfExpenses;
	}

	public void setNumberOfExpenses(int numberOfExpenses) {
		this.numberOfExpenses = numberOfExpenses;
	}

	public boolean isRemoved() {
		return isRemoved;
	}

	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public static void main(String[] args) {
		new BudgetPage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		bpForm = new BudgetPageForm();
		bpForm.budgetPageFrame.setVisible(true);
		mainEpFrame.dispose();
	}
	
	

}

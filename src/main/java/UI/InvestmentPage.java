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

public class InvestmentPage implements ActionListener {

	// Initializing global variables
	public JFrame mainEpFrame;
	private JPanel mainEpPanel;
	private JPanel mainPanel; // main panel to hold all other panels in the expense page form
	// JPanel ledgerPanel;
	private JButton addInvestment;
	private JButton removeInvestment;
	private JButton toMenu;
	private JLabel title;
	//private JTextArea ledgerInfo;
	private InvestmentPageForm epForm;
	private LedgerItem tempLedgerItem;
	public JTable investmentTable;
	private JScrollPane investmentScroller;
	private boolean isRemoved;
	private navigatorPage navigation;
	public static volatile int numberOfInvestments = 0;

	public InvestmentPage() {
		
		try
		{ 
	       	investmentTable = DBUtil.query(User.getLoginAs(),"tag","investment");
		}
		catch(SQLException er)
		{ 
		}
		investmentScroller = new JScrollPane(investmentTable);
		
		
		mainEpFrame = new JFrame();
		mainEpPanel = new JPanel();
		this.isRemoved = false;

		// Initialize main title on page, along with initializing button and layouts
		title = new JLabel("Investments");
		title.setSize(30, 30);
		title.setFont(new Font("Tahoma", Font.BOLD, 60));

		addInvestment = new JButton("Add New Investments");
		addInvestment.setSize(40, 40);
		addInvestment.addActionListener(this);
		
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
		mainEpPanel.add(addInvestment);
		mainEpPanel.add(toMenu);
		mainEpPanel.setBackground(Color.green);

//		removeInvestment = new JButton(new AbstractAction("Remove All Expenses") {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (numberOfInvestments > 0) {
//					
//				} else {
//					JOptionPane.showMessageDialog(mainEpFrame, "You need to input investments first!");
//				}
//			}
//
//		});
//		removeInvestment.setForeground(Color.green);

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
		mainEpFrame.add(removeInvestment, BorderLayout.SOUTH);
		mainEpFrame.add(investmentScroller, BorderLayout.CENTER);
		mainEpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainEpFrame.setTitle("Investments");
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

	public JButton getAddInvestment() {
		return addInvestment;
	}

	public int getNumberOfInvestment() {
		return numberOfInvestments;
	}

	public void setNumberOfInvestment(int numberOfInvestments) {
		this.numberOfInvestments = numberOfInvestments;
	}

	public boolean isRemoved() {
		return isRemoved;
	}

	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public static void main(String[] args) {
		new InvestmentPage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		epForm = new InvestmentPageForm();
		epForm.investmentPageFrame.setVisible(true);
		mainEpFrame.dispose();
	}

}

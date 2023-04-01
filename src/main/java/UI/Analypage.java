package UI;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import DB.DBUtil;
import persistence.ExpenseInputData;
import persistence.LedgerItem;
import persistence.User;

public class Analypage implements ActionListener {

	public JFrame anaPageFrame;
	public int source;
	private JPanel anaPanel;
	private JPanel anaPanel1;
	private JLabel title;
	public JTextField expenseNameInput;
	private JLabel expenseCost;
	public JTextField expenseCostInput;
	private JLabel expenseDescription;
	public JTextField expenseDescriptionInput;
	public JTextField expenseDateInput;
	private JButton submit;
	private LedgerItem ledgerItem;
	private EarningPage earn;
	private ExpenseInputData expData;
	
	
	
	private ExpensePage ep;
	private BudgetPage bp;
	
	private NavigatorPage np;
	
	private MiscPage mp;
	private InvestmentPage ip;
	private CardPursePage cp;
	private BillPlannerPage bpp;
	private int framesCreated;
	private JButton resume;
	
	private JTextField othertext;
	private String category;
	private JLabel dateSelector;
	private JDateChooser dateChooser;
	private String formattedDate;
	private static final int Earn=1;
	private static final int Expense=2;
	
	private static final int budget=3;
	private static final int nav=4;
	public Analypage(int source) {
		this.source=source;

		this.framesCreated = 0;

		anaPageFrame = new JFrame();
		anaPageFrame.setLocationRelativeTo(null);
		anaPanel = new JPanel();
		String returns="return to ";
		String head = "Graph for ";
		String choice ="";
		switch(source) {
		case 1:choice="earning";
			break;
		case 2:choice="expense";
		break;
		case 3:choice="budget";
		break;
		case 4:choice="overall";
		break;
		case 5:choice="billplan";
		break;
		case 6:choice="cardpurse";
		break;
		case 7:choice="investment";
		break;
		case 8:choice="misc";
		break;
		
		}
		//String choice = source==1?"earning":source==2?"expense":"budget";
		head+=choice;
		returns+=source!=4?choice:"Navigator";
		title = new JLabel(head);
		title.setSize(5, 6);
		title.setFont(new Font("Tahoma", Font.BOLD, 40));

		
		
		//add button to return to previous
		resume = new JButton(new AbstractAction(returns) {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(source==Earn)
					earn = new EarningPage();
				else if(source==Expense)
					ep = new ExpensePage();
				else if(source==budget)
					bp = new BudgetPage();
				else if(source==nav)
					np = new NavigatorPage();
				else if(source==8)
					mp = new MiscPage();
				else if(source==7)
					ip = new InvestmentPage();
				else if (source==6)
					cp = new CardPursePage();
				else 
					bpp = new BillPlannerPage();
				anaPageFrame.dispose();
			}
		});
		resume.setSize(40, 40);
		
		
		
		
		// This panel holds the top elements including the title and the ability to add
				// another button
				anaPanel.setLayout(new GridLayout(1, 3));
				anaPanel.add(title);
				//anaPanel.add(addEarning);
				//anaPanel.add(addana);
				anaPanel.add(resume);
				anaPanel.setBackground(Color.green);
		
				
				
				anaPanel1 = new JPanel();
				anaPanel1.setLayout(new BorderLayout());
				anaPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				anaPanel1.add(anaPanel);
				anaPanel1.setBackground(Color.cyan);
				
				
				
				
				anaPageFrame.add(anaPanel1, BorderLayout.NORTH);
				// mainEpFrame.add(ledgerInfo, BorderLayout.CENTER);
				//mainEpFrame.add(export, BorderLayout.SOUTH);
				//mainEpFrame.add(earningScroller, BorderLayout.CENTER);
				anaPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				anaPageFrame.setTitle("Earnings analyze");
				anaPageFrame.setSize(1000, 1000);
				// expensePageFrame.pack(); // when setSize on, then remove pack
				anaPageFrame.setVisible(true);
		//radioGroup = new ButtonGroup();
		
		//othertext = new JTextField(20);
		//othertext.setPreferredSize(null);

		
		/*
		bills = new JRadioButton("Bills");
		bills.setBorderPainted(true);
		food = new JRadioButton("Food");
		food.setBorderPainted(true);
		commute = new JRadioButton("Commute");
		commute.setBorderPainted(true);
		entertainment = new JRadioButton("Entertainment");
		entertainment.setBorderPainted(true);
		financial = new JRadioButton("Financial");
		financial.setBorderPainted(true);
		other = new JRadioButton("Other:");

		radioGroup.add(bills);
		radioGroup.add(food);
		radioGroup.add(commute);
		radioGroup.add(entertainment);
		radioGroup.add(financial);
		radioGroup.add(other);
		category = "default";

		bills.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (bills.isSelected())
					category = "Bills";
			}
		});

		food.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (food.isSelected())
					category = "Food";
			}
		});

		commute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (commute.isSelected())
					category = "Commute";
			}
		});

		entertainment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (entertainment.isSelected())
					category = "Entertainment";
			}
		});

		financial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (financial.isSelected())
					category = "Financial";
			}
		});

		other.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (other.isSelected()) {
					othertext.setEnabled(true);
					othertext.requestFocus();
					category = othertext.getText();
				} else {
					othertext.setEnabled(false);
				}
			}
		});
		*/

		

		
		/*
		expenseName = new JLabel("Name of Expense:");
		expenseName.setSize(100, 20);
		expenseName.setLocation(100, 100);
		expensePageForm.add(expenseName);

		expenseNameInput = new JTextField();
		// expenseNameInput.setBounds(200, 100, 100, 20);
		expenseNameInput.setSize(100, 20);
		expenseNameInput.setLocation(300, 100);
		expensePageForm.add(expenseNameInput);

		expenseCost = new JLabel("Cost of Expense:");
		expenseCost.setSize(100, 20);
		expenseCost.setLocation(100, 200);
		expensePageForm.add(expenseCost);

		expenseCostInput = new JTextField();
		expenseCostInput.setSize(100, 20);
		expenseCostInput.setLocation(200, 200);
		expensePageForm.add(expenseCostInput);

		expenseDescription = new JLabel("Description of Expense:");
		expenseDescription.setSize(100, 20);
		expenseDescription.setLocation(100, 300);
		expensePageForm.add(expenseDescription);

		expenseDescriptionInput = new JTextField();
		expenseDescriptionInput.setSize(100, 20);
		expenseDescriptionInput.setLocation(200, 300);
		expensePageForm.add(expenseDescriptionInput);

		dateSelector = new JLabel("Selected date: ");
		dateChooser = new JDateChooser();

		dateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					Date selectedDate = (Date) evt.getNewValue();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					formattedDate = dateFormat.format(selectedDate);
					dateSelector.setText("Selected date: " + formattedDate);
				}
			}
		});
		
		
		/*
		expensePageForm.add(dateSelector);
		expensePageForm.add(dateChooser);

		expensePageForm.add(bills);
		expensePageForm.add(food);
		expensePageForm.add(commute);
		expensePageForm.add(entertainment);
		expensePageForm.add(financial);
		expensePageForm.add(other);
		expensePageForm.add(othertext);

		submit = new JButton("Submit");
		submit.setBounds(20, 10, 100, 50);
		submit.addActionListener(this);
		expensePageForm.add(submit);

		// Adding the expense form panel into the main frame
		expensePageFrame.add(expensePageForm, BorderLayout.CENTER);
		expensePageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		expensePageFrame.setTitle("Add Expense");
		expensePageFrame.setSize(600, 400);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		expensePageFrame.setVisible(true);
*/
	}
	

	public JTextField getExpenseNameInput() {
		return expenseNameInput;
	}

	public JTextField getExpenseCostInput() {
		return expenseCostInput;
	}

	public JTextField getExpenseDescriptionInput() {
		return expenseDescriptionInput;
	}

	public JTextField getExpenseDateInput() {
		return expenseDateInput;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		/*

		if (this.framesCreated < 1) {
			ep = new ExpensePage();
			ep.mainEpFrame.setVisible(true);
			ep.getAddExpense().setVisible(false);

		}

		String expName = expenseNameInput.getText();
		String expNote = expenseDescriptionInput.getText();
		String expDate = formattedDate;
		double expCost = Double.parseDouble(expenseCostInput.getText());

		this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);
		this.ledgerItem.setCategory(category);

		DBUtil.insert(User.getLoginAs(), this.ledgerItem, "expense");

		ep.setTempLedgerItem(this.ledgerItem);
		ep.setNumberOfExpenses(ep.getNumberOfExpenses() + 1);

		try {
			ep.expenseTable = DBUtil.query(User.getLoginAs(), "tag", "expense");
			ep.mainEpFrame.dispose();
			ep = new ExpensePage();
			ep.mainEpFrame.setVisible(true);
			ep.getAddExpense().setVisible(false);
			expensePageFrame.dispose();
		} catch (SQLException er) {
		}
		this.framesCreated++;
		*/
	}

	public LedgerItem getLedgerItem() {
		return ledgerItem;
	}

	public static void main(String[] args) {
		new Analypage(4);
	}

}

package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import DB.DBUtil;
import persistence.ExpenseInputData;
import persistence.LedgerItem;
import persistence.User;

public class ExpensePageForm implements ActionListener {

	public JFrame expensePageFrame;
	private JPanel expensePageForm;
	private JLabel expenseName;
	public JTextField expenseNameInput;
	private JLabel expenseCost;
	public JTextField expenseCostInput;
	// JLabel expenseCategory; *No real code for this yet
	private JLabel expenseDescription;
	public JTextField expenseDescriptionInput;
	private JLabel expenseDate;
	public JTextField expenseDateInput;
	private JButton submit;
	private LedgerItem ledgerItem;
	private ExpenseInputData expData;
	private ExpensePage ep;
	private int framesCreated;
	private ButtonGroup radioGroup;
	private JRadioButton bills;
	private JRadioButton food;
	private JRadioButton commute;
	private JRadioButton entertainment;
	private JRadioButton financial;
	private JRadioButton other;
	private JTextField othertext;
	private String category;
	
	public ExpensePageForm() {

		this.framesCreated = 0;

		expensePageFrame = new JFrame();
		expensePageFrame.setLocationRelativeTo(null);
		expensePageForm = new JPanel();
		radioGroup = new ButtonGroup();
		othertext = new JTextField(20);
		othertext.setPreferredSize(null);
		
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
		other.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (other.isSelected()) {
                    othertext.setEnabled(true);
                    othertext.requestFocus();
                    category = othertext.getText();
                } else if (bills.isSelected()) {
                	category = "Bills";
                } else if (food.isSelected()) {
                	category = "Food";
                } else if (commute.isSelected()) {
                	category = "Commute";
                } else if (entertainment.isSelected()) {
                	category = "Entertainment";
                } else if (financial.isSelected()) {
                	category = "Financial";
                } else {
                    othertext.setEnabled(false);
                }
            }
        });
		
		expensePageForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		expensePageForm.setLayout(new GridLayout(5, 1));
		expensePageForm.setBackground(Color.cyan);

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

		expenseDate = new JLabel("Date of payment:");
		expenseDate.setSize(100, 20);
		expenseDate.setLocation(100, 400);
		expensePageForm.add(expenseDate);

		expenseDateInput = new JTextField();
		expenseDateInput.setSize(100, 20);
		expenseDateInput.setLocation(200, 400);
		expensePageForm.add(expenseDateInput);

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

		if (expenseNameInput.getText().isEmpty() || expenseCostInput.getText().isEmpty() || expenseDateInput.getText().isEmpty()) {
			JOptionPane.showMessageDialog(expensePageFrame, "Please enter the name, cost, and date.");
			return;
		}

		String expName = expenseNameInput.getText();
		String expNote = expenseDescriptionInput.getText();
		String expDate = expenseDateInput.getText();

		try {
			double expCost = Double.parseDouble(expenseCostInput.getText());
			this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);
		} catch (NumberFormatException ex) {
			new ErrorPage("Amount is not a valid number.", ex);
			return;
		} catch (IllegalArgumentException ex) {
			new ErrorPage("Date is not a valid date.", ex);
			return;
		}

		this.ledgerItem.setCategory(category);

		DBUtil.insert(User.getLoginAs(), this.ledgerItem, "expense");

		if (this.framesCreated < 1) {
			ep = new ExpensePage();
			ep.mainEpFrame.setVisible(true);
			ep.getAddExpense().setVisible(false);

		}

		ep.setTempLedgerItem(this.ledgerItem);
		ep.setNumberOfExpenses(ep.getNumberOfExpenses() + 1);

		try {
			ep.expenseTable = DBUtil.query(User.getLoginAs(),"tag","expense");
			ep.mainEpFrame.dispose();
			ep = new ExpensePage();
			ep.mainEpFrame.setVisible(true);
			ep.getAddExpense().setVisible(false);
			expensePageFrame.dispose();
			} catch(SQLException er) {
			}
		this.framesCreated++;
	}

	public LedgerItem getLedgerItem() {
		return ledgerItem;
	}

	public static void main(String[] args) {
		new ExpensePageForm();
	}

}

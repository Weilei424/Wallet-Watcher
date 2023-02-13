package pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ExpensePage {

	JFrame expensePageFrame;
	JPanel expensePageForm;
	JLabel expenseName;
	JLabel expenseCost;
	JLabel expenseCategory; //No real code for this yet
	JLabel expenseDescription;
	JLabel expenseDate;
	JButton submit;

	public ExpensePage() {

		expensePageFrame = new JFrame();
		expensePageForm = new JPanel();
		expenseName = new JLabel("Name of Expense:", SwingConstants.CENTER);
		expenseCost = new JLabel("Cost of Expense:", SwingConstants.CENTER);
		expenseCategory = new JLabel("Category of Expense:", SwingConstants.CENTER);
		expenseDescription = new JLabel("Description of Expense:", SwingConstants.CENTER);
		expenseDate = new JLabel("Date of payment:", SwingConstants.CENTER);
		
		submit = new JButton("Submit");

		// Created main panel for the expense form
		expensePageForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		expensePageForm.setLayout(new GridLayout(0, 1));
		expensePageForm.setBackground(Color.cyan);

		// Adding the expense form panel into the main frame
		expensePageFrame.add(expensePageForm, BorderLayout.CENTER);
		expensePageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		expensePageFrame.setTitle("Add Expense");
		expensePageFrame.pack();
		expensePageFrame.setVisible(true);

	}

	public static void main(String[] args) {
		new ExpensePage();
	}

}

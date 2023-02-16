package main.java;

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
import javax.swing.JTextField;

public class ExpensePageForm {

	public JFrame expensePageFrame;
	private JPanel expensePageForm;
	private JLabel expenseName;
	private JTextField expenseNameInput;
	private JLabel expenseCost;
	private JTextField expenseCostInput;
	// JLabel expenseCategory; *No real code for this yet
	private JLabel expenseDescription;
	private JTextField expenseDescriptionInput;
	private JLabel expenseDate;
	private JTextField expenseDateInput;
	private JButton submit;

	public ExpensePageForm() {

		expensePageFrame = new JFrame();
		expensePageForm = new JPanel();

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

		submit = new JButton("Submit");
		submit.setBounds(20, 10, 100, 50);
		expensePageForm.add(submit);

//		expenseName.setBounds(10, 20, 80, 25);
//		expenseNameInput.setBounds(30, 20, 80, 25);
//		expenseNameInput.setLocation(300, 300);
//		expenseCost.setBounds(10, 50, 80, 25);
//		expenseCostInput.setBounds(30, 50, 80, 25);

		// submit button bounds

		// Created main panel for the expense form
//		expensePageForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
//		expensePageForm.setLayout(new GridLayout(1, 1));
//		expensePageForm.setBackground(Color.cyan);
//		 expensePageForm.add(expenseName);
//		 expensePageForm.add(expenseNameInput);
		// expensePageForm.add(expenseCost);
		// expensePageForm.add(expenseCostInput);
		// expensePageForm.add(submit);

		// Adding the expense form panel into the main frame
		expensePageFrame.add(expensePageForm, BorderLayout.CENTER);
		expensePageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		expensePageFrame.setTitle("Add Expense");
		expensePageFrame.setSize(600, 900);
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

	public static void main(String[] args) {
		new ExpensePageForm();
	}

}

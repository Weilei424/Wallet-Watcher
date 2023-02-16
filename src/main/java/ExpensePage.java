package main.java;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ExpensePage implements ActionListener{

	// Initializing global variables
	public JFrame mainEpFrame;
	private JPanel mainEpPanel;
	private JPanel mainPanel; // main panel to hold all other panels in the expense page form
	// JPanel ledgerPanel;
	private JButton addExpense;
	private JButton removeExpense;
	private JLabel title;
	private JTextArea ledgerInfo;
	private ExpensePageForm epForm;

	public ExpensePage() {
		mainEpFrame = new JFrame();
		mainEpPanel = new JPanel();
		epForm = new ExpensePageForm();
		epForm.expensePageFrame.setVisible(false);

		// Initialize main title on page, along with initializing button and layouts
		title = new JLabel("Expenses");
		title.setSize(30, 30);
		title.setFont(new Font("Tahoma", Font.BOLD, 60));

		addExpense = new JButton("Add New Expense");
		addExpense.setSize(40, 40);
		addExpense.addActionListener(this);

		removeExpense = new JButton("Remove Expense");
		removeExpense.setBackground(Color.green);

		// This panel holds the top elements including the title and the ability to add
		// another button
		mainEpPanel.setLayout(new GridLayout(1, 2));
		mainEpPanel.add(title);
		mainEpPanel.add(addExpense);
		mainEpPanel.setBackground(Color.green);

		// This is the text area which shows all of the "ledger" information

		ledgerInfo = new JTextArea();
		ledgerInfo.append("Name of Expense" + "\t");
		ledgerInfo.append("Cost of Expense" + "\t");
		ledgerInfo.append("Date Due" + "\t");
		ledgerInfo.append("Special Notes");
		ledgerInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// Disables user from being able to add any text into area as it is only for
		// displaying the ledger
		ledgerInfo.setEditable(false);

		// This panel holds all other elements in the frame
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.add(mainEpPanel);
		mainPanel.setBackground(Color.green);

		// This is the main frame which holds the main panel and all other elements
		// enclosed in it
		mainEpFrame.add(mainPanel, BorderLayout.NORTH);
		mainEpFrame.add(ledgerInfo, BorderLayout.CENTER);
		mainEpFrame.add(removeExpense, BorderLayout.SOUTH);
		mainEpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainEpFrame.setTitle("Expense Title");
		mainEpFrame.setSize(800, 800);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		mainEpFrame.setVisible(true);

	}

	public static void main(String[] args) {
		new ExpensePage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		epForm.expensePageFrame.setVisible(true);
		mainEpFrame.setVisible(false);
	}

}

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ExpensePage {
	
	//Initializing global variables
	JFrame mainEpFrame;
	JPanel mainEpPanel;
	JPanel mainPanel; //main panel to hold all other panels in the expense page form
	//JPanel ledgerPanel;
	JButton addExpense;
	JButton removeExpense;
	JLabel title;
	JTextArea ledgerInfo;

	
	
	public ExpensePage() {
		mainEpFrame = new JFrame();
		mainEpPanel = new JPanel();
		
		//Initialize main title on page, along with initializing button and layouts
		title = new JLabel("Expenses");
		title.setSize(30, 30);
		title.setFont(new Font("Tahoma", Font.BOLD, 60));
		
		addExpense = new JButton("Add New Expense");
		addExpense.setSize(40, 40);
		
		removeExpense = new JButton("Remove Expense");
		removeExpense.setBackground(Color.green);

		//This panel holds the top elements including the title and the ability to add another button
		mainEpPanel.setLayout(new GridLayout(1, 2));
		mainEpPanel.add(title);
		mainEpPanel.add(addExpense);
		mainEpPanel.setBackground(Color.green);
		
		//This is the text area which shows all of the "ledger" information
		
		ledgerInfo = new JTextArea();
		ledgerInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		
		//This panel holds all other elements in the frame
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.add(mainEpPanel);
		mainPanel.setBackground(Color.green);
		//mainPanel.add(ledgerPanel);
		
		//This is the main frame which holds the main panel and all other elements enclosed in it
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
	
	

}

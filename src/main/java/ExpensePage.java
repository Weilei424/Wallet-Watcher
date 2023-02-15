package src.main.java;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ExpensePage {
	
	JFrame mainEpFrame;
	JPanel mainEpPanel;
	JPanel mainPanel; //main panel to hold all other panels in the expense page form
	JButton addExpense;
	JLabel title;

	
	
	public ExpensePage() {
		mainEpFrame = new JFrame();
		mainEpPanel = new JPanel();
		
		
		title = new JLabel("Expenses");
		title.setSize(30, 30);
		title.setFont(new Font("Tahoma", Font.BOLD, 60));
		
		addExpense = new JButton("Add New Expense");
		addExpense.setSize(40, 40);

		mainEpPanel.setLayout(new GridLayout(1, 2));
		mainEpPanel.add(title);
		mainEpPanel.add(addExpense);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(mainEpPanel);
		
		mainEpFrame.add(mainPanel, BorderLayout.NORTH);
		mainEpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainEpFrame.setTitle("All Expenses");
		mainEpFrame.setSize(800, 800);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		mainEpFrame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new ExpensePage();
	}
	
	

}

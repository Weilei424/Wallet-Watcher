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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ExpensePage {
	
	JFrame mainEpFrame;
	JPanel mainEpPanel;
	JLabel addExpense;
	
	
	public ExpensePage() {
		mainEpFrame = new JFrame();
		mainEpPanel = new JPanel();
		addExpense = new JLabel("Add New Expense");		
	}
	
	

}

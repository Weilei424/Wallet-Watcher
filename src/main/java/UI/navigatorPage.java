package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class navigatorPage {
	JFrame navigator;

	JPanel buttons; 
	
	//buttons to redirect to different pages 
	JButton expensePage;
	JButton earningsPage;
	JButton budgetPage;
	JButton cardPage;
	JButton logOut;

	JLabel navPage;

	ExpensePage expense;
	// switch this with budget
	// BillPlannerPage budget;
	// add in the earnings Page and modify action listener
	CardPursePage cardPurse;
	MainUi logIn;

	ActionListener expenseDirect = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			expense = new ExpensePage();
			expense.mainEpFrame.setVisible(true);
			navigator.dispose();
		}
	};	
	//TODO 
	ActionListener earningDirect=new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			//add earning page instance in when time is good 
			//navigator.dispose();
		}
	};
	
	ActionListener budgetDirect=new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			//add budget instance when budget page is implemented 
			//navigator.dispose();
		}
	};


	ActionListener cardPurseDirect = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			cardPurse = new CardPursePage();
			cardPurse.mainCpPage.setVisible(true);
      }
	};



	
	
	ActionListener logOutDirect = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			logIn = new MainUi();
			logIn.mainFrame.setVisible(true);
			navigator.dispose();
			JOptionPane.showMessageDialog(logIn.mainFrame, "Logged Out Successfully!");
		}
	};

	public navigatorPage() {

		navigator = new JFrame("Navigation Page");

		navigator.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

		buttons = new JPanel();
		buttons.setLayout(new GridLayout(5, 1, 10, 10));

		// padding for buttons

		expensePage = new JButton("Expense");
		expensePage.setBorder(new EmptyBorder(10, 10, 10, 10));

		earningsPage = new JButton("Earnings");
		earningsPage.setBorder(new EmptyBorder(10, 10, 10, 10));


		budgetPage = new JButton("Budget");
		budgetPage.setBorder(new EmptyBorder(10, 10, 10, 10));
		navPage = new JLabel("Navigation Page");
		navPage.setFont(new Font(navPage.getFont().getFontName(), Font.PLAIN, 24));

		cardPage = new JButton("Card Purse");
		cardPage.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		logOut = new JButton("Log Out");
		logOut.setBorder(new EmptyBorder(10, 10, 10, 10));

		navPage.setBorder(new EmptyBorder(0, 550, 0, 0));

		budgetPage.addActionListener(budgetDirect);
		cardPage.addActionListener(cardPurseDirect);
		earningsPage.addActionListener(earningDirect);
		expensePage.addActionListener(expenseDirect);
		logOut.addActionListener(logOutDirect);

		buttons.add(navPage);
		buttons.add(budgetPage);
		buttons.add(cardPage);
		buttons.add(expensePage);
		buttons.add(logOut);


		buttons.add(earningsPage);

		navigator.getContentPane().setLayout(new BorderLayout());
		navigator.getContentPane().add(buttons, BorderLayout.CENTER);

		navigator.add(buttons);
		navigator.setVisible(true);
	}

	public static void main(String[] args) {
		new navigatorPage();
	}
}

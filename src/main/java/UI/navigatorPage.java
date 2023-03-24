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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import persistence.User;

public class navigatorPage {
	JFrame navigator;

	JPanel buttons; 
	
	//buttons to redirect to different pages 
	JButton expensePage;
	JButton earningsPage;
	JButton budgetPage;
	JButton cardPage;
	JButton investmentPage;
	JButton settings; 
	JButton logOut;
	
	
	//buttons for account management page
	JButton changePassword; 
	JButton mergeAcc; 
	JButton deleteAcc; 
	
	JPanel label;
	JLabel navPage;
	ExpensePage expense;
	BudgetPage budget;
	EarningPage earnings;
	CardPursePage cardPurse;
	InvestmentPage investment;
	Settings settingsPage;
	MainUi logIn;

	ActionListener expenseDirect = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			expense = new ExpensePage();
			expense.mainEpFrame.setVisible(true);
			navigator.dispose();
		}
	};

	// TODO
	ActionListener earningDirect = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			earnings=new EarningPage();
			navigator.dispose();

		}
	};

	ActionListener budgetDirect = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			budget=new BudgetPage();
			navigator.dispose();
		}
	};


	ActionListener cardPurseDirect = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			cardPurse = new CardPursePage();
			cardPurse.mainCpPage.setVisible(true);
      }
	};


	ActionListener investmentDirect = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			investment = new InvestmentPage();
			investment.mainIvFrame.setVisible(true);
      }
	};

	
	
	ActionListener logOutDirect = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			logIn = new MainUi();
			logIn.mainFrame.setVisible(true);
			navigator.dispose();
			User.setLoginAs(null);
			JOptionPane.showMessageDialog(logIn.mainFrame, "Logged Out Successfully!");
		}
	};
	ActionListener settingsDirect=new ActionListener()
	{ 
		public void actionPerformed(ActionEvent e)
		{ 
			settingsPage=new Settings();
			settingsPage.settingsFrame.setVisible(true);
			navigator.dispose();
		}
		
	};

	public navigatorPage() {
		navigator = new JFrame("Navigation Page");
		navigator.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

		label=new JPanel();
		label.setBorder(BorderFactory.createEmptyBorder(0,0,0,550));
		
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(6, 1, 10, 10));

		// padding for buttons

		expensePage = new JButton("Expense");
		expensePage.setBorder(new EmptyBorder(10, 10, 10, 10));

		earningsPage = new JButton("Earnings");
		earningsPage.setBorder(new EmptyBorder(10, 10, 10, 10));


		budgetPage = new JButton("Budget");
		budgetPage.setBorder(new EmptyBorder(10, 10, 10, 10));
		navPage = new JLabel("Navigation Page");
		navPage.setFont(new Font(navPage.getFont().getFontName(), Font.PLAIN, 24));
		
	    label.add(navPage, BorderLayout.CENTER); // add the label to the CENTER of the BorderLayout

		cardPage = new JButton("Card Purse");
		cardPage.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		investmentPage = new JButton("Investment");
		investmentPage.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		settings=new JButton("Settings");
		settings.setBorder(new EmptyBorder(10,10,10,10));
		
		logOut = new JButton("Log Out");
		logOut.setBorder(new EmptyBorder(10, 10, 10, 10));

		navPage.setBorder(new EmptyBorder(0, 550, 0, 0));

		budgetPage.addActionListener(budgetDirect);
		cardPage.addActionListener(cardPurseDirect);
		earningsPage.addActionListener(earningDirect);
		expensePage.addActionListener(expenseDirect);
		investmentPage.addActionListener(investmentDirect);
		settings.addActionListener(settingsDirect);
		logOut.addActionListener(logOutDirect);

		buttons.add(budgetPage);
		buttons.add(cardPage);
		buttons.add(expensePage);
		buttons.add(earningsPage);
		buttons.add(investmentPage);
		buttons.add(settings);
		buttons.add(logOut);

		buttons.setBorder(BorderFactory.createEmptyBorder(100,0,0,0));
	   
		navigator.getContentPane().setLayout(new BorderLayout());
	    navigator.getContentPane().add(label, BorderLayout.NORTH);
	    navigator.getContentPane().add(buttons, BorderLayout.CENTER);
		navigator.setVisible(true);
	}

	public static void main(String[] args) {
		new navigatorPage();
	}
}

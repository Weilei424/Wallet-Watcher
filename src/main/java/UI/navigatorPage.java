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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class navigatorPage {
	JFrame navigator;
	JPanel buttons; 
	
	JButton expensePage;
	JButton earningsPage;
	JButton budgetPage;
	JButton cardPage;

	JLabel navPage;
	
	ExpensePage expense=new ExpensePage();
	//switch this with budget
	BillPlannerPage budget=new BillPlannerPage();
	//add in the earnings Page and modify action listener
	CardPursePage cardPurse=new CardPursePage();
	
	ActionListener expenseDirect=new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			navigator.dispose();
			budget.mainBpPage.dispose();
			cardPurse.mainCpPage.dispose();
			expense.mainEpFrame.setVisible(true);
		}
	};
	
	//TODO 
	ActionListener earningDirect=new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			navigator.dispose();
			budget.mainBpPage.dispose();
			cardPurse.mainCpPage.dispose();
			expense.mainEpFrame.dispose();

		}
	};
	
	ActionListener budgetDirect=new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			navigator.dispose();
			cardPurse.mainCpPage.dispose();
			expense.mainEpFrame.dispose();
			budget.mainBpPage.setVisible(true);
		}
	};
	
	ActionListener cardPurseDirect=new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			navigator.dispose();
			budget.mainBpPage.dispose();
			expense.mainEpFrame.dispose();
			cardPurse.mainCpPage.setVisible(true);

		}
	};
	public navigatorPage()
	{ 
		expense.mainEpFrame.setVisible(false);
		budget.mainBpPage.setVisible(false);
		cardPurse.mainCpPage.setVisible(false);
		
		navigator=new JFrame("Navigation Page");

		navigator.setExtendedState (java.awt.Frame.MAXIMIZED_BOTH);
		
		buttons=new JPanel(); 
		buttons.setLayout(new GridLayout(5,1,10,10));
		
		//padding for buttons
		
		expensePage=new JButton("Expense");
		expensePage.setBorder(new EmptyBorder(10,10,10,10));
		
		earningsPage=new JButton("Earnings");
		earningsPage.setBorder(new EmptyBorder(10,10,10,10));

		
		budgetPage=new JButton("Budget");
		budgetPage.setBorder(new EmptyBorder(10,10,10,10));

        navPage=new JLabel("Navigation Page");
        navPage.setFont(new Font(navPage.getFont().getFontName(),Font.PLAIN,24));
        
        cardPage=new JButton("Card Purse");
		cardPage.setBorder(new EmptyBorder(10,10,10,10));

        navPage.setBorder(new EmptyBorder(0,550,0,0));
        
        budgetPage.addActionListener(budgetDirect);
        cardPage.addActionListener(cardPurseDirect);
        earningsPage.addActionListener(earningDirect);
        expensePage.addActionListener(expenseDirect);
        
        buttons.add(navPage);
		buttons.add(budgetPage);
		buttons.add(cardPage);
		buttons.add(expensePage);

		buttons.add(earningsPage);		

        navigator.getContentPane().setLayout(new BorderLayout());

        // Add the JPanel to the center of the content pane
        navigator.getContentPane().add(buttons, BorderLayout.CENTER);
        navigator.add(buttons);
		navigator.setVisible(true);
	}	
	

	
	
	public static void main(String[] args) {
		new navigatorPage();
	}
}

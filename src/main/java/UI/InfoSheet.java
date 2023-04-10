package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import persistence.LedgerItem;
import persistence.LedgerList;
import persistence.User;

public class InfoSheet implements ActionListener {

	JFrame infoFrame;
	JPanel infoPanel;
	JPanel infoTitlePanel;
	JPanel returnMenu;
	JLabel infoTitle;
	JLabel averageExpenses;
	JLabel averageFood;
	JLabel averageCommute;
	JLabel averageEntertainment;
	JLabel budgetSurplus;
	JButton returnToMenu;
	double avgExpenses;
	double avgFood;
	double avgCommute;
	double avgEntertain;
	NavigatorPage nav;

	ActionListener menu = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			nav = new NavigatorPage();
			infoFrame.dispose();
		}
	};

	public InfoSheet() {
		// average total expenses

		infoTitle = new JLabel("Your Average Expenses Are:");
		infoTitle.setFont(new Font("Courier", Font.BOLD, 14));
		infoTitle.setLocation(200, 50);

		avgExpenses = averageExpenses(User.expenses, "expense", "hi");
		avgFood = averageExpenses(User.expenses, "expense", "Food");
		avgCommute = averageExpenses(User.expenses, "expense", "Commute");
		avgEntertain = averageExpenses(User.expenses, "expense", "Entertainment");
		double surplus=User.expenses.surplusCalculator(User.currBudget);
		infoFrame = new JFrame();
		infoTitlePanel = new JPanel();
		infoPanel = new JPanel();

		infoTitlePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 0, 50));
		// infoTitlePanel.setLayout(null);
		infoTitlePanel.setBackground(new Color(137, 208, 240));
		infoTitlePanel.add(infoTitle);

		infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 0, 50));
		infoPanel.setLayout(new GridLayout(5, 2));
		infoPanel.setBackground(new Color(137, 208, 240));

		averageExpenses = new JLabel("Average Total User Expenses: " + String.format("%.2f", avgExpenses));
		averageExpenses.setSize(100, 20);
		averageExpenses.setLocation(100, 100);
		averageExpenses.setForeground(Color.BLACK);

		averageFood = new JLabel("Average User Spent on Food: " + String.format("%.2f", avgFood));
		averageFood.setSize(100, 20);
		averageFood.setLocation(100, 200);
		averageFood.setForeground(Color.BLACK);

		averageCommute = new JLabel("Average User Spent on Commuting: " + String.format("%.2f", avgCommute));
		averageCommute.setSize(100, 20);
		averageCommute.setLocation(100, 200);
		averageCommute.setForeground(Color.BLACK);

		averageEntertainment = new JLabel(
				"Average User Spent on Entertainment: " + String.format("%.2f", avgEntertain));
		averageEntertainment.setSize(100, 20);
		averageEntertainment.setLocation(100, 300);
		averageEntertainment.setForeground(Color.black);
		;
		budgetSurplus = new JLabel(
				"How much budget you have remaining: " + String.format("%.2f", surplus));
		averageEntertainment.setSize(100, 20);
		averageEntertainment.setLocation(100, 300);
		averageEntertainment.setForeground(Color.black);
		;
		infoPanel.add(averageExpenses);
		infoPanel.add(averageFood);
		infoPanel.add(averageCommute);
		infoPanel.add(averageEntertainment);
		infoPanel.add(budgetSurplus);
		
		returnMenu = new JPanel();
		returnMenu.setBackground(new Color(137, 208, 240));
		returnMenu.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));

		returnToMenu = new JButton("Return to menu");

		returnToMenu.addActionListener(menu);

		returnMenu.add(returnToMenu);

		infoFrame.add(infoTitlePanel, BorderLayout.NORTH);
		infoFrame.add(infoPanel);
		infoFrame.add(returnMenu, BorderLayout.SOUTH);
		infoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		infoFrame.setTitle("User Information Sheet");
		infoFrame.setSize(400, 400);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		infoFrame.setLocationRelativeTo(null);

		infoFrame.setVisible(true);
	}

	public double averageExpenses(LedgerList userList, String averageChoice, String specificChoice) {

		Map<String, Double> map = userList.categorize(averageChoice);
		double average = 0;
		String specificC = specificChoice;

		if (specificC.equals("hi")) {
			for (Map.Entry<String, Double> entry : map.entrySet()) {
				average += entry.getValue();
			}
		} else {
			if (map.containsKey(specificC)) {
				average += map.get(specificC);
			}
		}

		
		return average;

	}

	public static void main(String[] args) {
		new InfoSheet();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}

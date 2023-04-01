package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import DB.DBUtil;
import persistence.LedgerItem;
import persistence.User;

public class BudgetPageForm implements ActionListener {

	public JFrame budgetPageFrame;
	private JPanel budgetPageForm;
	private JLabel budgetName;
	public JTextField budgetNameInput;
	private JLabel budgetCost;
	public JTextField budgetCostInput;
	// JLabel expenseCategory; *No real code for this yet
	private JLabel budgetDescription;
	public JTextField budgetDescriptionInput;
	private JLabel budgetDate;
	public JTextField budgetDateInput;
	private JButton submit;
	private LedgerItem ledgerItem;
	private BudgetPage bp;
	private JLabel expenseType;
	private JRadioButton food;
	private JRadioButton commute;
	private JRadioButton entertainment;
	private JRadioButton other;
	private JDateChooser date;
	private Box buttonBox;
	private ButtonGroup radioGroup;
	private String category;
	private String formattedDate;
	private int framesCreated;

	public BudgetPageForm() {

		food = new JRadioButton("Food");
		commute = new JRadioButton("Commute");
		entertainment = new JRadioButton("Entertainment");
		other = new JRadioButton("Other");

		buttonBox = Box.createHorizontalBox();

		buttonBox.add(food);
		buttonBox.add(commute);
		buttonBox.add(entertainment);
		buttonBox.add(other);

		food.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (food.isSelected())
					category = "Food";
			}
		});

		commute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (commute.isSelected())
					category = "Commute";
			}
		});

		entertainment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (entertainment.isSelected())
					category = "Entertainment";
			}
		});

		this.framesCreated = 0;

		budgetPageFrame = new JFrame();
		budgetPageFrame.setLocationRelativeTo(null);
		budgetPageForm = new JPanel();

		budgetPageForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		budgetPageForm.setLayout(new GridLayout(6, 2));
		budgetPageForm.setBackground(Color.cyan);

		budgetName = new JLabel("Name of Budget:");
		budgetName.setSize(100, 20);
		budgetName.setLocation(100, 100);
		budgetPageForm.add(budgetName);

		budgetNameInput = new JTextField();
		budgetNameInput.setSize(100, 20);
		budgetNameInput.setLocation(300, 100);
		budgetPageForm.add(budgetNameInput);

		budgetCost = new JLabel("Amount of Budget:");
		budgetCost.setSize(100, 20);
		budgetCost.setLocation(100, 200);
		budgetPageForm.add(budgetCost);

		budgetCostInput = new JTextField();
		budgetCostInput.setSize(100, 20);
		budgetCostInput.setLocation(200, 200);
		budgetPageForm.add(budgetCostInput);

		budgetDescription = new JLabel("Description of Budget:");
		budgetDescription.setSize(100, 20);
		budgetDescription.setLocation(100, 300);
		budgetPageForm.add(budgetDescription);

		budgetDescriptionInput = new JTextField();
		budgetDescriptionInput.setSize(100, 20);
		budgetDescriptionInput.setLocation(200, 300);
		budgetPageForm.add(budgetDescriptionInput);

		budgetDate = new JLabel("Date:");
		budgetDate.setSize(100, 20);
		budgetDate.setLocation(100, 400);
		budgetPageForm.add(budgetDate);

		date = new JDateChooser();

		date.addPropertyChangeListener("date", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					Date selectedDate = (Date) evt.getNewValue();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					formattedDate = dateFormat.format(selectedDate);
					budgetDate.setText("Selected date: " + formattedDate);
				}
			}
		});

		budgetPageForm.add(date);
		
		expenseType = new JLabel("Type of Expense:");
		
		budgetPageForm.add(expenseType);
		budgetPageForm.add(buttonBox);
		
		JLabel blank = new JLabel();
		
		budgetPageForm.add(blank);

		submit = new JButton("Submit");
		submit.setBounds(20, 10, 100, 50);
		submit.addActionListener(this);
		budgetPageForm.add(submit);

		// Adding the expense form panel into the main frame
		budgetPageFrame.add(budgetPageForm, BorderLayout.CENTER);
		budgetPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		budgetPageFrame.setTitle("Add Budget");
		budgetPageFrame.setSize(800, 400);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		budgetPageFrame.setVisible(true);

	}

	public JTextField getBudgetNameInput() {
		return budgetNameInput;
	}

	public JTextField getBudgetCostInput() {
		return budgetCostInput;
	}

	public JTextField getBudgetDescriptionInput() {
		return budgetDescriptionInput;
	}

	public JTextField getBudgetDateInput() {
		return budgetDateInput;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (budgetNameInput.getText().isEmpty() || budgetCostInput.getText().isEmpty() || date == null) {
			JOptionPane.showMessageDialog(budgetPageFrame, "Please enter the name, amount, and date.");
			return;
		}

		if (this.framesCreated < 1) {
			bp = new BudgetPage();
			bp.mainEpFrame.setVisible(true);
			bp.getAddBudget().setVisible(false);

		}

		String expName = budgetNameInput.getText();
		String expNote = budgetDescriptionInput.getText();
		String expDate = formattedDate;
		double expCost = Double.parseDouble(budgetCostInput.getText());

		this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);

		DBUtil.insert(User.getLoginAs(), this.ledgerItem, "budget");

		bp.setTempLedgerItem(this.ledgerItem);
		bp.setNumberOfBudgets(bp.getNumberOfExpenses() + 1);

		try {
			bp.budgetTable = DBUtil.query(User.getLoginAs(), "tag", "budget");
			bp.mainEpFrame.dispose();
			bp = new BudgetPage();
			bp.mainEpFrame.setVisible(true);
			bp.getAddBudget().setVisible(false);
			budgetPageFrame.dispose();
		} catch (SQLException er) {

		}
		this.framesCreated++;
	}

	public LedgerItem getLedgerItem() {
		return ledgerItem;
	}

	public static void main(String[] args) {
		new BudgetPageForm();
	}

}

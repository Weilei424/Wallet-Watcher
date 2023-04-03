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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import DB.DBUtil;
import businessLogic.Recurrence;
import persistence.LedgerItem;
import persistence.User;

public class ExpensePageForm implements ActionListener {

	public JFrame expensePageFrame;
	private JPanel expensePageForm;
	private JLabel expenseName;
	public JTextField expenseNameInput;
	private JLabel expenseCost;
	public JTextField expenseCostInput;
	private JLabel expenseDescription;
	public JTextField expenseDescriptionInput;
	private JLabel expenseDate;
	// public JTextField expenseDateInput;
	private JLabel expenseType;
	private JButton submit;
	private LedgerItem ledgerItem;
	private ExpensePage ep;
	private int framesCreated;
	private ButtonGroup radioGroup;
	private JRadioButton food;
	private JRadioButton commute;
	private JRadioButton entertainment;
	private JRadioButton other;
	private JTextField othertext;
	private String category;
	private Box buttonBox;
	private JLabel dateSelector;
	private JDateChooser date;
	private String formattedDate;
	private JCheckBox checkBox;
	private boolean recur;

	public ExpensePageForm() {

		food = new JRadioButton("Food");
		commute = new JRadioButton("Commute");
		entertainment = new JRadioButton("Entertainment");
		other = new JRadioButton("Other");

		checkBox = new JCheckBox("Recurring");

		checkBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkBox.isSelected()) {
					recur = true;
				} else {
					recur = false;
				}
			}
		});

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

		expensePageFrame = new JFrame();
		expensePageFrame.setLocationRelativeTo(null);
		expensePageForm = new JPanel();

		expensePageForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		expensePageForm.setLayout(new GridLayout(7, 2));
		expensePageForm.setBackground(new Color(137, 208, 240));

		expenseName = new JLabel("Name of Expense:");
		expenseName.setSize(100, 20);
		expenseName.setLocation(100, 100);
		expensePageForm.add(expenseName);

		expenseNameInput = new JTextField();
		expenseNameInput.setSize(100, 20);
		expenseNameInput.setLocation(300, 100);
		expensePageForm.add(expenseNameInput);

		expenseCost = new JLabel("Amount of Expense:");
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

		expenseDate = new JLabel("Date:");
		expenseDate.setSize(100, 20);
		expenseDate.setLocation(100, 400);
		expensePageForm.add(expenseDate);

		date = new JDateChooser();

		date.addPropertyChangeListener("date", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					Date selectedDate = (Date) evt.getNewValue();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					formattedDate = dateFormat.format(selectedDate);
					expenseDate.setText("Selected date: " + formattedDate);
				}
			}
		});

		expensePageForm.add(date);

		expenseType = new JLabel("Type of Expense:");

		expensePageForm.add(expenseType);
		expensePageForm.add(buttonBox);

		JLabel recurring = new JLabel("Is this a recurring expense?");

		expensePageForm.add(recurring);
		expensePageForm.add(checkBox);

		JLabel blank = new JLabel();

		expensePageForm.add(blank);

		submit = new JButton("Submit");
		submit.setBounds(20, 10, 100, 50);
		submit.addActionListener(this);
		expensePageForm.add(submit);

		// Adding the expense form panel into the main frame
		expensePageFrame.add(expensePageForm, BorderLayout.CENTER);
		expensePageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		expensePageFrame.setTitle("Add Expense");
		expensePageFrame.setSize(800, 400);
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

	@Override
	public void actionPerformed(ActionEvent e) {

		if (expenseNameInput.getText().isEmpty() || expenseCostInput.getText().isEmpty() || date.getDate() == null) {
			JOptionPane.showMessageDialog(expensePageFrame, "Please enter the name, cost, and date.");
			return;
		}

		String expName = expenseNameInput.getText();
		String expNote = expenseDescriptionInput.getText();
		String expDate = formattedDate;

		try {
			double expCost = Double.parseDouble(expenseCostInput.getText());
			this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);
		} catch (NumberFormatException ex) {
			new ErrorPage("Amount is not a valid number.", ex);
			return;
		}

		this.ledgerItem.setCategory(category);
		if (recur)
			this.ledgerItem.setRecurring(new Recurrence());

		DBUtil.insert(User.getLoginAs(), this.ledgerItem, "expense");

		if (this.framesCreated < 1) {
			ep = new ExpensePage();
			ep.mainEpFrame.setVisible(true);
			ep.getAddExpense().setVisible(false);

		}

		ep.setTempLedgerItem(this.ledgerItem);
		ep.setNumberOfExpenses(ep.getNumberOfExpenses() + 1);

		try {
			ep.expenseTable = DBUtil.query(User.getLoginAs(), "tag", "expense");
			ep.mainEpFrame.dispose();
			ep = new ExpensePage();
			ep.mainEpFrame.setVisible(true);
			expensePageFrame.dispose();
		} catch (SQLException er) {
		}
		this.framesCreated++;

	}

	public LedgerItem getLedgerItem() {
		return ledgerItem;
	}

	public static void main(String[] args) {
		new ExpensePageForm();
	}

}

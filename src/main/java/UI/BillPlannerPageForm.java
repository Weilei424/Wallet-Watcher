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
import businessLogic.Recurrence;
import persistence.LedgerItem;
import persistence.User;

public class BillPlannerPageForm implements ActionListener {

	public JFrame billPlannerPageFrame;
	private JPanel billPlannerPageForm;
	private JLabel billPlannerName;
	public JTextField billPlannerNameInput;
	private JLabel billPlannerCost;
	public JTextField billPlannerCostInput;
	// JLabel expenseCategory; *No real code for this yet
	private JLabel billPlannerDescription;
	public JTextField billPlannerDescriptionInput;
	public JTextField billPlannerDateInput;
	private JLabel billType;
	private JButton submit;
	private LedgerItem ledgerItem;
	private BillPlannerPage ep;
	private int framesCreated;
	private ButtonGroup radioGroup;
	private JRadioButton utility;
	private JRadioButton creditCard;
	private JRadioButton loan;
	private JRadioButton other;
	private JTextField othertext;
	private JLabel isOther;
	private String category;
	private JLabel dateSelector;
	private JDateChooser dateChooser;
	private String formattedDate;
	private JCheckBox checkBox;
	private Box buttonBox;
	private boolean recur;

	public BillPlannerPageForm() {

		utility = new JRadioButton("Utility");
		creditCard = new JRadioButton("Credit Card");
		loan = new JRadioButton("Loan");
		other = new JRadioButton("Other");

		radioGroup = new ButtonGroup();
		radioGroup.add(utility);
		radioGroup.add(creditCard);
		radioGroup.add(loan);
		radioGroup.add(other);

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

		buttonBox.add(utility);
		buttonBox.add(creditCard);
		buttonBox.add(loan);
		buttonBox.add(other);

		utility.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (utility.isSelected())
					category = "Utility";
			}
		});

		creditCard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (creditCard.isSelected())
					category = "Credit Card";
			}
		});

		loan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (loan.isSelected())
					category = "Loan";
			}
		});

		other.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (other.isSelected()) {
					category = othertext.getText();
				} else {
					othertext.setEnabled(false);
				}
			}
		});

		this.framesCreated = 0;

		billPlannerPageFrame = new JFrame();
		billPlannerPageFrame.setLocationRelativeTo(null);
		billPlannerPageForm = new JPanel();

		billPlannerPageForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		billPlannerPageForm.setLayout(new GridLayout(8, 2));
		billPlannerPageForm.setBackground(new Color(137, 208, 240));

		billPlannerName = new JLabel("Name of Bill:");
		billPlannerName.setSize(100, 20);
		billPlannerName.setLocation(100, 100);
		billPlannerPageForm.add(billPlannerName);

		billPlannerNameInput = new JTextField();
		billPlannerNameInput.setSize(100, 20);
		billPlannerNameInput.setLocation(300, 100);
		billPlannerPageForm.add(billPlannerNameInput);

		billPlannerCost = new JLabel("Bill Amount:");
		billPlannerCost.setSize(100, 20);
		billPlannerCost.setLocation(100, 200);
		billPlannerPageForm.add(billPlannerCost);

		billPlannerCostInput = new JTextField();
		billPlannerCostInput.setSize(100, 20);
		billPlannerCostInput.setLocation(200, 200);
		billPlannerPageForm.add(billPlannerCostInput);

		billPlannerDescription = new JLabel("Description of Bill:");
		billPlannerDescription.setSize(100, 20);
		billPlannerDescription.setLocation(100, 300);
		billPlannerPageForm.add(billPlannerDescription);

		billPlannerDescriptionInput = new JTextField();
		billPlannerDescriptionInput.setSize(100, 20);
		billPlannerDescriptionInput.setLocation(200, 300);
		billPlannerPageForm.add(billPlannerDescriptionInput);

		dateSelector = new JLabel("Date:");
		dateSelector.setSize(100, 20);
		dateSelector.setLocation(100, 400);
		billPlannerPageForm.add(dateSelector);

		isOther = new JLabel("If Bill is Other:");
		othertext = new JTextField();

		dateChooser = new JDateChooser();

		dateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					Date selectedDate = (Date) evt.getNewValue();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					formattedDate = dateFormat.format(selectedDate);
					dateSelector.setText("Selected date: " + formattedDate);
				}
			}
		});

		billPlannerPageForm.add(dateChooser);

		billPlannerPageForm.add(isOther);
		billPlannerPageForm.add(othertext);

		billType = new JLabel("Type of Bill:");

		billPlannerPageForm.add(billType);
		billPlannerPageForm.add(buttonBox);

		JLabel recurring = new JLabel("Is this a recurring expense?");

		billPlannerPageForm.add(recurring);
		billPlannerPageForm.add(checkBox);

		JLabel blank = new JLabel();

		billPlannerPageForm.add(blank);

		submit = new JButton("Submit");
		submit.setBounds(20, 10, 100, 50);
		submit.addActionListener(this);
		billPlannerPageForm.add(submit);

		// Adding the expense form panel into the main frame
		billPlannerPageFrame.add(billPlannerPageForm, BorderLayout.CENTER);
		billPlannerPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		billPlannerPageFrame.setTitle("Add Bill");
		billPlannerPageFrame.setSize(800, 400);
		// billPlannerPageFrame.pack(); // when setSize on, then remove pack
		billPlannerPageFrame.setVisible(true);

	}

	public JTextField getInvestmentNameInput() {
		return billPlannerNameInput;
	}

	public JTextField getInvestmentCostInput() {
		return billPlannerCostInput;
	}

	public JTextField getInvestmentDescriptionInput() {
		return billPlannerDescriptionInput;
	}

	public JTextField getInvestmentDateInput() {
		return billPlannerDateInput;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (billPlannerNameInput.getText().isEmpty() || billPlannerCostInput.getText().isEmpty()
				|| dateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(billPlannerPageFrame, "Please enter the name, cost, and date.");
			return;
		}

		String expName = billPlannerNameInput.getText();
		String expNote = billPlannerDescriptionInput.getText();
		String expDate = formattedDate;

		try {
			double expCost = Double.parseDouble(billPlannerCostInput.getText());
			this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);
		} catch (NumberFormatException ex) {
			new ErrorPage("Amount is not a valid number.", ex);
			return;
		}

		this.ledgerItem.setCategory(category);
		if (recur)
			this.ledgerItem.setRecurring(new Recurrence());

		DBUtil.insert(User.getLoginAs(), this.ledgerItem, "bill");

		if (this.framesCreated < 1) {
			ep = new BillPlannerPage();
			ep.mainBpPage.setVisible(true);
			ep.getAddNewBill().setVisible(false);

		}

		try {
			ep.setBillTable(DBUtil.query(User.getLoginAs(), "tag", "bill"));
			ep.mainBpPage.dispose();
			ep = new BillPlannerPage();
			ep.mainBpPage.setVisible(true);
			billPlannerPageFrame.dispose();
		} catch (SQLException er) {

		}
		this.framesCreated++;
	}

	public LedgerItem getLedgerItem() {
		return ledgerItem;
	}

	public static void main(String[] args) {
		new BillPlannerPageForm();
	}

}

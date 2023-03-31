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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import DB.DBUtil;
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
	private JLabel billPlannerDate;
	public JTextField billPlannerDateInput;
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
	private String category;
	private JLabel dateSelector;
	private JDateChooser dateChooser;
	private String formattedDate;

	public BillPlannerPageForm() {
		this.framesCreated = 0;

		billPlannerPageFrame = new JFrame();
		billPlannerPageFrame.setLocationRelativeTo(null);
		billPlannerPageForm = new JPanel();
		radioGroup = new ButtonGroup();
		othertext = new JTextField(20);
		othertext.setPreferredSize(null);

		utility = new JRadioButton("Utility");
		utility.setBorderPainted(true);
		creditCard = new JRadioButton("Credit Card");
		creditCard.setBorderPainted(true);
		loan = new JRadioButton("Loan");
		loan.setBorderPainted(true);
		other = new JRadioButton("Other:");

		radioGroup.add(utility);
		radioGroup.add(creditCard);
		radioGroup.add(loan);
		radioGroup.add(other);
		category = "default";

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
					othertext.setEnabled(true);
					othertext.requestFocus();
					category = othertext.getText();
				} else {
					othertext.setEnabled(false);
				}
			}
		});

		billPlannerPageForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		billPlannerPageForm.setLayout(new GridLayout(5, 1));
		billPlannerPageForm.setBackground(Color.cyan);

		billPlannerName = new JLabel("Name of Bill:");
		billPlannerName.setSize(100, 20);
		billPlannerName.setLocation(100, 100);
		billPlannerPageForm.add(billPlannerName);

		billPlannerNameInput = new JTextField();
		billPlannerNameInput.setSize(100, 20);
		billPlannerNameInput.setLocation(300, 100);
		billPlannerPageForm.add(billPlannerNameInput);

		billPlannerCost = new JLabel("Amount of Bill:");
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

		dateSelector = new JLabel("Selected date: ");
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
		billPlannerPageForm.add(dateSelector);
		billPlannerPageForm.add(dateChooser);

		billPlannerPageForm.add(utility);
		billPlannerPageForm.add(creditCard);
		billPlannerPageForm.add(loan);
		billPlannerPageForm.add(other);
		billPlannerPageForm.add(othertext);

		submit = new JButton("Submit");
		submit.setBounds(20, 10, 100, 50);
		submit.addActionListener(this);
		billPlannerPageForm.add(submit);

		// Adding the expense form panel into the main frame
		billPlannerPageFrame.add(billPlannerPageForm, BorderLayout.CENTER);
		billPlannerPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		billPlannerPageFrame.setTitle("Add Bill");
		billPlannerPageFrame.setSize(600, 400);
		// expensePageFrame.pack(); // when setSize on, then remove pack
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

		if (this.framesCreated < 1) {
			ep = new BillPlannerPage();
			ep.mainBpPage.setVisible(true);
			ep.getAddNewBill().setVisible(false);

		}

		String expName = billPlannerNameInput.getText();
		String expNote = billPlannerDescriptionInput.getText();
		String expDate = formattedDate;
		double expCost = Double.parseDouble(billPlannerCostInput.getText());

		this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);
		this.ledgerItem.setCategory(category);

		DBUtil.insert(User.getLoginAs(), this.ledgerItem, "bill");

		try {
			ep.setBillTable(DBUtil.query(User.getLoginAs(), "tag", "bill"));
			ep.mainBpPage.dispose();
			ep = new BillPlannerPage();
			ep.mainBpPage.setVisible(true);
			ep.getAddNewBill().setVisible(false);
			// billPlannerPageFrame.dispose();
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

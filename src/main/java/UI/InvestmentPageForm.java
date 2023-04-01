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

public class InvestmentPageForm implements ActionListener {

	public JFrame investmentPageFrame;
	private JPanel investmentPageForm;
	private JLabel investmentName;
	public JTextField investmentNameInput;
	private JLabel investmentCost;
	public JTextField investmentCostInput;
	private JLabel investmentDescription;
	public JTextField investmentDescriptionInput;
	public JTextField investmentDateInput;
	private JButton submit;
	private LedgerItem ledgerItem;
	private InvestmentPage ep;
	private int framesCreated;
	private ButtonGroup radioGroup;
	private JRadioButton stock;
	private JRadioButton bond;
	private JRadioButton saving;
	private JRadioButton other;
	private JTextField othertext;
	private String category;
	private JLabel dateSelector;
	private JDateChooser dateChooser;
	private String formattedDate;
	private JCheckBox checkBox;
	private boolean recur;

	public InvestmentPageForm() {
		this.framesCreated = 0;

		investmentPageFrame = new JFrame();
		investmentPageFrame.setLocationRelativeTo(null);
		investmentPageForm = new JPanel();
		radioGroup = new ButtonGroup();
		othertext = new JTextField(20);
		othertext.setPreferredSize(null);
		
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

		stock = new JRadioButton("Stock");
		stock.setBorderPainted(true);
		bond = new JRadioButton("Bond");
		bond.setBorderPainted(true);
		saving = new JRadioButton("Saving acc");
		saving.setBorderPainted(true);
		other = new JRadioButton("Other:");

		radioGroup.add(stock);
		radioGroup.add(bond);
		radioGroup.add(saving);
		radioGroup.add(other);
		category = "default";

		stock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (stock.isSelected())
					category = "Stock";
			}
		});

		bond.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (bond.isSelected())
					category = "Bond";
			}
		});

		saving.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (saving.isSelected())
					category = "Saving account";
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

		investmentPageForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		investmentPageForm.setLayout(new GridLayout(5, 1));
		investmentPageForm.setBackground(new Color(137, 208, 240));

		investmentName = new JLabel("Name of Investment:");
		investmentName.setSize(100, 20);
		investmentName.setLocation(100, 100);
		investmentPageForm.add(investmentName);

		investmentNameInput = new JTextField();
		investmentNameInput.setSize(100, 20);
		investmentNameInput.setLocation(300, 100);
		investmentPageForm.add(investmentNameInput);

		investmentCost = new JLabel("Amount of Investment:");
		investmentCost.setSize(100, 20);
		investmentCost.setLocation(100, 200);
		investmentPageForm.add(investmentCost);

		investmentCostInput = new JTextField();
		investmentCostInput.setSize(100, 20);
		investmentCostInput.setLocation(200, 200);
		investmentPageForm.add(investmentCostInput);

		investmentDescription = new JLabel("Description:");
		investmentDescription.setSize(100, 20);
		investmentDescription.setLocation(100, 300);
		investmentPageForm.add(investmentDescription);

		investmentDescriptionInput = new JTextField();
		investmentDescriptionInput.setSize(100, 20);
		investmentDescriptionInput.setLocation(200, 300);
		investmentPageForm.add(investmentDescriptionInput);

		investmentPageForm.add(checkBox);
		
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
		investmentPageForm.add(dateSelector);
		investmentPageForm.add(dateChooser);

		investmentPageForm.add(stock);
		investmentPageForm.add(bond);
		investmentPageForm.add(saving);
		investmentPageForm.add(other);
		investmentPageForm.add(othertext);

		submit = new JButton("Submit");
		submit.setBounds(20, 10, 100, 50);
		submit.addActionListener(this);
		investmentPageForm.add(submit);

		// Adding the expense form panel into the main frame
		investmentPageFrame.add(investmentPageForm, BorderLayout.CENTER);
		investmentPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		investmentPageFrame.setTitle("Add Investment");
		investmentPageFrame.setSize(600, 400);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		investmentPageFrame.setVisible(true);

	}

	public JTextField getInvestmentNameInput() {
		return investmentNameInput;
	}

	public JTextField getInvestmentCostInput() {
		return investmentCostInput;
	}

	public JTextField getInvestmentDescriptionInput() {
		return investmentDescriptionInput;
	}

	public JTextField getInvestmentDateInput() {
		return investmentDateInput;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (investmentNameInput.getText().isEmpty() || investmentCostInput.getText().isEmpty() || dateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(investmentPageFrame, "Please enter the name, amount, and date.");
			return;
		}

		String expName = investmentNameInput.getText();
		String expNote = investmentDescriptionInput.getText();
		String expDate = formattedDate;

		try {
			double expCost = Double.parseDouble(investmentCostInput.getText());
			this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);
		} catch (NumberFormatException ex) {
			new ErrorPage("Amount is not a valid number.", ex);
			return;
		}

		this.ledgerItem.setCategory(category);
		if (recur)
			this.ledgerItem.setRecurring(new Recurrence());
		
		DBUtil.insert(User.getLoginAs(), this.ledgerItem, "investment");

		if (this.framesCreated < 1) {
			ep = new InvestmentPage();
			ep.mainIvFrame.setVisible(true);
			ep.getAddInvestment().setVisible(false);

		}

		ep.setTempLedgerItem(this.ledgerItem);
		ep.setNumberOfInvestment(ep.getNumberOfInvestment() + 1);

		try {
			ep.investmentTable = DBUtil.query(User.getLoginAs(), "tag", "investment");
			ep.mainIvFrame.dispose();
			ep = new InvestmentPage();
			ep.mainIvFrame.setVisible(true);
			ep.getAddInvestment().setVisible(false);
			investmentPageFrame.dispose();
		} catch (SQLException er) {

		}
		this.framesCreated++;
	}

	public LedgerItem getLedgerItem() {
		return ledgerItem;
	}

	public static void main(String[] args) {
		new InvestmentPageForm();
	}

}

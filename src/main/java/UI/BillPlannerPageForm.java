package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

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
	private InvestmentPage ep;
	private int framesCreated;
	private ButtonGroup radioGroup;
	private JRadioButton utility;
	private JRadioButton creditCard;
	private JRadioButton loan;
	private JRadioButton other;
	private JTextField othertext;
	private String category;

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
		other.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (other.isSelected()) {
                    othertext.setEnabled(true);
                    othertext.requestFocus();
                    category = othertext.getText();
                } else if (utility.isSelected()) {
                	category = "Utility";
                } else if (creditCard.isSelected()) {
                	category = "Credit Card";
                } else if (loan.isSelected()) {
                	category = "Loan";
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

		billPlannerDate = new JLabel("Date due:");
		billPlannerDate.setSize(100, 20);
		billPlannerDate.setLocation(100, 400);
		billPlannerPageForm.add(billPlannerDate);

		billPlannerDateInput = new JTextField();
		billPlannerDateInput.setSize(100, 20);
		billPlannerDateInput.setLocation(200, 400);
		billPlannerPageForm.add(billPlannerDateInput);

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

		if (billPlannerNameInput.getText().isEmpty() || billPlannerCostInput.getText().isEmpty() || billPlannerDateInput.getText().isEmpty()) {
			JOptionPane.showMessageDialog(billPlannerPageFrame, "Please enter the name, cost, and date.");
			return;
		}

		String expName = billPlannerNameInput.getText();
		String expNote = billPlannerDescriptionInput.getText();
		String expDate = billPlannerDateInput.getText();

		try {
			double expCost = Double.parseDouble(billPlannerCostInput.getText());
			this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);
		} catch (NumberFormatException ex) {
			new ErrorPage("Amount is not a valid number.", ex);
			return;
		} catch (IllegalArgumentException ex) {
			new ErrorPage("Date is not a valid date.", ex);
			return;
		}

		this.ledgerItem.setCategory(category);
		
		DBUtil.insert(User.getLoginAs(), this.ledgerItem, "bill");

		if (this.framesCreated < 1) {
			ep = new InvestmentPage();
			ep.mainIvFrame.setVisible(true);
			ep.getAddInvestment().setVisible(false);

		}

		ep.setTempLedgerItem(this.ledgerItem);
		ep.setNumberOfInvestment(ep.getNumberOfInvestment() + 1);

		try {
			ep.investmentTable = DBUtil.query(User.getLoginAs(),"tag","bill");
			ep.mainIvFrame.dispose();
			ep = new InvestmentPage();
			ep.mainIvFrame.setVisible(true);
			ep.getAddInvestment().setVisible(false);
			billPlannerPageFrame.dispose();
			} catch(SQLException er) {
				
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

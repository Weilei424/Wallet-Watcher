package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import DB.DBUtil;
import persistence.LedgerItem;
import persistence.User;

public class InvestmentPageForm implements ActionListener {

	public JFrame investmentPageFrame;
	private JPanel investmentPageForm;
	private JLabel investmentName;
	public JTextField investmentNameInput;
	private JLabel investmentCost;
	public JTextField investmentCostInput;
	// JLabel expenseCategory; *No real code for this yet
	private JLabel investmentDescription;
	public JTextField investmentDescriptionInput;
	private JLabel investmentDate;
	public JTextField investmentDateInput;
	private JButton submit;
	private LedgerItem ledgerItem;
	private InvestmentPage ep;
	private int framesCreated;
	private ButtonGroup radioGroup;
	private JRadioButton stock;
	private JRadioButton bond;
	private JRadioButton mfund;
	private JRadioButton gic;
	private JRadioButton saving;
	private JRadioButton other;
	private JTextField othertext;
	private String category;

	public InvestmentPageForm() {
		this.framesCreated = 0;

		investmentPageFrame = new JFrame();
		investmentPageFrame.setLocationRelativeTo(null);
		investmentPageForm = new JPanel();
		radioGroup = new ButtonGroup();
		othertext = new JTextField(20);
		othertext.setPreferredSize(null);
		
		stock = new JRadioButton("Stock");
		stock.setBorderPainted(true);
		bond = new JRadioButton("Bond");
		bond.setBorderPainted(true);
		mfund = new JRadioButton("Mutual Fund");
		mfund.setBorderPainted(true);
		gic = new JRadioButton("GIC");
		gic.setBorderPainted(true);
		saving = new JRadioButton("Saving acc");
		saving.setBorderPainted(true);
		other = new JRadioButton("Other:");
		
		
		radioGroup.add(stock);
		radioGroup.add(bond);
		radioGroup.add(mfund);
		radioGroup.add(gic);
		radioGroup.add(saving);
		radioGroup.add(other);
		category = "default";
		other.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (other.isSelected()) {
                    othertext.setEnabled(true);
                    othertext.requestFocus();
                    category = othertext.getText();
                } else if (stock.isSelected()) {
                	category = "Stock";
                } else if (bond.isSelected()) {
                	category = "Bond";
                } else if (mfund.isSelected()) {
                	category = "Mutual Fund";
                } else if (gic.isSelected()) {
                	category = "GIC";
                } else if (saving.isSelected()) {
                	category = "Saving acc";
                } else {
                    othertext.setEnabled(false);
                }
            }
        });
		investmentPageForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		investmentPageForm.setLayout(new GridLayout(5, 1));
		investmentPageForm.setBackground(Color.cyan);

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

		investmentDate = new JLabel("Date:");
		investmentDate.setSize(100, 20);
		investmentDate.setLocation(100, 400);
		investmentPageForm.add(investmentDate);

		investmentDateInput = new JTextField();
		investmentDateInput.setSize(100, 20);
		investmentDateInput.setLocation(200, 400);
		investmentPageForm.add(investmentDateInput);

		investmentPageForm.add(stock);
		investmentPageForm.add(bond);
		investmentPageForm.add(mfund);
		investmentPageForm.add(gic);
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

		if (this.framesCreated < 1) {
			ep = new InvestmentPage();
			ep.mainIvFrame.setVisible(true);
			ep.getAddInvestment().setVisible(false);
			
		}

		String expName = investmentNameInput.getText();
		String expNote = investmentDescriptionInput.getText();
		String expDate = investmentDateInput.getText();
		double expCost = Double.parseDouble(investmentCostInput.getText());

		this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);
		this.ledgerItem.setCategory(category);
		
		DBUtil.insert(User.getLoginAs(), this.ledgerItem, "investment");

		ep.setTempLedgerItem(this.ledgerItem);
		ep.setNumberOfInvestment(ep.getNumberOfInvestment() + 1);

		try {
			ep.investmentTable = DBUtil.query(User.getLoginAs(),"tag","investment");
			ep.mainIvFrame.dispose();
			ep = new InvestmentPage();
			ep.mainIvFrame.setVisible(true);
			ep.getAddInvestment().setVisible(false);
			investmentPageFrame.dispose();
			} catch(SQLException er) {
				
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

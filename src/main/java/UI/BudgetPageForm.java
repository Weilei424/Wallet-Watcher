package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	private int framesCreated;

	public BudgetPageForm() {
		this.framesCreated = 0;

		budgetPageFrame = new JFrame();
		budgetPageForm = new JPanel();

		budgetPageForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		budgetPageForm.setLayout(new GridLayout(5, 1));
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

		budgetDateInput = new JTextField();
		budgetDateInput.setSize(100, 20);
		budgetDateInput.setLocation(200, 400);
		budgetPageForm.add(budgetDateInput);

		submit = new JButton("Submit");
		submit.setBounds(20, 10, 100, 50);
		submit.addActionListener(this);
		budgetPageForm.add(submit);

		// Adding the expense form panel into the main frame
		budgetPageFrame.add(budgetPageForm, BorderLayout.CENTER);
		budgetPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		budgetPageFrame.setTitle("Add Budget");
		budgetPageFrame.setSize(400, 300);
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

		if (this.framesCreated < 1) {
			bp = new BudgetPage();
			bp.mainEpFrame.setVisible(true);
			bp.getAddBudget().setVisible(false);
			
		}

		String expName = budgetNameInput.getText();
		String expNote = budgetDescriptionInput.getText();
		String expDate = budgetDateInput.getText();
		double expCost = Double.parseDouble(budgetCostInput.getText());

		this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);

		DBUtil.insert(User.getLoginAs(), this.ledgerItem, "budget");

		bp.setTempLedgerItem(this.ledgerItem);
		bp.setNumberOfBudgets(bp.getNumberOfExpenses() + 1);

		try {
			bp.budgetTable = DBUtil.query(User.getLoginAs(),"tag","budget");
			bp.mainEpFrame.dispose();
			bp = new BudgetPage();
			bp.mainEpFrame.setVisible(true);
			bp.getAddBudget().setVisible(false);
			budgetPageFrame.dispose();
			} catch(SQLException er) {
				
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

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
import persistence.ExpenseInputData;
import persistence.LedgerItem;
import persistence.User;

public class CardPursePageForm implements ActionListener{	

		public JFrame cardPurseFrame;
		private JPanel cardPurseForm;
		private JLabel cardName;
		public JTextField cardNameInput;
		private JLabel cardCost;
		public JTextField cardCostInput;
		// JLabel expenseCategory; *No real code for this yet
		private JLabel cardDescription;
		public JTextField cardDescriptionInput;
		private JLabel cardDate;
		public JTextField cardDateInput;
		private JButton submit;
		private LedgerItem ledgerItem;
		private CardPursePage cpp;
		private int framesCreated;

		public CardPursePageForm() {

			this.ledgerItem = ledgerItem;
			this.framesCreated = 0;

			cardPurseFrame = new JFrame();
			cardPurseFrame.setLocationRelativeTo(null);
			cardPurseForm = new JPanel();

			cardPurseForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
			cardPurseForm.setLayout(new GridLayout(5, 1));
			cardPurseForm.setBackground(Color.cyan);

			cardName = new JLabel("Name of Card:");
			cardName.setSize(100, 20);
			cardName.setLocation(100, 100);
			cardPurseForm.add(cardName);

			cardNameInput = new JTextField();
			// expenseNameInput.setBounds(200, 100, 100, 20);
			cardNameInput.setSize(100, 20);
			cardNameInput.setLocation(300, 100);
			cardPurseForm.add(cardNameInput);

			cardCost = new JLabel("Card Balance:");
			cardCost.setSize(100, 20);
			cardCost.setLocation(100, 200);
			cardPurseForm.add(cardCost);

			cardCostInput = new JTextField();
			cardCostInput.setSize(100, 20);
			cardCostInput.setLocation(200, 200);
			cardPurseForm.add(cardCostInput);

			cardDescription = new JLabel("Description of Card:");
			cardDescription.setSize(100, 20);
			cardDescription.setLocation(100, 300);
			cardPurseForm.add(cardDescription);

			cardDescriptionInput = new JTextField();
			cardDescriptionInput.setSize(100, 20);
			cardDescriptionInput.setLocation(200, 300);
			cardPurseForm.add(cardDescriptionInput);

			cardDate = new JLabel("Date of payment:");
			cardDate.setSize(100, 20);
			cardDate.setLocation(100, 400);
			cardPurseForm.add(cardDate);

			cardDateInput = new JTextField();
			cardDateInput.setSize(100, 20);
			cardDateInput.setLocation(200, 400);
			cardPurseForm.add(cardDateInput);

			submit = new JButton("Submit");
			submit.setBounds(20, 10, 100, 50);
			submit.addActionListener(this);
			cardPurseForm.add(submit);

			// Adding the expense form panel into the main frame
			cardPurseFrame.add(cardPurseForm, BorderLayout.CENTER);
			cardPurseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			cardPurseFrame.setTitle("Add Cards");
			cardPurseFrame.setSize(400, 300);
			// expensePageFrame.pack(); // when setSize on, then remove pack
			cardPurseFrame.setVisible(true);

		}

		public JTextField getExpenseNameInput() {
			return cardNameInput;
		}

		public JTextField getExpenseCostInput() {
			return cardCostInput;
		}

		public JTextField getExpenseDescriptionInput() {
			return cardDescriptionInput;
		}

		public JTextField getExpenseDateInput() {
			return cardDateInput;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (this.framesCreated < 1) {
				cpp = new CardPursePage();
				cpp.mainCpPage.setVisible(true);
				cpp.getAddNewCard().setVisible(false);
				
			}

			String expName = cardNameInput.getText();
			String expNote = cardDescriptionInput.getText();
			String expDate = cardDateInput.getText();
			double expCost = Double.parseDouble(cardCostInput.getText());

			this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);

			DBUtil.insert(User.getLoginAs(), this.ledgerItem, "card");

			
			// String previousText = cpp.getLedgerInfo().getText();
			// cpp.getLedgerInfo().append("\n");
			// cpp.getLedgerInfo().append(this.getLedgerItem().getItemName() + "\t" + "\t");
			// cpp.getLedgerInfo().append(this.getLedgerItem().getAmount() + "\t" + "\t");
			// cpp.getLedgerInfo().append(this.getLedgerItem().getDate() + "\t" + "\t");
			// cpp.getLedgerInfo().append(this.getLedgerItem().getNote() + "\t");
		//	cpp.setNumberOfExpenses(cpp.getNumberOfExpenses() + 1);

			try {
				cpp.cardPurseTable = DBUtil.query(User.getLoginAs(),"tag","expense");
				cpp.mainCpPage.dispose();
				cpp = new CardPursePage();
				cpp.mainCpPage.setVisible(true);
				cpp.getAddNewCard().setVisible(false);
				} catch(SQLException er) {
					
				}
			
//			if (cpp.isRemoved() == true) {
//				cpp.getLedgerInfo().setText(null);
//				cpp.getLedgerInfo().append("Name of Expense" + "\t");
//				cpp.getLedgerInfo().append("Cost of Expense" + "\t");
//				cpp.getLedgerInfo().append("Date Due" + "\t");
//				cpp.getLedgerInfo().append("Special Notes");
//				cpp.setRemoved(false);
//			}

			this.framesCreated++;

//			System.out.println(this.ledgerItem.getItemName());
//			System.out.println(this.ledgerItem.getAmount());
//			System.out.println(this.ledgerItem.getNote());
			// System.out.println(this.ledgerItem.getDate());

		}

		public LedgerItem getLedgerItem() {
			return ledgerItem;
		}

		public static void main(String[] args) {
			new CardPursePageForm();
		}

	

	
}

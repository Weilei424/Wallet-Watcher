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

public class CardPursePageForm implements ActionListener{	

		public JFrame cardPurseFrame;
		private JPanel cardPurseForm;
		private JLabel cardName;
		public JTextField cardNameInput;
		private JLabel cardCost;
		public JTextField cardCostInput;
		private JLabel cardDescription;
		public JTextField cardDescriptionInput;
		private JLabel cardDate;
		public JTextField cardDateInput;
		private JButton submit;
		private LedgerItem ledgerItem;
		private CardPursePage cpp;
		private int framesCreated;
		private ButtonGroup radioGroup;
		private JRadioButton debitCard;
		private JRadioButton creditCard;
		private JRadioButton pointsCard;
		private JRadioButton other;
		private JTextField othertext;
		private String category;
		private JLabel dateSelector;
		private JDateChooser dateChooser;
		private String formattedDate;

		public CardPursePageForm() {
			this.framesCreated = 0;

			cardPurseFrame = new JFrame();
			cardPurseFrame.setLocationRelativeTo(null);
			cardPurseForm = new JPanel();
			radioGroup = new ButtonGroup();
			othertext = new JTextField(20);
			othertext.setPreferredSize(null);
			
			debitCard = new JRadioButton("Debit Card");
			debitCard.setBorderPainted(true);
			creditCard = new JRadioButton("Credit Card");
			creditCard.setBorderPainted(true);
			pointsCard = new JRadioButton("Points Card");
			pointsCard.setBorderPainted(true);
			other = new JRadioButton("Other:");

			radioGroup.add(debitCard);
			radioGroup.add(creditCard);
			radioGroup.add(pointsCard);
			radioGroup.add(other);
			category = "default";

			debitCard.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (debitCard.isSelected())
						category = "Debit Card";
				}
			});

			creditCard.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (creditCard.isSelected())
						category = "Credit Card";
				}
			});

			pointsCard.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (pointsCard.isSelected())
						category = "Points Card";
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
			
			cardPurseForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
			cardPurseForm.setLayout(new GridLayout(5, 1));
			cardPurseForm.setBackground(Color.cyan);

			cardName = new JLabel("Name of Card:");
			cardName.setSize(100, 20);
			cardName.setLocation(100, 100);
			cardPurseForm.add(cardName);

			cardNameInput = new JTextField();
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
			cardPurseForm.add(dateSelector);
			cardPurseForm.add(dateChooser);
			
			cardPurseForm.add(debitCard);
			cardPurseForm.add(creditCard);
			cardPurseForm.add(pointsCard);
			cardPurseForm.add(other);
			cardPurseForm.add(othertext);

			submit = new JButton("Submit");
			submit.setBounds(20, 10, 100, 50);
			submit.addActionListener(this);
			cardPurseForm.add(submit);

			// Adding the expense form panel into the main frame
			cardPurseFrame.add(cardPurseForm, BorderLayout.CENTER);
			cardPurseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			cardPurseFrame.setTitle("Add Cards");
			cardPurseFrame.setSize(600, 400);
			cardPurseFrame.setVisible(true);

		}

		public JTextField getCardNameInput() {
			return cardNameInput;
		}

		public JTextField getCardCostInput() {
			return cardCostInput;
		}

		public JTextField getCardDescriptionInput() {
			return cardDescriptionInput;
		}

		public JTextField getCardDateInput() {
			return cardDateInput;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (cardNameInput.getText().isEmpty() || cardCostInput.getText().isEmpty() || dateChooser.getDate() == null) {
				JOptionPane.showMessageDialog(cardPurseFrame, "Please enter the name, balance, and date.");
				return;
			}

			if (this.framesCreated < 1) {
				cpp = new CardPursePage();
				cpp.mainCpPage.setVisible(true);
				cpp.getAddNewCard().setVisible(false);
				
			}

			String expName = cardNameInput.getText();
			String expNote = cardDescriptionInput.getText();
			String expDate = formattedDate;
			double expCost = Double.parseDouble(cardCostInput.getText());

			this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);
			this.ledgerItem.setCategory(category);
			DBUtil.insert(User.getLoginAs(), this.ledgerItem, "card");

		

			try {
				cpp.cardPurseTable = DBUtil.query(User.getLoginAs(),"tag","card");
				cpp.mainCpPage.dispose();
				cpp = new CardPursePage();
				cpp.mainCpPage.setVisible(true);
				cpp.getAddNewCard().setVisible(false);
				} catch(SQLException er) {
					
				}
			this.framesCreated++;
		}

		public LedgerItem getLedgerItem() {
			return ledgerItem;
		}

		public static void main(String[] args) {
			new CardPursePageForm();
		}
}

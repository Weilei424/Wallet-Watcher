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

public class CardPursePageForm implements ActionListener {

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
	private JLabel isOther;
	private JLabel cardType;
	private String category;
	private JLabel dateChoice;
	private JDateChooser dateSelector;
	private String formattedDate;
	private Box buttonBox;

	public CardPursePageForm() {
		debitCard = new JRadioButton("Debit Card");
		creditCard = new JRadioButton("Credit Card");
		pointsCard = new JRadioButton("Points Card");
		other = new JRadioButton("Other");

		radioGroup = new ButtonGroup();
		radioGroup.add(debitCard);
		radioGroup.add(creditCard);
		radioGroup.add(pointsCard);
		radioGroup.add(other);

//			checkBox = new JCheckBox("Recurring");
//
//			checkBox.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					if (checkBox.isSelected()) {
//						recur = true;
//					} else {
//						recur = false;
//					}
//				}
//			});

		buttonBox = Box.createHorizontalBox();

		buttonBox.add(debitCard);
		buttonBox.add(creditCard);
		buttonBox.add(pointsCard);
		buttonBox.add(other);

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
					category = othertext.getText();
				} else {
					othertext.setEnabled(false);
				}
			}
		});

		this.framesCreated = 0;

		cardPurseFrame = new JFrame();
		cardPurseFrame.setLocationRelativeTo(null);
		cardPurseForm = new JPanel();

		cardPurseForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		cardPurseForm.setLayout(new GridLayout(7, 2));
		cardPurseForm.setBackground(new Color(137, 208, 240));

		cardName = new JLabel("Name of Card:");
		cardName.setSize(100, 20);
		cardName.setLocation(100, 100);
		cardPurseForm.add(cardName);

		cardNameInput = new JTextField();
		cardNameInput.setSize(100, 20);
		cardNameInput.setLocation(300, 100);
		cardPurseForm.add(cardNameInput);

		cardCost = new JLabel("Card Amount:");
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

		dateChoice = new JLabel("Date:");
		dateChoice.setSize(100, 20);
		dateChoice.setLocation(100, 400);
		cardPurseForm.add(dateChoice);

		isOther = new JLabel("If Card is Other:");
		othertext = new JTextField();

		dateSelector = new JDateChooser();

		dateSelector.addPropertyChangeListener("date", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					Date selectedDate = (Date) evt.getNewValue();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					formattedDate = dateFormat.format(selectedDate);
					dateChoice.setText("Selected date: " + formattedDate);
				}
			}
		});

		cardPurseForm.add(dateSelector);

		cardPurseForm.add(isOther);
		cardPurseForm.add(othertext);

		cardType = new JLabel("Type of Card:");

		cardPurseForm.add(cardType);
		cardPurseForm.add(buttonBox);

		JLabel blank = new JLabel();

		cardPurseForm.add(blank);

		submit = new JButton("Submit");
		submit.setBounds(20, 10, 100, 50);
		submit.addActionListener(this);
		cardPurseForm.add(submit);

		// Adding the expense form panel into the main frame
		cardPurseFrame.add(cardPurseForm, BorderLayout.CENTER);
		cardPurseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cardPurseFrame.setTitle("Add Card");
		cardPurseFrame.setSize(800, 400);
		// cardPurseFrame.pack(); // when setSize on, then remove pack
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

		if (cardNameInput.getText().isEmpty() || cardCostInput.getText().isEmpty() || dateSelector.getDate() == null) {
			JOptionPane.showMessageDialog(cardPurseFrame, "Please enter the name, balance, and date.");
			return;
		}

		String expName = cardNameInput.getText();
		String expNote = cardDescriptionInput.getText();
		String expDate = formattedDate;

		try {
			double expCost = Double.parseDouble(cardCostInput.getText());
			this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);
		} catch (NumberFormatException ex) {
			new ErrorPage("Amount is not a valid number.", ex);
			return;
		}

		this.ledgerItem.setCategory(category);
		DBUtil.insert(User.getLoginAs(), this.ledgerItem, "card");

		if (this.framesCreated < 1) {
			cpp = new CardPursePage();
			cpp.mainCpPage.setVisible(true);
			cpp.getAddNewCard().setVisible(false);

		}

		try {
			cpp.cardPurseTable = DBUtil.query(User.getLoginAs(), "tag", "card");
			cpp.mainCpPage.dispose();
			cpp = new CardPursePage();
			cpp.mainCpPage.setVisible(true);
			cardPurseFrame.dispose();
		} catch (SQLException er) {

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

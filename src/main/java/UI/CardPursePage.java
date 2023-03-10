package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import persistence.DBUtil;
import persistence.LedgerItem;

public class CardPursePage implements ActionListener {

	public JFrame mainCpPage;
	private JPanel mainCpPanel;
	private JPanel mainPanel; // main panel to hold all other panels in the expense page form
	// JPanel ledgerPanel;
	private JButton addNewCard;
	private JButton removeCard;
	private JButton toMenu;
	private JLabel title;
	// private JTextArea cardInfo;
	private ExpensePageForm epForm;
	private LedgerItem tempLedgerItem;
	public JTable cardPurseTable;
	public JScrollPane cardScroller;
	private navigatorPage nav;
	private CardPursePageForm cppf;

	public CardPursePage() {

		try {
			cardPurseTable = DBUtil.query("ceojeff", "tag", "card");
		} catch (SQLException er) {
		}
		cardScroller = new JScrollPane(cardPurseTable);

		mainCpPage = new JFrame();
		mainCpPanel = new JPanel();
		this.tempLedgerItem = tempLedgerItem;

		// Initialize main title on page, along with initializing button and layouts
		title = new JLabel("User Cards");
		title.setSize(30, 30);
		title.setFont(new Font("Tahoma", Font.BOLD, 60));

		addNewCard = new JButton("Add New Card");
		addNewCard.setSize(40, 40);
		addNewCard.addActionListener(this);

		toMenu = new JButton(new AbstractAction("Main Menu") {

			@Override
			public void actionPerformed(ActionEvent e) {
				nav = new navigatorPage();
				mainCpPage.dispose();
			}
		});

		// This panel holds the top elements including the title and the ability to add
		// another button
		mainCpPanel.setLayout(new GridLayout(1, 3));
		mainCpPanel.add(title);
		mainCpPanel.add(addNewCard);
		mainCpPanel.add(toMenu);
		mainCpPanel.add(cardScroller);
		mainCpPanel.setBackground(Color.green);

		// This is the text area which shows all of the "ledger" information

//		cardInfo = new JTextArea();
//		cardInfo.append("Card Name" + "\t" + "\t");
//		cardInfo.append("Name On Card" + "\t" + "\t");
//		cardInfo.append("Card Balance" + "\t" + "\t");
//		cardInfo.append("Card Type");
//		cardInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//		// Disables user from being able to add any text into area as it is only for
//		// displaying the ledger
//		cardInfo.setEditable(false);

		removeCard = new JButton(new AbstractAction("Remove All Expenses") {

			@Override
			public void actionPerformed(ActionEvent e) {

//					cardInfo.setText(null);
//					cardInfo.append("Card Name" + "\t");
//					cardInfo.append("Name On Card" + "\t");
//					cardInfo.append("Card Balance" + "\t" + "\t" + "\t");
//					cardInfo.append("Card Type");
//				 
			}

		});
		removeCard.setForeground(Color.green);

		// This panel holds all other elements in the frame
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.add(mainCpPanel);
		mainPanel.setBackground(Color.green);

		// This is the main frame which holds the main panel and all other elements
		// enclosed in it
		mainCpPage.add(mainPanel, BorderLayout.NORTH);
		// mainCpPage.add(cardInfo, BorderLayout.CENTER);
		mainCpPage.add(cardScroller, BorderLayout.CENTER);
		mainCpPage.add(removeCard, BorderLayout.SOUTH);
		mainCpPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainCpPage.setTitle("Card Purse");
		mainCpPage.setSize(1000, 1000);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		mainCpPage.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cppf = new CardPursePageForm();
		mainCpPage.dispose();

	}

	public static void main(String[] args) {
		new CardPursePage();
	}

	public JButton getAddNewCard() {
		return addNewCard;
	}

}

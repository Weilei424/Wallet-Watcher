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

public class EarningPageForm implements ActionListener {

	public JFrame earningPageFrame;
	private JPanel earningPageForm;
	private JLabel earningName;
	public JTextField earningNameInput;
	private JLabel earningCost;
	public JTextField earningCostInput;
	// JLabel expenseCategory; *No real code for this yet
	private JLabel earningDescription;
	public JTextField earningDescriptionInput;
	private JLabel ifOther;
	public JTextField earningDateInput;
	private JLabel earningType;
	private JButton submit;
	private LedgerItem ledgerItem;
	private EarningPage ep;
	private int framesCreated;
	private ButtonGroup radioGroup;
	private JRadioButton salary;
	private JRadioButton commission;
	private JRadioButton sidegig;
	private JRadioButton other;
	private JTextField othertext;
	private String category;
	private JLabel dateSelector;
	private JDateChooser dateChooser;
	private String formattedDate;
	private JCheckBox checkBox;
	private Box buttonBox;
	private boolean recur;

	public EarningPageForm() {

		salary = new JRadioButton("Salary");
		commission = new JRadioButton("Commission");
		sidegig = new JRadioButton("Side Gig");
		other = new JRadioButton("Other");

		radioGroup = new ButtonGroup();
		radioGroup.add(salary);
		radioGroup.add(commission);
		radioGroup.add(sidegig);
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

		buttonBox.add(salary);
		buttonBox.add(commission);
		buttonBox.add(sidegig);
		buttonBox.add(other);

		salary.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (salary.isSelected())
					category = "Salary";
			}
		});

		commission.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (commission.isSelected())
					category = "Commission";
			}
		});

		sidegig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sidegig.isSelected())
					category = "Side Gig";
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

		earningPageFrame = new JFrame();
		earningPageFrame.setLocationRelativeTo(null);
		earningPageForm = new JPanel();

		earningPageForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		earningPageForm.setLayout(new GridLayout(8, 2));
		earningPageForm.setBackground(new Color(137, 208, 240));

		earningName = new JLabel("Name of Earning:");
		earningName.setSize(100, 20);
		earningName.setLocation(100, 100);
		earningPageForm.add(earningName);

		earningNameInput = new JTextField();
		earningNameInput.setSize(100, 20);
		earningNameInput.setLocation(300, 100);
		earningPageForm.add(earningNameInput);

		earningCost = new JLabel("Earning Amount:");
		earningCost.setSize(100, 20);
		earningCost.setLocation(100, 200);
		earningPageForm.add(earningCost);

		earningCostInput = new JTextField();
		earningCostInput.setSize(100, 20);
		earningCostInput.setLocation(200, 200);
		earningPageForm.add(earningCostInput);
		
		ifOther = new JLabel("If Earning Type is Other:");
		ifOther.setSize(100, 20);

		othertext = new JTextField();
		earningDescription = new JLabel("Description of Earning:");
		earningDescription.setSize(100, 20);
		earningDescription.setLocation(100, 300);
		earningPageForm.add(earningDescription);

		earningDescriptionInput = new JTextField();
		earningDescriptionInput.setSize(100, 20);
		earningDescriptionInput.setLocation(200, 300);
		earningPageForm.add(earningDescriptionInput);

		dateSelector = new JLabel("Date:");
		dateSelector.setSize(100, 20);
		dateSelector.setLocation(100, 400);
		earningPageForm.add(dateSelector);

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

		earningPageForm.add(dateChooser);
		
		earningPageForm.add(ifOther);
		earningPageForm.add(othertext);

		earningType = new JLabel("Type of Expense:");

		earningPageForm.add(earningType);
		earningPageForm.add(buttonBox);

		JLabel recurring = new JLabel("Is this a recurring expense?");
		
	

		earningPageForm.add(recurring);
		earningPageForm.add(checkBox);

		JLabel blank = new JLabel();

		earningPageForm.add(blank);

		submit = new JButton("Submit");
		submit.setBounds(20, 10, 100, 50);
		submit.addActionListener(this);
		earningPageForm.add(submit);

		// Adding the expense form panel into the main frame
		earningPageFrame.add(earningPageForm, BorderLayout.CENTER);
		earningPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		earningPageFrame.setTitle("Add Expense");
		earningPageFrame.setSize(800, 400);
		// earningPageFrame.pack(); // when setSize on, then remove pack
		earningPageFrame.setVisible(true);

	}

	public JTextField getEarningNameInput() {
		return earningNameInput;
	}

	public JTextField getEarningCostInput() {
		return earningCostInput;
	}

	public JTextField getEarningDescriptionInput() {
		return earningDescriptionInput;
	}

	public JTextField getEarningDateInput() {
		return earningDateInput;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (earningNameInput.getText().isEmpty() || earningCostInput.getText().isEmpty()
				|| dateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(earningPageFrame, "Please enter the name, amount, and date.");
			return;
		}

		String expName = earningNameInput.getText();
		String expNote = earningDescriptionInput.getText();
		String expDate = formattedDate;

		try {
			double expCost = Double.parseDouble(earningCostInput.getText());
			this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);
		} catch (NumberFormatException ex) {
			new ErrorPage("Amount is not a valid number.", ex);
			return;
		}

		this.ledgerItem.setCategory(category);
		if (recur)
			this.ledgerItem.setRecurring(new Recurrence());

		DBUtil.insert(User.getLoginAs(), this.ledgerItem, "earning");

		if (this.framesCreated < 1) {
			ep = new EarningPage();
			ep.mainEpFrame.setVisible(true);
			ep.getAddEarning().setVisible(false);

		}

		ep.setTempLedgerItem(this.ledgerItem);
		ep.setNumberOfEarning(ep.getNumberOfEarning() + 1);

		try {
			ep.earningTable = DBUtil.query(User.getLoginAs(), "tag", "earning");
			ep.mainEpFrame.dispose();
			ep = new EarningPage();
			ep.mainEpFrame.setVisible(true);
			ep.getAddEarning().setVisible(false);
			earningPageFrame.dispose();
		} catch (SQLException er) {

		}
		this.framesCreated++;
	}

	public LedgerItem getLedgerItem() {
		return ledgerItem;
	}

	public static void main(String[] args) {
		new EarningPageForm();
	}

}

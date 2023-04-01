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

public class MiscPageForm implements ActionListener{	

		public JFrame miscFrame;
		private JPanel miscForm;
		private JLabel miscdName;
		public JTextField miscNameInput;
		private JLabel miscCost;
		public JTextField miscCostInput;
		private JLabel miscDescription;
		public JTextField miscDescriptionInput;
		public JTextField miscDateInput;
		public JTextField miscCatInput;
		private JLabel miscCat;
		private JButton submit;
		private LedgerItem ledgerItem;
		private MiscPage mip;
		private int framesCreated;
		private String category;
		private JLabel dateSelector;
		private JDateChooser dateChooser;
		private String formattedDate;
		private JCheckBox checkBox;
		private boolean recur;
		
		public MiscPageForm() {
			this.framesCreated = 0;

			miscFrame = new JFrame();
			miscFrame.setLocationRelativeTo(null);
			miscForm = new JPanel();

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
			
			miscForm.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
			miscForm.setLayout(new GridLayout(5, 1));
			miscForm.setBackground(new Color(137, 208, 240));

			miscdName = new JLabel("Name of Misc:");
			miscdName.setSize(100, 20);
			miscdName.setLocation(100, 100);
			miscForm.add(miscdName);

			miscNameInput = new JTextField();
			miscNameInput.setSize(100, 20);
			miscNameInput.setLocation(300, 100);
			miscForm.add(miscNameInput);

			miscCost = new JLabel("Amount of Misc:");
			miscCost.setSize(100, 20);
			miscCost.setLocation(100, 200);
			miscForm.add(miscCost);

			miscCostInput = new JTextField();
			miscCostInput.setSize(100, 20);
			miscCostInput.setLocation(200, 200);
			miscForm.add(miscCostInput);

			miscDescription = new JLabel("Description of Misc:");
			miscDescription.setSize(100, 20);
			miscDescription.setLocation(100, 300);
			miscForm.add(miscDescription);

			miscDescriptionInput = new JTextField();
			miscDescriptionInput.setSize(100, 20);
			miscDescriptionInput.setLocation(200, 300);
			miscForm.add(miscDescriptionInput);

			miscForm.add(checkBox);
			
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
			miscForm.add(dateSelector);
			miscForm.add(dateChooser);
			
			miscCat = new JLabel("Category:");
			miscCat.setSize(100, 20);
			miscCat.setLocation(100, 500);
			miscForm.add(miscCat);

			miscCatInput = new JTextField();
			miscCatInput.setSize(100, 20);
			miscCatInput.setLocation(200, 500);
			miscForm.add(miscCatInput);

			submit = new JButton("Submit");
			submit.setBounds(20, 10, 100, 50);
			submit.addActionListener(this);
			miscForm.add(submit);

			// Adding the expense form panel into the main frame
			miscFrame.add(miscForm, BorderLayout.CENTER);
			miscFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			miscFrame.setTitle("Add Miscs");
			miscFrame.setSize(600, 400);
			miscFrame.setVisible(true);

		}

		public JTextField getMiscNameInput() {
			return miscNameInput;
		}

		public JTextField getMiscCostInput() {
			return miscCostInput;
		}

		public JTextField getMiscDescriptionInput() {
			return miscDescriptionInput;
		}

		public JTextField getMiscDateInput() {
			return miscDateInput;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (miscNameInput.getText().isEmpty() || miscCostInput.getText().isEmpty() || dateChooser.getDate() == null) {
				JOptionPane.showMessageDialog(miscFrame, "Please enter the name, amount, and date.");
				return;
			}

			String expName = miscNameInput.getText();
			String expNote = miscDescriptionInput.getText();
			String expDate = formattedDate;
			category = miscCatInput.getText();

			try {
				double expCost = Double.parseDouble(miscCostInput.getText());
				this.ledgerItem = new LedgerItem(expDate, expCost, expName, expNote);
			} catch (NumberFormatException ex) {
				new ErrorPage("Amount is not a valid number.", ex);
				return;
			}

			this.ledgerItem.setCategory(category);
			if (recur)
				this.ledgerItem.setRecurring(new Recurrence());
			
			DBUtil.insert(User.getLoginAs(), this.ledgerItem, "misc");

			if (this.framesCreated < 1) {
				mip = new MiscPage();
				mip.mainMiPage.setVisible(true);
				mip.getAddNewCard().setVisible(false);

			}

			try {
				mip.miscTable = DBUtil.query(User.getLoginAs(),"tag","misc");
				mip.mainMiPage.dispose();
				mip = new MiscPage();
				mip.mainMiPage.setVisible(true);
				mip.getAddNewCard().setVisible(false);
				} catch(SQLException er) {
					
				}
			this.framesCreated++;
		}

		public LedgerItem getLedgerItem() {
			return ledgerItem;
		}

		public static void main(String[] args) {
			new MiscPageForm();
		}
}

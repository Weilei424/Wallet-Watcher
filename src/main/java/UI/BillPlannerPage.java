package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import DB.DBUtil;
import persistence.LedgerItem;
import persistence.User;

public class BillPlannerPage implements ActionListener {

	public JFrame mainBpPage;
	private JPanel mainBpPanel;
	private JPanel mainPanel; // main panel to hold all other panels in the expense page form
	// JPanel ledgerPanel;
	private JButton addNewBill;
	private JButton removeBill;
	private JLabel title;
	private JButton toMenu;
//	private JTextArea billInfo;
	private BillPlannerPageForm bpForm;
	private LedgerItem tempLedgerItem;
	private JTable billTable;
	public JScrollPane billScroller;
	private navigatorPage nav;
	private JPopupMenu popupMenu;
	private JMenuItem updateMenuItem;
	private JMenuItem deleteMenuItem;
	private JDialog dialog;
	private JRadioButton item;
	private JRadioButton note;
	private JRadioButton amount;
	private JPanel dialogPanel;
	private ButtonGroup buttonGroup;

	public BillPlannerPage() {

		try {
			billTable = DBUtil.query(User.getLoginAs(), "tag", "bill");
		} catch (SQLException er) {
		}
		billScroller = new JScrollPane(billTable);

		mainBpPage = new JFrame();
		mainBpPage.setLocationRelativeTo(null);
		mainBpPanel = new JPanel();
		bpForm = new BillPlannerPageForm();
		bpForm.billPlannerPageFrame.setVisible(false);


		// Initialize main title on page, along with initializing button and layouts
		title = new JLabel("Bills Due");
		title.setSize(30, 30);
		title.setFont(new Font("Tahoma", Font.BOLD, 60));

		addNewBill = new JButton("Add Bill");
		addNewBill.setSize(40, 40);
		addNewBill.addActionListener(this);

		toMenu = new JButton(new AbstractAction("Main Menu") {

			@Override
			public void actionPerformed(ActionEvent e) {
				nav = new navigatorPage();
				mainBpPage.dispose();
			}
		});

		// This panel holds the top elements including the title and the ability to add
		// another button
		mainBpPanel.setLayout(new GridLayout(1, 3));
		mainBpPanel.add(title);
		mainBpPanel.add(addNewBill);
		mainBpPanel.add(toMenu);
		mainBpPanel.setBackground(Color.green);

		// pop up menu, on click for update and delete
				this.popupMenu = new JPopupMenu();
				this.updateMenuItem = new JMenuItem("Update");
				this.deleteMenuItem = new JMenuItem("Delete");
				popupMenu.add(updateMenuItem);
				popupMenu.add(deleteMenuItem);
				
				billTable.addMouseListener(new MouseAdapter() {
				    public void mousePressed(MouseEvent e) {
				        // check if the mouse button pressed is the right button
				        if (SwingUtilities.isRightMouseButton(e)) {
				            // get the row index of the clicked cell
				            int row = billTable.rowAtPoint(e.getPoint());
				            
				            // if the row index is valid, select the row
				            if (row >= 0 && row < billTable.getRowCount()) {
				            	billTable.setRowSelectionInterval(row, row);
				            }
				            
				            // show the popup menu
				            popupMenu.show(e.getComponent(), e.getX(), e.getY());
				        }
				    }
				});

				updateMenuItem.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        int row = billTable.getSelectedRow();
				        int ref = (int) billTable.getModel().getValueAt(row, 0);
				        
				        if (row != -1) {
				            // Get the value of the selected row's ID column
				            int id = (int) billTable.getValueAt(row, 0);
				            
				            // Create a new dialog box to prompt the user for input
				            dialog = new JDialog(mainBpPage, "Update Item", Dialog.ModalityType.APPLICATION_MODAL);
				            dialog.setPreferredSize(new Dimension(500, 400));
				            dialogPanel = new JPanel(new GridLayout(0, 1));
				            JLabel label = new JLabel("Enter new value:");
				            JTextField textField = new JTextField();
				            item = new JRadioButton("Item name");
				            note = new JRadioButton("Note");
				            amount = new JRadioButton("Amount");
				            buttonGroup = new ButtonGroup();
				            buttonGroup.add(item);
				            buttonGroup.add(note);
				            buttonGroup.add(amount);
				            dialogPanel.add(label);
				            dialogPanel.add(textField);
				            dialogPanel.add(item);
				            dialogPanel.add(note);
				            dialogPanel.add(amount);
				            
				            // Create a "Submit" button to close the dialog box
				            JButton submitButton = new JButton("Submit");
				            submitButton.addActionListener(new ActionListener() {
				                public void actionPerformed(ActionEvent e) {
				                    // Get the value entered by the user
				                	String selection = "";
				                    String newValue = textField.getText();
				                    boolean option1Selected = item.isSelected();
				                    boolean option2Selected = note.isSelected();
				                    boolean option3Selected = amount.isSelected();
				                    
				                    if (option1Selected) {
				                        selection = "item";
				                        billTable.setValueAt(newValue, row, 1);
				                    } else if (option2Selected) {
				                        selection = "note";
				                        billTable.setValueAt(newValue, row, 2);
				                    } else if (option3Selected) {
				                        selection = "amount";
				                        BigDecimal bd = new BigDecimal(newValue);
				            			bd = bd.setScale(2, RoundingMode.HALF_UP);
				            			newValue = bd.doubleValue() + "";
				                        billTable.setValueAt(newValue, row, 3);
				                    }
				                    DBUtil.update(User.getLoginAs(), ref, selection, newValue);
				                    // Close the dialog box
				                    dialog.dispose();
				                }
				            });
				            dialogPanel.add(submitButton);
				            
				            dialog.add(dialogPanel);
				            dialog.pack();
				            dialog.setLocationRelativeTo(null);
				            dialog.setVisible(true);
				        }
				    }
				});

				deleteMenuItem.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        int row = billTable.getSelectedRow();
				        int ref = (int) billTable.getModel().getValueAt(row, 0);
				        DBUtil.delete(User.getLoginAs(), ref);
				        try
						{ 
					       	billTable = DBUtil.query(User.getLoginAs(),"tag","bill");
						}
						catch(SQLException er)
						{ 
						}
				        JScrollPane newScroller = new JScrollPane(billTable);
				        mainBpPage.remove(billScroller);
				        billScroller = newScroller;
				        mainBpPage.add(billScroller, BorderLayout.CENTER);
				        mainBpPage.revalidate();
				        mainBpPage.repaint();
				    }
				});
		
		// This is the text area which shows all of the "ledger" information

//		billInfo = new JTextArea();
//		billInfo.append("Bill Name" + "\t" + "\t");
//		billInfo.append("Bill Charge" + "\t" + "\t");
//		billInfo.append("Date Due" + "\t" + "\t");
//		billInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//		// Disables user from being able to add any text into area as it is only for
//		// displaying the ledger
//		billInfo.setEditable(false);

		removeBill = new JButton(new AbstractAction("Remove All Bills") {

			@Override
			public void actionPerformed(ActionEvent e) {

//				billInfo.setText(null);
//				billInfo.append("Bill Name" + "\t");
//				billInfo.append("Bill Charge" + "\t");
//				billInfo.append("Date Due" + "\t" + "\t" + "\t");

			}

		});
		removeBill.setForeground(Color.green);

		// This panel holds all other elements in the frame
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.add(mainBpPanel);
		mainPanel.setBackground(Color.green);

		// This is the main frame which holds the main panel and all other elements
		// enclosed in it
		mainBpPage.add(mainPanel, BorderLayout.NORTH);
		// mainBpPage.add(billInfo, BorderLayout.CENTER);
		mainBpPage.add(removeBill, BorderLayout.SOUTH);
		mainBpPage.add(billScroller, BorderLayout.CENTER);
		mainBpPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainBpPage.setTitle("Upcoming Bills");
		mainBpPage.setSize(1000, 1000);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		mainBpPage.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		bpForm = new BillPlannerPageForm();
		bpForm.billPlannerPageFrame.setVisible(true);
		mainBpPage.dispose();

	}

	public static void main(String[] args) {
		new BillPlannerPage();
	}
}

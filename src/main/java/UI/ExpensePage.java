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
import javax.swing.JOptionPane;
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

public class ExpensePage implements ActionListener {

	// Initializing global variables
	public JFrame mainEpFrame;
	private JPanel mainEpPanel;
	private JPanel mainPanel; // main panel to hold all other panels in the expense page form
	// JPanel ledgerPanel;
	private JButton addExpense;
	private JButton removeExpense;
	private JButton toMenu;
	private JLabel title;
	//private JTextArea ledgerInfo;
	private ExpensePageForm epForm;
	private LedgerItem tempLedgerItem;
	public JTable expenseTable;
	private JScrollPane expenseScroller;
	private boolean isRemoved;
	private navigatorPage navigation;
	public static volatile int numberOfExpenses = 0;
	private JPopupMenu popupMenu;
	private JMenuItem updateMenuItem;
	private JMenuItem deleteMenuItem;
	private JDialog dialog;
	private JRadioButton item;
	private JRadioButton note;
	private JRadioButton amount;
	private JPanel dialogPanel;
	private ButtonGroup buttonGroup;

	public ExpensePage() {
		
		try
		{ 
	       	expenseTable = DBUtil.query(User.getLoginAs(),"tag","expense");
		}
		catch(SQLException er)
		{ 
		}
		expenseScroller = new JScrollPane(expenseTable);
		
		
		mainEpFrame = new JFrame();
		mainEpFrame.setLocationRelativeTo(null);
		mainEpPanel = new JPanel();
		
		this.isRemoved = false;

		// Initialize main title on page, along with initializing button and layouts
		title = new JLabel("Expenses");
		title.setSize(30, 30);
		title.setFont(new Font("Tahoma", Font.BOLD, 60));

		addExpense = new JButton("Add New Expense");
		addExpense.setSize(40, 40);
		addExpense.addActionListener(this);
		
		toMenu = new JButton(new AbstractAction("Main Menu") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				navigation = new navigatorPage();
				mainEpFrame.dispose();
			}
		});

		// This panel holds the top elements including the title and the ability to add
		// another button
		mainEpPanel.setLayout(new GridLayout(1, 3));
		mainEpPanel.add(title);
		mainEpPanel.add(addExpense);
		mainEpPanel.add(toMenu);
		mainEpPanel.setBackground(Color.green);

		// pop up menu, on click for update and delete
				this.popupMenu = new JPopupMenu();
				this.updateMenuItem = new JMenuItem("Update");
				this.deleteMenuItem = new JMenuItem("Delete");
				popupMenu.add(updateMenuItem);
				popupMenu.add(deleteMenuItem);
				
				expenseTable.addMouseListener(new MouseAdapter() {
				    public void mousePressed(MouseEvent e) {
				        // check if the mouse button pressed is the right button
				        if (SwingUtilities.isRightMouseButton(e)) {
				            // get the row index of the clicked cell
				            int row = expenseTable.rowAtPoint(e.getPoint());
				            
				            // if the row index is valid, select the row
				            if (row >= 0 && row < expenseTable.getRowCount()) {
				            	expenseTable.setRowSelectionInterval(row, row);
				            }
				            
				            // show the popup menu
				            popupMenu.show(e.getComponent(), e.getX(), e.getY());
				        }
				    }
				});

				updateMenuItem.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        int row = expenseTable.getSelectedRow();
				        int ref = (int) expenseTable.getModel().getValueAt(row, 0);
				        
				        if (row != -1) {
				            // Get the value of the selected row's ID column
				            int id = (int) expenseTable.getValueAt(row, 0);
				            
				            // Create a new dialog box to prompt the user for input
				            dialog = new JDialog(mainEpFrame, "Update Item", Dialog.ModalityType.APPLICATION_MODAL);
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
				                        expenseTable.setValueAt(newValue, row, 1);
				                    } else if (option2Selected) {
				                        selection = "note";
				                        expenseTable.setValueAt(newValue, row, 2);
				                    } else if (option3Selected) {
				                        selection = "amount";
				                        BigDecimal bd = new BigDecimal(newValue);
				            			bd = bd.setScale(2, RoundingMode.HALF_UP);
				            			newValue = bd.doubleValue() + "";
				            			expenseTable.setValueAt(newValue, row, 3);
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
				        int row = expenseTable.getSelectedRow();
				        int ref = (int) expenseTable.getModel().getValueAt(row, 0);
				        DBUtil.delete(User.getLoginAs(), ref);
				        try
						{ 
				        	expenseTable = DBUtil.query(User.getLoginAs(),"tag","expense");
						}
						catch(SQLException er)
						{ 
						}
				        JScrollPane newScroller = new JScrollPane(expenseTable);
				        mainEpFrame.remove(expenseScroller);
				        expenseScroller = newScroller;
				        mainEpFrame.add(expenseScroller, BorderLayout.CENTER);
				        mainEpFrame.revalidate();
				        mainEpFrame.repaint();
				    }
				});
		
		// This is the text area which shows all of the "ledger" information

//		ledgerInfo = new JTextArea();
//		ledgerInfo.append("Name of Expense" + "\t");
//		ledgerInfo.append("Cost of Expense" + "\t");
//		ledgerInfo.append("Date Due" + "\t" + "\t" + "\t");
//		ledgerInfo.append("Special Notes");
//		ledgerInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//		// Disables user from being able to add any text into area as it is only for
//		// displaying the ledger
//		ledgerInfo.setEditable(false);

		removeExpense = new JButton(new AbstractAction("Remove All Expenses") {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (numberOfExpenses > 0) {
//					ledgerInfo.setText(null);
//					ledgerInfo.append("Name of Expense" + "\t");
//					ledgerInfo.append("Cost of Expense" + "\t");
//					ledgerInfo.append("Date Due" + "\t" + "\t" + "\t");
//					ledgerInfo.append("Special Notes");
				} else {
					JOptionPane.showMessageDialog(mainEpFrame, "You need to input expenses first!");
				}
			}

		});
		removeExpense.setForeground(Color.green);

		// This panel holds all other elements in the frame
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.add(mainEpPanel);
		mainPanel.setBackground(Color.green);

		// This is the main frame which holds the main panel and all other elements
		// enclosed in it
		mainEpFrame.add(mainPanel, BorderLayout.NORTH);
		//mainEpFrame.add(ledgerInfo, BorderLayout.CENTER);
		mainEpFrame.add(removeExpense, BorderLayout.SOUTH);
		mainEpFrame.add(expenseScroller, BorderLayout.CENTER);
		mainEpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainEpFrame.setTitle("Expenses");
		mainEpFrame.setSize(1000, 1000);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		mainEpFrame.setVisible(true);

	}

	public LedgerItem getTempLedgerItem() {
		return tempLedgerItem;
	}

	public void setTempLedgerItem(LedgerItem tempLedgerItem) {
		this.tempLedgerItem = tempLedgerItem;
	}

//	public JTextArea getLedgerInfo() {
//		return ledgerInfo;
//	}

	public JButton getAddExpense() {
		return addExpense;
	}

	public int getNumberOfExpenses() {
		return numberOfExpenses;
	}

	public void setNumberOfExpenses(int numberOfExpenses) {
		this.numberOfExpenses = numberOfExpenses;
	}

	public boolean isRemoved() {
		return isRemoved;
	}

	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public static void main(String[] args) {
		new ExpensePage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		epForm = new ExpensePageForm();
		epForm.expensePageFrame.setVisible(true);
		mainEpFrame.dispose();
		// setTempLedgerItem(epForm.getLedgerItem());

	}

}

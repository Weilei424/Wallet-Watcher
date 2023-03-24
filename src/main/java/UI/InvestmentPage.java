package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Dialog;

import DB.DBUtil;
import persistence.LedgerItem;
import persistence.User;

public class InvestmentPage implements ActionListener {

	// Initializing global variables
	public JFrame mainIvFrame;
	private JPanel mainIvPanel;
	private JPanel mainPanel; // main panel to hold all other panels in the expense page form
	// JPanel ledgerPanel;
	private JButton addInvestment;
	private JButton removeInvestment;
	private JButton toMenu;
	private JLabel title;
	//private JTextArea ledgerInfo;
	private InvestmentPageForm ivForm;
	private LedgerItem tempLedgerItem;
	public JTable investmentTable;
	private JScrollPane investmentScroller;
	private boolean isRemoved;
	private navigatorPage navigation;
	public static volatile int numberOfInvestments = 0;
	private JPopupMenu popupMenu;
	private JMenuItem updateMenuItem;
	private JMenuItem deleteMenuItem;
	private JDialog dialog;
	private JTextField textField;
	private JButton submitButton;
	private JRadioButton item;
	private JRadioButton note;
	private JRadioButton amount;
	private JPanel dialogPanel;
	private ButtonGroup buttonGroup;
	

	public InvestmentPage() {
		
		try
		{ 
	       	investmentTable = DBUtil.query(User.getLoginAs(),"tag","investment");
		}
		catch(SQLException er)
		{ 
		}
		investmentScroller = new JScrollPane(investmentTable);
		
		mainIvFrame = new JFrame();
		mainIvFrame.setLocationRelativeTo(null);
		mainIvPanel = new JPanel();
		this.isRemoved = false;

		// Initialize main title on page, along with initializing button and layouts
		title = new JLabel("Investments");
		title.setSize(30, 30);
		title.setFont(new Font("Tahoma", Font.BOLD, 60));

		addInvestment = new JButton("Add New Investments");
		addInvestment.setSize(40, 40);
		addInvestment.addActionListener(this);
		
		toMenu = new JButton(new AbstractAction("Main Menu") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				navigation = new navigatorPage();
				mainIvFrame.dispose();
			}
		});

		// This panel holds the top elements including the title and the ability to add
		// another button
		mainIvPanel.setLayout(new GridLayout(1, 3));
		mainIvPanel.add(title);
		mainIvPanel.add(addInvestment);
		mainIvPanel.add(toMenu);
		mainIvPanel.setBackground(Color.green);

		// pop up menu, on click for update and delete
		this.popupMenu = new JPopupMenu();
		this.updateMenuItem = new JMenuItem("Update");
		this.deleteMenuItem = new JMenuItem("Delete");
		popupMenu.add(updateMenuItem);
		popupMenu.add(deleteMenuItem);
		
		investmentTable.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent e) {
		        // check if the mouse button pressed is the right button
		        if (SwingUtilities.isRightMouseButton(e)) {
		            // get the row index of the clicked cell
		            int row = investmentTable.rowAtPoint(e.getPoint());
		            
		            // if the row index is valid, select the row
		            if (row >= 0 && row < investmentTable.getRowCount()) {
		            	investmentTable.setRowSelectionInterval(row, row);
		            }
		            
		            // show the popup menu
		            popupMenu.show(e.getComponent(), e.getX(), e.getY());
		        }
		    }
		});

		updateMenuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int row = investmentTable.getSelectedRow();
		        int ref = (int) investmentTable.getModel().getValueAt(row, 0);
		        
		        if (row != -1) {
		            // Get the value of the selected row's ID column
		            int id = (int) investmentTable.getValueAt(row, 0);
		            
		            // Create a new dialog box to prompt the user for input
		            dialog = new JDialog(mainIvFrame, "Update Item", Dialog.ModalityType.APPLICATION_MODAL);
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
		                        investmentTable.setValueAt(newValue, row, 1);
		                    } else if (option2Selected) {
		                        selection = "note";
		                        investmentTable.setValueAt(newValue, row, 2);
		                    } else if (option3Selected) {
		                        selection = "amount";
		                        investmentTable.setValueAt(newValue, row, 3);
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
		        int row = investmentTable.getSelectedRow();
		        int ref = (int) investmentTable.getModel().getValueAt(row, 0);
		        DBUtil.delete(User.getLoginAs(), ref);
		        try
				{ 
			       	investmentTable = DBUtil.query(User.getLoginAs(),"tag","investment");
				}
				catch(SQLException er)
				{ 
				}
				investmentScroller = new JScrollPane(investmentTable);
		    }
		});
		
		// This panel holds all other elements in the frame
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.add(mainIvPanel);
		mainPanel.setBackground(Color.green);

		// This is the main frame which holds the main panel and all other elements
		// enclosed in it
		mainIvFrame.add(mainPanel, BorderLayout.NORTH);
		//mainEpFrame.add(ledgerInfo, BorderLayout.CENTER);
		//mainEpFrame.add(removeInvestment, BorderLayout.SOUTH);
		mainIvFrame.add(investmentScroller, BorderLayout.CENTER);
		mainIvFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainIvFrame.setTitle("Investments");
		mainIvFrame.setSize(1000, 1000);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		mainIvFrame.setVisible(true);

	}

	public LedgerItem getTempLedgerItem() {
		return tempLedgerItem;
	}

	public void setTempLedgerItem(LedgerItem tempLedgerItem) {
		this.tempLedgerItem = tempLedgerItem;
	}

	public JButton getAddInvestment() {
		return addInvestment;
	}

	public int getNumberOfInvestment() {
		return numberOfInvestments;
	}

	public void setNumberOfInvestment(int numberOfInvestments) {
		this.numberOfInvestments = numberOfInvestments;
	}

	public boolean isRemoved() {
		return isRemoved;
	}

	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public static void main(String[] args) {
		new InvestmentPage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ivForm = new InvestmentPageForm();
		ivForm.investmentPageFrame.setVisible(true);
		mainIvFrame.dispose();
	}

}

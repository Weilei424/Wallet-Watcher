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

import java.io.File;

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
import businessLogic.Util;
import persistence.LedgerItem;
import persistence.User;

public class CardPursePage implements ActionListener {

	public JFrame mainCpPage;
	private JPanel mainCpPanel;
	private JPanel mainPanel; // main panel to hold all other panels in the expense page form
	// JPanel ledgerPanel;
	private JButton addNewCard;
	private JButton export;
	private JButton toMenu;
	private JLabel title;
	// private JTextArea cardInfo;
	private ExpensePageForm epForm;
	private LedgerItem tempLedgerItem;
	public JTable cardPurseTable;
	public JScrollPane cardScroller;
	private navigatorPage nav;
	private CardPursePageForm cppf;
	private JPopupMenu popupMenu;
	private JMenuItem updateMenuItem;
	private JMenuItem deleteMenuItem;
	private JDialog dialog;
	private JRadioButton item;
	private JRadioButton note;
	private JRadioButton amount;
	private JPanel dialogPanel;
	private ButtonGroup buttonGroup;
	
	public CardPursePage() {

		try {
			cardPurseTable = DBUtil.query(User.getLoginAs(), "tag", "card");
		} catch (SQLException er) {
		}
		cardScroller = new JScrollPane(cardPurseTable);

		mainCpPage = new JFrame();
		mainCpPage.setLocationRelativeTo(null);
		mainCpPanel = new JPanel();
		

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

		// pop up menu, on click for update and delete
				this.popupMenu = new JPopupMenu();
				this.updateMenuItem = new JMenuItem("Update");
				this.deleteMenuItem = new JMenuItem("Delete");
				popupMenu.add(updateMenuItem);
				popupMenu.add(deleteMenuItem);
				
				cardPurseTable.addMouseListener(new MouseAdapter() {
				    public void mousePressed(MouseEvent e) {
				        // check if the mouse button pressed is the right button
				        if (SwingUtilities.isRightMouseButton(e)) {
				            // get the row index of the clicked cell
				            int row = cardPurseTable.rowAtPoint(e.getPoint());
				            
				            // if the row index is valid, select the row
				            if (row >= 0 && row < cardPurseTable.getRowCount()) {
				            	cardPurseTable.setRowSelectionInterval(row, row);
				            }
				            
				            // show the popup menu
				            popupMenu.show(e.getComponent(), e.getX(), e.getY());
				        }
				    }
				});

				updateMenuItem.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        int row = cardPurseTable.getSelectedRow();
				        int ref = (int) cardPurseTable.getModel().getValueAt(row, 0);
				        
				        if (row != -1) {
				            // Get the value of the selected row's ID column
				            int id = (int) cardPurseTable.getValueAt(row, 0);
				            
				            // Create a new dialog box to prompt the user for input
				            dialog = new JDialog(mainCpPage, "Update Item", Dialog.ModalityType.APPLICATION_MODAL);
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
				                        cardPurseTable.setValueAt(newValue, row, 1);
				                    } else if (option2Selected) {
				                        selection = "note";
				                        cardPurseTable.setValueAt(newValue, row, 2);
				                    } else if (option3Selected) {
				                        selection = "amount";
				                        BigDecimal bd = new BigDecimal(newValue);
				            			bd = bd.setScale(2, RoundingMode.HALF_UP);
				            			newValue = bd.doubleValue() + "";
				                        cardPurseTable.setValueAt(newValue, row, 3);
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
				        int row = cardPurseTable.getSelectedRow();
				        int ref = (int) cardPurseTable.getModel().getValueAt(row, 0);
				        DBUtil.delete(User.getLoginAs(), ref);
				        try
						{ 
				        	cardPurseTable = DBUtil.query(User.getLoginAs(),"tag","card");
						}
						catch(SQLException er)
						{ 
						}
				        JScrollPane newScroller = new JScrollPane(cardPurseTable);
				        mainCpPage.remove(cardScroller);
				        cardScroller = newScroller;
				        mainCpPage.add(cardScroller, BorderLayout.CENTER);
				        mainCpPage.revalidate();
				        mainCpPage.repaint();
				    }
				});
		

				export = new JButton(new AbstractAction("Export current page as Excel file") {

					@Override
					public void actionPerformed(ActionEvent e) {
						JTextField filenameField = new JTextField();
						filenameField.setColumns(20);
						filenameField.setPreferredSize(new Dimension(200, filenameField.getPreferredSize().height));
						JButton submitButton = new JButton("Submit");
						JPanel panel = new JPanel();
						panel.add(new JLabel("Enter filename: "));
						panel.add(filenameField);
						panel.add(submitButton);
						JDialog dialog = new JDialog();
						dialog.setPreferredSize(new Dimension(300, 200));
						dialog.add(panel);
						dialog.pack();
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);

						submitButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								String filename = filenameField.getText();
								dialog.dispose();
								File outputFile = new File("./Excel Sheets Exported/" + filename + ".xlsx");
								Util.exportToExcel(cardPurseTable, outputFile);
								JOptionPane.showMessageDialog(null, "Export successfully to \"Excel Sheets Exported\" folder.");
							}
						});
					}
				});
		export.setForeground(Color.green);

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
		mainCpPage.add(export, BorderLayout.SOUTH);
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

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
import java.io.File;
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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import DB.DBUtil;
import businessLogic.Util;
import persistence.LedgerItem;
import persistence.User;

public class EarningPage implements ActionListener {

	// Initializing global variables
	public JFrame mainEpFrame;
	private JPanel mainEpPanel;
	private JPanel mainPanel; // main panel to hold all other panels in the expense page form
	// JPanel ledgerPanel;
	private JButton addEarning;
	private JButton export;
	private JButton toMenu;

	private JLabel title;
	// private JTextArea ledgerInfo;
	private EarningPageForm epForm;
	private GraphPage ana;
	private JButton addana;
	private LedgerItem tempLedgerItem;
	public JTable earningTable;
	private JScrollPane earningScroller;
	private boolean isRemoved;
	private NavigatorPage navigation;
	public static volatile int numberOfEarnings = 0;
	private JPopupMenu popupMenu;
	private JMenuItem updateMenuItem;
	private JMenuItem deleteMenuItem;
	private JDialog dialog;
	private JRadioButton item;
	private JRadioButton note;
	private JRadioButton amount;
	private JPanel dialogPanel;
	private ButtonGroup buttonGroup;
	private TableColumnModel colMod;
	private TableColumn tabCol;
	private TableColumn tabCol1;
	private TableColumn tabCol2;
	private TableColumn tabCol3;
	private TableColumn tabCol4;
	private TableColumn tabCol5;
	private TableColumn tabCol6;

	public EarningPage() {

		try {
			earningTable = DBUtil.query(User.getLoginAs(), "tag", "earning");
		} catch (SQLException er) {
		}
		earningScroller = new JScrollPane(earningTable);

		colMod = earningTable.getColumnModel();
		tabCol = colMod.getColumn(0);
		tabCol.setPreferredWidth(150);

		// colMod1 = cardPurseTable.getColumnModel();
		tabCol1 = colMod.getColumn(1);
		tabCol1.setPreferredWidth(150);

		// colMod2 = cardPurseTable.getColumnModel();
		tabCol2 = colMod.getColumn(2);
		tabCol2.setPreferredWidth(150);

		// colMod3 = cardPurseTable.getColumnModel();
		tabCol3 = colMod.getColumn(3);
		tabCol3.setPreferredWidth(150);

		// colMod4 = cardPurseTable.getColumnModel();
		tabCol4 = colMod.getColumn(4);
		tabCol4.setPreferredWidth(150);

		// colMod5 = cardPurseTable.getColumnModel();
		tabCol5 = colMod.getColumn(5);
		tabCol5.setPreferredWidth(130);

		// colMod6 = cardPurseTable.getColumnModel();
		tabCol6 = colMod.getColumn(6);
		tabCol6.setPreferredWidth(90);

		mainEpFrame = new JFrame();
		mainEpPanel = new JPanel();
		this.isRemoved = false;

		// Initialize main title on page, along with initializing button and layouts
		title = new JLabel("Earnings");
		title.setSize(30, 30);
		title.setFont(new Font("Tahoma", Font.BOLD, 40));

		addEarning = new JButton("Add New Earnings");
		addEarning.setSize(40, 40);
		addEarning.addActionListener(this);

		addana = new JButton(new AbstractAction("Generate Graph") {

			@Override
			public void actionPerformed(ActionEvent e) {
				ana = new GraphPage(1);
				ana.anaPageFrame.setVisible(true);
				mainEpFrame.dispose();
			}
		});

		toMenu = new JButton(new AbstractAction("Main Menu") {

			@Override
			public void actionPerformed(ActionEvent e) {
				navigation = new NavigatorPage();
				mainEpFrame.dispose();
			}
		});

		// This panel holds the top elements including the title and the ability to add
		// another button
		mainEpPanel.setLayout(new GridLayout(1, 3));
		mainEpPanel.add(title);
		mainEpPanel.add(addEarning);
		mainEpPanel.add(addana);
		mainEpPanel.add(toMenu);
		mainEpPanel.setBackground(new Color(144, 238, 144));

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
						Util.exportToExcel(earningTable, outputFile);
						JOptionPane.showMessageDialog(null, "Export successfully to \"Excel Sheets Exported\" folder.");
					}
				});
			}
		});
		export.setForeground(Color.green);
		export.setPreferredSize(new Dimension(150, 50));

		// This panel holds all other elements in the frame
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.add(mainEpPanel);
		mainPanel.setBackground(new Color(144, 238, 144));

		// pop up menu, on click for update and delete
		this.popupMenu = new JPopupMenu();
		this.updateMenuItem = new JMenuItem("Update");
		this.deleteMenuItem = new JMenuItem("Delete");
		popupMenu.add(updateMenuItem);
		popupMenu.add(deleteMenuItem);

		earningTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// check if the mouse button pressed is the right button
				if (SwingUtilities.isRightMouseButton(e)) {
					// get the row index of the clicked cell
					int row = earningTable.rowAtPoint(e.getPoint());

					// if the row index is valid, select the row
					if (row >= 0 && row < earningTable.getRowCount()) {
						earningTable.setRowSelectionInterval(row, row);
					}

					// show the popup menu
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		updateMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = earningTable.getSelectedRow();
				int ref = (int) earningTable.getModel().getValueAt(row, 0);

				if (row != -1) {
					// Get the value of the selected row's ID column
					int id = (int) earningTable.getValueAt(row, 0);

					// Create a new dialog box to prompt the user for input
					dialog = new JDialog(mainEpFrame, "Update Item", Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setPreferredSize(new Dimension(500, 400));
					dialogPanel = new JPanel(new GridLayout(0, 1));
					JLabel label = new JLabel("Enter new value:");
					JTextField textField = new JTextField();
					item = new JRadioButton("Item name");
					note = new JRadioButton("Note");
					amount = new JRadioButton("Amount");
					amount.setSelected(true);
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
								earningTable.setValueAt(newValue, row, 1);
							} else if (option2Selected) {
								selection = "note";
								earningTable.setValueAt(newValue, row, 2);
							} else if (option3Selected) {
								selection = "amount";
								BigDecimal bd = new BigDecimal(newValue);
								bd = bd.setScale(2, RoundingMode.HALF_UP);
								newValue = bd.doubleValue() + "";
								earningTable.setValueAt(newValue, row, 3);
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
				int row = earningTable.getSelectedRow();
				int ref = (int) earningTable.getModel().getValueAt(row, 0);
				DBUtil.delete(User.getLoginAs(), ref);
				try {
					EarningPage ep = new EarningPage();
					ep.earningTable = DBUtil.query(User.getLoginAs(), "tag", "earning");
					mainEpFrame.dispose();
					mainEpFrame.setVisible(false);
					ep = new EarningPage();
					ep.mainEpFrame.setVisible(false);
				} catch (SQLException er) {
				}
//				JScrollPane newScroller = new JScrollPane(earningTable);
//				mainEpFrame.remove(earningScroller);
//				earningScroller = newScroller;
//				mainEpFrame.add(earningScroller, BorderLayout.CENTER);
//				mainEpFrame.revalidate();
//				mainEpFrame.repaint();
			}
		});

		// This is the main frame which holds the main panel and all other elements
		// enclosed in it
		mainEpFrame.add(mainPanel, BorderLayout.NORTH);
		// mainEpFrame.add(ledgerInfo, BorderLayout.CENTER);
		mainEpFrame.add(export, BorderLayout.SOUTH);
		mainEpFrame.add(earningScroller, BorderLayout.CENTER);
		mainEpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainEpFrame.setTitle("Earnings");
		mainEpFrame.setSize(1000, 1000);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		mainEpFrame.setLocationRelativeTo(null);

		mainEpFrame.setVisible(true);

	}

	public LedgerItem getTempLedgerItem() {
		return tempLedgerItem;
	}

	public void setTempLedgerItem(LedgerItem tempLedgerItem) {
		this.tempLedgerItem = tempLedgerItem;
	}

	public JButton getAddEarning() {
		return addEarning;
	}

	public int getNumberOfEarning() {
		return numberOfEarnings;
	}

	public void setNumberOfEarning(int numberOfEarnings) {
		this.numberOfEarnings = numberOfEarnings;
	}

	public boolean isRemoved() {
		return isRemoved;
	}

	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

//	public static void main(String[] args) {
//		new EarningPage();
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		epForm = new EarningPageForm();
		epForm.earningPageFrame.setVisible(true);
		mainEpFrame.dispose();
	}

}

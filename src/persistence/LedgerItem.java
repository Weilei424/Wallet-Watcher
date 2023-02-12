package persistence;

import java.util.Date;

import businessLogic.Recurrence;

public class LedgerItem {
	private Date date;
	private double amount;
	private String itemName;
	private String note;
	private String ref;
	private static int REFNUM = 0;
	private Recurrence recurring;
	
	private LedgerItem(Date date, double amount, String itemName, String note) {
		this.date = date;
		this.amount = amount;
		this.itemName = itemName;
		this.note = note;
		this.formatRef(REFNUM++);
	}
	
	/**
	 * Static factory method to initialize LedgerItem object
	 * @param	input string from user
	 * @return	instance is an object with attributes processed by Tokenizer
	 */
	public static LedgerItem getInstanceOf(String input) {
		try {
//			Tokenizer static method to check if the input is legit.
//			Tokenizer.checkLedger(input);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
//		Date date = Tokenizer.getDate();
//		double amount = Tokenizer.getAmount();
//		String itemName = Tokenizer.getItemName();
//		String note = Tokenizer.getNote();
		LedgerItem obj = new LedgerItem(null, 0.00, "", "");
		return obj;
	}

	private void formatRef(int ref) {
		this.ref = String.format("%07d", ref);
	}
	
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public String getRef() {
		return this.ref;
	}
	
	public Recurrence getRecurring() {
		return this.recurring;
	}

	public void setRecurring(Recurrence recurring) {
		this.recurring = recurring;
	}
}

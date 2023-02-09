package persistence;

import java.util.Date;

public class LedgerItem {
	private Date date;
	private double amount;
	private String itemName;
	private String note;
	private int ref;
	private static int REFNUM = 0;
	
	private LedgerItem(Date date, double amount, String itemName, String note) {
		this.date = date;
		this.amount = amount;
		this.itemName = itemName;
		this.note = note;
		this.ref = REFNUM++;
	}
	
	/**
	 * Static factory method to initialize LedgerItem object
	 * @param	input string from user
	 * @return	instance is an object with attributes processed by Tokenizer
	 */
	public static LedgerItem getInstanceOf(String input) {
		try {
//			Tokenizer static method to check if the input is legit.
//			Tokenizer.check(input);
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public int getRef() {
		return this.ref;
	}
}

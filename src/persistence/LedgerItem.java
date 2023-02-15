package persistence;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import businessLogic.Recurrence;
import parsers.DateParser;

public class LedgerItem {
	protected Date date;
	protected double amount;
	protected String itemName;
	protected String note;
	protected String ref;
	protected static int REFNUM = 0;
	protected Recurrence recurring;
	
	@SuppressWarnings("deprecation")
	public LedgerItem(String date, double amount, String itemName, String note) {
		this.date = Date.from(DateParser.getDateFromString(date).atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date temp = new Date();
		this.date.setMinutes(temp.getMinutes());
		this.date.setHours(temp.getHours());
		this.date.setSeconds(temp.getSeconds());
		this.amount = amount;
		this.itemName = itemName;
		this.note = note;
		this.formatRef(REFNUM++);
	}

	protected void formatRef(int ref) {
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
	
	public Recurrence getRecurringInfo() {
		return this.recurring;
	}

	public void setRecurring(Recurrence recurring) {
		this.recurring = recurring;
	}
	
	public boolean isRecurring() {
		return this.recurring != null;
	}
	public String toString()
	{ 
		return String.format( "%s: %s %f \n \t %s \n",this.date, this.itemName, this.amount, this.note);
	}
}

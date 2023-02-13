package persistence;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import businessLogic.Recurrence;

public class LedgerItem {
	protected Date date;
	protected double amount;
	protected String itemName;
	protected String note;
	protected String ref;
	protected static int REFNUM = 0;
	protected Recurrence recurring;
	
	public LedgerItem(LocalDate date, double amount, String itemName, String note) {
		this.date = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
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
	
	public Recurrence getRecurring() {
		return this.recurring;
	}

	public void setRecurring(Recurrence recurring) {
		this.recurring = recurring;
	}
}

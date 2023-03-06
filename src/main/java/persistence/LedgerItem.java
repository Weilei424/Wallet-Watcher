package persistence;

import java.time.LocalDate;
import java.time.ZoneId;

import businessLogic.Category;
import businessLogic.DateParser;
import businessLogic.Recurrence;

public class LedgerItem {
	protected LocalDate date;
	protected double amount;
	protected String itemName;
	protected String note;
	protected Recurrence recurring;
	protected Category category;
	
	/**
	 * 
	 * @param 	date
	 * @param 	amount
	 * @param 	itemName
	 * @param 	note
	 */
	public LedgerItem(String date, double amount, String itemName, String note) {
		this.date = DateParser.getDateFromString(date);
		this.amount = amount;
		this.itemName = itemName;
		this.note = note;
		this.category = new Category();
	}
	
	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
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
	
	public Recurrence getRecurringInfo() {
		return this.recurring;
	}

	public void setRecurring(Recurrence recurring) {
		this.recurring = recurring;
	}
	
	public boolean isRecurring() {
		return this.recurring != null;
	}
	
	public String getCategory() {
		return this.category.getName();
	}

	public void setCategory(String category) {
		this.category.setName(category);;
	}

	public String toString() { 
		return String.format( "%s: %s %f \n \t %s \n",this.date, this.itemName, this.amount, this.note);
	}
}

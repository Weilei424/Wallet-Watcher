package businessLogic;

import java.util.ArrayList;
import java.util.Date;

import persistence.LedgerItem;

public class Recurrence {
	private Date date;
	private String frequency;
	
	/**
	 * This constructor constructs Recurrence object. 
	 * @param 	date is the entry billing/payment date.
	 * @param 	frequency must be one of: "weekly", "biweekly", "monthly" and "yearly".
	 */
	public Recurrence(Date date, String frequency) {
		this.date = date;
		switch (frequency) {
			case "weekly":
				this.frequency = frequency;
				break;
			case "biweekly":
					this.frequency = frequency;
					break;
			case "monthly":
				this.frequency = frequency;
				break;
			case "yearly":
				this.frequency = frequency;
				break;
			default:
				this.frequency = null;
		}
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	/**
	 * This static method returns all recurring LedgerItem objects 
	 * in a new ArrayList from argument list.
	 * 
	 * @param 	<T> == <? extends LedgerItem>
	 * @param 	list is an ArrayList of <T>
	 * @return	all recurring LedgerItem objects.
	 */
	public static <T extends LedgerItem> ArrayList<T> findRecur(ArrayList<T> list) {
		ArrayList<T> res = new ArrayList<>();
		for (T ledger : list) {
			if (ledger.getRecurring() != null) {
				res.add(ledger);
			}
		}
		return res;
	}
}
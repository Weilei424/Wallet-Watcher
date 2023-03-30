package businessLogic;

import java.util.ArrayList;
import java.util.Date;

import UI.ErrorPage;
import exceptions.InvalidDateException;
import persistence.LedgerItem;

public class Recurrence {
	private Date startDate;
	private String frequency;
	private Date endDate;
	
	/**
	 * /**
	 * This constructor constructs Recurrence object. 
	 * @param 	startDate is the entry billing/payment date.
	 * @param 	endDate	is the end date, could be NULL.
	 * @param 	frequency must be one of: "weekly", "biweekly", "monthly" and "yearly".
	 */
	public Recurrence(Date startDate, Date endDate, String frequency) {
		try {
			boolean flag = startDate.getTime() - endDate.getTime() >= 0;
			if (flag)
				throw new InvalidDateException();
		} catch (IllegalArgumentException e) {
			new ErrorPage("Invalid date.", e);
			System.out.println("invalid date");
		}
		
		this.startDate = startDate;
		this.endDate = endDate;
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
	
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
			if (ledger.getRecurringInfo() != null) {
				res.add(ledger);
			}
		}
		return res;
	}
}
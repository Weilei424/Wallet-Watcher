package businessLogic;

import java.util.Date;

public class Recurrence {
	private Date date;
	private String frequency;
	
	/**
	 * 
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
	
	
}
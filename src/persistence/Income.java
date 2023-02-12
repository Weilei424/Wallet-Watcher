package persistence;

import java.util.Date;

public class Income {
	LedgerItem info;
	
	public Income(LedgerItem info) {
		this.info=info;
	}
	public Date getDate() {
		return this.info.getDate();
	}

	public double getAmount() {
		return this.info.getAmount();
	}
	
}

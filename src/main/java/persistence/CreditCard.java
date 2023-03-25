package persistence;

import java.util.Date;

class CreditCard extends BankingCard
{ 
	private double interest; 
	private Date monthlyPayment;
	public CreditCard(String name, double quantity, double interest, Date monthlyPayment, String note,double spendingLimit)
	{
		super(name,quantity,spendingLimit,note);
		this.interest=interest;
		this.monthlyPayment=monthlyPayment;
	}
}

package persistence;


public class BankingCard extends Card
{ 
	protected double spendingLimit;
	public BankingCard(String name, double quantity, double spendingLimit, String note)
	{
		super(name,quantity,note);
		this.spendingLimit=spendingLimit;
	}
	
}

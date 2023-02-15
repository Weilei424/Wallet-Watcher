package persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardData {
	private List <Card> Cards=new ArrayList<>();
	private Double cash; //added for now
	//default constructor
	public CardData()
	{
	
	}
	
	public void addCard(Card addedCard)
	{ 
		Cards.add(addedCard);
	}
	
	
	
	abstract class Card
	{ 
		protected String name; 
		protected Double quantity; 
		protected String note; 
		
		private Card(String name, Double quantity,String note)
		{ 
			this.name=name;
			this.quantity=quantity;
			this.note=note;
		}
		public void setName(String newName)
		{
			this.name=newName;
		}
		public void addAmount(Double newAmm)
		{
			this.quantity+=newAmm;
		}
		public void removeAmount(Double reduction)
		{
			this.quantity-=reduction; 
		}
		public String getName()
		{
			return this.name;
		}
		public Double getAmount()
		{
			return this.quantity;
		}
	}
	
	public class bankingCard extends Card
	{ 
		protected double spendingLimit;
		public bankingCard(String name, double quantity, double spendingLimit, String note)
		{
			super(name,quantity,note);
			this.spendingLimit=spendingLimit;
		}
		
	}
	public class CreditCard extends bankingCard
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

	public class PointsCard extends Card
	{
		private double ratio; 
		public PointsCard(String name, double quantity, double interest, Date monthlyPayment, String note)
		{
			super(name,quantity,note);
			this.ratio=ratio;
		}
		
	}
}

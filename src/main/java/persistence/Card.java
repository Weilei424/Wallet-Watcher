package persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

	
	
	public abstract class Card
	{ 
		protected String name; 
		protected Double quantity; 
		protected String note; 
		
		protected Card(String name, Double quantity,String note)
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
	
	class bankingCard extends Card
	{ 
		protected double spendingLimit;
		public bankingCard(String name, double quantity, double spendingLimit, String note)
		{
			super(name,quantity,note);
			this.spendingLimit=spendingLimit;
		}
		
	}
	
	class CreditCard extends bankingCard
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

	class PointsCard extends Card
	{
		private double ratio; 
		public PointsCard(String name, double quantity, double ratio, String note)
		{
			super(name,quantity,note);
			this.ratio=ratio;
		}
		
	}

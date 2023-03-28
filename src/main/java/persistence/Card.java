package persistence;
	
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


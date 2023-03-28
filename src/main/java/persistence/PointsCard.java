package persistence;

public class PointsCard extends Card
{
	private double ratio; 
	public PointsCard(String name, double quantity, double ratio, String note)
	{
		super(name,quantity,note);
		this.ratio=ratio;
	}
	
}

package pathSchedule;

public class Edge_End
{
	private int nodeIndex;//endNodeIndex
	//end node's label
	private String label;
	//distance
	private int distance;
	//energy cost
	private double energyCost;
	//rechargeStation
	private boolean rechargeStation;
	
	public Edge_End()
	{
		// TODO Auto-generated constructor stub
		nodeIndex = -1;
		label = "";
		distance = 0;
		energyCost = 0;
		rechargeStation = false;
	}	

	public int getNodeIndex()
	{
		return nodeIndex;
	}

	public void setNodeIndex(int nodeIndex)
	{
		this.nodeIndex = nodeIndex;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public int getDistance()
	{
		return distance;
	}

	public void setDistance(int distance)
	{
		this.distance = distance;
	}

	public double getEnergyCost()
	{
		return energyCost;
	}

	public void setEnergyCost(double energyCost)
	{
		this.energyCost = energyCost;
	}

	public boolean isRechargeStation()
	{
		return rechargeStation;
	}

	public void setRechargeStation(boolean rechargeStation)
	{
		this.rechargeStation = rechargeStation;
	}
	
	
	
}

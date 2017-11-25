package pathSchedule;

public class Node
{
	private int index;//the index of this node
	private String label;//the name of this node
	private int positionX;//location X of node
	private int positionY;//location Y of node
		
	//parameter for Dijkstra Algorithm
	private double priority;//cost for deciding the path
	private int previousNode;//the index of previous node. for storing path
	private int pathDistance;//the distance from A to B
	private double pathEnergyCost;//energy cost from A to B
	private int pathStationNumber;//number of station from A to B
	
	public Node()
	{
		super();
		//initial value
		index = -1;
		label = "";
		positionX = 0;
		positionY = 0;
		priority = Double.MAX_VALUE;//set max value of double to distance
		previousNode = -1;//set previous index = -1	
		pathDistance = 0;//the distance from A to B
		pathEnergyCost = 0;//energy cost from A to B
		pathStationNumber = 0;//number of station from A to B
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public double getPriority()
	{
		return priority;
	}

	public void setPriority(double priority)
	{
		this.priority = priority;
	}

	public int getPreviousNode()
	{
		return previousNode;
	}

	public void setPreviousNode(int previousNode)
	{
		this.previousNode = previousNode;
	}

	public int getPositionX()
	{
		return positionX;
	}

	public void setPositionX(int positionX)
	{
		this.positionX = positionX;
	}

	public int getPositionY()
	{
		return positionY;
	}

	public void setPositionY(int positionY)
	{
		this.positionY = positionY;
	}

	public int getPathDistance()
	{
		return pathDistance;
	}

	public void setPathDistance(int pathDistance)
	{
		this.pathDistance = pathDistance;
	}

	public double getPathEnergyCost()
	{
		return pathEnergyCost;
	}

	public void setPathEnergyCost(double pathEnergyCost)
	{
		this.pathEnergyCost = pathEnergyCost;
	}

	public int getPathStationNumber()
	{
		return pathStationNumber;
	}

	public void setPathStationNumber(int pathStationNumber)
	{
		this.pathStationNumber = pathStationNumber;
	}
	
	
	
}

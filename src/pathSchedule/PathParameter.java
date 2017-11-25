package pathSchedule;

import java.util.Vector;

//class for store the compator parameter between each node
public class PathParameter
{
	//flag for using different algorithm to compare path
	public enum CompareAlgorithm {
		Dijkstra_Distance, Dijkstra_Energy, Dijkstra_DisEnergy
	}
	
	private CompareAlgorithm algorithm;
	
	//adjacency-list
	private Vector<Edge_Start> startEdgeList;
	
	//param for percentage between distance and energy
	private double weightDistance;
	
	public PathParameter()
	{
		super();
		algorithm = CompareAlgorithm.Dijkstra_Distance;
		
		//new adj-edgeList 
		startEdgeList = new Vector<Edge_Start>();
		startEdgeList.clear();	
		
		weightDistance = 0.3;
	}

	public CompareAlgorithm getAlgorithm()
	{
		return algorithm;
	}

	public void setAlgorithm(CompareAlgorithm algorithm)
	{
		this.algorithm = algorithm;
	}
	
	public Vector<Edge_Start> getStartEdgeList()
	{
		return startEdgeList;
	}

	public void setStartEdgeList(Vector<Edge_Start> startEdgeList)
	{
		this.startEdgeList = startEdgeList;
	}
		
	public boolean addEdge(int index_1, int index_2, String label_1, String label_2, int distance, double energyCost, boolean rechargeStation)
	{
		//find start Edge from startEdgeList by label
		int startEdgeIndex = -1;
		for(int i=0;i<startEdgeList.size();i++)
		{
			if( startEdgeIndex != -1 )	{ break; }//found
			
			Edge_Start startEdge = startEdgeList.get(i);
			if( startEdge.getLabel().compareTo(label_1) == 0 )//if find start Edge in the list
			{//found
				startEdgeIndex = i;
				break;
			}
		}
		
		Edge_Start startEdge = null;
		if( startEdgeIndex == -1 ) //if not found create new startEdge
		{
			startEdge = new Edge_Start();
			startEdge.setNodeIndex(index_1);
			startEdge.setLabel(label_1);
			startEdge.clearEdge();
			startEdgeList.add(startEdge);
		}
		else
		{
			startEdge = startEdgeList.get(startEdgeIndex);
		}
		
		//add endEdge to startEdge
		//find End Edge from endEdgeList by label
		int endEdgeIndex = -1;
		Vector<Edge_End> endEdgeList = startEdge.getEndEdgeList();
		for(int i=0;i<endEdgeList.size();i++)
		{
			if( endEdgeIndex != -1 )	{ break; }//found
			
			Edge_End endEdge = endEdgeList.get(i);
			if( endEdge.getLabel().compareTo(label_2) == 0 )//if find end Edge in the list
			{//found
				endEdgeIndex = i;
				break;
			}
		}		
		
		Edge_End endEdge = null;
		if( endEdgeIndex == -1 )//if not found, create new endEdge object 
		{
			endEdge = new Edge_End();
			endEdge.setNodeIndex(index_2);
			endEdge.setLabel(label_2);
			endEdge.setDistance(distance);
			endEdge.setEnergyCost(energyCost);
			endEdge.setRechargeStation(rechargeStation);
			endEdgeList.add(endEdge);
		}
		else//exist this endEdge, then modify
		{
			endEdge = endEdgeList.get(endEdgeIndex);
//			endEdge.setNodeIndex(index_2);
//			endEdge.setLabel(label_2);
			endEdge.setDistance(distance);
			endEdge.setEnergyCost(energyCost);
			endEdge.setRechargeStation(rechargeStation);					
		}		
		return true;
	}
	
	public double caculatePrioirty(String label_1, String label_2)
	{
		double priority = 0;
		
		switch (this.algorithm)
		{
		case Dijkstra_Distance:
			priority = caculatePrioirty_dis( label_1, label_2);
			break;
		case Dijkstra_Energy:
			priority = caculatePrioirty_energy( label_1, label_2);
			break;
		case Dijkstra_DisEnergy:
			priority = caculatePrioirty_disEnergy( label_1, label_2);
			break;
		default:
			priority = caculatePrioirty_dis( label_1, label_2);
			break;
		}		
		return priority;
	}
	
	//find edge by label
	private Edge_End findEndEdge(String label_1, String label_2)
	{
		Edge_End endEdge = null;
		
		//find start Edge from startEdgeList by label
		int startEdgeIndex = -1;
		for(int i=0;i<startEdgeList.size();i++)
		{
			if( startEdgeIndex != -1 )	{ break; }//found
			
			Edge_Start startEdge = startEdgeList.get(i);
			if( startEdge.getLabel().compareTo(label_1) == 0 )//if find start Edge in the list
			{//found
				startEdgeIndex = i;
				break;
			}
		}
		
		Edge_Start startEdge = null;
		if( startEdgeIndex == -1 ) //if not found create new startEdge
		{ return null; }
		else {
			startEdge = startEdgeList.get(startEdgeIndex);
		}	
		
		//find End Edge from endEdgeList by label
		int endEdgeIndex = -1;
		Vector<Edge_End> endEdgeList = startEdge.getEndEdgeList();
		for(int i=0;i<endEdgeList.size();i++)
		{
			if( endEdgeIndex != -1 )	{ break; }//found
			
			endEdge = endEdgeList.get(i);
			if( endEdge.getLabel().compareTo(label_2) == 0 )//if find end Edge in the list
			{//found
				endEdgeIndex = i;
				break;
			}
		}		
		
		if( endEdgeIndex == -1 )//if not found, create new endEdge object 
		{ return null; }
		
		endEdge = endEdgeList.get(endEdgeIndex);
		return endEdge;
	}
	
	public double caculatePrioirty_dis(String label_1, String label_2)
	{
		Edge_End endEdge = findEndEdge(label_1, label_2);
		if( endEdge == null )	{ return 0; }
		
		double priority = (double)endEdge.getDistance();
		return priority;
	}
	
	public double caculatePrioirty_energy(String label_1, String label_2)
	{
		Edge_End endEdge = findEndEdge(label_1, label_2);
		if( endEdge == null )	{ return 0; }
		double priority = endEdge.getEnergyCost();
		return priority;
	}
	
	public boolean hasRechargeStation(String label_1, String label_2)
	{
		Edge_End endEdge = findEndEdge(label_1, label_2);
		if( endEdge == null )	{ return false; }
		return endEdge.isRechargeStation();
	}
	
	private double caculatePrioirty_disEnergy(String label_1, String label_2)
	{
		Edge_End endEdge = findEndEdge(label_1, label_2);
		if( endEdge == null )	{ return 0; }
		
		double priority = 0;
		double dis = (double)endEdge.getDistance();
		double energy = endEdge.getEnergyCost();
		priority = weightDistance * dis + (1-weightDistance) * energy;
		return priority;
	}
	
	public double getWeightDistance()
	{
		return weightDistance;
	}

	public void setWeightDistance(double weightDistance)
	{
		this.weightDistance = weightDistance;
	}
	
	public void clearEdge()
	{
		startEdgeList.clear();
	}
	
	public Edge_Start getStartEdgeByLabel(String label)
	{
		//find start Edge from startEdgeList by label
		int startEdgeIndex = -1;
		for(int i=0;i<startEdgeList.size();i++)
		{
			if( startEdgeIndex != -1 )	{ break; }//found
			
			Edge_Start startEdge = startEdgeList.get(i);
			if( startEdge.getLabel().compareTo(label) == 0 )//if find start Edge in the list
			{//found
				startEdgeIndex = i;
				break;
			}
		}
		
		Edge_Start startEdge = null;
		if( startEdgeIndex == -1 ) //if not found create new startEdge
		{ return null; }
		else {
			startEdge = startEdgeList.get(startEdgeIndex);
		}
		
		return startEdge;
	}
}

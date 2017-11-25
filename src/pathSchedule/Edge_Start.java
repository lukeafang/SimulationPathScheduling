package pathSchedule;

import java.util.Vector;

public class Edge_Start
{
	//start node's label name
	private int nodeIndex;//startNodeIndex
	private String label;
	
	////adjacency-list, store distance, energycos, rechargestation
	private Vector<Edge_End> endEdgeList;
	
	public Edge_Start()
	{
		nodeIndex = -1;
		label = "";
		endEdgeList = new Vector<Edge_End>();
		endEdgeList.clear();
	}
		
	public int getNodeIndex()
	{
		return nodeIndex;
	}

	public void setNodeIndex(int nodeIndex)
	{
		this.nodeIndex = nodeIndex;
	}

	public void clearEdge()
	{
		endEdgeList.clear();
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public Vector<Edge_End> getEndEdgeList()
	{
		return endEdgeList;
	}

	public void setEndEdgeList(Vector<Edge_End> endEdgeList)
	{
		this.endEdgeList = endEdgeList;
	}
	
	
	
}

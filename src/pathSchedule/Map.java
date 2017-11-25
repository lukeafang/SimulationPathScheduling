package pathSchedule;

import java.util.Stack;
import java.util.Vector;

import pathSchedule.PathParameter.CompareAlgorithm;

//class of Map structure and path scheduling
public class Map
{
	private Vector<Node> nodeList;//node vector of this map
	private PathParameter pathParameter;//parameter which contain edge information
	
	//store pathresult
	private Vector<Integer> pathNodeIndexList;
	
	public Map()
	{
		super();
		nodeList = new Vector<Node>();//create vector for storing nodes
		nodeList.clear();
		pathParameter = new PathParameter();//after add all nodes, create matrix by this object	
		
		pathNodeIndexList = new Vector<Integer>();
		pathNodeIndexList.clear();
	}
	
	public void clearNodes()
	{
		nodeList.clear();	
		pathNodeIndexList.clear();
		pathParameter.clearEdge();
	}
	
	//add new node into Map
	public void addNode(String label, int x, int y)
	{
		int nNode = this.nodeList.size();//index is from 0 to nNode-1
		int newIndex = nNode;//newIndex for new Vertex
		Node nodeObject = new Node();//create new node object
		nodeObject.setIndex(newIndex);//set newIndex
		nodeObject.setLabel(label);//set name of vertex
		nodeObject.setPositionX(x);//set locationX of Node
		nodeObject.setPositionY(y);//set locationY of Node
		nodeList.add(nodeObject);//add node into list
	}	
	
	public boolean addEdge(String label_1, String label_2, int distance, double energyCost, boolean rechargeStation)
	{			
		int index_1 = -1, index_2 = -1;
		for(int i=0;i<nodeList.size();i++)
		{
			Node node = nodeList.get(i);//get node from list
			if( (index_1 != -1) && (index_2 != -1) )//if both index are found, then stop loop
				break;
			
			if( index_1 == -1 )//if index_1 not find
			{
				if( node.getLabel().compareTo(label_1) == 0 )//if find the vertex_1 by label
				{
					index_1 = i;//store index_1
				}				
			}

			if( index_2 == -1)//if index_2 not find
			{
				if( node.getLabel().compareTo(label_2) == 0 )//if find the vertex_2 by label
				{
					index_2 = i;//store index_2
				}					
			}
		}
		
		if( (index_1 == -1) || (index_2 == -1) )//if index not found, return false;
			return false;
		
		return pathParameter.addEdge( index_1, index_2, label_1, label_2, distance, energyCost, rechargeStation);
	}
	
	//reset node status, such as priority and previuous node.
	//reset result value before starting searching
	protected void resetNodesStatus()
	{
		for(int i=0;i<nodeList.size();i++)
		{
			nodeList.get(i).setPriority(Double.MAX_VALUE);
			nodeList.get(i).setPreviousNode(-1);
			nodeList.get(i).setPathDistance(0);
			nodeList.get(i).setPathEnergyCost(0);
			nodeList.get(i).setPathStationNumber(0);
		}
		pathNodeIndexList.clear();
	}

	public Vector<Node> getNodeList()
	{
		return nodeList;
	}

	public void setNodeList(Vector<Node> nodeList)
	{
		this.nodeList = nodeList;
	}

	public PathParameter getPathParameter()
	{
		return pathParameter;
	}

	public void setPathParameter(PathParameter pathParameter)
	{
		this.pathParameter = pathParameter;
	}	
	
	public boolean findPath(String startNode, String destinationNode, CompareAlgorithm algorithm, boolean checkRechargeStation)
	{
		//before start, reset status of all nodes
		resetNodesStatus();
		
		//get the start index of Node by label
		int index = -1;
		for(int i=0;i<nodeList.size();i++)
		{
			Node node = nodeList.get(i);//get node from list
			if( node.getLabel().compareTo(startNode) == 0 )//if find the node by label
			{
				index = i;//store index
				break;//find vertex, then stop loop
			}				
		}		
		if( index == -1 ) { return false; }//doesn't exist this vertex by label		
		
		//set compator algorithm
		pathParameter.setAlgorithm(algorithm);
		
		//set distance of source node
		Node node = nodeList.get(index);//get source node object by index
		//set priority of node from start node to start node is 0
		node.setPriority(0);
		
		//create a temp buffer and put all reference of node into this buffer
		Vector<Node> nodeBuffer = new Vector<Node>();
		nodeBuffer.clear();		
		//copy reference into this buffer
		for(int i=0; i<nodeList.size(); i++)
		{
			nodeBuffer.add(nodeList.get(i));
		}		
		
		while (nodeBuffer.isEmpty() == false)
		{
			//find smallest distance from the buffer and remove from it
			Node u = null;
			int findIndex = -1;
			double smallestPriority = Double.MAX_VALUE;
			for(int i=0;i<nodeBuffer.size();i++)
			{
				Node tempV = nodeBuffer.get(i);
				if( tempV.getPriority() < smallestPriority )
				{
					u = tempV;
					findIndex = i;
				}
			}
			//remove finded vertex from buffer
			if( findIndex == -1 )
			{
				System.out.println("start node can't go throught all nodes.");
				return false;
			}
			nodeBuffer.remove(findIndex);
			
			//find all vertex which connected to node u
			int u_index = u.getIndex();//the index of node u
			String u_label = u.getLabel();//the label of node u
			Vector<Integer> indexList = getAdjacencyIndexList(u_label);	
			for(int i=0;i<indexList.size();i++)
			{
				int v_index = indexList.get(i);
				Node v = nodeList.get(v_index);//get node object v from original nodelist by index
				String v_label = v.getLabel();
				
				double tempPriority = 0;
				tempPriority = pathParameter.caculatePrioirty(u_label, v_label);
				double alt = u.getPriority() + tempPriority;//get new priority from s to v
				if( alt < v.getPriority() )//if new priority is smaller, then update
				{
					v.setPriority(alt);//update new priority to node
					v.setPreviousNode(u_index);//update previous index to node
					
					//update distance and energy for comparing result
					int newDis = u.getPathDistance() + (int)pathParameter.caculatePrioirty_dis(u_label, v_label);
					v.setPathDistance(newDis);
					double newEnergy = u.getPathEnergyCost() + pathParameter.caculatePrioirty_energy(u_label, v_label);
					v.setPathEnergyCost(newEnergy);
					int stationNumber = u.getPathStationNumber();
					if( pathParameter.hasRechargeStation(u_label, v_label) )
					{
						stationNumber++;
					}
					v.setPathStationNumber(stationNumber);
				}	
				else
				{	//consider recharge station
					boolean hasRechargeStation = pathParameter.hasRechargeStation(u_label, v_label); 
					if( (checkRechargeStation == true) && (hasRechargeStation == true) )
					{
						double threshold = 0.1;
						double diffValue = alt - v.getPriority();
						//check diffValue, if value in threshold then update this
						if( alt != 0 )
						{
							diffValue = diffValue / alt;
						}
						
						if( Math.abs(diffValue) <= threshold )
						{
							v.setPriority(alt);//update new priority to node
							v.setPreviousNode(u_index);//update previous index to node
							
							//update distance and energy for comparing result
							int newDis = u.getPathDistance() + (int)pathParameter.caculatePrioirty_dis(u_label, v_label);
							v.setPathDistance(newDis);
							double newEnergy = u.getPathEnergyCost() + pathParameter.caculatePrioirty_energy(u_label, v_label);
							v.setPathEnergyCost(newEnergy);
							int stationNumber = u.getPathStationNumber();
							stationNumber++;
							v.setPathStationNumber(stationNumber);							
						}
						
					}					
				}
				
			}		
		}		
		
		//find the index of destination node
		int destinationIndex = -1;
		for(int i=0;i<nodeList.size();i++)
		{
			node = nodeList.get(i);//get node from list
			if( node.getLabel().compareTo(destinationNode) == 0 )//if find the node by label
			{
				destinationIndex = i;//store index
				break;//find vertex, then stop loop
			}				
		}		
		if( destinationIndex == -1 ) {
			System.out.println("not exist label name of destination node");	
			return false;
		}//doesn't exist this destination node by label	
		
		Node destNode = nodeList.get(destinationIndex);//get destination node
		String label = destNode.getLabel();
		System.out.println("The path from ( "+startNode+" ) to ( "+label+" ) is "+destNode.getPriority());
		
		Stack<Integer> stack = new Stack<Integer>();	
		index = destinationIndex;
		stack.push(index);
		
		while(index != -1)
		{
			node = nodeList.get(index);
			index = node.getPreviousNode();
			if(index != -1)
			{
				stack.push(index);
			}			
		}	
		
		while (stack.empty() == false)
		{
			pathNodeIndexList.add(stack.pop());			
		}	
				
		return true;
	}
	
	//grab index list which connect to vertexIndex
	private Vector<Integer> getAdjacencyIndexList(String label)
	{
		Vector<Integer> indexList = new Vector<Integer>();
		indexList.clear();
		
		Edge_Start startEdge = pathParameter.getStartEdgeByLabel(label);
		if( startEdge == null )	{ return indexList; }
		
		Vector<Edge_End> endEdgeList = startEdge.getEndEdgeList();
		for(int i=0;i<endEdgeList.size();i++)
		{
			int index = endEdgeList.get(i).getNodeIndex();
			indexList.add(new Integer(index));
		}	
		return indexList;
	}

	public Vector<Integer> getPathNodeIndexList()
	{
		return pathNodeIndexList;
	}		
	
	
}

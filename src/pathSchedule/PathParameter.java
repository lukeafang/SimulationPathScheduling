package pathSchedule;


//class for store the compator parameter between each node
public class PathParameter
{
	//flag for using different algorithm to compare path
	public enum CompareAlgorithm {
		Dijkstra_Distance, Dijkstra_Energy, Dijkstra_DisEnergy
	}
	
	private CompareAlgorithm algorithm;
	private int nNode;//number of node in this Map
	
	//adjacency-matrix with distance of this graph, if value is -1, not exist edge.	
	private int[][] edgeDisMatrix;
	//energy-matrix, contain energy cost between each node, initial value is 0
	private double[][] edgeEnergyMatrix;
	//rechargeStation-matrix, if value is true, then exist rechargeStation during the path.
	private boolean[][] edgeRechargeStationMatrix;
	
	//param for percentage between distance and energy
	private double weightDistance;
	
	public PathParameter()
	{
		super();
		algorithm = CompareAlgorithm.Dijkstra_Distance;
		nNode = 0;
		//after set node number, call function to create matrix
		edgeDisMatrix = null;
		edgeEnergyMatrix = null;
		edgeRechargeStationMatrix = null;	
		
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

	public int getnNode()
	{
		return nNode;
	}

	public void setnNode(int nNode)
	{
		this.nNode = nNode;
	}

	public int[][] getEdgeDisMatrix()
	{
		return edgeDisMatrix;
	}

	public void setEdgeDisMatrix(int[][] edgeDisMatrix)
	{
		this.edgeDisMatrix = edgeDisMatrix;
	}

	public double[][] getEdgeEnergyMatrix()
	{
		return edgeEnergyMatrix;
	}

	public void setEdgeEnergyMatrix(double[][] edgeEnergyMatrix)
	{
		this.edgeEnergyMatrix = edgeEnergyMatrix;
	}

	public boolean[][] getEdgeRechargeStationMatrix()
	{
		return edgeRechargeStationMatrix;
	}

	public void setEdgeRechargeStationMatrix(boolean[][] edgeRechargeStationMatrix)
	{
		this.edgeRechargeStationMatrix = edgeRechargeStationMatrix;
	}
	
	//create all path-related matrices and set default value, if created success, return true
	public boolean createParameterMatrices()
	{
		if( nNode <= 0 )	{ return false; }
		
		//edge-martic
		edgeDisMatrix = new int[nNode][nNode];//adjacency-matrix 
		//reset edge information
		for(int i=0;i<edgeDisMatrix.length;i++)
		{
			for(int j=0;j<edgeDisMatrix[0].length;j++)
			{
				edgeDisMatrix[i][j] = -1;
			}
		}	
		
		//energy-matrix, contain energy cost between each node, initial value is 0
		edgeEnergyMatrix = new double[nNode][nNode];
		//reset edge information
		for(int i=0;i<edgeEnergyMatrix.length;i++)
		{
			for(int j=0;j<edgeEnergyMatrix[0].length;j++)
			{
				edgeEnergyMatrix[i][j] = 0;
			}
		}		
		
		//rechargeStation-matrix, if value is true, then exist rechargeStation during the path.
		edgeRechargeStationMatrix = new boolean[nNode][nNode];
		//reset edge information
		for(int i=0;i<edgeRechargeStationMatrix.length;i++)
		{
			for(int j=0;j<edgeRechargeStationMatrix[0].length;j++)
			{
				edgeRechargeStationMatrix[i][j] = false;
			}
		}				
		return true;
	}		
	
	public boolean addEdge(int index_1, int index_2, int distance, double energyCost, boolean RechargeStation)
	{
		//check the adj-matrix is valid or not
		if( index_1 >= edgeDisMatrix.length ) { return false; }//index is larger martrix
		if( index_2 >= edgeDisMatrix.length ) { return false; }//index is larger martrix
				
		//update edge to edgeMatrix
		edgeDisMatrix[index_1][index_2] = distance;	
		edgeEnergyMatrix[index_1][index_2] = energyCost;
		edgeRechargeStationMatrix[index_1][index_2] = RechargeStation;			
		return true;
	}
	
	public double caculatePrioirty(int index_1, int index_2)
	{
		double priority = 0;
		//check the adj-matrix is valid or not
		if( (index_1 >= edgeDisMatrix.length) || (index_2 >= edgeDisMatrix.length) )
		{
			System.out.println("index is larger than matrix");
			return 0;
		}
		
		switch (this.algorithm)
		{
		case Dijkstra_Distance:
			priority = caculatePrioirty_dis( index_1, index_2);
			break;
		case Dijkstra_Energy:
			priority = caculatePrioirty_energy( index_1, index_2);
			break;
		case Dijkstra_DisEnergy:
			priority = caculatePrioirty_disEnergy( index_1, index_2);
			break;
		default:
			priority = caculatePrioirty_dis( index_1, index_2);
			break;
		}		
		return priority;
	}
	
	public double caculatePrioirty_dis(int index_1, int index_2)
	{
		double priority = (double)edgeDisMatrix[index_1][index_2];
		return priority;
	}
	
	public double caculatePrioirty_energy(int index_1, int index_2)
	{
		double priority = edgeEnergyMatrix[index_1][index_2];
		return priority;
	}
	
	public boolean hasRechargeStation(int index_1, int index_2)
	{
		return edgeRechargeStationMatrix[index_1][index_2];
	}
	
	private double caculatePrioirty_disEnergy(int index_1, int index_2)
	{
		double priority = 0;
		double dis = (double)edgeDisMatrix[index_1][index_2];
		double energy = edgeEnergyMatrix[index_1][index_2];
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
	
}

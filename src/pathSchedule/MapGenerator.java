package pathSchedule;

public class MapGenerator
{
	public MapGenerator()
	{
		
	}
	
	public boolean generateMap(Map map, int mapIndex)
	{
		if( mapIndex == 1 )
		{
			return generateMap_1(map);
		}
		else if(mapIndex == 2 )
		{
			return generateMap_2(map);
		}
		return true;
	}
	
	public boolean generateMap_2(Map map)
	{
		map.clearNodes();
		//start add nodes
		String label = "";
		int x = 0, y = 0;
		int index = 0;
		
		label = String.format("%d", index++);
		x = 1; y = 0;
		map.addNode(label, x, y);			
		
		label = String.format("%d", index++);
		x = 0; y = 2;
		map.addNode(label, x, y);			
		label = String.format("%d", index++);
		x = 0; y = 3;
		map.addNode(label, x, y);			
		label = String.format("%d", index++);
		x = 0; y = 4;
		map.addNode(label, x, y);		
		label = String.format("%d", index++);
		x = 2; y = 2;
		map.addNode(label, x, y);	
		label = String.format("%d", index++);
		x = 2; y = 3;
		map.addNode(label, x, y);			
		label = String.format("%d", index++);
		x = 2; y = 4;
		map.addNode(label, x, y);	
		label = String.format("%d", index++);
		x = 2; y = 5;
		map.addNode(label, x, y);	
		label = String.format("%d", index++);
		x = 4; y = 5;
		map.addNode(label, x, y);		
		label = String.format("%d", index++);
		x = 6; y = 5;
		map.addNode(label, x, y);
		label = String.format("%d", index++);
		x = 8; y = 5;
		map.addNode(label, x, y);
		label = String.format("%d", index++);
		x = 10; y = 5;
		map.addNode(label, x, y);
		
		label = String.format("%d", index++);
		x = 2; y = 6;
		map.addNode(label, x, y);			
		label = String.format("%d", index++);
		x = 4; y = 6;
		map.addNode(label, x, y);			
		label = String.format("%d", index++);
		x = 10; y = 6;
		map.addNode(label, x, y);			
		label = String.format("%d", index++);
		x = 10; y = 8;
		map.addNode(label, x, y);	
		
		label = String.format("%d", index++);
		x = 4; y = 8;
		map.addNode(label, x, y);	
		label = String.format("%d", index++);
		x = 4; y = 10;
		map.addNode(label, x, y);	
		label = String.format("%d", index++);
		x = 0; y = 10;
		map.addNode(label, x, y);		
		label = String.format("%d", index++);
		x = 10; y = 10;
		map.addNode(label, x, y);	
		
		map.createParameterMatrices();//create edge matrices		

		
		return true;
	}
	
	public boolean generateMap_1(Map map)
	{
		map.clearNodes();
		
		//start add nodes
		String label = "";
		int x = 0, y = 0;
		int index = 0;
		
		label = String.format("%d", index++);
		x = 1;
		y = 1;
		map.addNode(label, x, y);		
		
		label = String.format("%d", index++);
		x = 2;
		y = 2;
		map.addNode(label, x, y);
		
		label = String.format("%d", index++);
		x = 3;
		y = 2;
		map.addNode(label, x, y);
		
		label = String.format("%d", index++);
		x = 4;
		y = 1;
		map.addNode(label, x, y);

		label = String.format("%d", index++);
		x = 3;
		y = 0;
		map.addNode(label, x, y);	
		
		label = String.format("%d", index++);
		x = 2;
		y = 0;
		map.addNode(label, x, y);	
		
		map.createParameterMatrices();//create edge matrices		
		
		//add edge into graph
		if( map.addEdge("0", "1", 12, 1, false) == false ) { System.out.println("add edge fault"); }		
		if( map.addEdge("0", "5", 1, 1, false) == false ) { System.out.println("add edge fault"); }		
		if( map.addEdge("1", "2", 5, 1, false) == false ) { System.out.println("add edge fault"); }		
		if( map.addEdge("2", "3", 4, 1, true) == false ) { System.out.println("add edge fault"); }		
		if( map.addEdge("4", "3", 15, 1, false) == false ) { System.out.println("add edge fault"); }		
		if( map.addEdge("4", "2", 13, 1, false) == false ) { System.out.println("add edge fault"); }		
		if( map.addEdge("4", "1", 4, 1, false) == false ) { System.out.println("add edge fault"); }		
		if( map.addEdge("5", "4", 3, 1, false) == false ) { System.out.println("add edge fault"); }		
		if( map.addEdge("5", "1", 9, 2, false) == false ) { System.out.println("add edge fault"); }		
			
		return true;
	}
	
	
}

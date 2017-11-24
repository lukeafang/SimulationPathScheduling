package pathSchedule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
		map.getPathNodeIndexList().clear();
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
	
	public boolean generateMap_1(Map map)
	{
		map.clearNodes();
		map.getPathNodeIndexList().clear();
		
		int index = 1;
		
		//load node information file and add node to map
		String fileName = "";
		fileName = String.format("mapData/%d_node.txt", index);	
        try {
            File f = new File(fileName);
            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                //get label, x , y
                String[] stringArray = readLine.split(" ");
                String label = "";
                int x = 0, y = 0;
                label = stringArray[0];
                x = Integer.parseInt(stringArray[1]);
                y = Integer.parseInt(stringArray[2]);
                map.addNode(label, x, y);	       
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        map.createParameterMatrices();//create edge matrices	
		
      //load edge information file and add edge to map
		fileName = String.format("mapData/%d_edge.txt", index);	
        try {
            File f = new File(fileName);
            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            while ((readLine = b.readLine()) != null) {
            	//get edge information
            	String[] stringArray = readLine.split(" ");
            	String label_1 = "", label_2 = "";
            	int dis = 0;
            	double energyCost = 0.0f;
            	boolean rechargeStation = false;
            	int tempD = 0;
            	
            	label_1 = stringArray[0];
            	label_2 = stringArray[1];
            	dis = Integer.parseInt(stringArray[2]);
            	energyCost = Double.parseDouble(stringArray[3]);
            	tempD = Integer.parseInt(stringArray[4]);
            	if(tempD == 1)	{ rechargeStation = true; }
            	else			{ rechargeStation = false; }
            	
            	if( map.addEdge(label_1, label_2, dis, energyCost, rechargeStation) == false ) 
            	{ 
            		System.out.println("add edge fault ("+label_1 + ", " + label_2+")");
            	}		
 	
            	
            }
        } catch (IOException e) {
            e.printStackTrace();
        }        
	  
       
			
		return true;
	}
	
	
}

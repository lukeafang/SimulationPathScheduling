import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;

import org.w3c.dom.NodeList;

import pathSchedule.Edge_End;
import pathSchedule.Edge_Start;
import pathSchedule.Map;
import pathSchedule.MapGenerator;
import pathSchedule.Node;
import pathSchedule.PathParameter;
import pathSchedule.PathParameter.CompareAlgorithm;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class SimulationPathScheduling
{
	//draw panel param
	private double m_resolution_X;
	private double m_resolution_Y;
	private double m_centerPanelX;
	private double m_centerPanelY;
	private double m_centerMapX;
	private double m_centerMapY;	
	
	private boolean isShowArrow;
	private boolean isShowDistance;
	private boolean isShowEnergy;
	private boolean isShowRechargeStation;
	private Map map;
	
	private JFrame frame;
	private JPanel panelDrawing;
	private JCheckBox chkShowDistance;
	private JCheckBox chkShowEnergy;
	private JCheckBox chkShowRechargeStation;
	private JCheckBox chkShowArrow;
	private JRadioButton rdbtnPathModeDistance;
	private JRadioButton rdbtnPathModeEnergy;
	private JRadioButton rdbtnPathModeDisEnergy;
	private JCheckBox chkPathParamChargeStation;
	private JTextField txtStartNode;
	private JTextField txtDesNode;
	private JCheckBox chkShowPath;
	private JLabel lblPathCost;
	private JTextField txtPathCost;
	private JTextField txtWeightDistance;
	private JLabel lblPathDistance;
	private JTextField txtPathDistance;
	private JLabel lblPathEnergyCost;
	private JTextField txtPathEnergyCost;
	private JLabel lblPathStationNumber;
	private JTextField txtPathStationNumber;
	private JComboBox comboBoxMap;
	private JCheckBox chkDrawLowEnergyPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					SimulationPathScheduling window = new SimulationPathScheduling();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SimulationPathScheduling()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		//initial draw panel param
		m_resolution_X  = m_resolution_Y = 0;	
		m_centerPanelX = m_centerPanelY = 0;
		m_centerMapX = m_centerMapY = 0;		
		
		//initial map
		map = new Map();
		map.clearNodes();
		
		//initial UI value
		isShowArrow = false;
		isShowDistance = true;
		isShowEnergy = false;
		isShowRechargeStation = false;
		
		//--------------------------------------------//
		
		frame = new JFrame();
		frame.setBounds(100, 50, 952, 690);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setBackground(Color.ORANGE);
		splitPane.setBounds(6, 6, 940, 656);
		frame.getContentPane().add(splitPane);
		
		JPanel panelMapData = new JPanel();
		panelMapData.setPreferredSize(new Dimension(650,588));
		splitPane.setLeftComponent(panelMapData);
		panelMapData.setLayout(null);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createMap();
				drawMap();
			}
		});
		btnCreate.setBounds(6, 6, 106, 29);
		panelMapData.add(btnCreate);
		
		panelDrawing = new JPanel();
		panelDrawing.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panelDrawing.setBounds(6, 99, 638, 553);
		panelMapData.add(panelDrawing);
		
		chkShowDistance = new JCheckBox("dis");
		chkShowDistance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chkShowDistance.isSelected()) {
					isShowDistance = true;
				}
				else {
					isShowDistance = false;
				}
				drawMap();
			}
		});
		chkShowDistance.setBounds(280, 64, 59, 23);
		panelMapData.add(chkShowDistance);
		
		chkShowEnergy = new JCheckBox("energy");
		chkShowEnergy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chkShowEnergy.isSelected()) {
					isShowEnergy = true;
				}
				else {
					isShowEnergy = false;
				}
				drawMap();
			}
		});
		chkShowEnergy.setBounds(331, 64, 74, 23);
		panelMapData.add(chkShowEnergy);
		
		chkShowRechargeStation = new JCheckBox("recharge");
		chkShowRechargeStation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chkShowRechargeStation.isSelected()) {
					isShowRechargeStation = true;
				}
				else {
					isShowRechargeStation = false;
				}
				drawMap();
			}
		});
		chkShowRechargeStation.setBounds(408, 64, 86, 23);
		panelMapData.add(chkShowRechargeStation);
		
		chkShowArrow = new JCheckBox("arrow");
		chkShowArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkShowArrow.isSelected()) {
					isShowArrow = true;
				}
				else {
					isShowArrow =false;
				}
				drawMap();
			}
		});
		chkShowArrow.setSelected(false);
		chkShowArrow.setBounds(162, 64, 86, 23);
		panelMapData.add(chkShowArrow);
		
		chkShowPath = new JCheckBox("path");
		chkShowPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawMap();
			}
		});
		chkShowPath.setSelected(false);
		chkShowPath.setBounds(6, 64, 74, 23);
		panelMapData.add(chkShowPath);
		
		comboBoxMap = new JComboBox();
		comboBoxMap.setModel(new DefaultComboBoxModel(new String[] {"1", "2"}));
		comboBoxMap.setBounds(121, 7, 106, 27);
		panelMapData.add(comboBoxMap);
		
		chkDrawLowEnergyPath = new JCheckBox("E");
		chkDrawLowEnergyPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawMap();
			}
		});
		chkDrawLowEnergyPath.setSelected(false);
		chkDrawLowEnergyPath.setBounds(69, 64, 74, 23);
		panelMapData.add(chkDrawLowEnergyPath);
		
		
		JPanel panelPathScheduling = new JPanel();
		splitPane.setRightComponent(panelPathScheduling);
		panelPathScheduling.setLayout(null);
		
		JButton btnPath = new JButton("Path");
		btnPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPathSchedule();
			}
		});
		btnPath.setBounds(6, 6, 110, 50);
		panelPathScheduling.add(btnPath);
		
		rdbtnPathModeDistance = new JRadioButton("Dijkstra (dis)");
		rdbtnPathModeDistance.setBounds(128, 6, 141, 23);
		panelPathScheduling.add(rdbtnPathModeDistance);
		
		rdbtnPathModeEnergy = new JRadioButton("Dijkstra (energy)");
		rdbtnPathModeEnergy.setBounds(128, 33, 141, 23);
		panelPathScheduling.add(rdbtnPathModeEnergy);
		
		rdbtnPathModeDisEnergy = new JRadioButton("DisEnergy");
		rdbtnPathModeDisEnergy.setBounds(128, 62, 141, 23);
		panelPathScheduling.add(rdbtnPathModeDisEnergy);
		
		//radio group
		ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnPathModeDistance);
	    group.add(rdbtnPathModeEnergy);
	    group.add(rdbtnPathModeDisEnergy);
	    rdbtnPathModeDistance.setSelected(true);
		
		chkPathParamChargeStation = new JCheckBox("charge station");
		chkPathParamChargeStation.setBounds(128, 97, 128, 23);
		panelPathScheduling.add(chkPathParamChargeStation);
		
		JLabel lblStartNode = new JLabel("S Node :");
		lblStartNode.setBounds(6, 66, 61, 26);
		panelPathScheduling.add(lblStartNode);
		
		JLabel lblDesNode = new JLabel("D Node :");
		lblDesNode.setBounds(6, 96, 61, 26);
		panelPathScheduling.add(lblDesNode);
		
		txtStartNode = new JTextField();
		txtStartNode.setBounds(70, 68, 46, 26);
		panelPathScheduling.add(txtStartNode);
		txtStartNode.setColumns(10);
		
		txtDesNode = new JTextField();
		txtDesNode.setColumns(10);
		txtDesNode.setBounds(70, 98, 46, 26);
		panelPathScheduling.add(txtDesNode);
		
		lblPathCost = new JLabel("Cost :");
		lblPathCost.setBounds(6, 134, 61, 26);
		panelPathScheduling.add(lblPathCost);
		
		txtPathCost = new JTextField();
		txtPathCost.setEnabled(false);
		txtPathCost.setText("0");
		txtPathCost.setColumns(10);
		txtPathCost.setBounds(70, 134, 46, 26);
		panelPathScheduling.add(txtPathCost);
		
		JButton btnParamSet = new JButton("Set");
		btnParamSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tempStr = txtWeightDistance.getText();
				PathParameter pathParameter = map.getPathParameter();
				Double tempF = Double.parseDouble(tempStr);
				pathParameter.setWeightDistance(tempF);
			}
		});
		btnParamSet.setBounds(138, 132, 117, 29);
		panelPathScheduling.add(btnParamSet);
		
		JLabel lblWeightDistance = new JLabel("W_dis :");
		lblWeightDistance.setBounds(128, 161, 61, 26);
		panelPathScheduling.add(lblWeightDistance);
		
		txtWeightDistance = new JTextField();
		txtWeightDistance.setText("4");
		txtWeightDistance.setColumns(10);
		txtWeightDistance.setBounds(192, 163, 46, 26);
		panelPathScheduling.add(txtWeightDistance);
		
		lblPathDistance = new JLabel("Dis :");
		lblPathDistance.setBounds(6, 161, 61, 26);
		panelPathScheduling.add(lblPathDistance);
		
		txtPathDistance = new JTextField();
		txtPathDistance.setText("0");
		txtPathDistance.setEnabled(false);
		txtPathDistance.setColumns(10);
		txtPathDistance.setBounds(70, 161, 46, 26);
		panelPathScheduling.add(txtPathDistance);
		
		lblPathEnergyCost = new JLabel("Energy :");
		lblPathEnergyCost.setBounds(6, 192, 61, 26);
		panelPathScheduling.add(lblPathEnergyCost);
		
		txtPathEnergyCost = new JTextField();
		txtPathEnergyCost.setText("0");
		txtPathEnergyCost.setEnabled(false);
		txtPathEnergyCost.setColumns(10);
		txtPathEnergyCost.setBounds(70, 192, 46, 26);
		panelPathScheduling.add(txtPathEnergyCost);
		
		lblPathStationNumber = new JLabel("C. N.:");
		lblPathStationNumber.setBounds(6, 225, 61, 26);
		panelPathScheduling.add(lblPathStationNumber);
		
		txtPathStationNumber = new JTextField();
		txtPathStationNumber.setText("0");
		txtPathStationNumber.setEnabled(false);
		txtPathStationNumber.setColumns(10);
		txtPathStationNumber.setBounds(70, 225, 46, 26);
		panelPathScheduling.add(txtPathStationNumber);
		
		updateUIStatus();//update UI statua by ui value
	}
	
	private void updateUIStatus()
	{
		//chk box
		chkShowArrow.setSelected(isShowArrow);
		chkShowDistance.setSelected(isShowDistance);
		chkShowEnergy.setSelected(isShowEnergy);
		chkShowRechargeStation.setSelected(isShowRechargeStation);
		
		txtStartNode.setText("05");
		txtDesNode.setText("81");
		
		//update parameter
		String tempStr = "";
		PathParameter pathParameter = map.getPathParameter();
		Double tempF = pathParameter.getWeightDistance();
		tempStr = String.format("%.2f", tempF);
		txtWeightDistance.setText(tempStr);
	}
	
	private void createMap()
	{
		int mapIndex = 0;
		String string = comboBoxMap.getSelectedItem().toString();
		mapIndex = Integer.parseInt(string);	
		MapGenerator mapGenerator = new MapGenerator();
		if ( mapGenerator.generateMap(map, mapIndex) == false)
		{
			System.out.println("create map by file fault!");
		}
		
	}
	
	private void drawMap()
	{
		if( panelDrawing == null )	{ return; }
		Graphics g = panelDrawing.getGraphics();
		if( g==null ){ return; }
		Dimension panelSize = panelDrawing.getSize();
		int panel_L = 0, panel_R = panelSize.width-1;
		int panel_T = 0, panel_B = panelSize.height-1;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, panel_R, panel_B);
		
		Vector<Node> nodeList = map.getNodeList();
		if(nodeList.size() <= 0)	{ return; }
		//get max and min x,y
		int min_X=0, min_Y=0, max_X=0, max_Y=0;
		for(int i=0;i<nodeList.size();i++)
		{
			int tempX = nodeList.get(i).getPositionX();
			int tempY = nodeList.get(i).getPositionY();		
			if(i==0)
			{
				min_X = max_X = tempX;
				min_Y = max_Y = tempY;
				continue;
			}
			
			if( tempX < min_X )	{ min_X = tempX; }
			if( tempX > max_X )	{ max_X = tempX; }
			if( tempY < min_Y )	{ min_Y = tempY; }
			if( tempY > max_Y )	{ max_Y = tempY; }		
		}
			
		//extend region
		int extendValue = 30;
		panel_L += extendValue;
		panel_R -= extendValue;
		panel_T += extendValue;
		panel_B -= extendValue;	
		int L = min_X, R = max_X;
		int T = min_Y, B = max_Y;		

		//caculate resolution
		m_resolution_X  = (double)(panel_R - panel_L) / (double)(R-L);
		m_resolution_Y = (double)(panel_B - panel_T) / (double)(B-T);	
		m_centerPanelX = (double)(panel_R + panel_L) * 0.5f;
		m_centerPanelY = (double)(panel_B + panel_T) * 0.5f;
		m_centerMapX = (double)(R+L)*0.5;
		m_centerMapY = (double)(T+B)*0.5;	
		
		//draw nodes
		
		int drawX=0,drawY=0;
		int nodeRadius = 8;
		for(int i=0;i<nodeList.size();i++)
		{
			int tempX = nodeList.get(i).getPositionX();
			int tempY = nodeList.get(i).getPositionY();	
			
			drawX = (int) (m_centerPanelX + (tempX-m_centerMapX) * m_resolution_X - nodeRadius*0.5);
			drawY = (int) (m_centerPanelY + (tempY-m_centerMapY) * m_resolution_Y - nodeRadius*0.5);
			g.setColor(Color.BLUE);
			g.drawOval(drawX, drawY, nodeRadius, nodeRadius);
			
			//draw nodeLabel
			drawX -= 20;
			String label = nodeList.get(i).getLabel();
			g.setColor(Color.BLACK);
			g.drawString(label, drawX, drawY);
			
		}
				
		boolean isDrawLowEnergyPath = false;
		if( chkDrawLowEnergyPath.isSelected() )	{ isDrawLowEnergyPath = true; }
		
		int drawX1=0, drawY1=0;
		int drawX2=0, drawY2=0;
		PathParameter pathParameter = map.getPathParameter();
		Vector<Edge_Start> startEdgeList = pathParameter.getStartEdgeList();
		for(int i=0;i<startEdgeList.size();i++)
		{
			Edge_Start startEdge = startEdgeList.get(i);
			int startIndex = startEdge.getNodeIndex();
			//get two node by index
			Node node1 = nodeList.get(startIndex);
			if(node1 == null)	{ System.out.println("node1 = null"); return; }
			
			Vector<Edge_End> endEdgeList = startEdge.getEndEdgeList();
			for(int j=0;j<endEdgeList.size();j++)
			{
				Edge_End endEdge = endEdgeList.get(j);
				int endIndex = endEdge.getNodeIndex();
				Node node2 = nodeList.get(endIndex);
				if(node2 == null)	{ System.out.println("node1 = null"); return; }
			
				int tempX = node1.getPositionX();
				int tempY = node1.getPositionY();			
				drawX1 = (int) (m_centerPanelX + (tempX-m_centerMapX) * m_resolution_X);
				drawY1 = (int) (m_centerPanelY + (tempY-m_centerMapY) * m_resolution_Y);
				
				tempX = node2.getPositionX();
				tempY = node2.getPositionY();			
				drawX2 = (int) (m_centerPanelX + (tempX-m_centerMapX) * m_resolution_X);
				drawY2 = (int) (m_centerPanelY + (tempY-m_centerMapY) * m_resolution_Y);
				
				if( isDrawLowEnergyPath )
				{
					if( endEdge.getEnergyCost() <= 1 )
					{
						g.setColor(Color.GREEN);
					}		
					else
					{
						g.setColor(Color.BLACK);
					}
				}
				else
				{
					g.setColor(Color.BLACK);
				}
				g.drawLine(drawX1, drawY1, drawX2, drawY2);
				
				//draw arrow
				if( isShowArrow ) { drawArrowLine( g, drawX1, drawY1, drawX2, drawY2); }
				
				int dis = endEdge.getDistance();
				drawX = (int)((0.1*drawX1 + 0.9*drawX2) );
				drawY = (int)((0.1*drawY1 + 0.9*drawY2) );
				if(isShowDistance)
				{
					g.setColor(Color.RED);
					g.drawString(String.format("%d", dis), drawX, drawY);						
				}			
				
				double energyCost = endEdge.getEnergyCost();
				drawY += 12;
				if( isShowEnergy )
				{
					g.setColor(Color.BLUE);
					g.drawString(String.format("%.1f", energyCost), drawX, drawY);						
				}
							
				drawY += 10;
				g.setColor(Color.BLUE);
				if( isShowRechargeStation )
				{
					boolean rechargeStation = endEdge.isRechargeStation();
					if( rechargeStation )
					{
						g.drawString("•", drawX, drawY);
					}						
				}				
			}		
		}
				
		drawPath(g);	
	}
		
	/**
	 * draw path
	 * @param g
	 */
	private void drawPath(Graphics g)
	{
		if( chkShowPath.isSelected() == false )	{ return; }	
		Vector<Integer> pathNodeIndexList = map.getPathNodeIndexList();
		if( pathNodeIndexList.size() <= 0 ){ return; }
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(2.0f));
		g2.setColor(Color.RED);
		
		Node node = null;
		int index = 0;
		int drawX1=0,drawX2=0,drawY1=0,drawY2=0;
		
		Vector<Node> nodeList = map.getNodeList();
		index = pathNodeIndexList.get(0);
		node = nodeList.get(index);
		if(node == null)	{ return; }	
		int tempX = node.getPositionX();
		int tempY = node.getPositionY();			
		drawX1 = (int) (m_centerPanelX + (tempX-m_centerMapX) * m_resolution_X);
		drawY1 = (int) (m_centerPanelY + (tempY-m_centerMapY) * m_resolution_Y);
		
		for(int i=1;i<pathNodeIndexList.size();i++)
		{
			index = pathNodeIndexList.get(i);
			node = nodeList.get(index);	
			tempX = node.getPositionX();
			tempY = node.getPositionY();
			drawX2 = (int) (m_centerPanelX + (tempX-m_centerMapX) * m_resolution_X);
			drawY2 = (int) (m_centerPanelY + (tempY-m_centerMapY) * m_resolution_Y);			
			g.setColor(Color.RED);
			g.drawLine(drawX1, drawY1, drawX2, drawY2);	
			
			drawX1 = drawX2;
			drawY1 = drawY2;
		}
	}
	
	private void drawArrowLine(Graphics g, int pointX1, int pointY1, int pointX2, int pointY2)
	{
		double slope = 0;
		double tempF = pointX2-pointX1;
		if( tempF == 0 )
		{
			slope = 0;
		}
		else
		{
			slope = (double)(pointY2-pointY1)/tempF;
		}
		
		int drawX = 0, drawY = 0;
		int arrowLength = 14;
		if( Math.abs(slope) <= 0.05 )
		{
			//check is vertical or horozontal
			if( Math.abs(pointX1-pointX2) <= 0 )
			{//vertical
				//check direction
				if( pointY1 < pointY2 )
				{//Up to down line
					drawX = (int) (pointX2 - arrowLength*0.5);
					drawY = (int) (pointY2 - arrowLength*0.5);
					g.drawLine(pointX2, pointY2, drawX, drawY);
					
					drawX = (int) (pointX2 + arrowLength*0.5);
					g.drawLine(pointX2, pointY2, drawX, drawY);
				}
				else
				{//down to up line
					drawX = (int) (pointX2 + arrowLength*0.5);
					drawY = (int) (pointY2 + arrowLength*0.5);
					g.drawLine(pointX2, pointY2, drawX, drawY);
					
					drawX = (int) (pointX2 - arrowLength*0.5);
					g.drawLine(pointX2, pointY2, drawX, drawY);
				}	
			}
			else
			{//horozontal line
				//check direction
				if( pointX1 < pointX2 )
				{//left to right line
					drawX = (int) (pointX2 - arrowLength*0.5);
					drawY = (int) (pointY2 - arrowLength*0.5);
					g.drawLine(pointX2, pointY2, drawX, drawY);
					
					drawY = (int) (pointY2 + arrowLength*0.5);
					g.drawLine(pointX2, pointY2, drawX, drawY);											
				}				
				else
				{//right to left line
					drawX = (int) (pointX2 + arrowLength*0.5);
					drawY = (int) (pointY2 + arrowLength*0.5);
					g.drawLine(pointX2, pointY2, drawX, drawY);
					
					drawY = (int) (pointY2 - arrowLength*0.5);
					g.drawLine(pointX2, pointY2, drawX, drawY);
				}
			}
		}
		else
		{
			//check direction
			if( pointX1 < pointX2 )
			{//left to right line
				if( pointY1 < pointY2 )
				{//Up to down line	//OK
					double newSlope = slope + 0.5;
					drawX = pointX2 - arrowLength;
					drawY = (int) (pointY2 - newSlope*(pointX2-drawX));
					g.drawLine(pointX2, pointY2, drawX, drawY);
					newSlope = slope - 0.5;
					drawX = pointX2 - arrowLength;
					drawY = (int) (pointY2 - newSlope*(pointX2-drawX));
					g.drawLine(pointX2, pointY2, drawX, drawY);				
				}
				else
				{//down to up line	//OK
					double newSlope = slope + 0.5;
					drawX = pointX2 - arrowLength;
					drawY = (int) (pointY2 - newSlope*(pointX2-drawX));
					g.drawLine(pointX2, pointY2, drawX, drawY);
					newSlope = slope - 0.5;
					drawX = pointX2 - arrowLength;
					drawY = (int) (pointY2 - newSlope*(pointX2-drawX));
					g.drawLine(pointX2, pointY2, drawX, drawY);						
				}
			}
			else
			{//right to left line
				if( pointY1 < pointY2 )
				{//Up to down line
					double newSlope = slope + 0.5;
					drawX = pointX2 + arrowLength;
					drawY = (int) (pointY2 - newSlope*(pointX2-drawX));
					g.drawLine(pointX2, pointY2, drawX, drawY);
					newSlope = slope - 0.5;
					drawX = pointX2 + arrowLength;
					drawY = (int) (pointY2 - newSlope*(pointX2-drawX));
					g.drawLine(pointX2, pointY2, drawX, drawY);					
				}
				else
				{//down to up line //OK
					double newSlope = slope + 0.5;
					drawX = pointX2 + arrowLength;
					drawY = (int) (pointY2 - newSlope*(pointX2-drawX));
					g.drawLine(pointX2, pointY2, drawX, drawY);
					newSlope = slope - 0.5;
					drawX = pointX2 + arrowLength;
					drawY = (int) (pointY2 - newSlope*(pointX2-drawX));
					g.drawLine(pointX2, pointY2, drawX, drawY);						
				}								
			}				
		}		
	}
	
	private void doPathSchedule()
	{
		txtPathCost.setText("0");
		String startNode = txtStartNode.getText();
		String destinationNode = txtDesNode.getText();
		
		CompareAlgorithm algorithm = CompareAlgorithm.Dijkstra_Distance;
		if( rdbtnPathModeDistance.isSelected() ) 		{ algorithm = CompareAlgorithm.Dijkstra_Distance; }
		else if( rdbtnPathModeEnergy.isSelected() )		{ algorithm = CompareAlgorithm.Dijkstra_Energy; }
		else if( rdbtnPathModeDisEnergy.isSelected() )	{ algorithm = CompareAlgorithm.Dijkstra_DisEnergy; }		
		
		boolean checkRechargeStation = false;
		if( chkPathParamChargeStation.isSelected() )	{ checkRechargeStation = true; }
		if( map.findPath(startNode, destinationNode, algorithm, checkRechargeStation) == false )
		{
			System.out.println("find path Error");
			return;
		}
		else
		{
			chkShowPath.setSelected(true);
			drawMap();
			
			//show cost
			Vector<Integer> pathNodeIndexList = map.getPathNodeIndexList();
			int index = pathNodeIndexList.get(pathNodeIndexList.size()-1);
			Vector<Node> nodeList = map.getNodeList();
			Node node = nodeList.get(index);
			double privority = node.getPriority();
			txtPathCost.setText(String.format("%.2f", privority));
			
			int pathDistance = node.getPathDistance();
			txtPathDistance.setText(String.format("%d", pathDistance));
			double pathEnergyCost = node.getPathEnergyCost();
			txtPathEnergyCost.setText(String.format("%.2f", pathEnergyCost));
			int pathStationNumber = node.getPathStationNumber();
			txtPathStationNumber.setText(String.format("%d", pathStationNumber));	
		}				
	}
}

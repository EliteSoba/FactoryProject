//Minh La

package factory.graphics;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

//Parts dumped into bins and fed down the lanes

public class GraphicLaneManager extends JFrame implements ActionListener{
	
	//Panels
	GraphicLaneGraphicPanel graphicPanel;
	GraphicLaneMenuPanel menuPanel;
	
	//Image Icons
	ImageIcon lane1, lane2;
	ImageIcon divergeLane;
	ImageIcon nest1, nest2;
	ImageIcon feeder;
	GraphicBin bin;
	
	//Bin coordinates
	int feederX,feederY;
	
	//Items
	ArrayList <GraphicItem> lane1Items;
	ArrayList <GraphicItem> lane2Items;
	ArrayList <GraphicItem> nest1Items;
	ArrayList <GraphicItem> nest2Items;
	ArrayList <Boolean> lane1QueueTaken;			//The queue
	ArrayList <Boolean> lane2QueueTaken;			//The queue
	
	//variables
	int vX, vY;		//velocities
	int lane_xPos, lane_yPos;	//lane relative position
	boolean laneStart;			//default = false
	boolean divergeUp;			//true - up, false - down
	int timerCount;				//counter to periodic add item to lane
	int currentItemCount;		//Current item to be moved
	int binItemCount;			//current item in bin to dump
	boolean placedBin;			//true is bin is added to feeder
	boolean feederOn;			//Feeder on/off
	boolean binExist;			
	
	public GraphicLaneManager(){
		bin = new GraphicBin();
		//declaration of variables
		lane1Items = new ArrayList<GraphicItem>();
		lane2Items = new ArrayList<GraphicItem>();
		nest1Items = new ArrayList<GraphicItem>();
		nest2Items = new ArrayList<GraphicItem>();
		lane1QueueTaken = new ArrayList<Boolean>();
		lane2QueueTaken = new ArrayList<Boolean>();
		vX = -8; vY = 8;
		lane_xPos = 20; lane_yPos = 30;					//MODIFY to change Lane position
		laneStart = false;
		divergeUp = false;
		feederOn = false;
		binExist = true;
		timerCount = 1; currentItemCount = 0; binItemCount = 0;
		
		//Location of bin to appear. x is fixed
		feederX = lane_yPos + 335; feederY = lane_yPos + 30;
		
		/*
		//Declaration of items in bin's location
		for(int i = 0;i < bin.getBinItems().size() ;i = i + 2){
			for(int j = 0; j < bin.getBinItems().size() / 4;j++){
				bin.getBinItems()[i + j].setX(feederX + 10 + j * 20);
				bin.getBinItems()[i + j].setY(feederY + 10 + (i/2) * 20);
			}
		}
		*/
		//Declaration of variables
		lane1 = new ImageIcon("Images/lane.png");
		lane2 = new ImageIcon("Images/lane.png");
		divergeLane = new ImageIcon("Images/divergeLane.png");
		nest1 = new ImageIcon("Images/nest.png");
		nest2 = new ImageIcon("Images/nest.png");
		feeder = new ImageIcon("Images/feeder.png");

		//Declaration of JFrame
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Factory Lane");
		this.setSize(700,400);
		this.setLayout(new BorderLayout());

		//Inserting Panels
		graphicPanel = new GraphicLaneGraphicPanel(this,bin);
		menuPanel = new GraphicLaneMenuPanel(this,bin);
		
		this.add(graphicPanel,BorderLayout.NORTH);
		this.add(menuPanel,BorderLayout.SOUTH);
		
		repaint();
		new javax.swing.Timer(50,this).start();
	}	
	
	
	public void actionPerformed(ActionEvent ae){
		repaint();
	}
	
	public void setBin(GraphicBin bin){
		this.bin = bin;
	}
	
	public GraphicBin getBin(){
		return bin;
	}
}



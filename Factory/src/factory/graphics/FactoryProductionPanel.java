
//Minh La

package factory.graphics;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import factory.*;
import factory.client.*;
public class FactoryProductionPanel extends GraphicPanel implements ActionListener {
	
	// LANE MANAGER
	/*GraphicLaneManagerClient client;
	GraphicLaneManager [] lane;
	
	// KIT MANAGER
	private FactoryProductionManager am; //The JFrame that holds this. Will be removed when gets integrated with the rest of the project
	private GraphicConveyorBelt belt; //The conveyer belt
	private GraphicKittingStation station; //The kitting station
	private GraphicKittingRobot kitRobot;
	public static final int WIDTH = 1100, HEIGHT = 720;
	
	// PARTS MANAGER
	private ArrayList<Nest> nests;
	PartsRobot partsRobot;
	
	// GANTRY
	GantryRobot gantryRobot;*/
	
	public FactoryProductionPanel(JFrame FKAM) {
		super();
		isFactoryProductionManager = true;
		
		if (FKAM instanceof Client)
			am = (Client)FKAM;
		
		belt = new GraphicConveyorBelt(0, 0, this);
		station = new GraphicKittingStation(200, 191, this);
		kitRobot = new GraphicKittingRobot(this, 70, 250);
		
		// Parts robot client
		// Add 8 nests
		nests = new ArrayList<Nest>();	
		for(int i = 0; i < 8; i++)
		{
			Nest newNest = new Nest(510,i*80+50,0,0,0,0,75,75,"Images/nest3x3.png");
			Random randomGen = new Random();
			for(int j = 0; j < randomGen.nextInt(5)+4; j++)
				newNest.addItem(new GraphicItem(20,20,"Images/eyesItem.png"));
			nests.add(newNest);
		}

		lane = new GraphicLaneManager [4];
		for (int i = 0; i < lane.length; i++)
			lane[i] = new GraphicLaneManager(510, 160*i + 50, i, this);
		
		partsRobot = new PartsRobot(WIDTH/2-200,HEIGHT/2,0,5,5,10,100,100,"Images/robot1.png");
		gantryRobot = new GantryRobot(WIDTH-150,HEIGHT/2,0,5,5,10,100,100,"Images/robot2.png");
		
		(new Timer(delay, this)).start();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		super.paint(g);
	}
	
	public void cameraFlash(int nestIndex) {
		flashCounter = 10;
		flashFeederIndex = nestIndex;
	}
	
	public void moveGantryRobotToPickup(String path)
	{
		gantryRobot.setState(1);
		gantryRobot.setDestination(WIDTH-100,-100);
	}
	
	public void moveGantryRobotToFeederForDropoff(int feederIndex)
	{
		// Error checking code has temporarily(?) been commented out as requested by Alfonso
		//if(lane[feederIndex].hasBin())
		//{
			//System.out.println("1");
			//System.err.println("Can't dropoff: feeder " + feederIndex + " (0-based index) already has a bin!");
			//gantryRobotArrivedAtFeederForDropoff();
		//}
		//else if(!gantryRobot.hasBin())
		//{
			//System.out.println("2");
			//System.err.println("Can't dropoff: gantry robot does not have a bin!");
			//gantryRobotArrivedAtFeederForDropoff();
		//}
		//else
		//{
			//System.out.println("3");
			gantryRobot.setState(3);
			gantryRobot.setDestinationFeeder(feederIndex);
			gantryRobot.setDestination(lane[feederIndex].feederX+95, lane[feederIndex].feederY+15);
			gantryRobotArrivedAtFeederForDropoff();
		//}
	}
	
	public void moveGantryRobotToFeederForPickup(int feederIndex)
	{
		// Error checking
		//if(!lane[feederIndex].hasBin())
		//{
		//	System.err.println("Can't pickup: no bin at feeder " + feederIndex + " (0-based index)!");
		//	gantryRobotArrivedAtFeederForPickup();
		//}
		//else
		//{
			gantryRobot.setState(5);
			gantryRobot.setDestinationFeeder(feederIndex);
			gantryRobot.setDestination(lane[feederIndex].feederX+95, lane[feederIndex].feederY+15);
		//}
	}
	
	//CHANGE TO 0 BASE
	public void movePartsRobotToNest(int nestIndex) {
		partsRobot.setState(1);
		partsRobot.adjustShift(5);
		partsRobot.setDestination(nests.get(nestIndex).getX()-nests.get(nestIndex).getImageWidth()-10,nests.get(nestIndex).getY()-15);
		partsRobot.setDestinationNest(nestIndex);
	}
	
	public void movePartsRobotToKit(int kitIndex) {
		partsRobot.setState(3);
		partsRobot.setDestination(station.getX()+35,station.getY()-station.getY()%5);
		partsRobot.setDestinationKit(kitIndex);
	}
	
	public void movePartsRobotToCenter() {
		partsRobot.setState(5);
		partsRobot.setDestination(WIDTH/2-200, HEIGHT/2);
	}
	
	public void feedLane(int laneNum){ //FEEDS THE LANE! Lane 0-7
		/*//Testing for quick feed
		lane[(laneNum) / 2].bin = new GraphicBin(new Part("eyes"));
		lane[(laneNum) / 2].binExist = true;
		//end Test*/
		if(!lane[(laneNum) / 2].lane1PurgeOn){	//If purging is on, cannot feed!
			if(lane[(laneNum) / 2].hasBin() && lane[(laneNum) / 2].bin.getBinItems().size() > 0){
				lane[(laneNum) / 2].laneStart = true;
				lane[(laneNum) / 2].divergeUp = ((laneNum) % 2 == 0);
				lane[(laneNum) / 2].feederOn = true;
			}
		}
		//System.out.println("bin size " + lane[(laneNum) / 2].bin.getBinItems().size());
	}
	
	public void startLane(int laneNum){
		lane[(laneNum) / 2].laneStart = true;
	}
	
	public void switchLane(int laneNum){
		lane[(laneNum) / 2].divergeUp = !lane[(laneNum) / 2].divergeUp;
		lane[(laneNum) / 2].vY = -(lane[(laneNum) / 2].vY);
	}
	
	public void switchFeederLane(int feederNum){
		lane[feederNum].divergeUp = !lane[feederNum].divergeUp;
		lane[feederNum].vY = -(lane[feederNum].vY);
		switchFeederLaneDone(feederNum);
	}
	
	public void stopLane(int laneNum){
		lane[(laneNum) / 2].laneStart = false;
	}
	
	public void turnFeederOn(int feederNum){
		lane[feederNum].feederOn = true;
		startFeederDone(feederNum);
	}

	public void turnFeederOff(int feederNum){
		lane[feederNum].feederOn = false;
		stopFeederDone(feederNum);
	}
	
	public void purgeFeeder(int feederNum){ // takes in lane 0 - 4
		lane[(feederNum)].bin = null;
		lane[(feederNum)].binExists = false;
		lane[(feederNum)].feederOn = false;
		
	}
	
	public void purgeTopLane(int feederNum){
		lane[feederNum].lane1PurgeOn = true;
		lane[feederNum].feederOn = false;
		lane[feederNum].laneStart = true;
	}
	
	public void purgeBottomLane(int feederNum){
		lane[feederNum].lane2PurgeOn = true;
		lane[feederNum].feederOn = false;
		lane[feederNum].laneStart = true;
	}
	
	public void partsRobotStateCheck() {
		// Has robot arrived at its destination?
		//System.out.println(partsRobot.getState());
		if(partsRobot.getState() == 2)		// partsRobot has arrived at nest
		{
			// Give item to partsRobot
			if(partsRobot.getSize() < 4)
			{
				if (nests.get(partsRobot.getDestinationNest()).hasItem())
					partsRobot.addItem(nests.get(partsRobot.getDestinationNest()).popItem());
				partsRobot.setState(0);
			}
			partsRobotArrivedAtNest();
		}
		else if(partsRobot.getState() == 4)	// partsRobot has arrived at kitting station
		{
			System.out.println("Size:"+partsRobot.getSize());
			int numberOfParts = partsRobot.getSize();
			for(int i = 0; i < numberOfParts; i++)
			{
				System.out.println("Adding part to kit: " + i);
				station.addItem(partsRobot.popItem(),partsRobot.getDestinationKit());
			}
			partsRobot.setState(0);
			partsRobotArrivedAtStation();
		}
		else if(partsRobot.getState() == 6)
		{
			partsRobot.setState(0);
			partsRobotArrivedAtCenter();
		}
	}
	
	public void gantryRobotStateCheck() {
		if(gantryRobot.getState() == 2)				// gantry robot reached bin pickup point
		{
			gantryRobot.setState(0);
			// Give gantry robot a bin
			gantryRobot.giveBin(new GraphicBin(new Part("Test Item")));
			gantryRobotArrivedAtPickup();
		}
		else if(gantryRobot.getState() == 4)		// gantry robot reached feeder for dropoff
		{
			gantryRobot.setState(0);
			lane[gantryRobot.getDestinationFeeder()].setBin(gantryRobot.popBin());
			gantryRobotArrivedAtFeederForDropoff();
		}
		else if(gantryRobot.getState() == 6)		// gantry robot reached feeder for pickup
		{
			gantryRobot.setState(0);
			gantryRobot.giveBin(lane[gantryRobot.getDestinationFeeder()].popBin());
			gantryRobotArrivedAtFeederForPickup();
		}
	}
	
	public void moveLanes() {
		for (int i = 0; i < lane.length; i++)
			lane[i].moveLane();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		partsRobotStateCheck();
		gantryRobotStateCheck();
		
		moveLanes();
		
		partsRobot.move();							// Update position and angle of partsRobot
		gantryRobot.move();
		belt.moveBelt(5);
		kitRobot.moveRobot(5);
		
		repaint();		
	}
}
	
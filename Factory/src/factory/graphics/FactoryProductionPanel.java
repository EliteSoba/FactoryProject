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
	private GraphicKitBelt belt; //The conveyer belt
	private GraphicKittingStation station; //The kitting station
	private GraphicKittingRobot kitRobot;
	public static final int WIDTH = 1100, HEIGHT = 720;
	
	// PARTS MANAGER
	private ArrayList<Nest> nests;
	PartsRobot partsRobot;
	
	// GANTRY
	GantryRobot gantryRobot;*/
	
	public FactoryProductionPanel(JFrame FKAM) {
		lane = new GraphicLaneManager [4];
		
		for (int i = 0; i < lane.length; i++)
			lane[i] = new GraphicLaneManager(510, 160*i + 50, i, this);
		if (FKAM instanceof Client)
		am = (Client)FKAM;
		belt = new GraphicKitBelt(0, 0, this);
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
		partsRobot = new PartsRobot(WIDTH/2-200,HEIGHT/2,0,5,5,10,100,100,"Images/robot1.png");
		gantryRobot = new GantryRobot(WIDTH-150,HEIGHT/2,0,5,5,10,100,100,"Images/robot2.png");
		
		(new Timer(50, this)).start();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, getWidth(), getHeight());
		if(lane[0] != null && lane[1] != null){
			lane[0].paintLane(g);
			lane[1].paintLane(g);
			lane[2].paintLane(g);
			lane[3].paintLane(g);
		}

		belt.paint(g);
		station.paint(g);
		kitRobot.paint(g);
		
		// Parts robot client
		// Draw the nests
		for(int i = 0; i < nests.size(); i++)
		{
			Nest currentNest = nests.get(i);
			currentNest.paint(g);
		}
		// Parts robot client
		// Draw the nests
		for(int i = 0; i < nests.size(); i++)
		{
			Nest currentNest = nests.get(i);
			currentNest.paint(g);
		}
		
		// Draw the parts robot
		final Graphics2D g3 = (Graphics2D)g.create();
		g3.rotate(Math.toRadians(360-partsRobot.getAngle()), partsRobot.getX()+partsRobot.getImageWidth()/2, partsRobot.getY()+partsRobot.getImageHeight()/2);
		// Draw items partsRobot is carrying
		partsRobot.paint(g3);
		g3.dispose();
		final Graphics2D g4 = (Graphics2D)g.create();
		g4.rotate(Math.toRadians(360-gantryRobot.getAngle()), gantryRobot.getX()+gantryRobot.getImageWidth()/2, gantryRobot.getY()+gantryRobot.getImageHeight()/2);
		gantryRobot.paint(g4);
		// Draw bin gantryRobot is carrying

		g4.dispose();
	}

	
	public void newEmptyKit() {
		//Adds a kit into the factory via conveyer belt
		if (belt.kitin())
			return;
		belt.inKit();
	}
	/*public void newEmptyKitDone() {
		//am.newEmptyKitAtConveyor();
	}*/
	
	public void moveEmptyKitToSlot(int target) {
		//Sends robot to pick up kit from belt and move to designated slot in the station
		if (belt.pickUp() && !kitRobot.kitted() && station.getKit(target) == null) {
			kitRobot.setFromBelt(true);
			kitRobot.setStationTarget(target);
		}
	}
	/*public void moveEmptyKitToSlotDone() {
		//am.moveEmptyKitToSlotDone();
	}*/
	
	public void moveKitToInspection(int target) {
		//Sends robot to move kit from designated slot in the station to inspection station
		if (!kitRobot.kitted() && station.getKit(target) != null) {
			kitRobot.setCheckKit(true);
			kitRobot.setStationTarget(target);
		}
	}
	/*public void moveKitToInspectionDone() {
		//am.moveKitToInspectionDone();
	}*/
	
	public void takePictureOfInspectionSlot() {
		//Triggers the camera flash
		station.checkKit();
	}
	/*public void takePictureOfInspectionSlotDone() {
		//am.takePictureOfInspectionSlotDone();
	}*/
	
	public void dumpKitAtInspection() {
		//Sends robot to move kit from inspection station to trash
		if (!kitRobot.kitted() && station.getCheck() != null)
			kitRobot.setPurgeKit(true);
	}
	/*public void dumpKitAtInspectionDone() {
		//am.dumpKitAtInspectionDone();
	}*/
	
	public void moveKitFromInspectionToConveyor() {
		//Sends a kit out of the factory via conveyer belt
		if (station.getCheck() != null && !kitRobot.kitted())
			kitRobot.setFromCheck(true);
	}
	/*public void moveKitFromInspectionToConveyorDone() {
		//am.moveKitFromInspectionToConveyorDone();
	}*/
	
	/*public void exportKit() {
		belt.exportKit();
	}
	public void exportKitDone() {
		//am.exportKitDone();
	}*/
	
	public void cameraFlash() {
		
	}
	
	public void moveGantryRobotToPickup(String path) {
		//System.out.println("Moving");
		gantryRobot.setState(0);
		gantryRobot.setDestination(WIDTH-100,-200);
	}
	/*public void gantryRobotArrivedAtPickup() {
		System.out.println("DEBUG: ARRIVED AT PICKUP");
	}*/
	
	public void moveGantryRobotToFeeder(int feederIndex) {
		gantryRobot.setState(3);
		gantryRobot.setDestinationFeeder(feederIndex);
		gantryRobot.setDestination(lane[feederIndex].feederX+50, lane[feederIndex].feederY+15);
	}
	/*public void gantryRobotArrivedAtFeeder() {
		System.out.println("DEBUG: ARRIVED AT FEEDER");
	}*/
	
	public void movePartsRobotToNest(int nestIndex) {
		partsRobot.setState(0);
		partsRobot.adjustShift(5);
		partsRobot.setDestination(nests.get(nestIndex-1).getX()-nests.get(nestIndex-1).getImageWidth()-10,nests.get(nestIndex-1).getY()-15);
		partsRobot.setDestinationNest(nestIndex);
	}
	/*public void partsRobotArrivedAtNest() {
		System.out.println("Debug:ARRIVED AT NEST");
	}*/
	
	public void movePartsRobotToKit(int kitIndex) {
		partsRobot.setState(3);
		partsRobot.setDestination(station.getX()+35,station.getY()-station.getY()%5);
		partsRobot.setDestinationKit(kitIndex);
	}
	/*public void partsRobotArrivedAtStation() {
		System.out.println("DEBUG: ARRIVED AT STATION");
	}*/
	
	public void movePartsRobotToCenter() {
		partsRobot.setDestination(WIDTH/2-100, HEIGHT/2);
	}
	/*public void partsRobotArrivedAtCenter() {
		System.out.println("DEBUG: ARRIVED AT CENTER");
	}*/
	
	public void feedLane(int laneNum){ //FEEDS THE LANE! Lane 1-8, NOT 0-7
		/*//Testing for quick feed
		lane[(laneNum - 1) / 2].bin = new GraphicBin(new Part("eyes"));
		lane[(laneNum - 1) / 2].binExist = true;
		//end Test*/
		if(lane[(laneNum - 1) / 2].binExist && lane[(laneNum - 1) / 2].bin.getBinItems().size() > 0){
			lane[(laneNum - 1) / 2].laneStart = true;
			lane[(laneNum - 1) / 2].divergeUp = ((laneNum- 1) % 2 == 0);
			lane[(laneNum - 1) / 2].feederOn = true;
		}
		//System.out.println("bin size " + lane[(laneNum - 1) / 2].bin.getBinItems().size());
	}
	/*public void feedLaneDone(int laneNum){
		//am.feedLaneDone(laneNum);
	}*/
	
	public void startLane(int laneNum){
		lane[(laneNum - 1) / 2].laneStart = true;
	}
	
	public void switchLane(int laneNum){
		lane[(laneNum - 1) / 2].divergeUp = !lane[(laneNum - 1) / 4].divergeUp;
		lane[(laneNum - 1) / 2].vY = -(lane[(laneNum - 1) / 4].vY);
	}
	
	public void stopLane(int laneNum){
		lane[(laneNum - 1) / 2].laneStart = false;
	}
	
	public void turnFeederOnLane(int laneNum){
		lane[(laneNum - 1) / 2].feederOn = true;
	}
	
	public void turnFeederOffLane(int laneNum){
		lane[(laneNum - 1) / 2].feederOn = false;
	}
	
	public void purgeFeederLane(int feederNum){ // takes in lane 1 - 4
		lane[(feederNum - 1)].bin = null;
		lane[(feederNum - 1)].binExist = false;
		lane[(feederNum - 1)].feederOn = false;
	}
	
	public void purgeLaneLane(int laneNum){
		if((laneNum - 1) % 2 == 0)
			lane[(laneNum - 1) / 2].lane1PurgeOn = true;
		else
			lane[(laneNum - 1) / 2].lane2PurgeOn = true;
		lane[(laneNum - 1) / 2].feederOn = false;
		lane[(laneNum - 1) / 2].laneStart = false;
	}
	
	/*public GraphicKittingStation getStation() {
		return station;
	}
	public GraphicKitBelt getBelt() {
		return belt;
	}
	public ArrayList<Nest> getNest(){
		return nests;
	}
	public GraphicLaneManager getLane(int index) {
		return lane[index];
	}*/
	
	public void actionPerformed(ActionEvent arg0) {
		// Has robot arrived at its destination?
		//System.out.println(partsRobot.getState());
		if(partsRobot.getState() == 1)		// partsRobot has arrived at nest
		{
			// Give item to partsRobot
			if(partsRobot.getSize() < 4)
			{
				partsRobot.addItem(nests.get(partsRobot.getDestinationNest()-1).popItem());
				partsRobot.setState(2);
			}
				partsRobotArrivedAtNest();
		}
		else if(partsRobot.getState() == 4)	// partsRobot has arrived at kitting station
		{
			for(int i = 0; i < partsRobot.getSize(); i++)
				station.addItem(partsRobot.popItem(),partsRobot.getDestinationKit());
			partsRobotArrivedAtStation();
		}
		else if(partsRobot.getState() == 6)
		{
			partsRobotArrivedAtCenter();
		}
		if(gantryRobot.getState() == 1)
		{
			gantryRobotArrivedAtPickup();
		}
		else if(gantryRobot.getState() == 4)
		{
			lane[gantryRobot.getDestinationFeeder()].setBin(gantryRobot.takeBin());
			lane[gantryRobot.getDestinationFeeder()].binExist = true;
			gantryRobotArrivedAtFeeder();
		}
		partsRobot.move();							// Update position and angle of partsRobot
		gantryRobot.move();
		belt.moveBelt(5);
		kitRobot.moveRobot(5);
		
		repaint();		
	}
}
	
	
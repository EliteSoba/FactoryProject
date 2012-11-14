package factory.graphics;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import factory.client.*;

public abstract class GraphicPanel extends JPanel implements ActionListener{
	
	public static final int WIDTH = 1100, HEIGHT = 720;
	protected Client am; //The Client that holds this
	
	// LANE MANAGER
	protected GraphicLaneManagerClient client;
	protected GraphicLaneManager [] lane;
	
	// KIT MANAGER
	protected GraphicKitBelt belt; //The conveyer belt
	protected GraphicKittingStation station; //The kitting station
	protected GraphicKittingRobot kitRobot;
	
	// PARTS MANAGER
	protected ArrayList<Nest> nests;
	protected PartsRobot partsRobot;
	
	// GANTRY
	protected GantryRobot gantryRobot;
	
	public void exportKit() {
		belt.exportKit();
	}
	
	public void sendMessage(String message) {
		//am.sendCommand(message);
	}
	
	public void newEmptyKitDone() {
		sendMessage("FILLER");
	}
	
	public void moveEmptyKitToSlotDone() {
		sendMessage("FILLER");
	}

	public void moveKitToInspectionDone() {
		sendMessage("FILLER");
	}
	
	public void takePictureOfInspectionSlotDone() {
		sendMessage("FILLER");
	}

	public void dumpKitAtInspectionDone() {
		sendMessage("FILLER");
	}

	public void moveKitFromInspectionToConveyorDone() {
		sendMessage("FILLER");
	}

	public void exportKitDone() {
		sendMessage("FILLER");
	}

	public void gantryRobotArrivedAtPickup() {
		sendMessage("FILLER");
	}

	public void gantryRobotArrivedAtFeeder() {
		sendMessage("FILLER");
	}

	public void partsRobotArrivedAtNest() {
		sendMessage("FILLER");
	}

	public void partsRobotArrivedAtStation() {
		sendMessage("FILLER");
	}

	public void partsRobotArrivedAtCenter() {
		sendMessage("FILLER");
	}

	public void feedLaneDone(int laneNum){
		sendMessage("FILLER");
	}
	
	public GraphicKittingStation getStation() {
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
	}
	
	public void paint(Graphics g) {
		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
			
	}

}

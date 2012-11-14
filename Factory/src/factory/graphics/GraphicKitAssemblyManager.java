package factory.graphics;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import factory.client.Client;

public class GraphicKitAssemblyManager extends GraphicPanel implements ActionListener{
	
	/*GraphicKitAssemblyManager.java (350x720) - Tobias Lee
	 * This is the graphical display of the Kit Assembly Manager
	 * Currently, this displays a conveyer belt and its kits, and a kitting station
	 * and animates the two.
	 * 
	 * TODO: (ã)
	 * 		[ã] Rework GraphicKittingStation to hold ArrayList of GraphicKits 
	 * 			>Give them positions<, or have them slide down to the bottom?
	 * 				Second option is easier, but no way of confirming that bottom-most kit will be filled first. If Robot needs to target completed kits, then first option is just as viable
	 * 		[ã] Update GraphicKittingRobot movement to add kit to GraphicKittingStation
	 * 		[ã] Add functionality of GraphicKittingRobot to move from kitting station back to belt
	 * 		[ã] Update Conveyor Belt:
	 * 				Only belt - no other peripherals
	 * 				Belt only moves when kit enters/exits
	 * 		[ã]	READ APIS ON WIKI: https://github.com/usc-csci200-fall2012/team02/wiki/Agent-Graphics-API
	 * 		[ã]	RENAME METHODS FOR CONSISTENCY TO MATCH ^
	 * 		[ã] Create GraphicItems class to display rudimentary items
	 * 		[ã] Rework GraphicKit to be able to hold GraphicItems
	 * 		[ã] Rotation of GraphicKit
	 * 		[ã] Rework GraphicKittingRobot to actually hold kits rather than using an image with a blank kit
	 * 		[ã]	Update pathing of Robot
	 * 		[X] Add functionality of GraphicKittingStation to randomly add items to a kit
	 * 				NO LONGER NECCESSARY BECAUSE V1
	 * 		[X] Create buttons for each potential command in ControlPanel
	 * 				NO LONBER NECCESSARY BECAUSE V1
	 * 		[ã] Update GraphicKittingStation so that the positions of the kits are not as autistic
	 * 				GraphicKittingRobot must be able to pick which kit it removes
	 * 		[ã]	Get as much as possible out of this class and into their subclasses
	 * 		[ ]	ADD COMMENTS TO EVERYTHING
	 * 		[X] ROBOT COMMANDS ROBOT COMMANDS ROBOT COMMANDS ROBOT COMMANDS ROBOT COMMANDS ROBOT COMMANDS ROBOT COMMANDS
	 * 				It can take 3 kits from the station at once...
	 * 				TOO MANY BRUTE FORCE SOLUTIONS. NEED ELEGANCE
	 * 				Queue of Command Strings would be kinda cool
	 * 				!- HANDLED BY 201 TEAM
	 * 		{ã} (Perhaps) Create a generic moveTo(int x, int y) function for GraphicKittingRobot to move to a location
	 * 		{ } Rework comments to have /**MESSAGE* / at the front of each method
	 * 				-LOW PRIORITY-
	 * 
	 * CURRENT ISSUES:
	 * 		[X] Hierarchy of commands: Robot will prioritize getting new kit from belt above all else
	 * 			- HARDFIXED FOR NOW. FIX LATER
	 * 			- HANDLE WHAT TO DO WHEN TOO MANY KITS ARE CIRCULATING (4TH KIT FROM BELT TRANSFERRING TO STATION)
	 * 			Are hardfixes really a bad thing?
	 * 			!- HANDLED BY 201 TEAM
	 */
	
	private Client am; //The JFrame that holds this. Will be removed when gets integrated with the rest of the project
	private GraphicKitBelt belt; //The conveyer belt
	private GraphicKittingStation station; //The kitting station
	private GraphicKittingRobot robot;
	public static final int WIDTH = 280, HEIGHT = 720;
	
	public GraphicKitAssemblyManager(JFrame FKAM) {
		//Constructor
		//x = 0;
		if (FKAM instanceof Client)
			am = (Client)FKAM;
		belt = new GraphicKitBelt(0, 0, this);
		station = new GraphicKittingStation(200, 191, this);
		robot = new GraphicKittingRobot(this, 70, 250);
		(new Timer(50, this)).start();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public void newEmptyKit() {
		//Adds a kit into the factory via conveyer belt
		if (belt.kitin())
			return;
		belt.inKit();
	}
	
	public void moveEmptyKitToSlot(int target) {
		//Sends robot to pick up kit from belt and move to designated slot in the station
		if (belt.pickUp() && !robot.kitted() && station.getKit(target) == null) {
			robot.setFromBelt(true);
			robot.setStationTarget(target);
		}
	}
	
	public void moveKitToInspection(int target) {
		//Sends robot to move kit from designated slot in the station to inspection station
		if (!robot.kitted() && station.getKit(target) != null) {
			robot.setCheckKit(true);
			robot.setStationTarget(target);
		}
	}
	
	public void takePictureOfInspectionSlot() {
		//Triggers the camera flash
		station.checkKit();
	}
	
	public void dumpKitAtInspection() {
		//Sends robot to move kit from inspection station to trash
		if (!robot.kitted() && station.getCheck() != null)
			robot.setPurgeKit(true);
	}
	
	public void moveKitFromInspectionToConveyor() {
		//Sends a kit out of the factory via conveyer belt
		if (station.getCheck() != null && !robot.kitted())
			robot.setFromCheck(true);
	}
	
	public void paint(Graphics g) {
		//Paints all the objects
		super.paint(g);
		
		belt.paint(g);
		station.paint(g);
		robot.paint(g);
	}
	
	public GraphicKittingStation getStation() {
		return station;
	}
	public GraphicKitBelt getBelt() {
		return belt;
	}

	public void actionPerformed(ActionEvent arg0) {
		//etc.
		belt.moveBelt(5);
		robot.moveRobot(5);
		repaint();
	}
	
	public static void main(String args[]) {
		JFrame f = new JFrame();
		f.add(new GraphicKitAssemblyManager(null));
		f.setVisible(true);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

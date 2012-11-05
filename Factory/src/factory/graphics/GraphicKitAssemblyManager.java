package factory.graphics;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GraphicKitAssemblyManager extends JPanel implements ActionListener{
	
	/*GraphicKitAssemblyManager.java (600x600) - Tobias Lee
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
	 * 		[ ] Create commands as instructed by the Wiki
	 * 		[ ] Rework comments to have /**MESSAGE* / at the front of each method 
	 * 		[ ] Create GraphicItems class to display rudimentary items
	 * 		[ ] Rework GraphicKit to be able to hold GraphicItems
	 * 		[ ] Rotation of GraphicKit
	 * 		[ ] Rework GraphicKittingRobot to actually hold kits rather than using an image with a blank kit
	 * 		[ ] Add functionality of GraphicKittingStation to randomly add items to a kit
	 * 		[ ] Create buttons for each potential command in ControlPanel
	 * 		[ ] Update GraphicKittingStation so that the positions of the kits are not as autistic
	 * 				GraphicKittingRobot must be able to pick which kit it removes
	 * 		[X] ROBOT COMMANDS ROBOT COMMANDS ROBOT COMMANDS ROBOT COMMANDS ROBOT COMMANDS ROBOT COMMANDS ROBOT COMMANDS
	 * 				It can take 3 kits from the station at once...
	 * 				TOO MANY BRUTE FORCE SOLUTIONS. NEED ELEGANCE
	 * 				Queue of Command Strings would be kinda cool
	 * 				!- HANDLED BY 201 TEAM
	 * 		{ } (Potentially) have GraphicKittingRobot know where to move to put items where they belong on GraphicKittingStation, instead of moving to a fixed spot
	 * 		{ } (Perhaps) Create a generic moveTo(int x, int y) function for GraphicKittingRobot to move to a location
	 * 
	 * CURRENT ISSUES:
	 * 		[X] Hierarchy of commands: Robot will prioritize getting new kit from belt above all else
	 * 			- HARDFIXED FOR NOW. FIX LATER
	 * 			- HANDLE WHAT TO DO WHEN TOO MANY KITS ARE CIRCULATING (4TH KIT FROM BELT TRANSFERRING TO STATION)
	 * 			Are hardfixes really a bad thing?
	 * 			!- HANDLED BY 201 TEAM
	 */
	
	//int x; //This was just for testing purposes, uncomment the x-related lines to watch a square move along a sin path
	private FrameKitAssemblyManager am; //The JFrame that holds this. Will be removed when gets integrated with the rest of the project
	private GraphicKitBelt belt; //The conveyer belt
	private GraphicKittingStation station; //The kitting station
	private GraphicKittingRobot robot;
	private boolean fromBelt;
	private boolean toStation;
	private boolean toBelt;
	private boolean toCheck;
	private boolean checkKit;
	private boolean fromCheck;
	private boolean purgeKit;
	private boolean toDump;
	private int stationTarget;
	
	public GraphicKitAssemblyManager(FrameKitAssemblyManager FKAM) {
		//Constructor
		//x = 0;
		am = FKAM;
		belt = new GraphicKitBelt(0, 0, this);
		station = new GraphicKittingStation(400, 131);
		robot = new GraphicKittingRobot(this, 250, 150);
		fromBelt = false;
		toStation = false;
		toBelt = false;
		toCheck = false;
		checkKit = false;
		fromCheck = false;
		purgeKit = false;
		toDump = false;
		stationTarget = 0;
		(new Timer(50, this)).start();
		this.setPreferredSize(new Dimension(600, 600));
	}
	
	public void addInKit() {
		//Adds a kit into the factory via conveyer belt
		if (belt.kitin())
			return;
		belt.inKit();
	}
	
	public void robotFromBelt(int target) {
		//Sends robot to pick up kit from belt and move to designated slot in the station
		if (belt.pickUp() && !robot.kitted() && station.getKit(target) == null) {
			fromBelt = true;
			stationTarget = target;
		}
	}
	
	public void checkKit(int target) {
		if (!robot.kitted() && station.getKit(target) != null) {
			checkKit = true;
			stationTarget = target;
		}
	}
	
	public void purgeKit() {
		if (!robot.kitted() && station.getCheck() != null)
			purgeKit = true;
	}
	
	public void outKit() {
		//Sends a kit out of the factory via conveyer belt
		if (station.getCheck() != null && !robot.kitted())
			fromCheck = true;
	}
	
	public void paint(Graphics g) {
		//Paints all the objects
		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, 600, 600);
		//g.setColor(Color.black);
		//g.fillRect(x, (int)(20*Math.sin(x/90.0*3.1415))+100, 5, 5);
		belt.paint(g);
		station.paint(g);
		robot.paint(g);
		
		belt.moveBelt(5);
		moveRobot();
		//x += 1;
	}
	
	public void moveRobot() {
		//Moving path control into separate method
		
		if (fromBelt) {
			if (robot.moveFromBelt(5)) {
				fromBelt = false;
				robot.setKit(belt.unKitIn());
				toStation = true;
			}
		}
		else if (toStation) {
			if (robot.moveToStation(5, stationTarget)) {
				toStation = false;
				station.addKit(robot.unkit(), stationTarget);
				am.fromBeltDone();
			}
		}
		else if (checkKit) {
			if (robot.moveFromStation(5, stationTarget)) {
				checkKit = false;
				robot.setKit(station.popKit(stationTarget));
				toCheck = true;
			}
		}
		else if (toCheck) {
			if (robot.moveToCheck(5)) {
				toCheck = false;
				station.addCheck(robot.unkit());
				am.toCheckDone();
			}
		}
		else if (fromCheck) {
			if (robot.moveToCheck(5)) {
				fromCheck = false;
				robot.setKit(station.popCheck());
				toBelt = true;
			}
		}
		else if (toBelt) {
			if (robot.moveToBelt(5)) {
				toBelt = false;
				belt.outKit(robot.unkit());
				am.outKitDone();
			}
		}
		else if (purgeKit) {
			if (robot.moveToCheck(5)) {
				purgeKit = false;
				robot.setKit(station.popCheck());
				toDump = true;
			}
		}
		else if (toDump) {
			if (robot.moveToTrash(5)) {
				toDump = false;
				robot.unkit();
				am.dumpDone();
			}
		}
		else
			robot.moveToStartX(5);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		//etc.
		repaint();
	}
	
	public boolean getFromBelt() {
		return fromBelt;
	}
	
	public boolean getToStation() {
		return toStation;
	}
	
	public boolean getToBelt() {
		return toBelt;
	}
	
	public void newEmptyKit() {
		am.newEmptyKitAtConveyor();
	}

}

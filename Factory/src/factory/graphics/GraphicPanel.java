//Minh La

package factory.graphics;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GraphicPanel extends JPanel implements ActionListener{
	
	//LANE MANAGER
	GraphicLaneManagerClient client;
	GraphicLaneManager [] lane;
	
	//KIT MANAGER
	private FactoryProductionManager am; //The JFrame that holds this. Will be removed when gets integrated with the rest of the project
	private GraphicKitBelt belt; //The conveyer belt
	private GraphicKittingStation station; //The kitting station
	private GraphicKittingRobot robot;
	public static final int WIDTH = 980, HEIGHT = 720;
	
	public GraphicPanel(FactoryProductionManager FKAM){
		
		lane = new GraphicLaneManager [4];
		lane[0] = new GraphicLaneManager(575,50);
		lane[1] = new GraphicLaneManager(575,210);
		lane[2] = new GraphicLaneManager(575,370);
		lane[3] = new GraphicLaneManager(575,530);
		
		am = FKAM;
		belt = new GraphicKitBelt(0, 0, this);
		station = new GraphicKittingStation(200, 191, this);
		robot = new GraphicKittingRobot(this, 70, 250);
		(new Timer(50, this)).start();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		this.setPreferredSize(new Dimension(980,720));
		this.setVisible(true);
	}
	

	public void paint(Graphics g){
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
		robot.paint(g);
		
		belt.moveBelt(5);
		robot.moveRobot(5);
	}
	public GraphicLaneManager getLane(int index) {
		return lane[index];
	}
	
	public void newEmptyKit() {
		//Adds a kit into the factory via conveyer belt
		if (belt.kitin())
			return;
		belt.inKit();
	}
	public void newEmptyKitDone() {
		am.newEmptyKitAtConveyor();
	}
	
	public void moveEmptyKitToSlot(int target) {
		//Sends robot to pick up kit from belt and move to designated slot in the station
		if (belt.pickUp() && !robot.kitted() && station.getKit(target) == null) {
			robot.setFromBelt(true);
			robot.setStationTarget(target);
		}
	}
	public void moveEmptyKitToSlotDone() {
		am.moveEmptyKitToSlotDone();
	}
	
	public void moveKitToInspection(int target) {
		//Sends robot to move kit from designated slot in the station to inspection station
		if (!robot.kitted() && station.getKit(target) != null) {
			robot.setCheckKit(true);
			robot.setStationTarget(target);
		}
	}
	public void moveKitToInspectionDone() {
		am.moveKitToInspectionDone();
	}
	
	public void takePictureOfInspectionSlot() {
		//Triggers the camera flash
		station.checkKit();
	}
	public void takePictureOfInspectionSlotDone() {
		am.takePictureOfInspectionSlotDone();
	}
	
	public void dumpKitAtInspection() {
		//Sends robot to move kit from inspection station to trash
		if (!robot.kitted() && station.getCheck() != null)
			robot.setPurgeKit(true);
	}
	public void dumpKitAtInspectionDone() {
		am.dumpKitAtInspectionDone();
	}
	
	public void exportKit() {
		//Sends a kit out of the factory via conveyer belt
		if (station.getCheck() != null && !robot.kitted())
			robot.setFromCheck(true);
	}
	public void exportKitDone() {
		am.exportKitDone();
	}
	
	public GraphicKittingStation getStation() {
		return station;
	}
	
	public GraphicKitBelt getBelt() {
		return belt;
	}

	public void actionPerformed(ActionEvent arg0) {
		repaint();		
	}
}
	
	
package factory.graphics;
import java.awt.*;
import javax.swing.*;

import factory.ConveyorAgent;
import factory.KitRobotAgent;
import factory.PartsRobotAgent;
import factory.StandAgent;
import factory.VisionAgent;

public class FrameKitAssemblyManager extends JFrame{
	
	/*FrameKitAssemblyManager.java (800x600) - Tobias Lee
	 * This integrates the GraphicKitAssemblyManager with a small Control Panel to demonstrate commands
	 * This will be removed when the project gets integrated
	 */
	
	GraphicKitAssemblyManager GKAM; //The Graphics part
	ControlPanel CP; //The Swing control panel
	
	
	// These are for v.0
	ConveyorAgent conveyor = new ConveyorAgent();
	VisionAgent vision = new VisionAgent();
	PartsRobotAgent partsRobot = new PartsRobotAgent();
	StandAgent stand = new StandAgent(conveyor, vision, null, partsRobot);
	KitRobotAgent kitRobot = new KitRobotAgent(stand, this);
	
	public FrameKitAssemblyManager() {
		//Constructor. BorderLayout
		GKAM = new GraphicKitAssemblyManager(this);
		GKAM.setPreferredSize(new Dimension(600, 600));
		this.add(GKAM, BorderLayout.CENTER);
		CP = new ControlPanel(this);
		CP.setPreferredSize(new Dimension(200, 600));
		this.add(CP, BorderLayout.LINE_END);
		
		// v.0 stuff
		stand.kitRobot = kitRobot;
		conveyor.startThread();
		vision.startThread();
		partsRobot.startThread();
		kitRobot.startThread();
		stand.startThread();
	}
	
	public void moveEmptyKitToSlot(int slot){
		GKAM.robotFromBelt(slot);
	}
	
	/**
	 * Method to send a new empty kit in the conveyor
	 */
	public void sendNewEmptyKit() {
		//Adds a Kit into the factory
		GKAM.addInKit();
		System.out.println("New Empty Kit Requested!");
	}
	
	public void outKit() {
		//Sends a Kit out of the factory
		GKAM.outKit();
	}
	
	public void kitToCheck(int slot) {
		GKAM.checkKit(slot);
	}
	
	public void dumpKit() {
		GKAM.purgeKit();
	}
	
	public void fromBelt() {
		kitRobot.msgGrabAndBringEmptyKitFromConveyorToSlot("topSlot");
	}
	
	public void newEmptyKitAtConveyor(){
		System.out.println("New Empty Kit Arrived!");
	}
	
	public void fromBeltDone() {
		System.out.println("Kit sent to Kitting Station!");
	}
	
	public void toCheckDone() {
		System.out.println("Kit sent to Inspection Station!");
	}
	
	public void dumpDone() {
		System.out.println("Kit has been dumped!");
	}
	
	public void outKitDone() {
		System.out.println("Kit has left the building!");
	}
	
	public static void main(String args[]) {
		//Implements this JFrame
		FrameKitAssemblyManager FKAM = new FrameKitAssemblyManager();
		FKAM.setVisible(true);
		FKAM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FKAM.setSize(800, 600);
	}

}

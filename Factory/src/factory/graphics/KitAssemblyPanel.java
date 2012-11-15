package factory.graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import factory.client.*;
import factory.*;
import java.util.Scanner;

public class KitAssemblyPanel extends GraphicPanel implements ActionListener{
	
	/*GraphicKitAssemblyManager.java (350x720) - Tobias Lee
	 * This is the graphical display of the Kit Assembly Manager
	 * Currently, this displays a conveyer belt and its kits, and a kitting station
	 * and animates the two.
	 * TODO: Comments
	 */
	
	public static final int WIDTH = 280, HEIGHT = 720;
	
	public KitAssemblyPanel(JFrame FKAM) {
		super();
		isKitAssemblyManager = true;
		
		if (FKAM instanceof Client)
			am = (Client)FKAM;
		
		belt = new GraphicKitBelt(0, 0, this);
		station = new GraphicKittingStation(200, 191, this);
		kitRobot = new GraphicKittingRobot(this, 70, 250);
		
		(new Timer(delay, this)).start();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setVisible(true);
	}
	
	public void newEmptyKit() {
		//Adds a kit into the factory via conveyer belt
		if (belt.kitin())
			return;
		belt.inKit();
	}
	
	public void moveEmptyKitToSlot(int target) {
		//Sends robot to pick up kit from belt and move to designated slot in the station
		if (belt.pickUp() && !kitRobot.kitted() && station.getKit(target) == null) {
			kitRobot.setFromBelt(true);
			kitRobot.setStationTarget(target);
		}
	}
	
	public void moveKitToInspection(int target) {
		//Sends robot to move kit from designated slot in the station to inspection station
		if (!kitRobot.kitted() && station.getKit(target) != null) {
			kitRobot.setCheckKit(true);
			kitRobot.setStationTarget(target);
		}
	}
	
	public void takePictureOfInspectionSlot() {
		//Triggers the camera flash
		station.checkKit();
	}
	
	public void dumpKitAtInspection() {
		//Sends robot to move kit from inspection station to trash
		if (!kitRobot.kitted() && station.getCheck() != null)
			kitRobot.setPurgeKit(true);
	}
	
	public void moveKitFromInspectionToConveyor() {
		//Sends a kit out of the factory via conveyer belt
		if (station.getCheck() != null && !kitRobot.kitted())
			kitRobot.setFromCheck(true);
	}
	
	/*public void paint(Graphics g) {
		//Paints all the objects
		super.paint(g);
		
//		belt.paint(g);
//		station.paint(g);
//		kitRobot.paint(g);
	}/**/

	public void actionPerformed(ActionEvent arg0) {
		//etc.
		belt.moveBelt(5);
		kitRobot.moveRobot(5);
		repaint();
	}
	
	public static void main(String args[]) {
		JFrame f = new JFrame();
		KitAssemblyPanel kam = new KitAssemblyPanel(f);
		f.add(kam);
		f.setVisible(true);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Scanner kbr = new Scanner(System.in);
		String command;
		do {
			command = kbr.nextLine();
			switch (command.charAt(0)) {
			case 'n': kam.newEmptyKit(); break;
			case '1': kam.moveEmptyKitToSlot(0); break;
			case '2': kam.moveEmptyKitToSlot(1); break;
			case '3': kam.moveKitToInspection(0); break;
			case '4': kam.moveKitToInspection(1); break;
			case 'f': kam.takePictureOfInspectionSlot(); break;
			case 'd': kam.dumpKitAtInspection(); break;
			case 'e': kam.moveKitFromInspectionToConveyor(); break;
			case 'o': kam.exportKit(); break;
			case 'q': System.exit(0); break;
			}
		} while(command.charAt(0) != 'q');
	}

}

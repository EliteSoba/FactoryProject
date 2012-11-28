package factory.graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import factory.client.*;
import factory.*;
import java.util.Scanner;

/**
 * @author Minh La, Tobias Lee, George Li<p>
 * <b>{@code KitAssemblyPanel.java}</b> (280x720)<br>
 * This is the graphical component of the Kit Assembly Manager.<br>
 * This panel displays the Kit Robot, Kit Station, and Conveyor Belt
 */

public class KitAssemblyPanel extends GraphicPanel implements ActionListener{
	
	public KitAssemblyPanel(JFrame FKAM) {
		super();
		WIDTH = 280;
		isKitAssemblyManager = true;
		
		if (FKAM instanceof Client)
			am = (Client)FKAM;
		
		belt = new GraphicConveyorBelt(0, 0, this);
		station = new GraphicKittingStation(200, 191, this);
		kitRobot = new GraphicKittingRobot(this, 70, 250);
		
		(new Timer(DELAY, this)).start();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		//etc.
		belt.moveBelt(5);
		kitRobot.moveRobot(5);
		repaint();
	}
	
	public static void main(String args[]) {
		//Testing
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
			case '5': kam.moveKitFromInspectionBackToStation(0); break;
			case '6': kam.moveKitFromInspectionBackToStation(1); break;
			case 'f': kam.takePictureOfInspectionSlot(); break;
			case 'd': kam.dumpKitAtInspection(); break;
			case 'e': kam.moveKitFromInspectionToConveyor(); break;
			case 'o': kam.exportKit(); break;
			case 'q': System.exit(0); break;
			}
		} while(command.charAt(0) != 'q');
	}

}

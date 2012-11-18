package factory.graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import factory.client.*;
import factory.*;
import java.util.Scanner;

public class KitAssemblyPanel extends GraphicPanel implements ActionListener{
	
	/*GraphicKitAssemblyManager.java (280x720) - Tobias Lee
	 * This is the graphical display of the Kit Assembly Manager
	 * Currently, this displays a conveyer belt and its kits, and a kitting station
	 * and animates the two.
	 * TODO: Comments
	 */
	
	public KitAssemblyPanel(JFrame FKAM) {
		super(0);
		WIDTH = 280;
		isKitAssemblyManager = true;
		
		if (FKAM instanceof Client)
			am = (Client)FKAM;
		
		/*belt = new GraphicConveyorBelt(0, 0, this);
		station = new GraphicKittingStation(200, 191, this);
		kitRobot = new GraphicKittingRobot(this, 70, 250);*/
		
		(new Timer(delay, this)).start();
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

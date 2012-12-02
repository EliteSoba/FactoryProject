package factory.graphics;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import factory.client.*;
import factory.Part;

/**
 * @author Minh La, Tobias Lee, George Li<p>
 * <b>{@code KitAssemblyPanel.java}</b> (300x720)<br>
 * This is the graphical component of the Gantry Manager.<br>
 * This panel displays the Gantry Robot, and the Feeders
 */

public class GantryRobotPanel extends GraphicPanel implements ActionListener{
	
	public GantryRobotPanel(JFrame GR) {
		super();
		WIDTH = 300;
		isGantryRobotManager = true;
		
		if (GR instanceof Client)
			am = (Client)GR;
		
		lane = new GraphicLaneManager [4];
		for (int i = 0; i < lane.length; i++)
			lane[i] = new GraphicLaneManager(-200, 160*i + 50, i, this);
		
		gantryRobot = new GraphicGantryRobot(WIDTH-125,HEIGHT/2,0,5,5,10,100,100,"Images/robot2.png");
		
		(new Timer(DELAY, this)).start();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		gantryRobotStateCheck();
		gantryRobot.move();
		
		moveLanes();
		
		repaint();
	}
	
	public static void main(String args[]) {
		//For testing
		JFrame f = new JFrame();
		GantryRobotPanel gr = new GantryRobotPanel(f);
		f.add(gr);
		f.setVisible(true);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Scanner kbr = new Scanner(System.in);
		String command;
		do {
			command = kbr.nextLine();
			switch (command.charAt(0)) {
			case 'n': gr.moveGantryRobotToPickup(command.substring(1)); break;
			case '1': gr.moveGantryRobotToFeederForDropoff(0); break;
			case '2': gr.moveGantryRobotToFeederForDropoff(1); break;
			case '3': gr.moveGantryRobotToFeederForDropoff(2); break;
			case '4': gr.moveGantryRobotToFeederForDropoff(3); break;

			case '!': gr.moveGantryRobotToFeederForPickup(0); break;
			case '@': gr.moveGantryRobotToFeederForPickup(1); break;
			case '#': gr.moveGantryRobotToFeederForPickup(2); break;
			case '$': gr.moveGantryRobotToFeederForPickup(3); break;
			case 'q': System.exit(0); break;
			}
		} while(command.charAt(0) != 'q');
	}

}

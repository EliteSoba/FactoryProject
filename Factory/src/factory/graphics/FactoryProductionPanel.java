package factory.graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import factory.*;
import factory.client.*;

/**
 * @author Minh La, Tobias Lee, George Li<p>
 * <b>{@code FactoryProductionPanel.java}</b> (1100x720)<br>
 * This is the graphical component of the Factory Production Manager.<br>
 * This panel displays every component.
 */

public class FactoryProductionPanel extends GraphicPanel implements ActionListener {
	
	public FactoryProductionPanel(JFrame FKAM) {
		super();
		WIDTH = 1100;
		isFactoryProductionManager = true;
		
		if (FKAM instanceof Client)
			am = (Client)FKAM;
		
		belt = new GraphicConveyorBelt(0, 0, this);
		station = new GraphicKittingStation(200, 191, this);
		kitRobot = new GraphicKittingRobot(this, 70, 250);
		
		// Parts robot client
		// Add 8 nests
		nests = new ArrayList<GraphicNest>();	
		for(int i = 0; i < 8; i++)
		{
			GraphicNest newNest = new GraphicNest(510,i*80+50,0,0,0,0,75,75,"Images/nest3x3.png");
			Random randomGen = new Random();
			nests.add(newNest);
		}

		lane = new GraphicLaneManager [4];
		for (int i = 0; i < lane.length; i++)
			lane[i] = new GraphicLaneManager(510, 160*i + 50, i, this);
		
		partsRobot = new GraphicPartsRobot(WIDTH/2-200,HEIGHT/2,0,5,5,10,114,100,"Images/robot1.png");
		gantryRobot = new GraphicGantryRobot(WIDTH-150,HEIGHT/2,0,5,5,10,100,114,"Images/robot2.png");
		
		(new Timer(DELAY, this)).start();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		partsRobotStateCheck();
		gantryRobotStateCheck();
		
		moveLanes();
		
		partsRobot.move();							// Update position and angle of partsRobot
		gantryRobot.move();
		belt.moveBelt(5);
		kitRobot.moveRobot(5);
		
		repaint();		
	}
}
	
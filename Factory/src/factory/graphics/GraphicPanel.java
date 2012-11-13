package factory.graphics;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GraphicPanel extends JPanel implements ActionListener{
	
	public static final int WIDTH = 1100, HEIGHT = 720;
	
	// LANE MANAGER
	protected GraphicLaneManagerClient client;
	protected GraphicLaneManager [] lane;
	
	// KIT MANAGER
	protected FactoryProductionManager am; //The JFrame that holds this. Will be removed when gets integrated with the rest of the project
	protected GraphicKitBelt belt; //The conveyer belt
	protected GraphicKittingStation station; //The kitting station
	protected GraphicKittingRobot kitRobot;
	
	// PARTS MANAGER
	protected  ArrayList<Nest> nests;
	protected PartsRobot partsRobot;
	
	// GANTRY
	protected GantryRobot gantryRobot;
	
	
	
	public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
			
	}

}

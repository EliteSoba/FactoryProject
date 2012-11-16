package factory.graphics;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import factory.client.*;

public abstract class GraphicPanel extends JPanel implements ActionListener{
	
	public static final int WIDTH = 1100, HEIGHT = 720;
	public static final int delay = 50;
	
	protected Client am; //The Client that holds this
	
	protected boolean isLaneManager;
	protected boolean isGantryRobotManager;
	protected boolean isKitAssemblyManager;
	protected boolean isFactoryProductionManager;
	
	// LANE MANAGER
	protected GraphicLaneManagerClient client;
	protected GraphicLaneManager [] lane;
	
	// KIT MANAGER
	protected GraphicKitBelt belt; //The conveyer belt
	protected GraphicKittingStation station; //The kitting station
	protected GraphicKittingRobot kitRobot;
	
	// PARTS MANAGER
	protected ArrayList<Nest> nests;
	protected PartsRobot partsRobot;
	
	// GANTRY
	protected GantryRobot gantryRobot;
	
	public GraphicPanel() {
		am = null;
		client = null;
		lane = null;
		belt = null;
		station = null;
		kitRobot = null;
		nests = null;
		partsRobot = null;
		gantryRobot = null;
		
		isLaneManager = false;
		isGantryRobotManager = false;
		isKitAssemblyManager = false;
		isFactoryProductionManager = false;
		
		flashImage = Toolkit.getDefaultToolkit().getImage("Images/flash3x3.png");
	}
	
	// CAMERA
	protected int flashCounter;
	protected int flashFeederIndex;
	protected static Image flashImage;
	
	public void exportKit() {
		if (isKitAssemblyManager || isFactoryProductionManager)
			belt.exportKit();
	}
	
	public void sendMessage(String command) {
		//if (am == null)
			//return;
		//asdfasd
		String message;
		if (isLaneManager)
			message = "lm ";
		else if (isGantryRobotManager)
			message = "grm ";
		else if (isKitAssemblyManager)
			message = "kam ";
		else if (isFactoryProductionManager)
			message = "fpm ";
		else
			return;
		
		if (am != null)
			am.sendCommand(message + command);
		else
			System.out.println(message + command);
	}
	
	public void newEmptyKitDone() {
		sendMessage("cca cnf");
	}
	
	public void moveEmptyKitToSlotDone() {
		sendMessage("kra cnf");
	}

	public void moveKitToInspectionDone() {
		sendMessage("kra cnf");
	}
	
	public void takePictureOfInspectionSlotDone() {
		sendMessage("va cnf");
	}

	public void dumpKitAtInspectionDone() {
		sendMessage("kra cnf");
	}

	public void moveKitFromInspectionToConveyorDone() {
		sendMessage("kra cnf");
	}

	public void exportKitDone() {
		sendMessage("ca cnf");
	}
	
	public void cameraFlashDone() {
		sendMessage("FILLER");
	}

	public void gantryRobotArrivedAtPickup() {
		sendMessage("FILLER");
	}

	public void gantryRobotArrivedAtFeeder() {
		sendMessage("FILLER");
	}

	public void partsRobotArrivedAtNest() {
		sendMessage("FILLER");
	}

	public void partsRobotArrivedAtStation() {
		sendMessage("FILLER");
	}

	public void partsRobotArrivedAtCenter() {
		sendMessage("FILLER");
	}

	public void feedLaneDone(int laneNum){
		sendMessage("FILLER");
	}
	
	public void purgeTopLaneDone(int feederNum) {
		sendMessage("fa cnf " + feederNum);
	}
	
	public void purgeBottomLaneDone(int feederNum) {
		sendMessage("fa cnf " + feederNum);
	}
	
	public void purgeFeederDone(int feederNum) {
		sendMessage("fa cnf " + feederNum);
	}
	
	public void startFeederDone(int feederNum) {
		sendMessage("fa cnf " + feederNum);
	}
	
	public void stopFeederDone(int feederNum) {
		sendMessage("fa cnf " + feederNum);
	}
	
	public void switchFeederLaneDone(int feederNum) {
		sendMessage("fa cnf " + feederNum);
	}
	
	public GraphicKittingStation getStation() {
		return station;
	}
	public GraphicKitBelt getBelt() {
		return belt;
	}
	public ArrayList<Nest> getNest(){
		return nests;
	}
	public GraphicLaneManager getLane(int index) {
		return lane[index];
	}
	
	public void paint(Graphics g) {
		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if (isKitAssemblyManager || isFactoryProductionManager) {
			belt.paint(g);
			station.paint(g);
			kitRobot.paint(g);
		}
		
		if (isLaneManager || isFactoryProductionManager) {
			for (int i = 0; i < lane.length; i++) {
				//if (lane[i] != null)
				lane[i].paintLane(g);
			}
		}
		
		if (isLaneManager || isFactoryProductionManager || isGantryRobotManager) {
			for (int i = 0; i < lane.length; i++) {
				//if (lane[i] != null)
				lane[i].paintFeeder(g);
			}
		}
		
		// Parts robot client
		// Draw the nests
		if (isLaneManager || isFactoryProductionManager)
		for(int i = 0; i < nests.size(); i++) {
			Nest currentNest = nests.get(i);
			currentNest.paint(g);
		}
		
		if(isLaneManager || isFactoryProductionManager) {
			if(flashCounter >= 0)
			{
				int flashX = nests.get(flashFeederIndex*2).getX();
				int flashY = nests.get(flashFeederIndex*2).getY();
				//System.out.println("==="+flashX+" "+flashY);
				g.drawImage(flashImage, flashX, flashY, null);
				flashX = nests.get(flashFeederIndex*2+1).getX();
				flashY = nests.get(flashFeederIndex*2+1).getY();
				g.drawImage(flashImage, flashX, flashY, null);
				flashCounter --;
			}
		}
		
		// Draw the parts robot
		if (isFactoryProductionManager) {
			final Graphics2D g3 = (Graphics2D)g.create();
			g3.rotate(Math.toRadians(360-partsRobot.getAngle()), partsRobot.getX()+partsRobot.getImageWidth()/2, partsRobot.getY()+partsRobot.getImageHeight()/2);
			// Draw items partsRobot is carrying
			partsRobot.paint(g3);
			g3.dispose();
		}
		
		// Draw the Gantry Robot
		if (isGantryRobotManager || isFactoryProductionManager) {
			final Graphics2D g4 = (Graphics2D)g.create();
			g4.rotate(Math.toRadians(360-gantryRobot.getAngle()), gantryRobot.getX()+gantryRobot.getImageWidth()/2, gantryRobot.getY()+gantryRobot.getImageHeight()/2);
			gantryRobot.paint(g4);
			// Draw bin gantryRobot is carrying
			
			g4.dispose();
		}
	}
	
	public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
			
	}

}

package factory.graphics;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import factory.Part;

public class ControlPanel extends JPanel implements ActionListener{
	
	/*ControlPanel.java (200x720) - Tobias Lee
	 * This is a demonstrative Control Panel to show what the Graphics part can do.
	 * Will be removed when integration occurs
	 */

	GraphicFactoryProductionManager am; //The JFrame etc, etc.
	JLabel[] blank;
	int blanki;
	
	JButton test;
	JButton outKit;
	JButton fromBelt;
	JButton inspect1;
	JButton inspect2;
	JButton dumpKit;
	JButton partsRobotNest1;
	JButton partsRobotStation;
	JButton gantryRobotGetBin;
	JButton gantryRobotFeeder1;
	JButton cameraFlash;
	JButton feedLane1, feedLane2, purgeLane1, purgeLane2, purgeFeeder;
	
	public ControlPanel(GraphicFactoryProductionManager fpm) {
		//Constructor
		am = fpm;
		blanki = 0;
		inspect1 = new JButton("Kit 1 Complete");
		inspect2 = new JButton("Kit 2 Complete");
		outKit = new JButton("Send Kit Out");
		dumpKit = new JButton("Dump Kit");
		test = new JButton("Quit");
		blank = new JLabel[20];
		partsRobotNest1 = new JButton("Nest 1 Full");
		partsRobotStation = new JButton("Station");
		gantryRobotGetBin = new JButton("Fetch a Bin");
		gantryRobotFeeder1 = new JButton("Feeder 1 Dump");
		feedLane1 = new JButton("Feed Lane 1");
		feedLane2 = new JButton("Feed Lane 2");
		purgeLane1 = new JButton("purge Lane 1");
		purgeLane2 = new JButton("Purge Lane 2");
		purgeFeeder = new JButton("purge Feeder");
		cameraFlash = new JButton("Camera Flash Nest 1");
		for (int i = 0; i < 20; i++) {
			blank[i] = new JLabel("   ");
			blank[i].setPreferredSize(new Dimension(150, 10));
		}
		this.setLayout(new FlowLayout());
		//this.add(fromBelt);
		this.addButton(inspect1);
		this.addButton(inspect2);
		/*this.add(outKit);
		this.add(dumpKit);*/
		this.addButton(test);
		this.addButton(partsRobotNest1);
		this.addButton(partsRobotStation);
		this.addButton(feedLane1);
		this.addButton(feedLane2);
		this.addButton(purgeLane1);
		this.addButton(purgeLane2);
		this.addButton(purgeFeeder);
		this.addButton(gantryRobotGetBin);
		this.addButton(gantryRobotFeeder1);
		this.addButton(cameraFlash);
		this.setPreferredSize(new Dimension(200, 720));
	}
	
	private void addButton(JButton button) {
		this.add(button);
		this.add(blank[blanki++]);
		button.addActionListener(this);
		button.setPreferredSize(new Dimension(170, 30));
	}
	
	public void actionPerformed(ActionEvent arg0) {
		//Button presses
		Object source = arg0.getSource();
		if (source == test)
			System.exit(0);
		else if (source == outKit)
			am.exportKit();
		else if (source == inspect1)
			//am.kitToCheck(0);
			am.sendNewEmptyKit();
		else if (source == inspect2)
			//am.kitToCheck(1);
			am.moveEmptyKitToSlot(0);
		else if (source == dumpKit)
			am.dumpKit();
		else if(source == partsRobotNest1)
			am.moveRobotToNest1();
		else if(source == partsRobotStation)
			am.moveRobotToStation();
		else if(source == feedLane1)
			am.feedLane(0);	//FEEDS THE LANE! Lane 1-8, NOT 0-7
		else if(source == feedLane2)
			am.feedLane(1);
		else if(source == purgeLane1)
			am.purgeTopLane(0);
		else if(source == purgeLane2)
			am.purgeBottomLane(0);
		else if(source == purgeFeeder)
			am.purgeFeeder(0);
		else if(source == gantryRobotGetBin)
			am.getBin();
		else if(source == gantryRobotFeeder1)
			am.moveGantryToFeeder1();
		else if(source == cameraFlash)
			am.takePictureFeeder1();
	}

}

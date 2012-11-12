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

	FactoryProductionManager am; //The JFrame etc, etc.
	JLabel[] blank;
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
	JButton feedLane1;
	
	public ControlPanel(FactoryProductionManager fpm) {
		//Constructor
		am = fpm;
		inspect1 = new JButton("Kit 1 Complete");
		inspect2 = new JButton("Kit 2 Complete");
		outKit = new JButton("Send Kit Out");
		dumpKit = new JButton("Dump Kit");
		test = new JButton("Quit");
		blank = new JLabel[10];
		partsRobotNest1 = new JButton("Nest 1 Full");
		partsRobotStation = new JButton("Station");
		gantryRobotGetBin = new JButton("Fetch a Bin");
		gantryRobotFeeder1 = new JButton("Feeder 1 Dump");
		feedLane1 = new JButton("Feed Lane 1");
		for (int i = 0; i < 10; i++) {
			blank[i] = new JLabel("   ");
			blank[i].setPreferredSize(new Dimension(150, 10));
		}
		this.setLayout(new FlowLayout());
		//this.add(blank[0]);
		//this.add(blank[1]);
		//this.add(fromBelt);
		this.add(blank[2]);
		this.add(inspect1);
		this.add(blank[3]);
		this.add(inspect2);
		this.add(blank[4]);
		/*this.add(outKit);
		this.add(blank[5]);
		this.add(dumpKit);
		this.add(blank[6]);*/
		this.add(test);
		this.add(blank[5]);
		this.add(partsRobotNest1);
		this.add(blank[6]);
		this.add(partsRobotStation);
		this.add(blank[7]);
		this.add(feedLane1);
		this.add(blank[8]);
		this.add(gantryRobotGetBin);
		this.add(blank[9]);
		this.add(gantryRobotFeeder1);
		this.setPreferredSize(new Dimension(200, 720));
		outKit.addActionListener(this);
		outKit.setPreferredSize(new Dimension(170, 50));
		//fromBelt.addActionListener(this);
		//fromBelt.setPreferredSize(new Dimension(170, 50));
		inspect1.addActionListener(this);
		inspect1.setPreferredSize(new Dimension(170, 50));
		inspect2.addActionListener(this);
		inspect2.setPreferredSize(new Dimension(170, 50));
		dumpKit.addActionListener(this);
		dumpKit.setPreferredSize(new Dimension(170, 50));
		test.addActionListener(this);
		test.setPreferredSize(new Dimension(170, 50));
		partsRobotNest1.addActionListener(this);
		partsRobotNest1.setPreferredSize(new Dimension(170, 50));
		partsRobotStation.addActionListener(this);
		partsRobotStation.setPreferredSize(new Dimension(170,50));
		gantryRobotGetBin.addActionListener(this);
		gantryRobotGetBin.setPreferredSize(new Dimension(170,50));
		gantryRobotFeeder1.addActionListener(this);
		gantryRobotFeeder1.setPreferredSize(new Dimension(170,50));
		feedLane1.addActionListener(this);
		feedLane1.setPreferredSize(new Dimension(170, 50));
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
			am.kitToCheck(1);
		else if (source == dumpKit)
			am.dumpKit();
		else if(source == partsRobotNest1)
			am.moveRobotToNest1();
		else if(source == partsRobotStation)
			am.moveRobotToStation();
		else if(source == feedLane1)
			am.feedLane(new GraphicBin(new Part("ear")),1,true);
		//else if(source == gantryRobotGetBin)
		//	am.getBin();
		//else if(source == gantryRobotFeeder1)
		//	am.moveGantryToFeeder1();
	}

}

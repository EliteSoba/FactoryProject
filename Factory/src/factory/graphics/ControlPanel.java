package factory.graphics;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ControlPanel extends JPanel implements ActionListener{
	
	/*ControlPanel.java (200x600) - Tobias Lee
	 * This is a demonstrative Control Panel to show what the Graphics part can do.
	 * Will be removed when integration occurs
	 */

	FrameKitAssemblyManager am; //The JFrame etc, etc.
	JButton sendKit; //Buttons, etc.
	JLabel[] blank;
	JButton test;
	JButton outKit;
	//JButton fromBelt;
	JButton inspect1;
	JButton inspect2;
	JButton dumpKit;
	
	public ControlPanel(FrameKitAssemblyManager FKAM) {
		//Constructor
		am = FKAM;
		sendKit = new JButton("Send New Kit");
		inspect1 = new JButton("Send Kit 1 to Inspection");
		inspect2 = new JButton("Send Kit 2 to Inspection");
		outKit = new JButton("Send Kit Out");
		dumpKit = new JButton("Dump Kit");
		//fromBelt = new JButton("Get Kit from Belt");
		test = new JButton("Quit");
		blank = new JLabel[10];
		for (int i = 0; i < 10; i++) {
			blank[i] = new JLabel("   ");
			blank[i].setPreferredSize(new Dimension(150, 10));
		}
		this.setLayout(new FlowLayout());
		this.add(blank[0]);
		this.add(sendKit);
		this.add(blank[1]);
		//this.add(fromBelt);
		//this.add(blank[2]);
		this.add(inspect1);
		this.add(blank[3]);
		this.add(inspect2);
		this.add(blank[4]);
		/*this.add(outKit);
		this.add(blank[5]);
		this.add(dumpKit);
		this.add(blank[6]);*/
		this.add(test);
		this.setPreferredSize(new Dimension(200, 600));
		sendKit.addActionListener(this);
		sendKit.setPreferredSize(new Dimension(170, 50));
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
	}
	
	public void actionPerformed(ActionEvent arg0) {
		//Button presses
		Object source = arg0.getSource();
		if (source == test)
			System.exit(0);
		else if (source == sendKit)
			am.sendNewEmptyKit();
		else if (source == outKit)
			am.outKit();
		//else if (source == fromBelt)
			//am.fromBelt();
		else if (source == inspect1)
			am.kitToCheck(0);
		else if (source == inspect2)
			am.kitToCheck(1);
		else if (source == dumpKit)
			am.dumpKit();
	}

}

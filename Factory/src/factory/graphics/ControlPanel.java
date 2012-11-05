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
	JButton fromBelt;
	
	public ControlPanel(FrameKitAssemblyManager FKAM) {
		//Constructor
		am = FKAM;
		sendKit = new JButton("Send Kit In");
		outKit = new JButton("Send Kit Out");
		fromBelt = new JButton("Get Kit from Belt");
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
		this.add(fromBelt);
		this.add(blank[2]);
		this.add(outKit);
		this.add(blank[3]);
		this.add(test);
		this.setPreferredSize(new Dimension(200, 600));
		sendKit.addActionListener(this);
		sendKit.setPreferredSize(new Dimension(150, 50));
		outKit.addActionListener(this);
		outKit.setPreferredSize(new Dimension(150, 50));
		fromBelt.addActionListener(this);
		fromBelt.setPreferredSize(new Dimension(150, 50));
		test.setPreferredSize(new Dimension(150, 50));
		test.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		//Button presses
		if (arg0.getSource() == test)
			System.exit(0);
		else if (arg0.getSource() == sendKit)
			am.sendNewEmptyKit();
		else if (arg0.getSource() == outKit)
			am.addOutKit();
		else if (arg0.getSource() == fromBelt)
			am.fromBelt();
	}

}

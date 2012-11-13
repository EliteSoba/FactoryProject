/*

Author: Joey Huang
Last Edited: 11/11/12 3:38pm

Contains non-normative scenario controls for the Gantry Robot.
*/

package factory.swing;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import factory.managers.*;

public class GantryManPanel extends JPanel implements ActionListener {
	JButton robotRevoltButton;
	GantryManager gantryManager;

	public GantryManPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

	c.gridx = 1;
	c.gridy = 1;
	c.gridwidth = 2;
	c.insets = new Insets(0,0,25,0); 
	add(new JLabel("Gantry Robot Manager"),c);
	
	c.insets = new Insets(10,0,10,0);
	c.gridy = 2;
	c.anchor = GridBagConstraints.LINE_START;
	
	add(new JLabel("Non-Normative Scenarios"),c);
	c.gridy = 3;
	c.gridwidth = 1;
	
	robotRevoltButton = new JButton("Robot Revolt");
	robotRevoltButton.addActionListener(this);
	add(robotRevoltButton,c);
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == robotRevoltButton){
			gantryManager.sendMessage(null, null, "robotRevolt");
			// do something v.2
			// gantry manager send message
		}
	}

	public void setManager(GantryManager gm) {
		gantryManager = gm;	
	}
}

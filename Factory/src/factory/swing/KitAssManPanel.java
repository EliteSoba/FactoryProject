package factory.swing;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import factory.managers.*;

/*
* This class is the GUI for the Kit Assembly Manaager. This will be
* instantiated in the general KitAssManager class (which extends JFrame).
* Written by : Marc Mendiola
* Last edited : 11/4/12 11:03 PM
*/


public class KitAssManPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = -528843836963262949L;
	JLabel title;
	JLabel subtitle;
	JButton incorrectKits;
	JButton kitRobotFreeze;
	KitAssemblyManager kitAssemblyManager;
	
	JLabel currentKit; //will display current kit in production
	JRadioButton item1; //will get the string of item name
	JRadioButton item2; //will get the string of item name
	JRadioButton item3; //will get the string of item name
	JRadioButton item4; //will get the string of item name
	JRadioButton item5; //will get the string of item name
	JRadioButton item6; //will get the string of item name
	JRadioButton item7; //will get the string of item name
	JRadioButton item8; //will get the string of item name
	JButton go; //command to robot to ruin next kit

	public KitAssManPanel(KitAssemblyManager k){
		kitAssemblyManager = k;
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		/*
		title = new JLabel("Kit Assembly Manager");
		title.setFont(new Font("Serif", Font.BOLD, 16));
		subtitle = new JLabel("Non-Normative : ");
		incorrectKits = new JButton("Incorrect Kits");
		kitRobotFreeze = new JButton("Kit Robot Freeze");*/
		
		currentKit = new JLabel("No kits to ruin");
		item1 = new JRadioButton("Part 1");
		item2 = new JRadioButton("Part 2");
		item3 = new JRadioButton("Part 3");
		item4 = new JRadioButton("Part 4");
		item5 = new JRadioButton("Part 5");
		item6 = new JRadioButton("Part 6");
		item7 = new JRadioButton("Part 7");
		item8 = new JRadioButton("Part 8");
		go = new JButton("GO!");
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.insets = new Insets(5,10,5,10);
		
		this.add(currentKit, c);
		c.gridx = 0;
		c.gridy = 1;
		this.add(item1, c);
		c.gridx = 0;
		c.gridy = 2;
		this.add(item2, c);
		c.gridx = 0;
		c.gridy = 3;
		this.add(item3, c);
		c.gridx = 0;
		c.gridy = 4;
		this.add(item4, c);
		c.gridx = 0;
		c.gridy = 5;
		this.add(item5, c);
		c.gridx = 0;
		c.gridy = 6;
		this.add(item6, c);
		c.gridx = 0;
		c.gridy = 7;
		this.add(item7, c);
		c.gridx = 0;
		c.gridy = 8;
		this.add(item8, c);
	}

	public void actionPerformed(ActionEvent ae) {
		String set = new String (" ");
		if(ae.getSource() == incorrectKits){
			kitAssemblyManager.sendCommand("incorrectKits");
			//to be implemented in v.2
		}
		
		else if (ae.getSource() == kitRobotFreeze){
			kitAssemblyManager.sendCommand("kitRobotFreeze");
			//to be implemented in v.2
		}
	}

}

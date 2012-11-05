package factory.swing;

import java.awt.*;

import javax.swing.*;


public class KitAssManPanel extends JPanel{
	private static final long serialVersionUID = -528843836963262949L;
	JLabel title;
	JLabel subtitle;
	JButton incorrectKits;
	JButton kitRobotFreeze;
	
	public KitAssManPanel(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		title = new JLabel("Kit Assembly Manager");
		title.setFont(new Font("Serif", Font.BOLD, 16));
		subtitle = new JLabel("Non-Normative : ");
		incorrectKits = new JButton("Incorrect Kits");
		kitRobotFreeze = new JButton("Kit Robot Freeze");
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.VERTICAL;
		c.insets = new Insets(0,0,20,0);
		this.add(title, c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		this.add(subtitle, c);
		c.gridx = 0;
		c.gridy = 2;
		this.add(incorrectKits, c);
		c.gridx = 1;
		c.gridy = 2;
		this.add(kitRobotFreeze, c);
	}

}

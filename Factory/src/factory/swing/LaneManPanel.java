//Stephanie Reagle
//CS 200
package factory.swing;

import java.awt.*;
import javax.swing.*;

public class LaneManPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4485912622490446254L;
	JButton zombie = new JButton("Zombie Apocalypse");
	JButton robotMutiny = new JButton("Robot Mutiny");
	JButton fire = new JButton("Fire");
	JButton earthquake = new JButton("Earthquake");

	public LaneManPanel(){
		
	       GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		add(zombie, c);

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 1;
		add(robotMutiny, c);

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 0;
		add(fire, c);

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 1;
		add(earthquake, c);
	
	}
	 //main method used for testing
	//do not delete just comment out
	/*
	public static void main (String[] args){
		LaneManPanel l = new LaneManPanel();
		l.repaint();
		l.setVisible(true);
		l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		l.setSize(400,450);
		l.repaint(); 
	}*/
		
}

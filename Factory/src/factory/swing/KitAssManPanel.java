package factory.swing;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

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

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource() == incorrectKits){
			System.out.println("Initiating non-normative case : Incorrect Kits");
		}else if (ae.getSource() == kitRobotFreeze){
			System.out.println("Initiating non-normative case : Kit Robot Freeze");
		}
	}

}

package factory.swing;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import factory.managers.*;

//Marc Mendiola, Stephanie Reagle

public class KitAssManPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = -528843836963262949L;

	KitAssemblyManager kitAssemblyManager;

	public Boolean kitsProduced = false;


	JLabel label; //"Bad Kit - Non normative"
	JLabel labelKit1; //"Current Kit: "
	JLabel labelKit2; //will display current kit in production

	//buttons to select items to drop - will get the string of item name

	JRadioButton item1; 
	JRadioButton item2; 
	JRadioButton item3; 
	JRadioButton item4; 
	JRadioButton item5; 
	JRadioButton item6; 
	JRadioButton item7; 
	JRadioButton item8; 

	//label the radio buttons with corresponding item labels
	JLabel i1;
	JLabel i2;
	JLabel i3;
	JLabel i4;
	JLabel i5;
	JLabel i6;
	JLabel i7;
	JLabel i8;

	JTextArea updateField; //will display updates
	JButton go; //command to robot to ruin next kit

	public KitAssManPanel(KitAssemblyManager k){
		kitAssemblyManager = k;

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		label = new JLabel("Bad Kit - Non normative");
		labelKit1 = new JLabel("Current Kit: ");
		labelKit2 = new JLabel("No Kits to Ruin yet!!!");

		item1 = new JRadioButton("N/A");
		item2 = new JRadioButton("N/A");
		item3 = new JRadioButton("N/A");
		item4 = new JRadioButton("N/A");
		item5 = new JRadioButton("N/A");
		item6 = new JRadioButton("N/A");
		item7 = new JRadioButton("N/A");
		item8 = new JRadioButton("N/A");

		i1 = new JLabel("Part 1");
		i2 = new JLabel("Part 2");
		i3 = new JLabel("Part 3");
		i4 = new JLabel("Part 4");
		i5 = new JLabel("Part 5");
		i6 = new JLabel("Part 6");
		i7 = new JLabel("Part 7");
		i8 = new JLabel("Part 8");

		updateField = new JTextArea("System Messages\n",10,10);
		go = new JButton("GO!");

		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.VERTICAL;
		c.insets = new Insets(5,10,20,10);
		this.add(label, c);

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5,10,5,10);
		this.add(labelKit1, c);

		c.gridx = 0;
		c.gridy = 2;
		this.add(i1, c);

		c.gridx = 0;
		c.gridy = 3;
		this.add(i2, c);

		c.gridx = 0;
		c.gridy = 4;
		this.add(i3, c);

		c.gridx = 0;
		c.gridy = 5;
		this.add(i4, c);

		c.gridx = 0;
		c.gridy = 6;
		this.add(i5, c);

		c.gridx = 0;
		c.gridy = 7;
		this.add(i6, c);

		c.gridx = 0;
		c.gridy = 8;
		this.add(i7, c);

		c.gridx = 0;
		c.gridy = 9;
		this.add(i8, c);

		c.gridx = 0;
		c.gridy = 10;
		add(new JScrollPane(updateField),c);

		c.gridx = 1;
		c.gridy = 1;
		this.add(labelKit2, c);

		c.gridx = 1;
		c.gridy = 2;
		this.add(item1, c);

		c.gridx = 1;
		c.gridy = 3;
		this.add(item2, c);

		c.gridx = 1;
		c.gridy = 4;
		this.add(item3, c);

		c.gridx = 1;
		c.gridy = 5;
		this.add(item4, c);

		c.gridx = 1;
		c.gridy = 6;
		this.add(item5, c);

		c.gridx = 1;
		c.gridy = 7;
		this.add(item6, c);

		c.gridx = 1;
		c.gridy = 8;
		this.add(item7, c);

		c.gridx = 1;
		c.gridy = 9;
		this.add(item8, c);

		c.gridx = 1;
		c.gridy = 10;
		this.add(go, c);
	}

	public void actionPerformed(ActionEvent ae) {
		String cmd = new String (" ");
		if(ae.getSource() == go){
			if (kitsProduced){
				cmd = "kam fcsa cmd kitdropparts " + 
						//get current kit in production
						//if item selected pass none else pass normal part
						"#kitname #numpart1 #numpart2 ... #numpart";
				kitAssemblyManager.sendCommand(cmd);
			}
			else{
				label.setText("No Kits in production, nothing will happen");
			}

		}

	}

}

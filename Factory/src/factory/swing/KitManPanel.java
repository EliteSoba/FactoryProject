//Stephanie Reagle
//CS 200
package factory.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import factory.managers.KitManager;
import factory.managers.LaneManager;

public class KitManPanel extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5760501420685052853L;
	//for Parts Assignment Tab
	JLabel pLabel1 = new JLabel ("Item 1");
	JLabel pLabel2 = new JLabel ("Item 2");
	JLabel pLabel3 = new JLabel ("Item 3");
	JLabel pLabel4 = new JLabel ("Item 4");
	JLabel pLabel5 = new JLabel ("Item 5");
	JLabel pLabel6 = new JLabel ("Item 6");
	JLabel pLabel7 = new JLabel ("Item 7");
	JLabel pLabel8 = new JLabel ("Item 8");

	String[] items = { "None", "Head", "Hand", "Nose", "Eyes", "Feet", "Ears" };

	JComboBox pItemComboBox1 = new JComboBox(items);
	JComboBox pItemComboBox2 = new JComboBox(items);
	JComboBox pItemComboBox3 = new JComboBox(items);
	JComboBox pItemComboBox4 = new JComboBox(items);
	JComboBox pItemComboBox5 = new JComboBox(items);
	JComboBox pItemComboBox6 = new JComboBox(items);
	JComboBox pItemComboBox7 = new JComboBox(items);
	JComboBox pItemComboBox8 = new JComboBox(items);

	JTextField pKitName = new JTextField( "Kit Name");
	JLabel pMessages = new JLabel ("Messages:");
	JButton pSave = new JButton("Save Kit Configuration");

	//for Modify Kit Tab

	String[] kits = { "Default Kit", "Mr. Potato Head", "Mrs. Potato Head"};

	JComboBox mKitComboBox = new JComboBox(kits);

	JLabel mLabel1 = new JLabel ("Item 1");
	JLabel mLabel2 = new JLabel ("Item 2");
	JLabel mLabel3 = new JLabel ("Item 3");
	JLabel mLabel4 = new JLabel ("Item 4");
	JLabel mLabel5 = new JLabel ("Item 5");
	JLabel mLabel6 = new JLabel ("Item 6");
	JLabel mLabel7 = new JLabel ("Item 7");
	JLabel mLabel8 = new JLabel ("Item 8");

	JComboBox mItemComboBox1 = new JComboBox(items);
	JComboBox mItemComboBox2 = new JComboBox(items);
	JComboBox mItemComboBox3 = new JComboBox(items);
	JComboBox mItemComboBox4 = new JComboBox(items);
	JComboBox mItemComboBox5 = new JComboBox(items);
	JComboBox mItemComboBox6 = new JComboBox(items);
	JComboBox mItemComboBox7 = new JComboBox(items);
	JComboBox mItemComboBox8 = new JComboBox(items);

	JTextField mKitName = new JTextField( "Default Kit");
	JLabel mMessages = new JLabel ("Messages:");
	JButton mSave = new JButton("Save Kit Configuration");
	JButton mRemove = new JButton("Remove Kit");
	
	KitManager kitManager;

	public KitManPanel(KitManager k){
		kitManager = k;
		
		JTabbedPane tabbedPane = new JTabbedPane();
	    GridBagConstraints c = new GridBagConstraints();

		JPanel createKit = new JPanel();
		createKit.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		createKit.add(pLabel1, c);

		c.gridy = 1;
		createKit.add(pLabel2, c);

		c.gridy = 2;
		createKit.add(pLabel3, c);

		c.gridy = 3;
		createKit.add(pLabel4, c);

		c.gridy = 4;
		createKit.add(pLabel5, c);

		c.gridy = 5;
		createKit.add(pLabel6, c);

		c.gridy = 6;
		createKit.add(pLabel7, c);

		c.gridy = 7;
		createKit.add(pLabel8, c);

		c.gridy = 9;
		createKit.add(pKitName, c);

		c.gridy = 10;
		createKit.add(pSave, c);

		c.gridx = 1;
		c.gridy = 0;
		createKit.add(pItemComboBox1, c);

		c.gridy = 1;
		createKit.add(pItemComboBox2, c);

		c.gridy = 2;
		createKit.add(pItemComboBox3, c);

		c.gridy = 3;
		createKit.add(pItemComboBox4, c);

		c.gridy = 4;
		createKit.add(pItemComboBox5, c);

		c.gridy = 5;
		createKit.add(pItemComboBox6, c);

		c.gridy = 6;
		createKit.add(pItemComboBox7, c);

		c.gridy = 7;
		createKit.add(pItemComboBox8, c);
		
		c.gridy = 8;
		createKit.add(pMessages, c);
		
		JPanel modifyKit = new JPanel();
		modifyKit.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		modifyKit.add(mKitComboBox, c);

		c.gridy = 1;
		modifyKit.add(mLabel1, c);

		c.gridy = 2;
		modifyKit.add(mLabel2, c);

		c.gridy = 3;
		modifyKit.add(mLabel3, c);

		c.gridy = 4;
		modifyKit.add(mLabel4, c);

		c.gridy = 5;
		modifyKit.add(mLabel5, c);

		c.gridy = 6;
		modifyKit.add(mLabel6, c);

		c.gridy = 7;
		modifyKit.add(mLabel7, c);

		c.gridy = 8;
		modifyKit.add(mLabel8, c);

		c.gridx = 1;
		c.gridy = 1;
		modifyKit.add(mItemComboBox1, c);

		c.gridy = 2;
		modifyKit.add(mItemComboBox2, c);

		c.gridy = 3;
		modifyKit.add(mItemComboBox3, c);

		c.gridy = 4;
		modifyKit.add(mItemComboBox4, c);

		c.gridy = 5;
		modifyKit.add(mItemComboBox5, c);

		c.gridy = 6;
		modifyKit.add(mItemComboBox6, c);

		c.gridy = 7;
		modifyKit.add(mItemComboBox7, c);

		c.gridy = 8;
		modifyKit.add(mItemComboBox8, c);

		c.gridy = 9;
		modifyKit.add(mKitName, c);

		c.gridy = 10;
		modifyKit.add(mSave, c);

		c.gridx = 1;
		c.gridy = 10;
		modifyKit.add(mRemove, c);

		tabbedPane.addTab("Create Kit", createKit);
		tabbedPane.addTab("Modify Kit", modifyKit);
		
		add(tabbedPane);	
	}

	  public void actionPerformed(ActionEvent ae) {
		  String set = new String();
		if (ae.getSource() == pSave) {
			kitManager.sendCommand(set);
		}
		else if (ae.getSource() == mSave) {
		}
		  
		else if (ae.getSource() == mRemove){

		}
		
	}
		
}

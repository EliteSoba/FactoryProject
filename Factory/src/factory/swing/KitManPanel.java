//Stephanie Reagle, Marc Mendiola
//CS 200
package factory.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

import factory.managers.KitManager;
import factory.managers.LaneManager;

public class KitManPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = -5760501420685052853L;


	//for Create Kit Tab
	JLabel cLabel1 = new JLabel ("Item 1");
	JLabel cLabel2 = new JLabel ("Item 2");
	JLabel cLabel3 = new JLabel ("Item 3");
	JLabel cLabel4 = new JLabel ("Item 4");
	JLabel cLabel5 = new JLabel ("Item 5");
	JLabel cLabel6 = new JLabel ("Item 6");
	JLabel cLabel7 = new JLabel ("Item 7");
	JLabel cLabel8 = new JLabel ("Item 8");

	JComboBox cItemComboBox1 = new JComboBox();
	JComboBox cItemComboBox2 = new JComboBox();
	JComboBox cItemComboBox3 = new JComboBox();
	JComboBox cItemComboBox4 = new JComboBox();
	JComboBox cItemComboBox5 = new JComboBox();
	JComboBox cItemComboBox6 = new JComboBox();
	JComboBox cItemComboBox7 = new JComboBox();
	JComboBox cItemComboBox8 = new JComboBox();
	
	JLabel cItemFrame1;
	JLabel cItemFrame2;
	JLabel cItemFrame3;
	JLabel cItemFrame4;
	JLabel cItemFrame5;
	JLabel cItemFrame6;
	JLabel cItemFrame7;
	JLabel cItemFrame8;
	ImageIcon cItem1;
	ImageIcon cItem2;
	ImageIcon cItem3;
	ImageIcon cItem4;
	ImageIcon cItem5;
	ImageIcon cItem6;
	ImageIcon cItem7;
	ImageIcon cItem8;
	
	JLabel mItemFrame1;
	JLabel mItemFrame2;
	JLabel mItemFrame3;
	JLabel mItemFrame4;
	JLabel mItemFrame5;
	JLabel mItemFrame6;
	JLabel mItemFrame7;
	JLabel mItemFrame8;
	ImageIcon mItem1;
	ImageIcon mItem2;
	ImageIcon mItem3;
	ImageIcon mItem4;
	ImageIcon mItem5;
	ImageIcon mItem6;
	ImageIcon mItem7;
	ImageIcon mItem8;

	JTextField cKitName = new JTextField( "Kit Name");
	JLabel cMessages = new JLabel ("Messages:");
	JButton cSave = new JButton("Save Kit Configuration");

	//for Modify Kit Tab



	JComboBox mKitComboBox = new JComboBox();

	JLabel mLabel1 = new JLabel ("Item 1");
	JLabel mLabel2 = new JLabel ("Item 2");
	JLabel mLabel3 = new JLabel ("Item 3");
	JLabel mLabel4 = new JLabel ("Item 4");
	JLabel mLabel5 = new JLabel ("Item 5");
	JLabel mLabel6 = new JLabel ("Item 6");
	JLabel mLabel7 = new JLabel ("Item 7");
	JLabel mLabel8 = new JLabel ("Item 8");

	JComboBox mItemComboBox1 = new JComboBox();
	JComboBox mItemComboBox2 = new JComboBox();
	JComboBox mItemComboBox3 = new JComboBox();
	JComboBox mItemComboBox4 = new JComboBox();
	JComboBox mItemComboBox5 = new JComboBox();
	JComboBox mItemComboBox6 = new JComboBox();
	JComboBox mItemComboBox7 = new JComboBox();
	JComboBox mItemComboBox8 = new JComboBox();
	

	JTextField mKitName = new JTextField( "Default Kit");
	JLabel mMessages = new JLabel ("Messages:");
	JButton mSave = new JButton("Save Kit Configuration");
	JButton mRemove = new JButton("Remove Kit");

	KitManager kitManager;

	public KitManPanel(KitManager k){
		kitManager = k;

		for(String key : kitManager.getPartsList().keySet()){
			mItemComboBox1.addItem(key);
			mItemComboBox2.addItem(key);
			mItemComboBox3.addItem(key);
			mItemComboBox4.addItem(key);
			mItemComboBox5.addItem(key);
			mItemComboBox6.addItem(key);
			mItemComboBox7.addItem(key);
			mItemComboBox8.addItem(key);
			cItemComboBox1.addItem(key);
			cItemComboBox2.addItem(key);
			cItemComboBox3.addItem(key);
			cItemComboBox4.addItem(key);
			cItemComboBox5.addItem(key);
			cItemComboBox6.addItem(key);
			cItemComboBox7.addItem(key);
			cItemComboBox8.addItem(key);
		}

		for(String key : kitManager.getKitConfigList().keySet()){
			mKitComboBox.addItem(key);
		}
		//Create Panel
		cItemComboBox1.addActionListener(this);
		cItemComboBox2.addActionListener(this);
		cItemComboBox3.addActionListener(this);
		cItemComboBox4.addActionListener(this);
		cItemComboBox5.addActionListener(this);
		cItemComboBox6.addActionListener(this);
		cItemComboBox7.addActionListener(this);
		cItemComboBox8.addActionListener(this);
		
		cItem1 = new ImageIcon(kitManager.getPartsList().get(cItemComboBox1.getItemAt(0)).imagePath);
		cItem2 = new ImageIcon(kitManager.getPartsList().get(cItemComboBox2.getItemAt(0)).imagePath);
		cItem3 = new ImageIcon(kitManager.getPartsList().get(cItemComboBox3.getItemAt(0)).imagePath);
		cItem4 = new ImageIcon(kitManager.getPartsList().get(cItemComboBox4.getItemAt(0)).imagePath);
		cItem5 = new ImageIcon(kitManager.getPartsList().get(cItemComboBox5.getItemAt(0)).imagePath);
		cItem6 = new ImageIcon(kitManager.getPartsList().get(cItemComboBox6.getItemAt(0)).imagePath);
		cItem7 = new ImageIcon(kitManager.getPartsList().get(cItemComboBox7.getItemAt(0)).imagePath);
		cItem8 = new ImageIcon(kitManager.getPartsList().get(cItemComboBox8.getItemAt(0)).imagePath);

		cItemFrame1 = new JLabel();
		cItemFrame1.setIcon(cItem1);
		cItemFrame2 = new JLabel();
		cItemFrame2.setIcon(cItem2);
		cItemFrame3 = new JLabel();
		cItemFrame3.setIcon(cItem3);
		cItemFrame4 = new JLabel();
		cItemFrame4.setIcon(cItem4);
		cItemFrame5 = new JLabel();
		cItemFrame5.setIcon(cItem5);
		cItemFrame6 = new JLabel();
		cItemFrame6.setIcon(cItem6);
		cItemFrame7 = new JLabel();
		cItemFrame7.setIcon(cItem7);
		cItemFrame8 = new JLabel();
		cItemFrame8.setIcon(cItem8);
		
		
		mItemComboBox1.addActionListener(this);
		mItemComboBox2.addActionListener(this);
		mItemComboBox3.addActionListener(this);
		mItemComboBox4.addActionListener(this);
		mItemComboBox5.addActionListener(this);
		mItemComboBox6.addActionListener(this);
		mItemComboBox7.addActionListener(this);
		mItemComboBox8.addActionListener(this);
		
		mItem1 = new ImageIcon(kitManager.getPartsList().get(mItemComboBox1.getItemAt(0)).imagePath);
		mItem2 = new ImageIcon(kitManager.getPartsList().get(mItemComboBox2.getItemAt(0)).imagePath);
		mItem3 = new ImageIcon(kitManager.getPartsList().get(mItemComboBox3.getItemAt(0)).imagePath);
		mItem4 = new ImageIcon(kitManager.getPartsList().get(mItemComboBox4.getItemAt(0)).imagePath);
		mItem5 = new ImageIcon(kitManager.getPartsList().get(mItemComboBox5.getItemAt(0)).imagePath);
		mItem6 = new ImageIcon(kitManager.getPartsList().get(mItemComboBox6.getItemAt(0)).imagePath);
		mItem7 = new ImageIcon(kitManager.getPartsList().get(mItemComboBox7.getItemAt(0)).imagePath);
		mItem8 = new ImageIcon(kitManager.getPartsList().get(mItemComboBox8.getItemAt(0)).imagePath);

		mItemFrame1 = new JLabel();
		mItemFrame1.setIcon(mItem1);
		mItemFrame2 = new JLabel();
		mItemFrame2.setIcon(mItem2);
		mItemFrame3 = new JLabel();
		mItemFrame3.setIcon(mItem3);
		mItemFrame4 = new JLabel();
		mItemFrame4.setIcon(mItem4);
		mItemFrame5 = new JLabel();
		mItemFrame5.setIcon(mItem5);
		mItemFrame6 = new JLabel();
		mItemFrame6.setIcon(mItem6);
		mItemFrame7 = new JLabel();
		mItemFrame7.setIcon(mItem7);
		mItemFrame8 = new JLabel();
		mItemFrame8.setIcon(mItem8);
		JTabbedPane tabbedPane = new JTabbedPane();
		GridBagConstraints c = new GridBagConstraints();

		JPanel createKit = new JPanel();
		createKit.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		createKit.add(cLabel1, c);

		c.gridy = 1;
		createKit.add(cLabel2, c);

		c.gridy = 2;
		createKit.add(cLabel3, c);

		c.gridy = 3;
		createKit.add(cLabel4, c);

		c.gridy = 4;
		createKit.add(cLabel5, c);

		c.gridy = 5;
		createKit.add(cLabel6, c);

		c.gridy = 6;
		createKit.add(cLabel7, c);

		c.gridy = 7;
		createKit.add(cLabel8, c);

		c.gridy = 9;
		createKit.add(cKitName, c);

		c.gridy = 10;
		createKit.add(cSave, c);

		c.gridx = 1;
		c.gridy = 0;
		createKit.add(cItemComboBox1, c);
		
		c.gridy = 1;
		createKit.add(cItemComboBox2, c);

		c.gridy = 2;
		createKit.add(cItemComboBox3, c);

		c.gridy = 3;
		createKit.add(cItemComboBox4, c);

		c.gridy = 4;
		createKit.add(cItemComboBox5, c);

		c.gridy = 5;
		createKit.add(cItemComboBox6, c);

		c.gridy = 6;
		createKit.add(cItemComboBox7, c);

		c.gridy = 7;
		createKit.add(cItemComboBox8, c);
		
		c.gridy = 8;
		createKit.add(cMessages, c);
		
		c.gridx = 2;
		c.gridy = 0;
		createKit.add(cItemFrame1, c);
		
		c.gridy = 1;
		createKit.add(cItemFrame2, c);

		c.gridy = 2;
		createKit.add(cItemFrame3, c);

		c.gridy = 3;
		createKit.add(cItemFrame4, c);

		c.gridy = 4;
		createKit.add(cItemFrame5, c);

		c.gridy = 5;
		createKit.add(cItemFrame6, c);

		c.gridy = 6;
		createKit.add(cItemFrame7, c);

		c.gridy = 7;
		createKit.add(cItemFrame8, c);


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
		
		c.gridx = 2;
		c.gridy = 1;
		modifyKit.add(mItemFrame1, c);
		
		c.gridy = 2;
		modifyKit.add(mItemFrame2, c);

		c.gridy = 3;
		modifyKit.add(mItemFrame3, c);

		c.gridy = 4;
		modifyKit.add(mItemFrame4, c);

		c.gridy = 5;
		modifyKit.add(mItemFrame5, c);

		c.gridy = 6;
		modifyKit.add(mItemFrame6, c);

		c.gridy = 7;
		modifyKit.add(mItemFrame7, c);

		c.gridy = 8;
		modifyKit.add(mItemFrame8, c);

		tabbedPane.addTab("Create Kit", createKit);
		tabbedPane.addTab("Modify Kit", modifyKit);

		add(tabbedPane);	
	}

	public void actionPerformed(ActionEvent ae) {
		String set = new String();
		if (ae.getSource() == cSave) {
			
		}
		else if (ae.getSource() == mSave) {
		}

		else if (ae.getSource() == mRemove){

		}else{
			JComboBox cb = (JComboBox)ae.getSource();
			String selectedItem = (String)cb.getSelectedItem();
			if(cb == cItemComboBox1)
				updatePicture(cItem1, cItemFrame1, selectedItem);
			else if(cb == cItemComboBox2)
				updatePicture(cItem2, cItemFrame2, selectedItem);
			else if(cb == cItemComboBox3)
				updatePicture(cItem3, cItemFrame3, selectedItem);
			else if(cb == cItemComboBox4)
				updatePicture(cItem4, cItemFrame4, selectedItem);
			else if(cb == cItemComboBox5)
				updatePicture(cItem5, cItemFrame5, selectedItem);
			else if(cb == cItemComboBox6)
				updatePicture(cItem6, cItemFrame6, selectedItem);
			else if(cb == cItemComboBox7)
				updatePicture(cItem7, cItemFrame7, selectedItem);
			else if(cb == cItemComboBox8)
				updatePicture(cItem8, cItemFrame8, selectedItem);
			else if(cb == mItemComboBox1)
				updatePicture(mItem1, mItemFrame1, selectedItem);
			else if(cb == mItemComboBox2)
				updatePicture(mItem2, mItemFrame2, selectedItem);
			else if(cb == mItemComboBox3)
				updatePicture(mItem3, mItemFrame3, selectedItem);
			else if(cb == mItemComboBox4)
				updatePicture(mItem4, mItemFrame4, selectedItem);
			else if(cb == mItemComboBox5)
				updatePicture(mItem5, mItemFrame5, selectedItem);
			else if(cb == mItemComboBox6)
				updatePicture(mItem6, mItemFrame6, selectedItem);
			else if(cb == mItemComboBox7)
				updatePicture(mItem7, mItemFrame7, selectedItem);
			else if(cb == mItemComboBox8)
				updatePicture(mItem8, mItemFrame8, selectedItem);
			
		}

	}

	public void updatePicture(ImageIcon icon, JLabel frame, String path){
		icon = new ImageIcon("Images/" + path + ".png");
		System.out.println(path);
		frame.setIcon(icon);
	}

}


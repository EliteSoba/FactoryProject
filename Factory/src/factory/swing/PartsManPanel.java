package factory.swing;


import factory.managers.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;


/*
* This class is the GUI for the Parts Manaager. This will be
* instantiated in the general PartsManager class (which extends JFrame).
* Written by : Marc Mendiola
* Last edited : 11/12/12 11:59 PM
*/



public class PartsManPanel extends JPanel{

	// Data
	JTabbedPane tabbedPane;
	AddPanel addPanel;
	RemovePanel removePanel;
	ArrayList<String> fileNames;
	ArrayList<String> parts;
	PartsManager partsManager;
	ArrayList<CurrentItem> partsList;
	JScrollPane currentList;
	//New Part

	// Methods

	public PartsManPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		fileNames = new ArrayList<String>();
		parts = new ArrayList<String>();
		fileNames.add("ear");
		fileNames.add("helmet");
		fileNames.add("circleItem");
		addPanel = new AddPanel();
		removePanel = new RemovePanel();
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("addPanel", addPanel);
		tabbedPane.addTab("removePanel", removePanel);
		partsList = new ArrayList<CurrentItem>();
		currentList = new JScrollPane();
		this.add(tabbedPane);
		this.add(currentList);
		
	}
	
	public void setManager(PartsManager manager){
		partsManager = manager;
	}



	// Internal Classes

	private class AddPanel extends JPanel implements ActionListener{

		// Data
		PartsManager partsManager;
		JLabel title;
		JLabel label1;
		JLabel label2;
		JLabel previewFrame;
		JTextField itemName;
		JComboBox imageSelection;
		JButton saveItem;
		

		//Methods
		
		public AddPanel(){
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
				
			
			title = new JLabel ("Parts Manager");
			title.setFont(new Font("Serif", Font.BOLD, 16));
			label1 = new JLabel ("New Item : ");
			itemName = new JTextField(8);
			label2 = new JLabel ("Image : ");
			imageSelection = new JComboBox();
			for(int i = 0; i < fileNames.size(); i++){
				imageSelection.addItem(fileNames.get(i));
			}
			
			imageSelection.addActionListener(this);
			previewFrame = new JLabel();
			ImageIcon imagePreview = new ImageIcon("Images/ear.png");
			previewFrame.setIcon(imagePreview);
			
			saveItem = new JButton ("Save Item");
			saveItem.addActionListener(this);
			
			partsList = new ArrayList<CurrentItem>();
			currentList = new JScrollPane();
			
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			c.fill = GridBagConstraints.VERTICAL;
			c.insets = new Insets(0,0,20,0);
			this.add(title, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 1;
			this.add(label1, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 1;
			this.add(itemName, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 2;
			this.add(label2, c);
			c.gridx = 1;
			c.gridy = 2;
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(imageSelection, c);
			c.gridx = 2;
			c.gridy = 2;
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(previewFrame, c);
			c.gridx = 0;
			c.gridy = 3;
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(saveItem, c);

		}
		
		public void updatePicture(String item){
			ImageIcon imagePreview = new ImageIcon("Images/" + item + ".png");
			previewFrame.setIcon(imagePreview);
		}
		@Override
		public void actionPerformed(ActionEvent ae) {
			 if (ae.getSource() == saveItem){
				 boolean nameTaken = false;
				 /*for(int i = 0; i < partsManager.getParts().size(); i++){
					 if(itemName.getText().equals(partsManager.getParts().get(i)))
						 nameTaken = true;
				 }*/
				 if(nameTaken){
					 itemName.setText("Name taken.");
				 }else{
					 System.out.println("I will create a new part and send it to the server.");
					 //partsManager.sendMessage("add", itemName.getText(), (String)imageSelection.getSelectedItem());
					 partsList.add(new CurrentItem(new JLabel(itemName.getText()), new JLabel(), new ImageIcon((String)imageSelection.getSelectedItem())));
					 //System.out.println((partsList.get(partsList.size()-1).name.getText()));
					 //partsList.get(partsList.size()-1).addCurrentItem(currentList.getViewport());
					 partsList.get(partsList.size() - 1).addCurrentItem(currentList.getViewport());
				 }
		     }else{
			 JComboBox cb = (JComboBox)ae.getSource();
		     String petName = (String)cb.getSelectedItem();
		     updatePicture(petName);
		     }

		}
		
		
	}

	private class RemovePanel extends JPanel implements ActionListener{
		// Data

				JLabel title;
				JLabel label1;
				JLabel previewFrame;
				JComboBox imageSelection;
				JButton removeItem;

				//Methods

				public RemovePanel(){
					this.setLayout(new GridBagLayout());
					GridBagConstraints c = new GridBagConstraints();
						
					
					title = new JLabel ("Parts Manager");
					title.setFont(new Font("Serif", Font.BOLD, 16));
					label1 = new JLabel ("Item : ");
					imageSelection = new JComboBox();
					for(int i = 0; i < fileNames.size(); i++){
						imageSelection.addItem(fileNames.get(i));
					}
					imageSelection.addActionListener(this);
					previewFrame = new JLabel();
					ImageIcon imagePreview = new ImageIcon("Images/ear.png");
					previewFrame.setIcon(imagePreview);
					
					removeItem = new JButton ("Remove Item");
					removeItem.addActionListener(this);
			
					c.gridx = 0;
					c.gridy = 0;
					c.gridwidth = 2;
					c.fill = GridBagConstraints.VERTICAL;
					c.insets = new Insets(0,0,20,0);
					this.add(title, c);
					c.fill = GridBagConstraints.VERTICAL;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridwidth = 1;
					c.gridx = 0;
					c.gridy = 1;
					this.add(label1, c);
					c.fill = GridBagConstraints.VERTICAL;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridx = 1;
					c.gridy = 1;
					this.add(imageSelection, c);
					c.fill = GridBagConstraints.VERTICAL;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridx = 2;
					c.gridy = 1;
					this.add(previewFrame, c);
					c.gridx = 0;
					c.gridy = 2;
					c.fill = GridBagConstraints.VERTICAL;
					c.fill = GridBagConstraints.HORIZONTAL;
					this.add(removeItem, c);

				}
				
				public void updatePicture(String item){
					ImageIcon imagePreview = new ImageIcon("Images/" + item + ".png");
					previewFrame.setIcon(imagePreview);
				}

				public void actionPerformed(ActionEvent ae) {
					 
				     if (ae.getSource() == removeItem){
				    	 System.out.println("I will remove a part and update the server.");
				    	 partsManager.sendMessage("remove", (String)imageSelection.getSelectedItem(), null);
				    	 
				     }else{
				    	 JComboBox cb = (JComboBox)ae.getSource();
					     String petName = (String)cb.getSelectedItem();
					     updatePicture(petName);
				     }

				}
				
	}
	
	private class CurrentItem{
		JLabel name;
		JLabel previewFrame;
		ImageIcon imagePreview;
		
		public CurrentItem(JLabel n, JLabel p, ImageIcon i){
			name = n;
			previewFrame = p;
			imagePreview = i;
		}
		
		public void addCurrentItem(JViewport panel){
			JPanel panel1 = new JPanel();
			panel1.add(name);
			previewFrame.setIcon(imagePreview);
			panel1.add(previewFrame);
			panel.add(panel1);
		}
		
	}

}

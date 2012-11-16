package factory.swing;


import factory.Part;
import factory.managers.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


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
	EditPanel EditPanel;
	ArrayList<String> fileNames;
	PartsManager partsManager;
	JScrollPane scrollPane;
	JPanel currentListPanel;
	JTable table;
	PartsTableModel model;

	//New Part

	// Methods

	public PartsManPanel(PartsManager p){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		partsManager = p;
		fileNames = new ArrayList<String>();
		fileNames.add("eye");
		fileNames.add("body");
		fileNames.add("hat");
		fileNames.add("arm");
		fileNames.add("shoe");
		fileNames.add("mouth");
		fileNames.add("nose");
		fileNames.add("moustache");
		fileNames.add("ear");

		model = new PartsTableModel();
		model.addColumn("ID");
		model.addColumn("Name");
		table = new JTable(model);

		TableColumn column = null;
		for (int i = 0; i < 1; i ++){
			column = table.getColumnModel().getColumn(i);
			if(i==1){
				column.setPreferredWidth(30);
			}else
				column.setPreferredWidth(200);
		}
		table.setRowHeight(30);

		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		for(int i = 1; i < partsManager.parts.size(); i++){
			for(Part temp : partsManager.parts.values()){
				if(i == temp.id){
					ImageIcon image = new ImageIcon(temp.imagePath);
					Object[] row = {temp.id, image};
					model.insertRow(model.getRowCount(), row);
				}
			}
		}
		addPanel = new AddPanel();
		EditPanel = new EditPanel();
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("addPanel", addPanel);
		tabbedPane.addTab("EditPanel", EditPanel);
		currentListPanel = new JPanel();

		this.add(tabbedPane);
		//this.add(currentList);
		this.add(scrollPane);
	}

	public void setManager(PartsManager manager){
		partsManager = manager;
	}



	// Internal Classes

	private class AddPanel extends JPanel implements ActionListener{

		// Data
		JLabel title;
		JLabel nameLabel;
		JLabel imageLabel;
		JLabel idLabel;
		JLabel descriptionLabel;
		JLabel nestStabalizationTimeLabel;
		JLabel previewFrame;
		JTextField name;
		JComboBox imageSelection;
		JSpinner nestStabalizationTime;
		JTextField description;
		
		JButton saveItem;


		//Methods

		public AddPanel(){
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();


			title = new JLabel ("Parts Manager");
			title.setFont(new Font("Serif", Font.BOLD, 16));
			nameLabel = new JLabel ("New Item : ");
			name = new JTextField(8);
			imageLabel = new JLabel ("Image : ");
			idLabel = new JLabel("ID# : " + partsManager.parts.size());
			descriptionLabel = new JLabel("Description : ");
			nestStabalizationTimeLabel = new JLabel ("Nest Stabalization Time : ");
			nestStabalizationTime = new JSpinner();
			description = new JTextField("Enter description here");
			imageSelection = new JComboBox();
			for(int i = 0; i < fileNames.size(); i++){
				imageSelection.addItem(fileNames.get(i));
			}
			imageSelection.addActionListener(this);
			previewFrame = new JLabel();
			ImageIcon imagePreview = new ImageIcon("Images/" + fileNames.get(0) + ".png");
			previewFrame.setIcon(imagePreview);
			saveItem = new JButton ("Save Item");
			saveItem.addActionListener(this);

			//currentList = new JScrollPane();

			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			c.fill = GridBagConstraints.VERTICAL;
			c.insets = new Insets(0,0,20,0);
			this.add(title, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 1;
			this.add(nameLabel, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 1;
			this.add(name, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 2;
			this.add(idLabel, c);
			c.gridx = 0;
			c.gridy = 3;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(nestStabalizationTimeLabel, c);
			c.gridx = 1;
			c.gridy = 3;
			c.fill = GridBagConstraints.VERTICAL;
			this.add(nestStabalizationTime, c);
			c.gridx = 0;
			c.gridy = 4;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(imageLabel, c);
			c.gridx = 1;
			c.gridy = 4;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(imageSelection, c);
			c.gridx = 2;
			c.gridy = 4;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(previewFrame, c);
			c.gridx = 0;
			c.gridy = 5;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(descriptionLabel, c);
			c.gridx = 1;
			c.gridy = 5;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(description, c);
			c.gridx = 0;
			c.gridy = 6;
			c.fill = GridBagConstraints.VERTICAL;
			c.anchor = GridBagConstraints.LINE_START;
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
					 if(name.getText().equals(partsManager.getParts().get(i)))
						 nameTaken = true;
				 }*/
				if(nameTaken){
					name.setText("Name taken.");
				}else{
					System.out.println("I will create a new part and send it to the server.");

					System.out.println("parts size : " + partsManager.parts.size());
					Part p = new Part(name.getText(), partsManager.parts.size(), description.getText(),"Images/" + (String)imageSelection.getSelectedItem() + ".png", (Integer)nestStabalizationTime.getValue());
					addItem(p);
					partsManager.sendMessage("add",p);
					idLabel = new JLabel("ID# : " + partsManager.parts.size());
				}
			}else{
				JComboBox cb = (JComboBox)ae.getSource();
				String petName = (String)cb.getSelectedItem();
				updatePicture(petName);
			}

		}


	}

	private class EditPanel extends JPanel implements ActionListener{
		// Data

		// Data
				JLabel title;
				JLabel nameLabel;
				JLabel imageLabel;
				JLabel idLabel;
				JLabel descriptionLabel;
				JLabel nestStabalizationTimeLabel;
				JLabel previewFrame;
				JTextField name;
				JComboBox imageSelection;
				JSpinner nestStabalizationTime;
				JTextField description;
				
				JButton saveItem;
				JButton removeItem;
			


				//Methods

				public EditPanel(){
					this.setLayout(new GridBagLayout());
					GridBagConstraints c = new GridBagConstraints();


					title = new JLabel ("Parts Manager");
					title.setFont(new Font("Serif", Font.BOLD, 16));
					nameLabel = new JLabel ("New Item : ");
					name = new JTextField(8);
					imageLabel = new JLabel ("Image : ");
					idLabel = new JLabel("ID# : " + partsManager.parts.size());
					descriptionLabel = new JLabel("Description : ");
					nestStabalizationTimeLabel = new JLabel ("Nest Stabalization Time : ");
					nestStabalizationTime = new JSpinner();
					description = new JTextField("Enter description here");
					imageSelection = new JComboBox();
					for(int i = 0; i < fileNames.size(); i++){
						imageSelection.addItem(fileNames.get(i));
					}
					imageSelection.addActionListener(this);
					previewFrame = new JLabel();
					ImageIcon imagePreview = new ImageIcon("Images/" + fileNames.get(0) + ".png");
					previewFrame.setIcon(imagePreview);
					saveItem = new JButton ("Save Changes");
					saveItem.addActionListener(this);
					removeItem = new JButton ("Remove Item");
					removeItem.addActionListener(this);

					//currentList = new JScrollPane();

					c.gridx = 0;
					c.gridy = 0;
					c.gridwidth = 2;
					c.fill = GridBagConstraints.VERTICAL;
					c.insets = new Insets(0,0,20,0);
					this.add(title, c);
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridwidth = 1;
					c.gridx = 0;
					c.gridy = 1;
					this.add(nameLabel, c);
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridx = 1;
					c.gridy = 1;
					this.add(name, c);
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridx = 0;
					c.gridy = 2;
					this.add(idLabel, c);
					c.gridx = 0;
					c.gridy = 3;
					c.fill = GridBagConstraints.HORIZONTAL;
					this.add(nestStabalizationTimeLabel, c);
					c.gridx = 1;
					c.gridy = 3;
					c.fill = GridBagConstraints.VERTICAL;
					this.add(nestStabalizationTime, c);
					c.gridx = 0;
					c.gridy = 4;
					c.fill = GridBagConstraints.HORIZONTAL;
					this.add(imageLabel, c);
					c.gridx = 1;
					c.gridy = 4;
					c.fill = GridBagConstraints.HORIZONTAL;
					this.add(imageSelection, c);
					c.gridx = 2;
					c.gridy = 4;
					c.fill = GridBagConstraints.HORIZONTAL;
					this.add(previewFrame, c);
					c.gridx = 0;
					c.gridy = 5;
					c.fill = GridBagConstraints.HORIZONTAL;
					this.add(descriptionLabel, c);
					c.gridx = 1;
					c.gridy = 5;
					c.fill = GridBagConstraints.HORIZONTAL;
					this.add(description, c);
					c.gridx = 0;
					c.gridy = 6;
					c.fill = GridBagConstraints.VERTICAL;
					c.anchor = GridBagConstraints.LINE_START;
					this.add(saveItem, c);
					c.gridx = 1;
					c.gridy = 6;
					c.fill = GridBagConstraints.VERTICAL;
					c.anchor = GridBagConstraints.LINE_START;
					this.add(removeItem, c);
					


		}

		public void updatePicture(Part p){
			ImageIcon imagePreview = new ImageIcon(p.imagePath);
			previewFrame.setIcon(imagePreview);
		}

		public void actionPerformed(ActionEvent ae) {

			if (ae.getSource() == removeItem){
				System.out.println("I will remove a part and update the server.");
				partsManager.sendMessage("remove", partsManager.parts.get((String)imageSelection.getSelectedItem()));
				removeItem(partsManager.parts.get((String)imageSelection.getSelectedItem()));
			}else{
				JComboBox cb = (JComboBox)ae.getSource();
				String petName = (String)cb.getSelectedItem();
				updatePicture(partsManager.parts.get(petName));
			}

		}

	}


	public void addItem(Part p){
		partsManager.parts.put(p.name, p);
		ImageIcon image = new ImageIcon(p.imagePath);
		Object[] rowData = {p.id,image};
		model.insertRow(model.getRowCount(),rowData);
		table.revalidate();
	}
	public void removeItem(Part p){
		System.out.println("Removing " + p.name + " " + p.id);
		for(int i = 0; i < model.getRowCount(); i++){
			System.out.println("Row : " + (Integer)model.getValueAt(i, 0));
			if((Integer)p.id == (Integer)model.getValueAt(i, 0)){
				model.removeRow(i);
				break;
			}

		}
		partsManager.parts.remove(p.name);

		table.revalidate();
	}



	public class PartsTableModel extends DefaultTableModel{
		public boolean isCellEditable(int row, int column){
			return false;
		}
		public Class getColumnClass(int column)
		{
			return getValueAt(0, column).getClass();
		}
	}

}

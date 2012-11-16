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
	RemovePanel removePanel;
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
		
		for(Part temp : partsManager.parts.values()){
			ImageIcon image = new ImageIcon(temp.imagePath);
			 Object[] row = {temp.id, image};
			 model.insertRow(model.getRowCount(), row);
		}
		addPanel = new AddPanel();
		removePanel = new RemovePanel();
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("addPanel", addPanel);
		tabbedPane.addTab("removePanel", removePanel);
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
			c.fill = GridBagConstraints.VERTICAL;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 1;
			this.add(label1, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = 1;
			c.gridy = 1;
			this.add(itemName, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = 0;
			c.gridy = 2;
			this.add(label2, c);
			c.gridx = 1;
			c.gridy = 2;
			c.fill = GridBagConstraints.VERTICAL;
			this.add(imageSelection, c);
			c.gridx = 0;
			c.gridy = 3;
			c.fill = GridBagConstraints.VERTICAL;
			this.add(saveItem, c);
			c.gridx = 1;
			c.gridy = 3;
			c.fill = GridBagConstraints.VERTICAL;
			this.add(previewFrame, c);

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

					System.out.println("parts size : " + partsManager.parts.size());
					Part p = new Part(itemName.getText(), 2, "This is a test.","Images/" + (String)imageSelection.getSelectedItem() + ".png", 2);
					addItem(p);
					partsManager.sendMessage("add",p);
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
			/*Iterator it = partsManager.getParts().entrySet().iterator();

					while(it.hasNext()){
						Map.Entry keyPair = (Map.Entry)it.next();
						imageSelection.addItem(keyPair.getValue().imagePath);
						it.remove();
					}*/
			for ( Part p : partsManager.getParts().values()){
				imageSelection.addItem(p.name);
				System.out.println(p.name);
			}
			imageSelection.addActionListener(this);
			previewFrame = new JLabel();
			System.out.println("Hey " + (String)imageSelection.getItemAt(0));
			//ImageIcon imagePreview = new ImageIcon((String)imageSelection.getItemAt(0));
			//previewFrame.setIcon(imagePreview);

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

		public void updatePicture(Part p){
			ImageIcon imagePreview = new ImageIcon(p.imagePath);
			previewFrame.setIcon(imagePreview);
		}

		public void actionPerformed(ActionEvent ae) {

			if (ae.getSource() == removeItem){
				System.out.println("I will remove a part and update the server.");
				partsManager.sendMessage("remove", partsManager.parts.get((String)imageSelection.getSelectedItem()));
				removeItem(partsManager.parts.get((String)imageSelection.getSelectedItem()));
				partsManager.parts.remove((String)imageSelection.getSelectedItem());
				
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
		
		for(String name : partsManager.parts.keySet()){
			System.out.println("Part" + p.name);
		}
		for(int i = 0; i < model.getRowCount(); i++){
			if((Integer)p.id == (Integer)model.getValueAt(i, 0));
			model.removeRow(i);
			break;
			
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

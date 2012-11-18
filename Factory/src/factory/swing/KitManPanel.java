
package factory.swing;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;

import factory.KitConfig;
import factory.Part;
import factory.managers.*;

/*
 * This class is the GUI for the Parts Manaager. This will be
 * instantiated in the general PartsManager class (which extends JFrame).
 * Written by : Stephanie Reagle, Marc Mendiola
 */

public class KitManPanel extends JPanel /*implements ActionListener*/{

	private static final long serialVersionUID = -5760501420685052853L;

	AddPanel addPanel;
	//EditPanel editPanel;
	ArrayList<String> fileNames;
	KitManager kitManager;
	JScrollPane scrollPane;
	JPanel currentListPanel;
	JTable table;
	PartsTableModel model;
	PartsTableCellRenderer renderer;
	JPanel basePanel1;
	JPanel basePanel2;

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


	public KitManPanel(KitManager k){
		kitManager = k;
		this.setLayout(new CardLayout());
		kitManager = k;
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
		fileNames.add("cane");
		fileNames.add("sword");
		fileNames.add("tentacle");

		model = new PartsTableModel();
		model.addColumn("Name");
		renderer = new PartsTableCellRenderer();
		table = new JTable(model);
		table.setDefaultRenderer(Integer.class, renderer);
		table.setDefaultRenderer(Double.class, renderer);
		table.setDefaultRenderer(String.class, renderer);
		table.setAutoCreateRowSorter(true);
		//table.addMouseListener(new MouseListener());
		TableColumn column = null;
		for (int i = 0; i < 1; i ++){
			column = table.getColumnModel().getColumn(i);
			if(i==0)
				column.setPreferredWidth(2);
		}
		table.setRowHeight(30);

		scrollPane = new JScrollPane(table);
		scrollPane.setMaximumSize(new Dimension(460, 300));
		table.setFillsViewportHeight(true);


		for(int i = 1; i < kitManager.getKitConfigList().size(); i++){
			for(KitConfig temp : kitManager.getKitConfigList().values()){
				Object[] row = {temp.kitName};
				model.insertRow(model.getRowCount(), row);
			}
		}
		addPanel = new AddPanel();
		//editPanel = new EditPanel();

		basePanel1 = new JPanel();
		basePanel1.setLayout(new BoxLayout(basePanel1, BoxLayout.Y_AXIS));
		basePanel2 = new JPanel();
		basePanel1.add(addPanel);
		basePanel1.add(scrollPane);
		//basePanel2.add(editPanel);
		this.add("basePanel1", basePanel1);
		this.add("basePanel2", basePanel2);
		/*

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
		 */
	}
	/*
	public void actionPerformed(ActionEvent ae) {
		String set = new String();
		if (ae.getSource() == cSave) {
			//for (int i = 0; i < kits.length; i++ ){
				//if (kits[i] == cKitName.getText()){
				//	cMessages.setText("Kit Name already exists, try another name");
			//	}
			//}
			/*else if (false){

			}
			else {
				set = "km multi cmd addkitname " +
					(String)cItemComboBox1.getSelectedItem() + " " + (String)cItemComboBox2.getSelectedItem() + " " + 
					(String)cItemComboBox3.getSelectedItem() + " " + (String)cItemComboBox4.getSelectedItem() + " " + 
					(String)cItemComboBox4.getSelectedItem() + " " + (String)cItemComboBox5.getSelectedItem() + " " + 
					(String)cItemComboBox6.getSelectedItem() + " " + (String)cItemComboBox7.getSelectedItem() + " " + 
					(String)cItemComboBox8.getSelectedItem(); //#kitname #partname1 #partname2 ... #partname8";
				kitManager.sendCommand(set);
			}*/
	/*}
		else if (ae.getSource() == mSave) {
		}

		else if (ae.getSource() == mRemove){

		}

	}*/
	/*
	private class AddPanel{

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

		JLabel cMessages = new JLabel ("Messages:");
		JButton cSave = new JButton("Save Kit Configuration");
		public AddPanel(){
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

		}
	}*/



	private class AddPanel extends JPanel implements ActionListener{

		// Data
		JLabel title;
		JLabel cKitNameLabel;
		JTextField cKitName;
		JLabel cItemLabel;
		JLabel previewFrame;
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
		JComboBox cItemComboBox;
		JButton saveItem;
		Part currentPart;
		Border border;


		//Methods

		public AddPanel(){
			this.setLayout(new GridBagLayout());
			currentPart = new Part();
			GridBagConstraints c = new GridBagConstraints();
			title = new JLabel ("Kit Manager");
			title.setFont(new Font("Serif", Font.BOLD, 16));
			cKitNameLabel = new JLabel ("New Kit : ");
			cKitName = new JTextField(8);
			cItemLabel = new JLabel("Item : ");
			cItemComboBox = new JComboBox();
			for(String name : kitManager.getPartsList().keySet()){
				cItemComboBox.addItem(name);
			}
			cItemComboBox.addActionListener(this);
			previewFrame = new JLabel();
			ImageIcon imagePreview = new ImageIcon(kitManager.getPartsList().get(cItemComboBox.getItemAt(0)).imagePath);
			previewFrame.setIcon(imagePreview);
			saveItem = new JButton ("Save Item");
			saveItem.addActionListener(this);
			
			
			cItem1 = new ImageIcon();
			cItem2 = new ImageIcon();
			cItem3 = new ImageIcon();
			cItem4 = new ImageIcon();
			cItem5 = new ImageIcon();
			cItem6 = new ImageIcon();
			cItem7 = new ImageIcon();
			cItem8 = new ImageIcon();
			
			border = BorderFactory.createLineBorder(Color.black);
			cItemFrame1 = new JLabel();
			cItemFrame1.setBorder(border);
			cItemFrame1.setIcon(cItem1);
			cItemFrame2 = new JLabel();
			cItemFrame2.setBorder(border);
			cItemFrame2.setIcon(cItem2);
			cItemFrame3 = new JLabel();
			cItemFrame3.setBorder(border);
			cItemFrame3.setIcon(cItem3);
			cItemFrame4 = new JLabel();
			cItemFrame4.setBorder(border);
			cItemFrame4.setIcon(cItem4);
			cItemFrame5 = new JLabel();
			cItemFrame5.setBorder(border);
			cItemFrame5.setIcon(cItem5);
			cItemFrame6 = new JLabel();
			cItemFrame6.setBorder(border);
			cItemFrame6.setIcon(cItem6);
			cItemFrame7 = new JLabel();
			cItemFrame7.setBorder(border);
			cItemFrame7.setIcon(cItem7);
			cItemFrame8 = new JLabel();
			cItemFrame8.setBorder(border);
			cItemFrame8.setIcon(cItem8);
			

			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			c.fill = GridBagConstraints.VERTICAL;
			c.insets = new Insets(5,0,20,0);
			this.add(title, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.anchor = GridBagConstraints.LINE_START;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 1;
			this.add(cKitNameLabel, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = 1;
			c.gridy = 1;
			this.add(cKitName, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = 0;
			c.gridy = 2;
			this.add(cItemLabel, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = 1;
			c.gridy = 2;
			this.add(cItemComboBox, c);
			c. gridx = 2;
			c.gridy = 2;
			c.insets = new Insets(5,20,20,0);
			this.add(previewFrame, c);
			
		}

		public void updatePicture(String item){
			ImageIcon imagePreview = new ImageIcon("Images/" + item + ".png");
			previewFrame.setIcon(imagePreview);
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == saveItem){
				
			}else{
				JComboBox cb = (JComboBox)ae.getSource();
				String selectedItem = (String)cb.getSelectedItem();
				updatePicture(selectedItem);
			}

		}

	}


	public class PartsTableModel extends DefaultTableModel{
		public boolean isCellEditable(int row, int column){
			return false;
		}
		public Class getColumnClass(int column)
		{
			return getValueAt(0, column).getClass();
		}
		public TableCellRenderer getCellRenderer(int row, int column){
			return renderer;
		}
	}

	public class PartsTableCellRenderer	extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel renderedLabel = (JLabel) super.getTableCellRendererComponent(
					table, value, isSelected, hasFocus, row, column);
			renderedLabel.setHorizontalAlignment(SwingConstants.CENTER);
			return renderedLabel;
		}


	}
	/*
	public class MouseListener extends MouseAdapter{

		public void mouseClicked(MouseEvent ae) {
			if (ae.getClickCount() == 2) {
				JTable target = (JTable)ae.getSource();
				int row = target.getSelectedRow();
				int column = target.getSelectedColumn();
				editPanel.updateEditPanel(target, row);
				basePanel1.setVisible(false);
				basePanel2.setVisible(true);
				addPanel.name.setText("");
				addPanel.description.setText("Enter description here");
				addPanel.imageSelection.setSelectedIndex(0);
				addPanel.nestStabalizationTime.setValue(0);

			}
		}

	}*/

}


// Internal Classes





/*


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
		JTextArea description;
		JScrollPane descriptionScrollPane;

		JButton saveItem;
		JButton removeItem;
		JButton cancel;
		Part currentPart;



		//Methods

		public EditPanel(){
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			currentPart = new Part();

			title = new JLabel ("Parts Manager");
			title.setFont(new Font("Serif", Font.BOLD, 16));
			nameLabel = new JLabel ("New Item : ");
			name = new JTextField(8);
			imageLabel = new JLabel ("Image : ");
			idLabel = new JLabel("ID# : " + currentID);
			descriptionLabel = new JLabel("Description : ");
			nestStabalizationTimeLabel = new JLabel ("Nest Stabalization Time : ");
			nestStabalizationTime = new JSpinner();
			description = new JTextArea("Enter description here", 2, 10);
			description.setLineWrap(true);
			description.setWrapStyleWord(true);
			descriptionScrollPane = new JScrollPane(description);
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
			cancel = new JButton ("Cancel");
			cancel.addActionListener(this);
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			c.fill = GridBagConstraints.VERTICAL;
			c.insets = new Insets(0,0,20,0);
			this.add(title, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.gridx = 0;
			c.gridy = 1;
			this.add(nameLabel, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = 1;
			c.gridy = 1;
			this.add(name, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = 0;
			c.gridy = 2;
			this.add(idLabel, c);
			c.gridx = 0;
			c.gridy = 3;
			c.fill = GridBagConstraints.VERTICAL;
			this.add(nestStabalizationTimeLabel, c);
			c.gridx = 1;
			c.gridy = 3;
			c.fill = GridBagConstraints.VERTICAL;
			this.add(nestStabalizationTime, c);
			c.gridx = 0;
			c.gridy = 4;
			c.fill = GridBagConstraints.VERTICAL;
			this.add(imageLabel, c);
			c.gridx = 1;
			c.gridy = 4;
			c.fill = GridBagConstraints.VERTICAL;
			this.add(imageSelection, c);
			c.gridx = 2;
			c.gridy = 4;
			c.fill = GridBagConstraints.VERTICAL;
			c.insets = new Insets(0,20,20,0);
			this.add(previewFrame, c);
			c.insets = new Insets(0,0,20,0);
			c.gridx = 0;
			c.gridy = 5;
			c.fill = GridBagConstraints.VERTICAL;
			c.anchor = GridBagConstraints.LINE_START;
			this.add(descriptionLabel, c);
			c.gridx = 1;
			c.gridy = 5;
			c.gridheight = 2;
			c.fill = GridBagConstraints.VERTICAL;
			this.add(descriptionScrollPane, c);
			c.gridx = 0;
			c.gridy = 7;
			c.gridheight = 1;
			c.fill = GridBagConstraints.VERTICAL;
			c.anchor = GridBagConstraints.LINE_START;
			this.add(saveItem, c);
			c.gridx = 1;
			c.gridy = 7;
			c.fill = GridBagConstraints.VERTICAL;
			c.anchor = GridBagConstraints.LINE_START;
			this.add(removeItem, c);
			c.gridx = 2;
			c.gridy = 7;
			c.fill = GridBagConstraints.VERTICAL;
			c.anchor = GridBagConstraints.CENTER;
			this.add(cancel, c);



		}

		public void updatePicture(String path){
			ImageIcon imagePreview = new ImageIcon(path);
			previewFrame.setIcon(imagePreview);
		}

		public void actionPerformed(ActionEvent ae) {

			if (ae.getSource() == removeItem){
				System.out.println("I will remove a part and update the server.");
				partsManager.sendMessage("remove", currentPart, null);
				removeItem(currentPart);
				basePanel1.setVisible(true);
				basePanel2.setVisible(false);
			}else if(ae.getSource() == saveItem){

				String testName;
				name.setText(name.getText().replaceAll("\\s","")) ;
				testName = name.getText().toUpperCase();
				boolean nameTaken = false;
				for(String tempName : partsManager.parts.keySet()){
					if(testName.equals(tempName.toUpperCase()) && !testName.equals(currentPart.name.toUpperCase()))
						nameTaken = true;
				}
				if(nameTaken){
					name.setText("Name taken.");
				}else if (name.getText().equals("")){
					name.setText("No input.");
				}else if ((Integer)nestStabalizationTime.getValue() <= 0){
					name.setText("Invalid time.");	
				}else{

					Part p = new Part(name.getText(), currentPart.id, description.getText(),"Images/" + (String)imageSelection.getSelectedItem() + ".png", (Integer)nestStabalizationTime.getValue());
					partsManager.sendMessage("edit", p, currentPart);
					editItem(p);
					partsManager.parts.remove(currentPart.name);
					partsManager.parts.put(p.name, p);
					currentPart = p;
					basePanel1.setVisible(true);
					basePanel2.setVisible(false);
				}
			}else if(ae.getSource() == cancel){
				basePanel1.setVisible(true);
				basePanel2.setVisible(false);

			}else{
				JComboBox cb = (JComboBox)ae.getSource();
				String selectedItem = (String)cb.getSelectedItem();
				updatePicture("Images/" + selectedItem + ".png");
			}

		}

		public void updateEditPanel(JTable tempTable, int tempRow){
			String tempName;
			Part p;
			tempName = (String) tempTable.getValueAt(tempRow, 1);
			p = partsManager.parts.get(tempName);
			currentPart = p;
			name.setText(p.name);
			idLabel.setText("ID# : " + p.id);
			description.setText(p.description);
			nestStabalizationTime.setValue((int)p.nestStabilizationTime);
			for(int i = 0; i < fileNames.size(); i++){
				if(p.imagePath.equals("Images/" + fileNames.get(i) + ".png")){
					System.out.println("Image : " + fileNames.get(i));
					imageSelection.setSelectedIndex(i);
					break;
				}
			}
		}

	}


	public void addItem(Part p){
		partsManager.parts.put(p.name, p);
		ImageIcon image = new ImageIcon(p.imagePath);
		Object[] rowData = {p.id,p.name, p.nestStabilizationTime, image};
		model.insertRow(model.getRowCount(),rowData);
		table.revalidate();
	}

	public void editItem(Part p){
		ImageIcon image = new ImageIcon(p.imagePath);
		Object[] rowData = {p.id,p.name, p.nestStabilizationTime, image};
		for(int i = 0; i < model.getRowCount(); i++){
			if((Integer)p.id == (Integer)model.getValueAt(i, 0)){
				model.removeRow(i);
				model.insertRow(i,rowData);
				break;
			}

		}

		table.revalidate();
	}

	public void removeItem(Part p){
		System.out.println("Removing " + p.name + " " + p.id);
		for(int i = 0; i < model.getRowCount(); i++){
			if((Integer)p.id == (Integer)model.getValueAt(i, 0)){
				model.removeRow(i);
				break;
			}

		}
		partsManager.parts.remove(p.name);
		table.revalidate();
	}


 */



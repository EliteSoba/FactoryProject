//Stephanie Reagle, Marc Mendiola
package factory.swing;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import factory.KitConfig;
import factory.Part;
import factory.managers.*;

/*
 * This class is the GUI for the Kit Manaager. This will be
 * instantiated in the general KitManager class (which extends Client).
 * Written by : Stephanie Reagle, Marc Mendiola
 */

public class KitManPanel extends JPanel /*implements ActionListener*/{

	private static final long serialVersionUID = -5760501420685052853L;

	AddPanel addPanel;
	EditPanel editPanel;
	ArrayList<String> fileNames;
	KitManager kitManager;
	JScrollPane scrollPane;
	JPanel currentListPanel;
	JTable table;
	PartsTableModel model;
	PartsTableCellRenderer renderer;
	JPanel basePanel1;
	JPanel basePanel2;
	MouseListener mouseListener;

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

		mouseListener = new MouseListener();
		model = new PartsTableModel();
		model.addColumn("Name");
		renderer = new PartsTableCellRenderer();
		table = new JTable(model);
		table.setDefaultRenderer(Integer.class, renderer);
		table.setDefaultRenderer(Double.class, renderer);
		table.setDefaultRenderer(String.class, renderer);
		table.setAutoCreateRowSorter(true);
		table.addMouseListener(mouseListener);
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
		editPanel = new EditPanel();

		basePanel1 = new JPanel();
		basePanel1.setLayout(new BoxLayout(basePanel1, BoxLayout.Y_AXIS));
		basePanel2 = new JPanel();
		basePanel1.add(addPanel);
		basePanel1.add(scrollPane);
		basePanel2.add(editPanel);
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
		ImageIcon imagePreview;
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
		JLabel cMessages;
		JButton cSave;
		Part currentPart;
		Border border;
		Border borderPadding;


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
			cItemComboBox.addItem("None");
			for(String name : kitManager.getPartsList().keySet()){
				cItemComboBox.addItem(name);
			}
			cItemComboBox.addActionListener(this);
			previewFrame = new JLabel();
			imagePreview = new ImageIcon("Images/none.png");
			previewFrame.setIcon(imagePreview);
			cMessages = new JLabel ("Messages:");
			cSave = new JButton("Save Kit");
			cSave.addActionListener(this);

			cItem1 = new ImageIcon("Images/none.png");
			cItem2 = new ImageIcon("Images/none.png");
			cItem3 = new ImageIcon("Images/none.png");
			cItem4 = new ImageIcon("Images/none.png");
			cItem5 = new ImageIcon("Images/none.png");
			cItem6 = new ImageIcon("Images/none.png");
			cItem7 = new ImageIcon("Images/none.png");
			cItem8 = new ImageIcon("Images/none.png");

			border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
			borderPadding = BorderFactory.createEmptyBorder(2, 2, 2, 2);
			cItemFrame1 = new JLabel();
			cItemFrame1.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame1.setIcon(cItem1);
			cItemFrame1.addMouseListener(mouseListener);
			cItemFrame2 = new JLabel();
			cItemFrame2.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame2.setIcon(cItem2);
			cItemFrame2.addMouseListener(mouseListener);
			cItemFrame3 = new JLabel();
			cItemFrame3.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame3.setIcon(cItem3);
			cItemFrame3.addMouseListener(mouseListener);
			cItemFrame4 = new JLabel();
			cItemFrame4.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame4.setIcon(cItem4);
			cItemFrame4.addMouseListener(mouseListener);
			cItemFrame5 = new JLabel();
			cItemFrame5.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame5.setIcon(cItem5);
			cItemFrame5.addMouseListener(mouseListener);
			cItemFrame6 = new JLabel();
			cItemFrame6.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame6.setIcon(cItem6);
			cItemFrame6.addMouseListener(mouseListener);
			cItemFrame7 = new JLabel();
			cItemFrame7.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame7.setIcon(cItem7);
			cItemFrame7.addMouseListener(mouseListener);
			cItemFrame8 = new JLabel();
			cItemFrame8.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame8.setIcon(cItem8);
			cItemFrame8.addMouseListener(mouseListener);


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

			JPanel kitBase = new JPanel(new GridBagLayout());

			c. gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(0,0,0,0);
			kitBase.add(cItemFrame1, c);
			c. gridx = 1;
			c.gridy = 0;
			kitBase.add(cItemFrame2, c);
			c. gridx = 2;
			c.gridy = 0;
			kitBase.add(cItemFrame3, c);
			c. gridx = 3;
			c.gridy = 0;
			kitBase.add(cItemFrame4, c);
			c. gridx = 0;
			c.gridy = 1;
			kitBase.add(cItemFrame5, c);
			c. gridx = 1;
			c.gridy = 1;
			kitBase.add(cItemFrame6, c);
			c. gridx = 2;
			c.gridy = 1;
			kitBase.add(cItemFrame7, c);
			c. gridx = 3;
			c.gridy = 1;
			kitBase.add(cItemFrame8, c);

			c.gridx = 1;
			c.gridy = 3;
			c.insets = new Insets(0,0,20,0);
			this.add(kitBase, c);
			
			c.gridx = 0;
			c.gridy = 4;
			this.add(cSave, c);

		}

		public void updatePicture(ImageIcon icon, JLabel frame, String path){

			if(path.length() > 5 && path.substring(0,6).equals("Images")){
				System.out.println("Path" + path.substring(0,5));
				icon = new ImageIcon(path);
			}else{
				icon = new ImageIcon("Images/" + path + ".png");
			}
			frame.setIcon(icon);
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == cSave){
				System.out.println("Save");
				
				String testName;
				cKitName.setText(cKitName.getText().replaceAll("\\s","")) ;
				testName = cKitName.getText().toUpperCase();
				boolean nameTaken = false;
				for(String tempName : kitManager.getKitConfigList().keySet()){
					if(testName.equals(tempName.toUpperCase()))
						nameTaken = true;
				}
				if(nameTaken){
					cKitName.setText("Name taken.");
				}else if (cKitName.getText().equals("")){
					cKitName.setText("No input.");
				}else{
/*
					KitConfig k = new KitConfig(cKitName, );
					addItem(p);
					currentID++;
					messageAddPanel = "pm multi cmd addpartname " + p.name + " " + p.id + " " +
							p.imagePath + " " + p.nestStabilizationTime + " " + p.description;
					partsManager.sendCommand(messageAddPanel);
					idLabel.setText("ID# : " + currentID);
					name.setText("");
					description.setText("Enter description here");
					imageSelection.setSelectedIndex(0);
					nestStabalizationTime.setValue(0);
					*/
				}
			}else{
				JComboBox cb = (JComboBox)ae.getSource();
				String selectedItem = (String)cb.getSelectedItem();
				updatePicture(imagePreview, previewFrame, selectedItem);
			}

		}

	}




	private class EditPanel extends JPanel implements ActionListener{
		// Data
		JLabel title;
		JLabel cKitNameLabel;
		JTextField cKitName;
		JLabel cItemLabel;
		JLabel previewFrame;
		ImageIcon imagePreview;
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
		JButton saveKit;
		JButton removeKit;
		JButton cancel;
		Part currentPart;
		KitConfig currentKit;
		Border border;
		Border borderPadding;


		//Methods

		public EditPanel(){
			this.setLayout(new GridBagLayout());
			currentPart = new Part();
			currentKit = new KitConfig();
			GridBagConstraints c = new GridBagConstraints();
			title = new JLabel ("Kit Manager");
			title.setFont(new Font("Serif", Font.BOLD, 16));
			cKitNameLabel = new JLabel ("New Kit : ");
			cKitName = new JTextField(8);
			cItemLabel = new JLabel("Item : ");
			cItemComboBox = new JComboBox();
			cItemComboBox.addItem("None");
			for(String name : kitManager.getPartsList().keySet()){
				cItemComboBox.addItem(name);
			}
			cItemComboBox.addActionListener(this);
			previewFrame = new JLabel();
			imagePreview = new ImageIcon("Images/none.png");
			previewFrame.setIcon(imagePreview);
			saveKit = new JButton ("Save Kit");
			saveKit.addActionListener(this);
			removeKit = new JButton ("Remove Kit");
			removeKit.addActionListener(this);
			cancel = new JButton ("Cancel");
			cancel.addActionListener(this);


			cItem1 = new ImageIcon("Images/none.png");
			cItem2 = new ImageIcon("Images/none.png");
			cItem3 = new ImageIcon("Images/none.png");
			cItem4 = new ImageIcon("Images/none.png");
			cItem5 = new ImageIcon("Images/none.png");
			cItem6 = new ImageIcon("Images/none.png");
			cItem7 = new ImageIcon("Images/none.png");
			cItem8 = new ImageIcon("Images/none.png");

			border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
			borderPadding = BorderFactory.createEmptyBorder(2, 2, 2, 2);
			cItemFrame1 = new JLabel();
			cItemFrame1.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame1.setIcon(cItem1);
			cItemFrame1.addMouseListener(mouseListener);
			cItemFrame2 = new JLabel();
			cItemFrame2.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame2.setIcon(cItem2);
			cItemFrame2.addMouseListener(mouseListener);
			cItemFrame3 = new JLabel();
			cItemFrame3.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame3.setIcon(cItem3);
			cItemFrame3.addMouseListener(mouseListener);
			cItemFrame4 = new JLabel();
			cItemFrame4.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame4.setIcon(cItem4);
			cItemFrame4.addMouseListener(mouseListener);
			cItemFrame5 = new JLabel();
			cItemFrame5.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame5.setIcon(cItem5);
			cItemFrame5.addMouseListener(mouseListener);
			cItemFrame6 = new JLabel();
			cItemFrame6.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame6.setIcon(cItem6);
			cItemFrame6.addMouseListener(mouseListener);
			cItemFrame7 = new JLabel();
			cItemFrame7.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame7.setIcon(cItem7);
			cItemFrame7.addMouseListener(mouseListener);
			cItemFrame8 = new JLabel();
			cItemFrame8.setBorder(BorderFactory.createCompoundBorder(border, borderPadding));
			cItemFrame8.setIcon(cItem8);
			cItemFrame8.addMouseListener(mouseListener);


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

			JPanel kitBase = new JPanel(new GridBagLayout());

			c. gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(0,0,0,0);
			kitBase.add(cItemFrame1, c);
			c. gridx = 1;
			c.gridy = 0;
			kitBase.add(cItemFrame2, c);
			c. gridx = 2;
			c.gridy = 0;
			kitBase.add(cItemFrame3, c);
			c. gridx = 3;
			c.gridy = 0;
			kitBase.add(cItemFrame4, c);
			c. gridx = 0;
			c.gridy = 1;
			kitBase.add(cItemFrame5, c);
			c. gridx = 1;
			c.gridy = 1;
			kitBase.add(cItemFrame6, c);
			c. gridx = 2;
			c.gridy = 1;
			kitBase.add(cItemFrame7, c);
			c. gridx = 3;
			c.gridy = 1;
			kitBase.add(cItemFrame8, c);

			c.gridx = 1;
			c.gridy = 3;
			c.insets = new Insets(0,0,20,0);
			this.add(kitBase, c);
			
			c.gridx = 0;
			c.gridy = 4;
			this.add(saveKit, c);
			c.gridx = 1;
			c.gridy = 4;
			this.add(removeKit, c);
			c.gridx = 2;
			c.gridy = 4;
			this.add(cancel, c);

		}

		public void updatePicture(ImageIcon icon, JLabel frame, String path){
			if(path.length() > 5 && path.substring(0,6).equals("Images")){
				icon = new ImageIcon(path);
			}else{
				icon = new ImageIcon("Images/" + path + ".png");
			}
			System.out.println(path);
			frame.setIcon(icon);
		}

		
		public void actionPerformed(ActionEvent ae) {

			if (ae.getSource() == removeKit){
				System.out.println("I will remove a part and update the server.");
				//KitManager.sendMessage("remove", currentKit, null);
				basePanel1.setVisible(true);
				basePanel2.setVisible(false);
				cItemComboBox.setSelectedIndex(0);
			}else if(ae.getSource() == saveKit){
				String testName;
				cKitName.setText(cKitName.getText().replaceAll("\\s","")) ;
				testName = cKitName.getText().toUpperCase();
				boolean nameTaken = false;
				for(String tempName : kitManager.getKitConfigList().keySet()){
					if(testName.equals(tempName.toUpperCase()) && !testName.equals(currentPart.name.toUpperCase()))
						nameTaken = true;
				}
				if(nameTaken){
					cKitName.setText("Name taken.");
				}else if (cKitName.getText().equals("")){
					cKitName.setText("No input.");
				}else{
					/*
					Part p = new Part(name.getText(), currentPart.id, description.getText(),"Images/" + (String)imageSelection.getSelectedItem() + ".png", (Integer)nestStabalizationTime.getValue());
					partsManager.sendMessage("edit", p, currentPart);
					editItem(p);
					partsManager.parts.remove(currentPart.name);
					partsManager.parts.put(p.name, p);
					currentPart = p;
					*/
					basePanel1.setVisible(true);
					basePanel2.setVisible(false);
					cItemComboBox.setSelectedIndex(0);
				}
			}else if(ae.getSource() == cancel){
				basePanel1.setVisible(true);
				basePanel2.setVisible(false);
				cItemComboBox.setSelectedIndex(0);
			}else{
				JComboBox cb = (JComboBox)ae.getSource();
				String selectedItem = (String)cb.getSelectedItem();
				updatePicture(imagePreview, previewFrame, selectedItem);
			}

		}

		public void updateEditPanel(JTable tempTable, int tempRow){
			String tempName;
			KitConfig k;
			tempName = (String) tempTable.getValueAt(tempRow, 0);
			k = kitManager.getKitConfigList().get(tempName);
			currentKit = k;
			cKitName.setText(currentKit.kitName);

			editPanel.updatePicture(editPanel.cItem1, editPanel.cItemFrame1, currentKit.listOfParts.get(0).imagePath);
			editPanel.updatePicture(editPanel.cItem2, editPanel.cItemFrame2, currentKit.listOfParts.get(1).imagePath);
			editPanel.updatePicture(editPanel.cItem3, editPanel.cItemFrame3, currentKit.listOfParts.get(2).imagePath);
			editPanel.updatePicture(editPanel.cItem4, editPanel.cItemFrame4, currentKit.listOfParts.get(3).imagePath);
			if(currentKit.listOfParts.size() == 5){
				editPanel.updatePicture(editPanel.cItem5, editPanel.cItemFrame5, currentKit.listOfParts.get(4).imagePath);
				editPanel.updatePicture(editPanel.cItem6, editPanel.cItemFrame6, "Images/none.png");
				editPanel.updatePicture(editPanel.cItem7, editPanel.cItemFrame7, "Images/none.png");
				editPanel.updatePicture(editPanel.cItem8, editPanel.cItemFrame8, "Images/none.png");
			}else if (currentKit.listOfParts.size() == 6){
				editPanel.updatePicture(editPanel.cItem5, editPanel.cItemFrame5, currentKit.listOfParts.get(4).imagePath);
				editPanel.updatePicture(editPanel.cItem6, editPanel.cItemFrame6, currentKit.listOfParts.get(5).imagePath);
				editPanel.updatePicture(editPanel.cItem7, editPanel.cItemFrame7, "Images/none.png");
				editPanel.updatePicture(editPanel.cItem8, editPanel.cItemFrame8, "Images/none.png");
				
			}else if (currentKit.listOfParts.size() == 7){
				editPanel.updatePicture(editPanel.cItem5, editPanel.cItemFrame5, currentKit.listOfParts.get(4).imagePath);
				editPanel.updatePicture(editPanel.cItem6, editPanel.cItemFrame6, currentKit.listOfParts.get(5).imagePath);
				editPanel.updatePicture(editPanel.cItem7, editPanel.cItemFrame7, currentKit.listOfParts.get(6).imagePath);
				editPanel.updatePicture(editPanel.cItem8, editPanel.cItemFrame8, "Images/none.png");
			}else if (currentKit.listOfParts.size() == 8){
				editPanel.updatePicture(editPanel.cItem5, editPanel.cItemFrame5, currentKit.listOfParts.get(4).imagePath);
				editPanel.updatePicture(editPanel.cItem6, editPanel.cItemFrame6, currentKit.listOfParts.get(5).imagePath);
				editPanel.updatePicture(editPanel.cItem7, editPanel.cItemFrame7, currentKit.listOfParts.get(6).imagePath);
				editPanel.updatePicture(editPanel.cItem8, editPanel.cItemFrame8, currentKit.listOfParts.get(7).imagePath);
				
			}else{
				editPanel.updatePicture(editPanel.cItem5, editPanel.cItemFrame5, "Images/none.png");
				editPanel.updatePicture(editPanel.cItem6, editPanel.cItemFrame6, "Images/none.png");
				editPanel.updatePicture(editPanel.cItem7, editPanel.cItemFrame7, "Images/none.png");
				editPanel.updatePicture(editPanel.cItem8, editPanel.cItemFrame8, "Images/none.png");
			}
			
		}

	}

	/*
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

	public class MouseListener extends MouseAdapter{

		public void mouseClicked(MouseEvent ae) {
			if (ae.getClickCount() == 1){
				if(ae.getSource() == addPanel.cItemFrame1){
					System.out.println("Changed Item 1 " + (String)addPanel.cItemComboBox.getSelectedItem());
					addPanel.updatePicture(addPanel.cItem1, addPanel.cItemFrame1, (String)addPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == addPanel.cItemFrame2){
					System.out.println("Changed Item 2");
					addPanel.updatePicture(addPanel.cItem2, addPanel.cItemFrame2, (String)addPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == addPanel.cItemFrame3){
					System.out.println("Changed Item 3");
					addPanel.updatePicture(addPanel.cItem3, addPanel.cItemFrame3, (String)addPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == addPanel.cItemFrame4){
					System.out.println("Changed Item 4");
					addPanel.updatePicture(addPanel.cItem4, addPanel.cItemFrame4, (String)addPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == addPanel.cItemFrame5){
					System.out.println("Changed Item 5");
					addPanel.updatePicture(addPanel.cItem5, addPanel.cItemFrame5, (String)addPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == addPanel.cItemFrame6){
					System.out.println("Changed Item 6");
					addPanel.updatePicture(addPanel.cItem6, addPanel.cItemFrame6, (String)addPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == addPanel.cItemFrame7){
					System.out.println("Changed Item 7");
					addPanel.updatePicture(addPanel.cItem7, addPanel.cItemFrame7, (String)addPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == addPanel.cItemFrame8){
					System.out.println("Changed Item 8");
					addPanel.updatePicture(addPanel.cItem8, addPanel.cItemFrame8, (String)addPanel.cItemComboBox.getSelectedItem());
				}else if(ae.getSource() == editPanel.cItemFrame1){
					System.out.println("Changed Item 1 " + (String)editPanel.cItemComboBox.getSelectedItem());
					editPanel.updatePicture(editPanel.cItem1, editPanel.cItemFrame1, (String)editPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == editPanel.cItemFrame2){
					System.out.println("Changed Item 2");
					editPanel.updatePicture(editPanel.cItem2, editPanel.cItemFrame2, (String)editPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == editPanel.cItemFrame3){
					System.out.println("Changed Item 3");
					editPanel.updatePicture(editPanel.cItem3, editPanel.cItemFrame3, (String)editPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == editPanel.cItemFrame4){
					System.out.println("Changed Item 4");
					editPanel.updatePicture(editPanel.cItem4, editPanel.cItemFrame4, (String)editPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == editPanel.cItemFrame5){
					System.out.println("Changed Item 5");
					editPanel.updatePicture(editPanel.cItem5, editPanel.cItemFrame5, (String)editPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == editPanel.cItemFrame6){
					System.out.println("Changed Item 6");
					editPanel.updatePicture(editPanel.cItem6, editPanel.cItemFrame6, (String)editPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == editPanel.cItemFrame7){
					System.out.println("Changed Item 7");
					editPanel.updatePicture(editPanel.cItem7, editPanel.cItemFrame7, (String)editPanel.cItemComboBox.getSelectedItem());
				}else if (ae.getSource() == editPanel.cItemFrame8){
					System.out.println("Changed Item 8");
					editPanel.updatePicture(editPanel.cItem8, editPanel.cItemFrame8, (String)editPanel.cItemComboBox.getSelectedItem());
				}
			}
			else if (ae.getClickCount() == 2) {
				if(ae.getSource() == table){
					JTable target = (JTable)ae.getSource();
					int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					editPanel.updateEditPanel(target, row);
					basePanel1.setVisible(false);
					basePanel2.setVisible(true);

					addPanel.cKitName.setText("");
					addPanel.cItemComboBox.setSelectedIndex(0);
					addPanel.updatePicture(addPanel.cItem1, addPanel.cItemFrame1, "Images/none.png");
					addPanel.updatePicture(addPanel.cItem2, addPanel.cItemFrame2, "Images/none.png");
					addPanel.updatePicture(addPanel.cItem3, addPanel.cItemFrame3, "Images/none.png");
					addPanel.updatePicture(addPanel.cItem4, addPanel.cItemFrame4, "Images/none.png");
					addPanel.updatePicture(addPanel.cItem5, addPanel.cItemFrame5, "Images/none.png");
					addPanel.updatePicture(addPanel.cItem6, addPanel.cItemFrame6, "Images/none.png");
					addPanel.updatePicture(addPanel.cItem7, addPanel.cItemFrame7, "Images/none.png");
					addPanel.updatePicture(addPanel.cItem8, addPanel.cItemFrame8, "Images/none.png");
				}
			}
		}

	}

}


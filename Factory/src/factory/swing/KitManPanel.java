//Stephanie Reagle, Marc Mendiola
//CS 200
package factory.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import factory.KitConfig;
import factory.Part;
import factory.managers.KitManager;

public class KitManPanel extends JPanel{

	private static final long serialVersionUID = -5760501420685052853L;


	KitConfig currentKit = new KitConfig();
	//for Create Kit Tab
	AddPanel addPanel;
	EditPanel editPanel;

	JScrollPane scrollPane;
	JPanel currentListPanel;
	JTable table;
	PartsTableModel model;
	PartsTableCellRenderer renderer;
	JPanel basePanel1;
	JPanel basePanel2;
	MouseListener mouseListener;


	KitManager kitManager;

	public KitManPanel(KitManager k){
		setLayout(new CardLayout());
		kitManager = k;
		addPanel = new AddPanel();
		editPanel = new EditPanel();
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

		System.out.println(kitManager.getKitConfigList().size());
		for(KitConfig temp : kitManager.getKitConfigList().values()){
			Object[] row = {temp.kitName};
			model.insertRow(model.getRowCount(), row);
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


	}
	public class AddPanel extends JPanel implements ActionListener{  // For add panel

		JLabel cKitNameLabel = new JLabel ("Kit Name : ");
		JTextField cKitName = new JTextField(8);
		JButton cSave = new JButton("Save Kit Configuration");


		//initializing components
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

		public AddPanel(){


			cSave.addActionListener(this);

			cItemComboBox1.addItem("None");
			cItemComboBox2.addItem("None");
			cItemComboBox3.addItem("None");
			cItemComboBox4.addItem("None");
			cItemComboBox5.addItem("None");
			cItemComboBox6.addItem("None");
			cItemComboBox7.addItem("None");
			cItemComboBox8.addItem("None");
			for(String name : kitManager.getPartsList().keySet()){
				cItemComboBox1.addItem(name);
				cItemComboBox2.addItem(name);
				cItemComboBox3.addItem(name);
				cItemComboBox4.addItem(name);
				cItemComboBox5.addItem(name);
				cItemComboBox6.addItem(name);
				cItemComboBox7.addItem(name);
				cItemComboBox8.addItem(name);
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

			cItem1 = new ImageIcon("Images/none.png");
			cItem2 = new ImageIcon("Images/none.png");
			cItem3 = new ImageIcon("Images/none.png");
			cItem4 = new ImageIcon("Images/none.png");
			cItem5 = new ImageIcon("Images/none.png");
			cItem6 = new ImageIcon("Images/none.png");
			cItem7 = new ImageIcon("Images/none.png");
			cItem8 = new ImageIcon("Images/none.png");
			
			//laying out components 
			
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

			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = 0;
			c.gridy = 0;
			this.add(cLabel1, c);

			c.gridy = 1;
			this.add(cLabel2, c);

			c.gridy = 2;
			this.add(cLabel3, c);

			c.gridy = 3;
			this.add(cLabel4, c);

			c.gridy = 4;
			this.add(cLabel5, c);

			c.gridy = 5;
			this.add(cLabel6, c);

			c.gridy = 6;
			this.add(cLabel7, c);

			c.gridy = 7;
			this.add(cLabel8, c);

			c.gridy = 9;
			this.add(cKitNameLabel, c);


			c.gridy = 10;
			this.add(cSave, c);

			c.gridx = 1;
			c.gridy = 9;
			this.add(cKitName, c);

			c.gridx = 1;
			c.gridy = 0;
			this.add(cItemComboBox1, c);

			c.gridy = 1;
			this.add(cItemComboBox2, c);

			c.gridy = 2;
			this.add(cItemComboBox3, c);

			c.gridy = 3;
			this.add(cItemComboBox4, c);

			c.gridy = 4;
			this.add(cItemComboBox5, c);

			c.gridy = 5;
			this.add(cItemComboBox6, c);

			c.gridy = 6;
			this.add(cItemComboBox7, c);

			c.gridy = 7;
			this.add(cItemComboBox8, c);

			c.gridx = 2;
			c.gridy = 0;
			this.add(cItemFrame1, c);

			c.gridy = 1;
			this.add(cItemFrame2, c);

			c.gridy = 2;
			this.add(cItemFrame3, c);

			c.gridy = 3;
			this.add(cItemFrame4, c);

			c.gridy = 4;
			this.add(cItemFrame5, c);

			c.gridy = 5;
			this.add(cItemFrame6, c);

			c.gridy = 6;
			this.add(cItemFrame7, c);

			c.gridy = 7;
			this.add(cItemFrame8, c);
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			String set = new String();
			String cmd = new String();
			if (ae.getSource() == cSave) {  // save button
				String testName;  // error checking
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

					KitConfig k = new KitConfig(cKitName.getText());

					if(!cItemComboBox1.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(cItemComboBox1.getSelectedItem()));
					}
					if(!cItemComboBox2.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(cItemComboBox2.getSelectedItem()));
					}
					if(!cItemComboBox3.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(cItemComboBox3.getSelectedItem()));
					}
					if(!cItemComboBox4.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(cItemComboBox4.getSelectedItem()));
					}
					if(!cItemComboBox5.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(cItemComboBox5.getSelectedItem()));
					}
					if(!cItemComboBox6.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(cItemComboBox6.getSelectedItem()));
					}
					if(!cItemComboBox7.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(cItemComboBox7.getSelectedItem()));
					}if(!cItemComboBox8.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(cItemComboBox8.getSelectedItem()));
					}
					if(k.listOfParts.size() >=4){
						addKit(k);
						kitManager.addToKitConfigList(k);
						//editPanel.mKitComboBox.addItem(k.kitName);
						basePanel1.setVisible(true);
						basePanel2.setVisible(false);
						//"km fpm cmd addkitname #kitname #partname1 #partname2 ... #partname8"
						cmd = "km fpm cmd addkitname " + cKitName.getText() + " " + (String)cItemComboBox1.getSelectedItem() + " " + 
								(String)cItemComboBox2.getSelectedItem() + " " + (String)cItemComboBox3.getSelectedItem() + " " + 
								(String)cItemComboBox4.getSelectedItem() + " " + (String)cItemComboBox5.getSelectedItem() + " " +
								(String)cItemComboBox6.getSelectedItem() + " " + (String)cItemComboBox7.getSelectedItem() + " " +
								(String)cItemComboBox8.getSelectedItem();
						kitManager.sendCommand(cmd);

						// "km fcsa cmd addkitname #kitname #partname1 #partname2 ... #partname8" 
						cmd = "km fcsa cmd addkitname " + cKitName.getText() + " " + (String)cItemComboBox1.getSelectedItem() + " " + 
								(String)cItemComboBox2.getSelectedItem() + " " + (String)cItemComboBox3.getSelectedItem() + " " + 
								(String)cItemComboBox4.getSelectedItem() + " " + (String)cItemComboBox5.getSelectedItem() + " " +
								(String)cItemComboBox6.getSelectedItem() + " " + (String)cItemComboBox7.getSelectedItem() + " " +
								(String)cItemComboBox8.getSelectedItem();
						kitManager.sendCommand(cmd);
					}else{
						cKitName.setText("must have >= 4 items");
					}
				}
			}else{
				JComboBox cb = (JComboBox)ae.getSource();  // updates picture
				String selectedItem = (String)cb.getSelectedItem();
				if(cb == this.cItemComboBox1)
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

			}
		}

		public void refreshAddPanel(){

			cItemComboBox1.removeAllItems();
			cItemComboBox2.removeAllItems();
			cItemComboBox3.removeAllItems();
			cItemComboBox4.removeAllItems();
			cItemComboBox5.removeAllItems();
			cItemComboBox6.removeAllItems();
			cItemComboBox7.removeAllItems();
			cItemComboBox8.removeAllItems();
			
			cItemComboBox1.addItem("None");
			cItemComboBox2.addItem("None");
			cItemComboBox3.addItem("None");
			cItemComboBox4.addItem("None");
			cItemComboBox5.addItem("None");
			cItemComboBox6.addItem("None");
			cItemComboBox7.addItem("None");
			cItemComboBox8.addItem("None");

			for(String name : kitManager.getPartsList().keySet()){
				cItemComboBox1.addItem(name);
				cItemComboBox2.addItem(name);
				cItemComboBox3.addItem(name);
				cItemComboBox4.addItem(name);
				cItemComboBox5.addItem(name);
				cItemComboBox6.addItem(name);
				cItemComboBox7.addItem(name);
				cItemComboBox8.addItem(name);
			}

			addPanel.cItemComboBox1.setSelectedIndex(0);
			addPanel.cItemComboBox2.setSelectedIndex(0);
			addPanel.cItemComboBox3.setSelectedIndex(0);
			addPanel.cItemComboBox4.setSelectedIndex(0);
			addPanel.cItemComboBox5.setSelectedIndex(0);
			addPanel.cItemComboBox6.setSelectedIndex(0);
			addPanel.cItemComboBox7.setSelectedIndex(0);
			addPanel.cItemComboBox8.setSelectedIndex(0);
			addPanel.cKitName.setText("");

		}


	}




	public class EditPanel extends JPanel implements ActionListener{
		JLabel mItemFrame1;  // image previews
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
		//JComboBox mKitComboBox = new JComboBox();

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

		JLabel mKitNameLabel = new JLabel ("Kit Name : ");

		JTextField mKitName = new JTextField(8);
		JButton mSave = new JButton("Save Kit Configuration");

		JButton mRemove = new JButton("Remove Kit");
		JButton cancel = new JButton("Cancel");

		public EditPanel(){
			mItemComboBox1.addItem("None");
			mItemComboBox2.addItem("None");
			mItemComboBox3.addItem("None");
			mItemComboBox4.addItem("None");
			mItemComboBox5.addItem("None");
			mItemComboBox6.addItem("None");
			mItemComboBox7.addItem("None");
			mItemComboBox8.addItem("None");

			for(String key : kitManager.getPartsList().keySet()){
				mItemComboBox1.addItem(key);
				mItemComboBox2.addItem(key);
				mItemComboBox3.addItem(key);
				mItemComboBox4.addItem(key);
				mItemComboBox5.addItem(key);
				mItemComboBox6.addItem(key);
				mItemComboBox7.addItem(key);
				mItemComboBox8.addItem(key);
			}

			/*for(String key : kitManager.getKitConfigList().keySet()){
				mKitComboBox.addItem(key);
			}
			mKitComboBox.addActionListener(this);

			 */

			mItemComboBox1.addActionListener(this);
			mItemComboBox2.addActionListener(this);
			mItemComboBox3.addActionListener(this);
			mItemComboBox4.addActionListener(this);
			mItemComboBox5.addActionListener(this);
			mItemComboBox6.addActionListener(this);
			mItemComboBox7.addActionListener(this);
			mItemComboBox8.addActionListener(this);

			mItem1 = new ImageIcon("Images/none.png");
			mItem2 = new ImageIcon("Images/none.png");
			mItem3 = new ImageIcon("Images/none.png");
			mItem4 = new ImageIcon("Images/none.png");
			mItem5 = new ImageIcon("Images/none.png");
			mItem6 = new ImageIcon("Images/none.png");
			mItem7 = new ImageIcon("Images/none.png");
			mItem8 = new ImageIcon("Images/none.png");


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

			mSave.addActionListener(this);
			mRemove.addActionListener(this);
			cancel.addActionListener(this);

			JTabbedPane tabbedPane = new JTabbedPane();
			GridBagConstraints c = new GridBagConstraints();


			// lays out components
			setLayout(new GridBagLayout());

			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = 0;
			c.gridy = 0;
			//add(mKitComboBox, c);

			c.gridy = 1;
			add(mLabel1, c);

			c.gridy = 2;
			add(mLabel2, c);

			c.gridy = 3;
			add(mLabel3, c);

			c.gridy = 4;
			add(mLabel4, c);

			c.gridy = 5;
			add(mLabel5, c);

			c.gridy = 6;
			add(mLabel6, c);

			c.gridy = 7;
			add(mLabel7, c);

			c.gridy = 8;
			add(mLabel8, c);

			c.gridx = 1;
			c.gridy = 1;
			add(mItemComboBox1, c);

			c.gridy = 2;
			add(mItemComboBox2, c);

			c.gridy = 3;
			add(mItemComboBox3, c);

			c.gridy = 4;
			add(mItemComboBox4, c);

			c.gridy = 5;
			add(mItemComboBox5, c);

			c.gridy = 6;
			add(mItemComboBox6, c);

			c.gridy = 7;
			add(mItemComboBox7, c);

			c.gridy = 8;
			add(mItemComboBox8, c);

			c.gridy = 9;
			add(mKitName, c);

			c.gridx = 0;
			c.gridy = 9;
			add(mKitNameLabel, c);

			c.gridy = 10;
			add(mSave, c);

			c.gridx = 1;
			c.gridy = 10;
			add(mRemove, c);

			c.gridx = 2;
			c.gridy = 10;
			add(cancel, c);

			c.gridx = 2;
			c.gridy = 1;
			add(mItemFrame1, c);

			c.gridy = 2;
			add(mItemFrame2, c);

			c.gridy = 3;
			add(mItemFrame3, c);

			c.gridy = 4;
			add(mItemFrame4, c);

			c.gridy = 5;
			add(mItemFrame5, c);

			c.gridy = 6;
			add(mItemFrame6, c);

			c.gridy = 7;
			add(mItemFrame7, c);

			c.gridy = 8;
			add(mItemFrame8, c);
		}


		public void actionPerformed(ActionEvent ae) {
			String set = new String();
			String cmd = new String();
			if(ae.getSource() == mSave){
				String testName;
				mKitName.setText(mKitName.getText().replaceAll("\\s","")) ;
				testName = mKitName.getText().toUpperCase();
				boolean nameTaken = false;
				for(String tempName : kitManager.getKitConfigList().keySet()){
					if(testName.equals(tempName.toUpperCase()) && !testName.equals(currentKit.kitName.toUpperCase()))
						nameTaken = true;
				}
				if(nameTaken || testName.equals("NAMETAKEN")){
					mKitName.setText("Name taken");
				}else if (mKitName.getText().equals("") || testName.equals("NOINPUT") ){
					mKitName.setText("No input");
				}else{

					KitConfig k = new KitConfig(mKitName.getText());

					if(!mItemComboBox1.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(mItemComboBox1.getSelectedItem()));
					}
					if(!mItemComboBox2.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(mItemComboBox2.getSelectedItem()));
					}
					if(!mItemComboBox3.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(mItemComboBox3.getSelectedItem()));
					}
					if(!mItemComboBox4.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(mItemComboBox4.getSelectedItem()));
					}
					if(!mItemComboBox5.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(mItemComboBox5.getSelectedItem()));
					}
					if(!mItemComboBox6.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(mItemComboBox6.getSelectedItem()));
					}
					if(!mItemComboBox7.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(mItemComboBox7.getSelectedItem()));
					}if(!mItemComboBox8.getSelectedItem().equals("None")){
						k.listOfParts.add(kitManager.getPartsList().get(mItemComboBox8.getSelectedItem()));
					}

					if(k.listOfParts.size() >=4 && !testName.equals("MUSTHAVE>=4ITEMS")){
						kitManager.removeFromKitConfigList(currentKit);
						kitManager.addToKitConfigList(k);
						editKit(k);

						//editPanel.mKitComboBox.removeItem(currentKit.kitName);
						//editPanel.mKitComboBox.addItem(k.kitName);

						currentKit = k;

						//currentKit = kitManager.getKitConfigList().get(mKitComboBox.getSelectedItem());

						basePanel1.setVisible(true);
						basePanel2.setVisible(false);

						//"km fpm set kitcontent #oldkitname #kitname #partname1 #partname2 ... #partname8"
						set = "km fpm set kitcontent " + currentKit.kitName + " " + mKitName.getText() + " " +
								(String)mItemComboBox1.getSelectedItem() + " " + (String)mItemComboBox2.getSelectedItem() + " " +  
								(String)mItemComboBox3.getSelectedItem() + " " + (String)mItemComboBox4.getSelectedItem() + " " + 
								(String)mItemComboBox5.getSelectedItem() + " " + (String)mItemComboBox6.getSelectedItem() + " " + 
								(String)mItemComboBox7.getSelectedItem() + " " + (String)mItemComboBox8.getSelectedItem();
						kitManager.sendCommand(set);

						//"km fcsa set kitcontent #oldkitname #kitname #partname1 #partname2 ... #partname8"

						set = "km fcsa set kitcontent " + currentKit.kitName + " " + mKitName.getText() + " " +
								(String)mItemComboBox1.getSelectedItem() + " " + (String)mItemComboBox2.getSelectedItem() + " " +  
								(String)mItemComboBox3.getSelectedItem() + " " + (String)mItemComboBox4.getSelectedItem() + " " + 
								(String)mItemComboBox5.getSelectedItem() + " " + (String)mItemComboBox6.getSelectedItem() + " " + 
								(String)mItemComboBox7.getSelectedItem() + " " + (String)mItemComboBox8.getSelectedItem();
						kitManager.sendCommand(set);


						//if(kitManager.getKitConfigList().size() > 0)
						//mKitComboBox.setSelectedIndex(0);

					}else{
						mKitName.setText("must have >= 4 items");
					}
				}
			}else if (ae.getSource() == mRemove){  // remove button
				basePanel1.setVisible(true);
				basePanel2.setVisible(false);
				removeKit(currentKit);
				//"km fpm cmd rmkitname #kitname"
				cmd = "km fpm cmd rmkitname " + mKitName.getText();
				kitManager.sendCommand(cmd);
				//"km fcsa cmd rmkitname #kitname"
				cmd = "km fcsa cmd rmkitname " + mKitName.getText();
				kitManager.sendCommand(cmd);

				kitManager.removeFromKitConfigList(currentKit);

				/*editPanel.mKitComboBox.removeItem(currentKit.kitName);
				if(kitManager.getKitConfigList().size() > 0){
					mKitComboBox.setSelectedIndex(0);
					currentKit = kitManager.getKitConfigList().get(mKitComboBox.getSelectedItem());
				}else{
					currentKit = new KitConfig();
				}
				 */
			}else if (ae.getSource() == cancel){  // cancel button
				basePanel1.setVisible(true);
				basePanel2.setVisible(false);
				//mKitComboBox.setSelectedIndex(0);
			}else{
				JComboBox cb = (JComboBox)ae.getSource();
				String selectedItem = (String)cb.getSelectedItem();
				if(cb == mItemComboBox1)
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
				/*else if(cb == mKitComboBox){

					KitConfig k;
					if(kitManager.getKitConfigList().size() >0){
						k = kitManager.getKitConfigList().get(mKitComboBox.getSelectedItem());
						currentKit = k;

						mKitName.setText(currentKit.kitName);

						mItemComboBox1.setSelectedItem(currentKit.listOfParts.get(0).name);
						mItemComboBox2.setSelectedItem(currentKit.listOfParts.get(1).name);
						mItemComboBox3.setSelectedItem(currentKit.listOfParts.get(2).name);
						mItemComboBox4.setSelectedItem(currentKit.listOfParts.get(3).name);
						if(currentKit.listOfParts.size() == 5){
							mItemComboBox5.setSelectedItem(currentKit.listOfParts.get(4).name);
							mItemComboBox6.setSelectedItem("None");
							mItemComboBox7.setSelectedItem("None");
							mItemComboBox8.setSelectedItem("None");
						}else if (currentKit.listOfParts.size() == 6){
							mItemComboBox5.setSelectedItem(currentKit.listOfParts.get(4).name);
							mItemComboBox6.setSelectedItem(currentKit.listOfParts.get(5).name);
							mItemComboBox7.setSelectedItem("None");
							mItemComboBox8.setSelectedItem("None");

						}else if (currentKit.listOfParts.size() == 7){
							mItemComboBox5.setSelectedItem(currentKit.listOfParts.get(4).name);
							mItemComboBox6.setSelectedItem(currentKit.listOfParts.get(5).name);
							mItemComboBox7.setSelectedItem(currentKit.listOfParts.get(6).name);
							mItemComboBox8.setSelectedItem("None");
						}else if (currentKit.listOfParts.size() == 8){
							mItemComboBox5.setSelectedItem(currentKit.listOfParts.get(4).name);
							mItemComboBox6.setSelectedItem(currentKit.listOfParts.get(5).name);
							mItemComboBox7.setSelectedItem(currentKit.listOfParts.get(6).name);
							mItemComboBox8.setSelectedItem(currentKit.listOfParts.get(7).name);

						}else{
							mItemComboBox5.setSelectedItem("None");
							mItemComboBox6.setSelectedItem("None");
							mItemComboBox7.setSelectedItem("None");
							mItemComboBox8.setSelectedItem("None");
						}
					}else{

						mItemComboBox1.setSelectedItem("None");
						mItemComboBox2.setSelectedItem("None");
						mItemComboBox3.setSelectedItem("None");
						mItemComboBox4.setSelectedItem("None");
						mItemComboBox5.setSelectedItem("None");
						mItemComboBox6.setSelectedItem("None");
						mItemComboBox7.setSelectedItem("None");
						mItemComboBox8.setSelectedItem("None");
					}

				}*/

			}


		}


		public void updateEditPanel(JTable tempTable, int tempRow){  // updates edit panel
			//mKitComboBox.setSelectedItem(tempTable.getValueAt(tempRow, 0));
			KitConfig k;
			k = kitManager.getKitConfigList().get(tempTable.getValueAt(tempRow, 0));
			currentKit = k;
			mKitName.setText(currentKit.kitName);

			if(kitManager.getKitConfigList().size() >0){
				mItemComboBox1.setSelectedItem(currentKit.listOfParts.get(0).name);
				mItemComboBox2.setSelectedItem(currentKit.listOfParts.get(1).name);
				mItemComboBox3.setSelectedItem(currentKit.listOfParts.get(2).name);
				mItemComboBox4.setSelectedItem(currentKit.listOfParts.get(3).name);
				if(currentKit.listOfParts.size() == 5){
					mItemComboBox5.setSelectedItem(currentKit.listOfParts.get(4).name);
					mItemComboBox6.setSelectedItem("None");
					mItemComboBox7.setSelectedItem("None");
					mItemComboBox8.setSelectedItem("None");
				}else if (currentKit.listOfParts.size() == 6){
					mItemComboBox5.setSelectedItem(currentKit.listOfParts.get(4).name);
					mItemComboBox6.setSelectedItem(currentKit.listOfParts.get(5).name);
					mItemComboBox7.setSelectedItem("None");
					mItemComboBox8.setSelectedItem("None");

				}else if (currentKit.listOfParts.size() == 7){
					mItemComboBox5.setSelectedItem(currentKit.listOfParts.get(4).name);
					mItemComboBox6.setSelectedItem(currentKit.listOfParts.get(5).name);
					mItemComboBox7.setSelectedItem(currentKit.listOfParts.get(6).name);
					mItemComboBox8.setSelectedItem("None");
				}else if (currentKit.listOfParts.size() == 8){
					mItemComboBox5.setSelectedItem(currentKit.listOfParts.get(4).name);
					mItemComboBox6.setSelectedItem(currentKit.listOfParts.get(5).name);
					mItemComboBox7.setSelectedItem(currentKit.listOfParts.get(6).name);
					mItemComboBox8.setSelectedItem(currentKit.listOfParts.get(7).name);

				}else{
					mItemComboBox5.setSelectedItem("None");
					mItemComboBox6.setSelectedItem("None");
					mItemComboBox7.setSelectedItem("None");
					mItemComboBox8.setSelectedItem("None");
				}
			}else{

				mItemComboBox1.setSelectedItem("None");
				mItemComboBox2.setSelectedItem("None");
				mItemComboBox3.setSelectedItem("None");
				mItemComboBox4.setSelectedItem("None");
				mItemComboBox5.setSelectedItem("None");
				mItemComboBox6.setSelectedItem("None");
				mItemComboBox7.setSelectedItem("None");
				mItemComboBox8.setSelectedItem("None");
			}

		}

		public void refreshEditPanel(){  // refreshes screen
			mItemComboBox1.removeAllItems();
			mItemComboBox2.removeAllItems();
			mItemComboBox3.removeAllItems();
			mItemComboBox4.removeAllItems();
			mItemComboBox5.removeAllItems();
			mItemComboBox6.removeAllItems();
			mItemComboBox7.removeAllItems();
			mItemComboBox8.removeAllItems();
			
			mItemComboBox1.addItem("None");
			mItemComboBox2.addItem("None");
			mItemComboBox3.addItem("None");
			mItemComboBox4.addItem("None");
			mItemComboBox5.addItem("None");
			mItemComboBox6.addItem("None");
			mItemComboBox7.addItem("None");
			mItemComboBox8.addItem("None");

			for(String name : kitManager.getPartsList().keySet()){
				mItemComboBox1.addItem(name);
				mItemComboBox2.addItem(name);
				mItemComboBox3.addItem(name);
				mItemComboBox4.addItem(name);
				mItemComboBox5.addItem(name);
				mItemComboBox6.addItem(name);
				mItemComboBox7.addItem(name);
				mItemComboBox8.addItem(name);
			}
			
			
			mItemComboBox1.setSelectedIndex(0);
			mItemComboBox2.setSelectedIndex(0);
			mItemComboBox3.setSelectedIndex(0);
			mItemComboBox4.setSelectedIndex(0);
			mItemComboBox5.setSelectedIndex(0);
			mItemComboBox6.setSelectedIndex(0);
			mItemComboBox7.setSelectedIndex(0);
			mItemComboBox8.setSelectedIndex(0);
			mKitName.setText("");


		}


	}

	public void updatePicture(ImageIcon icon, JLabel frame, String path){
		icon = new ImageIcon("Images/" + path + ".png");
		frame.setIcon(icon);
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
			setToolTipText("Double-Click to edit kit.");
			return renderedLabel;
		}


	}

	public class MouseListener extends MouseAdapter{

		public void mouseClicked(MouseEvent ae) {

			if (ae.getClickCount() == 2) {
				if(ae.getSource() == table){
					JTable target = (JTable)ae.getSource();
					int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					editPanel.updateEditPanel(target, row);
					basePanel1.setVisible(false);
					basePanel2.setVisible(true);
					addPanel.cItemComboBox1.setSelectedIndex(0);
					addPanel.cItemComboBox2.setSelectedIndex(0);
					addPanel.cItemComboBox3.setSelectedIndex(0);
					addPanel.cItemComboBox4.setSelectedIndex(0);
					addPanel.cItemComboBox5.setSelectedIndex(0);
					addPanel.cItemComboBox6.setSelectedIndex(0);
					addPanel.cItemComboBox7.setSelectedIndex(0);
					addPanel.cItemComboBox8.setSelectedIndex(0);
					addPanel.cKitName.setText("");

				}
			}
		}


	}



	public void addKit(KitConfig k){

		Object[] rowData = {k.kitName};
		model.insertRow(model.getRowCount(),rowData);
		table.revalidate();



	}

	public void editKit(KitConfig k){  // edits a kit on the table
		Object[] rowData = {k.kitName};
		for(int i = 0; i < model.getRowCount(); i++){
			if(currentKit.kitName.equals(model.getValueAt(i, 0))){
				model.removeRow(i);
				model.insertRow(i, rowData);
				break;
			}
		}

		table.revalidate();
	}

	public void removeKit(KitConfig k){  // removes a kit from the table
		Object[] rowData = {k.kitName};
		for(int i = 0; i < model.getRowCount(); i++){
			if(k.kitName.equals(model.getValueAt(i, 0))){
				model.removeRow(i);
				break;
			}
		}
		table.revalidate();
	}

	public void refreshTable(){  // refreshes the table
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		for(KitConfig temp : kitManager.getKitConfigList().values()){
			Object[] row = {temp.kitName};
			model.insertRow(model.getRowCount(), row);
		}
	}

	public void refreshAll(){    // This refreshes the screen if any changes are made to parts/kits
		basePanel1.setVisible(true);
		basePanel2.setVisible(false);
		refreshTable();
		addPanel.refreshAddPanel();
		editPanel.refreshEditPanel();
		System.out.println("I refreshed!");

	}

}
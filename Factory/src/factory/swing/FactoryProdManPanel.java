/*

author: Joey Huang
Last edited: 11/29/12 11:55pm


This program contains the user interface panel in the factory production manager client
window. Operations include submitting a new factory order (specifying kit name and quantity),
and displaying the production schedule. When a kit has been added or removed, this panel
shall reflect those changes. Also allows the user to initiate the shut down of the factory. 

 */
package factory.swing;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import factory.managers.*;
import factory.swing.LaneManPanel.LaneNonNormPanel;
import factory.swing.LaneManPanel.LaneNonNormPanel.SliderDetection;


public class FactoryProdManPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -1621739724552837187L;

	JComboBox kitNameBox; // contain String names of saved kit configurations
	JSpinner spinner; // quantity of kits to produce
	JButton submitButton; // submit order
	JTextArea messageBox; // submission confirmations
	FactoryProductionManager factoryProductionManager;
	JTabbedPane tabContainer;
	JPanel newOrderPanel; // new order panel


	JPanel schedulePanel; // production schedule panel
	DefaultTableModel model; 
	JTable table; // table to house production schedule
	JButton stopFactory; // terminate all factory operations - close program

	LaneNonNormPanel nonnormativePanel; // nonnormative controls
	
	public FactoryProdManPanel(FactoryProductionManager fpm) { 
		factoryProductionManager = fpm;
		nonnormativePanel = new LaneNonNormPanel();
		
		newOrderPanel = new JPanel();
		kitNameBox = new JComboBox();
		kitNameBox.setPreferredSize(new Dimension(225,25));

		newOrderPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 1;
		c.gridx = 1;
		c.gridwidth = 4;
		c.gridheight = 1;
		c.insets = new Insets(0,0,25,0); 
		newOrderPanel.add(new JLabel("Factory Production Manager"),c);


		c.insets = new Insets(10,0,10,0);
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_START;
		newOrderPanel.add(new JLabel("Submit New Batch Order:"),c);

		c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		newOrderPanel.add(kitNameBox,c);

		c.anchor = GridBagConstraints.LINE_END;
		c.gridy = 4;
		c.gridx = 2;
		c.gridwidth = 1;
		newOrderPanel.add(new JLabel("Quantity"),c);

		c.gridx = 4;
		SpinnerNumberModel qntyModel = new SpinnerNumberModel(1,1,500,1);
		spinner = new JSpinner(qntyModel);
		newOrderPanel.add(spinner,c);

		c.gridy = 5;
		c.gridx = 4;
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		newOrderPanel.add(submitButton,c);

		c.gridy = 6;
		c.gridx = 1;
		c.gridwidth = 4;
		messageBox = new JTextArea("System Messages\n",20,20);
		newOrderPanel.add(new JScrollPane(messageBox),c);


		// tab 2 production schedule
		schedulePanel = new JPanel();
		model = new DefaultTableModel();

		model.addColumn("No.");
		model.addColumn("Kit");
		model.addColumn("Quantity");

		table=new JTable(model);

		// formatting the widths of table columns
		TableColumn column = null; 
		for (int i = 0; i < 3; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(30); 
			} else if (i ==1) {
				column.setPreferredWidth(160);
			} else {
				column.setPreferredWidth(70);
			}
		}

		schedulePanel.setLayout(new BorderLayout());
		JLabel label = new JLabel("<html><p style=\"margin:30px 0 30px 0;\">Production Schedule</html>");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		schedulePanel.add(label,BorderLayout.NORTH);

		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(300,400));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(260,400));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		container.add(scrollPane);
		schedulePanel.add(container,BorderLayout.CENTER);


		stopFactory = new JButton("Terminate All Operations");
		stopFactory.addActionListener(this);
		schedulePanel.add(stopFactory, BorderLayout.SOUTH);

		tabContainer = new JTabbedPane();
		tabContainer.addTab("New Order",newOrderPanel);
		tabContainer.addTab("Schedule",schedulePanel);
		tabContainer.addTab("Non-Normative",nonnormativePanel);
		tabContainer.setPreferredSize(new Dimension(290,710));
		add(tabContainer);
	}

	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == submitButton) {		// print messages to be displayed in messageBox
			if (kitNameBox.getSelectedItem() == null) {
				messageBox.append("No kit selected.\n");
				messageBox.setCaretPosition(messageBox.getDocument().getLength());
			}
			else {
				String name = (String)kitNameBox.getSelectedItem();
				String qnty = spinner.getValue().toString();

				String set = new String("");
				set = "fpm fcsa cmd makekits " + qnty + " " + name;	
				try {			factoryProductionManager.sendCommand(set);

				Object[] rowData = {model.getRowCount()+1,name,qnty}; // add to production schedule
				model.insertRow(model.getRowCount(),rowData);

				messageBox.append("Order Submitted.\n     Details: " + qnty + " units of " + name + "\n" );
				messageBox.setCaretPosition(messageBox.getDocument().getLength());
				} catch (Exception e) {
					System.out.println("An error occured trying to send new order");
					e.printStackTrace();
				}	
			}
		}
		else if (ae.getSource() == stopFactory) { // message that initiates factory shutdown
			factoryProductionManager.sendCommand("fpm mcs cmd stopfactory");
			factoryProductionManager.quit();
			/*
			messageBox.append("Terminating all operations...\n");
			messageBox.setCaretPosition(messageBox.getDocument().getLength());
			String set = new String("");
			set = "fpm mcs cmd stopfactory";
			try {
				factoryProductionManager.sendCommand(set);
			} catch (Exception e) {
				System.out.println("An error occurred trying to shut down factory");
				e.printStackTrace();
			}
			*/
		}
	}

	public void addKit(String kitName) {	//add new kit name (String) to Jcombobox list - received from kit manager
		kitNameBox.addItem(kitName);	
		kitNameBox.setSelectedIndex(0); //sending back updated kit list to agents
	}

	public void removeKit(String kitName) { // remove kit from list - received from kit manager
		kitNameBox.removeItem(kitName); //sending back updated kit list to agents
	}

	public void kitProduced() { // update kits remaining - decrement by one
		String numstr = (String)model.getValueAt(0,2);
		int num = Integer.parseInt(numstr);
		if(num >0) {
			num--;
			model.setValueAt(Integer.toString(num), 0, 2);
		}

		if (num == 0) {
			model.removeRow(0);
			for (int i = 0; i < model.getRowCount();i++) {
				model.setValueAt((i+1)+"",i,0);
			}
			table.revalidate();
		}
	}

	public void removePart(String partName,ArrayList<String> affectedKits) {
		messageBox.append("Parts Manager has removed a part : " + partName + ".\n");
		messageBox.append("Affected kits will be removed : \n");
		for (String str: affectedKits) {
			kitNameBox.removeItem(str);
			messageBox.append("\t"+str+"\n");
		}
		messageBox.setCaretPosition(messageBox.getDocument().getLength());

	}

	public void addMessage(String msg) {
		messageBox.append(msg + "\n");
		messageBox.setCaretPosition(messageBox.getDocument().getLength());
	}
	
	
	public class LaneNonNormPanel extends JPanel implements ActionListener { // Panel containing non-normative case controls
		//labels
		JLabel badPartsLabel; 
		JLabel diverterSpeedLabel;

		// selectors
		JComboBox laneBoxList;
		JComboBox feederBoxList;

		// containers
		JPanel laneContainer;
		JPanel feederContainer;

		// components / controls
		JButton laneJamButton; // initiates non-normative 2.2: Lane Jam
		JButton diverterButton; // initiates non-normative 2.6: slower diverter
		JButton badPartsButton; // initiates non-normative 3.1:
		JButton blockingRobotButton; // initiates non-normative 3.
		JTextArea messageBox;
		JSlider badPartsPercentage;
		int badPartsPercentageMin;
		int badPartsPercentageMax;
		JSlider diverterSpeed;
		int diverterSpeedMin;
		int diverterSpeedMax;

		public LaneNonNormPanel() { // constructor
			
			badPartsLabel = new JLabel("% Bad Parts");
			laneJamButton = new JButton("Lane Jam");
			diverterButton = new JButton("Diverter Too Slow");
			badPartsButton = new JButton("Bad Parts in Feeder");
			blockingRobotButton = new JButton("Robot Blocking Camera");
			diverterSpeedLabel = new JLabel("Diverter Speed");
			messageBox = new JTextArea("Actions...\n");
			messageBox.setLineWrap(true);
			badPartsPercentageMin = 0;
			badPartsPercentageMax = 100;
			badPartsPercentage = new JSlider(badPartsPercentageMin, badPartsPercentageMax);
			
			// hash table for bad parts percentage slider for easy access
			Hashtable labelTable = new Hashtable();
			for(int i = 0; i <=100; i+=25){
				labelTable.put( new Integer( i ), new JLabel(i + "%") );
			}
			badPartsPercentage.setLabelTable( labelTable );
			badPartsPercentage.setMinorTickSpacing(5);
			badPartsPercentage.setMajorTickSpacing(25);
			badPartsPercentage.setPaintTicks(true);
			badPartsPercentage.setSnapToTicks(true);
			badPartsPercentage.setPaintLabels(true);
			badPartsPercentage.setValue(0);
			
			diverterSpeedMin = 0;
			diverterSpeedMax = 20;
			diverterSpeed = new JSlider(diverterSpeedMin, diverterSpeedMax);
			labelTable = new Hashtable();
			labelTable.put( new Integer(diverterSpeedMin), new JLabel("Slow") );
			labelTable.put( new Integer(diverterSpeedMax), new JLabel("Fast"));
			diverterSpeed.setLabelTable( labelTable );
			diverterSpeed.setMinorTickSpacing(1);
			diverterSpeed.setMajorTickSpacing(5);
			diverterSpeed.setPaintTicks(true);
			diverterSpeed.setSnapToTicks(true);
			diverterSpeed.setPaintLabels(true);
			diverterSpeed.setValue(diverterSpeedMax);
			diverterSpeed.addChangeListener(new SliderDetection());
			laneJamButton.addActionListener(this);
			diverterButton.addActionListener(this);
			badPartsButton.addActionListener(this);
			blockingRobotButton.addActionListener(this);

			laneJamButton.setPreferredSize(new Dimension(200,25));
			diverterButton.setPreferredSize(new Dimension(200,25));
			badPartsButton.setPreferredSize(new Dimension(200,25));
			blockingRobotButton.setPreferredSize(new Dimension(200,25));

			setLayout(new FlowLayout());
			Box boxContainer = Box.createVerticalBox();
			laneBoxList = new JComboBox();
			for (int i = 1; i < 9;i++) {
				laneBoxList.addItem("Lane "+i);
			}
			laneBoxList.setSelectedIndex(0);
			feederBoxList = new JComboBox();
			for (int i = 1; i < 5; i++) {
				feederBoxList.addItem("Feeder "+i);
			}

			feederBoxList.setPreferredSize(new Dimension(200,25));
			laneBoxList.setPreferredSize(new Dimension(200,25));
			laneContainer = new JPanel();
			feederContainer = new JPanel();


			laneContainer.setPreferredSize(new Dimension(250,120));
			feederContainer.setPreferredSize(new Dimension(250,330));
			
			laneContainer.setLayout(new GridBagLayout());
			feederContainer.setLayout(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			
			TitledBorder title = BorderFactory.createTitledBorder("Lanes / Nests");
			laneContainer.setBorder(title);	

			title = BorderFactory.createTitledBorder("Feeders");
			feederContainer.setBorder(title);
			c.gridx = 0;
			c.gridy = 0;
			laneContainer.add(laneBoxList,c);
			c.gridy = 1;
			c.insets = new Insets(10,0,0,0);
			laneContainer.add(laneJamButton,c);
			//laneContainer.add(diverterButton);
			
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			feederContainer.add(feederBoxList,c);
			c.gridy = 1;
			c.insets = new Insets(30,0,0,0);
			feederContainer.add(badPartsLabel,c);
			c.insets = new Insets(0,0,0,0);
			c.gridy = 2;
			feederContainer.add(badPartsPercentage,c);
			c.gridy = 3;
			c.insets = new Insets(3,0,0,0);
			feederContainer.add(badPartsButton,c);
			//feederContainer.add(blockingRobotButton);
			c.insets = new Insets(30,0,0,0);
			c.gridy = 4;
			feederContainer.add(diverterSpeedLabel,c);
			c.insets = new Insets(0,0,0,0);
			c.gridy = 5;
			feederContainer.add(diverterSpeed,c);
			//boxContainer.add(Box.createRigidArea(new Dimension(0,30)));
			JLabel label = new JLabel("Non-Normative Cases");
			label.setAlignmentX(Component.CENTER_ALIGNMENT);
			boxContainer.add(label);
			//boxContainer.add(Box.createRigidArea(new Dimension(0,30)));
			//boxContainer.add(laneBoxList);
			boxContainer.add(Box.createRigidArea(new Dimension(0,20)));
			boxContainer.add(laneContainer);
			boxContainer.add(Box.createRigidArea(new Dimension(0,30)));
			boxContainer.add(feederContainer);
			boxContainer.add(Box.createRigidArea(new Dimension(0,10)));
			JScrollPane scrollPane = new JScrollPane(messageBox);
			scrollPane.setPreferredSize(new Dimension(200,100));
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			boxContainer.add(scrollPane);
			add(boxContainer);
		}

		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == laneJamButton) {
				messageBox.append("Lane jam initated in " + laneBoxList.getSelectedItem() + ".\n");
				messageBox.setCaretPosition(messageBox.getDocument().getLength());
				int lanenum = laneBoxList.getSelectedIndex();
				String set = "fpm va cmd missingparts " + lanenum/2 + " " + lanenum%2;
				try {
					factoryProductionManager.sendCommand(set);
					
					if (lanenum%2 == 0)
						factoryProductionManager.sendCommand("fpm lm cmd jamtoplane " + lanenum/2);
					else
						factoryProductionManager.sendCommand("fpm lm cmd jambottomlane " + lanenum/2);
				} catch (Exception e) {
					System.out.println("An error occurred trying to initiate non-normative case: lane jam.");
				} 
			} else if (ae.getSource() == diverterButton) {
				messageBox.append("Diverter was too slow switching to " + laneBoxList.getSelectedItem() + ".\n");
				messageBox.setCaretPosition(messageBox.getDocument().getLength());
				int feedernum = feederBoxList.getSelectedIndex();
				String set = "fpm va cmd missingparts " + feedernum + " -1";
				try {
					factoryProductionManager.sendCommand(set);
					//TODO: missingparts command appends a nest # (0-7), but slowdiverter is feeder-based (0-3).
					//Figure out a way to determine how to do this.
					factoryProductionManager.sendCommand("fpm fa cmd slowdiverter " + feedernum);
				} catch (Exception e) {
					System.out.println("An error occurred trying initiate non-normative case: slow diverter change.");
				} 
			} else if (ae.getSource() == badPartsButton) {
				messageBox.append("Bad parts found in " + laneBoxList.getSelectedItem() + "'s nest.\n");
				messageBox.setCaretPosition(messageBox.getDocument().getLength());
				int feedernum = feederBoxList.getSelectedIndex();
				String set = "fpm lm cmd badparts " + feedernum + " "+ badPartsPercentage.getValue();
				try {
					factoryProductionManager.sendCommand(set);
				} catch (Exception e) {
					System.out.println("An error occurred trying initiate non-normative case: bad parts in nest.");
				} 
			} else if (ae.getSource() == blockingRobotButton) {
				messageBox.append("A robot is blocking the camera at " + laneBoxList.getSelectedItem() + "'s nest.\n");
				messageBox.setCaretPosition(messageBox.getDocument().getLength());
				int lanenum = laneBoxList.getSelectedIndex();
				String set = "fpm va cmd blockingrobot " + lanenum;
				try {
					factoryProductionManager.sendCommand(set);
				} catch (Exception e) {
					System.out.println("An error occurred trying to initiate non-normative case: robot blocking camera.");
				} 
			}

		}
		
		public class SliderDetection implements ChangeListener{

			@Override
			public void stateChanged(ChangeEvent ce) {
				// TODO Auto-generated method stub
				JSlider source = (JSlider) ce.getSource();
				if(source == diverterSpeed){
					if (!source.getValueIsAdjusting()) {
						int speed = (int)source.getValue();
						// send amplitude to server
						int feederNumber = (Integer)feederBoxList.getSelectedIndex();
						String set = "fpm fa set diverterspeed " + (feederNumber) + " " + (diverterSpeedMax-speed);
						try {
							factoryProductionManager.sendCommand(set);
							factoryProductionManager.sendCommand("fpm fa set diverterspeed " + feederNumber + " " + (speed));
						} catch (Exception e) {
							System.out.println("An error occurred trying to send message to change lane amplitude.");
						} 
						System.out.println("Feeder : " + feederNumber + " going at " + speed);
					}
				}
						
			}

		}
		

	}	
}

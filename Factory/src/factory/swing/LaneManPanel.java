//Stephanie Reagle
//CS 200
// last edited: 11:45pm 11/27/12 by Joey Huang
package factory.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import factory.managers.*;

import javax.swing.*;
import javax.swing.border.*;
public class LaneManPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -4485912622490446254L;
	
	String [] stringLanes = { "1", "2", "3", "4", "5", "6", "7", "8"};
	JComboBox lane = new JComboBox(stringLanes);
	//ImageIcon red = new ImageIcon("red.png");
	JButton redButton = new JButton("Red");
	JButton yellowButton = new JButton("Yellow");
	JButton greenButton = new JButton("Green");
	JButton powerButton = new JButton("Power");
	
	LaneManager laneManager;
	JTabbedPane tabbedPane; 
	JPanel preferencesPanel;
	LaneNonNormPanel nonnormativePanel;
	
	public LaneManPanel(LaneManager l){
		laneManager = l;
		powerButton.addActionListener(this); 
		redButton.addActionListener(this); 
		yellowButton.addActionListener(this); 
		greenButton.addActionListener(this); 
		preferencesPanel = new JPanel();
		nonnormativePanel = new LaneNonNormPanel();
		tabbedPane = new JTabbedPane();
		
	    GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		preferencesPanel.add(lane, c);
		
		c.gridx = 2;
		preferencesPanel.add(redButton, c);
		
		c.gridy = 1;
		preferencesPanel.add(yellowButton, c);
		
		c.gridy = 2; 
		preferencesPanel.add(greenButton, c);
		
		c.gridx = 5;
		c.gridy = 0;
		preferencesPanel.add(powerButton);
		
		tabbedPane.addTab("Preferences", preferencesPanel);
		tabbedPane.addTab("Non-Normative", nonnormativePanel);
		
		this.add(tabbedPane);

	
	}
	
	public int getSelectedLane(){
		return (Integer) lane.getSelectedItem();
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		String set = new String (" ");
		if (ae.getSource() == powerButton){
			set = "lm la lanepowertoggle " + getSelectedLane() + " " + "power";
			laneManager.sendCommand(set);
		}
		else if (ae.getSource() == redButton){
			set = "lm lma set lanevibration "+ getSelectedLane() + " 1";
			laneManager.sendCommand(set);
		}
		else if (ae.getSource() == yellowButton){
			set = "lm lma set lanevibration "+ getSelectedLane() + " 2";
			laneManager.sendCommand(set);
		}
		else if (ae.getSource() == greenButton){
			set = "lm lma set lanevibration "+ getSelectedLane() + " 3";
			laneManager.sendCommand(set);
		}
	}


public class LaneNonNormPanel extends JPanel implements ActionListener {
	JComboBox laneBoxList;
	JPanel partsMissingContainer;
	JPanel partsBadContainer;
	JButton laneJamButton;
	JButton diverterButton;
	JButton badPartsButton;
	JButton blockingRobotButton;
	JTextArea messageBox;
	
	public LaneNonNormPanel() {
		
		laneJamButton = new JButton("Lane Jam");
		diverterButton = new JButton("Diverter Too Slow");
		badPartsButton = new JButton("Bad Parts in Nest");
		blockingRobotButton = new JButton("Robot Blocking Camera");
		messageBox = new JTextArea("Actions...");
		
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
		
		partsMissingContainer = new JPanel();
		partsBadContainer = new JPanel();

	
		partsMissingContainer.setPreferredSize(new Dimension(250,180));
		partsBadContainer.setPreferredSize(new Dimension(250,180));
		
		TitledBorder title = BorderFactory.createTitledBorder("Missing Parts in Nest");
		partsMissingContainer.setBorder(title);	
		
		title = BorderFactory.createTitledBorder("No Good Parts in Nest");
		partsBadContainer.setBorder(title);
		
		partsMissingContainer.add(laneJamButton);
		partsMissingContainer.add(diverterButton);
		
		partsBadContainer.add(badPartsButton);
		partsBadContainer.add(blockingRobotButton);
		boxContainer.add(Box.createRigidArea(new Dimension(0,30)));
		JLabel label = new JLabel("Non-Normative Cases");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		boxContainer.add(label);
		boxContainer.add(Box.createRigidArea(new Dimension(0,30)));
		boxContainer.add(laneBoxList);
		boxContainer.add(Box.createRigidArea(new Dimension(0,30)));
		boxContainer.add(partsMissingContainer);
		boxContainer.add(Box.createRigidArea(new Dimension(0,30)));
		boxContainer.add(partsBadContainer);
		boxContainer.add(Box.createRigidArea(new Dimension(0,10)));
		JScrollPane scrollPane = new JScrollPane(messageBox);
		scrollPane.setPreferredSize(new Dimension(200,100));
		boxContainer.add(scrollPane);
		add(boxContainer);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == laneJamButton) {
			
		} else if (ae.getSource() == diverterButton) {
			
		} 
		
		
	}
	
	
	}	
}

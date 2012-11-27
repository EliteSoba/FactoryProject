//Stephanie Reagle
//CS 200
package factory.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import factory.managers.*;

import javax.swing.*;

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
	JPanel nonnormativePanel;
	
	public LaneManPanel(LaneManager l){
		laneManager = l;
		powerButton.addActionListener(this); 
		redButton.addActionListener(this); 
		yellowButton.addActionListener(this); 
		greenButton.addActionListener(this); 
		preferencesPanel = new JPanel();
		nonnormativePanel = new JPanel();
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
}

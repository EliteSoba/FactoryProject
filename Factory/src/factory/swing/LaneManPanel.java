//Stephanie Reagle
//CS 200
package factory.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import factory.managers.*;

import javax.swing.*;

public class LaneManPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4485912622490446254L;
	String [] stringLanes = { "1", "2", "3", "4", "5", "6", "7", "8"};
	JComboBox lane = new JComboBox(stringLanes);
	//ImageIcon red = new ImageIcon("red.png");
	JButton redButton = new JButton("Red");
	JButton yellowButton = new JButton("Yellow");
	JButton greenButton = new JButton("Green");
	JButton powerButton = new JButton("Power");
	
	LaneManager laneManager;

	public LaneManPanel(){
		powerButton.addActionListener(this); 
		redButton.addActionListener(this); 
		yellowButton.addActionListener(this); 
		greenButton.addActionListener(this); 
		
	    GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		add(lane, c);
		
		c.gridx = 2;
		add(redButton, c);
		
		c.gridy = 1;
		add(yellowButton, c);
		
		c.gridy = 2; 
		add(greenButton, c);
		
		c.gridx = 5;
		c.gridy = 0;
		add(powerButton);

	
	}
	
	public int getSelectedLane(){
		return (Integer) lane.getSelectedItem();
		
	}
	 //main method used for testing
	//do not delete just comment out
	/*
	public static void main (String[] args){
		LaneManPanel l = new LaneManPanel();
		l.repaint();
		l.setVisible(true);
		l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		l.setSize(400,450);
		l.repaint(); 
	}
	*/
	public void setManager(LaneManager l){
		laneManager=l;
	}
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == powerButton){
			laneManager.sendMessage(this.getSelectedLane(), 0, "power");
		}
		else if (ae.getSource() == redButton){
			laneManager.sendMessage(this.getSelectedLane(), 1, "red");
		}
		else if (ae.getSource() == yellowButton){
			laneManager.sendMessage(this.getSelectedLane(), 2, "yellow");
		}
		else if (ae.getSource() == greenButton){
			laneManager.sendMessage(this.getSelectedLane(), 3, "green");
		}
	}	
}

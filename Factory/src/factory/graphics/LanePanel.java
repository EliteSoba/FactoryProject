package factory.graphics;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import factory.client.*;
import factory.*;

/**
 * @author Minh La, Tobias Lee, George Li<p>
 * <b>{@code LanePanel.java}</b> (450x720)<br>
 * This is the graphical component of the Lane Manager.<br>
 * This panel displays just the Lane, Nests, and Feeders
 */

public class LanePanel extends GraphicPanel implements ActionListener{
	
	public LanePanel(JFrame LM) {
		super(/*485/**/);
		isLaneManager = true;
		
		WIDTH = 450;
		
		if (LM instanceof Client)
			am = (Client)LM;
		// Parts robot client
		// Add 8 nests
		nests = new ArrayList<GraphicNest>();	
		for(int i = 0; i < 8; i++)
		{
			GraphicNest newNest = new GraphicNest(35,i*80+50,0,0,0,0,75,75,"Images/nest3x3.png");
			nests.add(newNest);
		}
		lane = new GraphicLaneManager [4];
		for (int i = 0; i < lane.length; i++)
			lane[i] = new GraphicLaneManager(35, 160*i + 50, i, this);
		
		(new Timer(DELAY, this)).start();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		moveLanes();
		
		repaint();
	}
	
	public static void main(String args[]) {
		//For testing
		JFrame f = new JFrame();
		LanePanel lp = new LanePanel(f);
		f.add(lp);
		f.setVisible(true);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//For testing
		Scanner kbr = new Scanner(System.in);
		String command;
		do {
			command = kbr.nextLine();
			switch (command.charAt(0)) {
			case '`': System.exit(0); break;
			}
		} while(command.charAt(0) != '`');
	}
}

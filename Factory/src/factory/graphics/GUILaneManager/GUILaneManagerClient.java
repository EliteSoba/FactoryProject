package factory.graphics.GUILaneManager;

import java.awt.*;

import javax.swing.*;

import factory.Kit.KitState;

public class GUILaneManagerClient extends JFrame {
	
	GUILaneGraphicPanel panel;
	
	public GUILaneManagerClient() {
		panel = new GUILaneGraphicPanel(this);
		this.add(panel, BorderLayout.CENTER);
	}
	
	public static void main(String args[]) {
		//Implements this JFrame
		GUILaneManagerClient lc = new GUILaneManagerClient();
		lc.setVisible(true);
		lc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//lc.setSize(800, 720);
		lc.pack();
	}

}
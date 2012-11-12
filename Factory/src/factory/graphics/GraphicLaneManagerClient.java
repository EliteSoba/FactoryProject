//Minh La

package factory.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class GraphicLaneManagerClient extends JFrame implements ActionListener{
	
	//Panels
	GraphicLaneMenuPanel menuPanel;
	GraphicLaneGraphicPanel graphicPanel;
	
	public GraphicLaneManagerClient(){
		//Declaration of JFrame
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Factory Lane");
		this.setSize(1000,720);
		this.setLayout(new BorderLayout());
		
		//Inserting Panels
		graphicPanel = new GraphicLaneGraphicPanel(this);
		menuPanel = new GraphicLaneMenuPanel(this);
		
		this.add(graphicPanel,BorderLayout.WEST);
		this.add(menuPanel,BorderLayout.EAST);
		//this.add(graphicPanel);
		//this.add(menuPanel);
		
		//repaint();
		new javax.swing.Timer(50,this).start();

	}
	
	public GraphicLaneManager getLane(int index) {
		return graphicPanel.getLane(index);
	}
	
	public static void main(String args[]){
		GraphicLaneManagerClient app = new GraphicLaneManagerClient();
	}

	public void actionPerformed(ActionEvent ae){
		repaint();
	}
	
}

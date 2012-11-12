//Minh La

package factory.graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GraphicPanel extends JPanel{
	
	//LANE MANAGER
	GraphicLaneManagerClient client;
	GraphicLaneManager [] lane;
	
	//KIT MANAGER
	private FrameKitAssemblyManager am; //The JFrame that holds this. Will be removed when gets integrated with the rest of the project
	private GraphicKitBelt belt; //The conveyer belt
	private GraphicKittingStation station; //The kitting station
	private GraphicKittingRobot robot;
	public static final int WIDTH = 280, HEIGHT = 720;
	
	public GraphicPanel(FrameKitAssemblyManager FKAM){
		
		lane = new GraphicLaneManager [4];
		lane[0] = new GraphicLaneManager(20,50);
		lane[1] = new GraphicLaneManager(20,210);
		lane[2] = new GraphicLaneManager(20,370);
		lane[3] = new GraphicLaneManager(20,530);
		
		am = FKAM;
		belt = new GraphicKitBelt(0, 0, this);
		station = new GraphicKittingStation(200, 191, this);
		robot = new GraphicKittingRobot(this, 70, 250);
		(new Timer(50, this)).start();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		this.setPreferredSize(new Dimension(980,720));
		this.setVisible(true);
	}
	

	public void paint(Graphics g){
		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, 600, 720);
		if(lane[0] != null && lane[1] != null){
			lane[0].paintLane(g);
			lane[1].paintLane(g);
			lane[2].paintLane(g);
			lane[3].paintLane(g);
		}
	}
	public GraphicLaneManager getLane(int index) {
		return lane[index];
	}
}
	
	
//Minh La

package factory.graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GraphicLaneGraphicPanel extends GraphicPanel{
	
	GraphicLaneManagerClient client;
	GraphicLaneManager [] lane;
	
	public GraphicLaneGraphicPanel(GraphicLaneManagerClient c){
		this.client = c;
		
		lane = new GraphicLaneManager [4];
		for (int i = 0; i < lane.length; i++)
			lane[i] = new GraphicLaneManager(20, 160*i + 50, i, this);
		
		this.setPreferredSize(new Dimension(600,720));
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
	
	
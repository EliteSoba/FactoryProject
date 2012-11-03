import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GraphicKitAssemblyManager extends JPanel implements ActionListener{
	
	/*GraphicKitAssemblyManager.java (600x600)
	 * This is the graphical display of the Kit Assembly Manager
	 * Currently, this displays a conveyer belt and its kits, and a kitting station
	 * and animates the two.
	 */
	
	//int x; //This was just for testing purposes, uncomment the x-related lines to watch a square move along a sin path
	FrameKitAssemblyManager am; //The JFrame that holds this. Will be removed when gets integrated with the rest of the project
	GraphicKitBelt belt; //The conveyer belt
	GraphicKittingStation station; //The kitting station
	
	public GraphicKitAssemblyManager(FrameKitAssemblyManager FKAM) {
		//Constructor
		//x = 0;
		am = FKAM;
		belt = new GraphicKitBelt(0, 0);
		station = new GraphicKittingStation(400, 130);
		(new Timer(50, this)).start();
		this.setPreferredSize(new Dimension(600, 600));
	}
	
	public void addInKit() {
		//Adds a kit into the factory via conveyer belt
		if (belt.kitin() && !belt.pickUp())
			return;
		belt.inKit();
	}
	
	public void addOutKit() {
		//Sends a kit out of the factory via conveyer belt
		if (belt.kitout())
			return;
		belt.outKit();
	}
	
	public void paint(Graphics g) {
		//Paints all the objects
		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, 600, 600);
		//g.setColor(Color.black);
		//g.fillRect(x, (int)(20*Math.sin(x/90.0*3.1415))+100, 5, 5);
		belt.paint(g);
		belt.moveBelt(5);
		station.paint(g);
		//x += 1;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		//etc.
		repaint();
	}

}

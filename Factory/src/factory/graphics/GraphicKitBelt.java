package factory.graphics;
import java.awt.*;
import javax.swing.*;

public class GraphicKitBelt {
	
	/*GraphicKitBelt.java (50x720) - Tobias Lee
	 * This displays and animates the conveyer belts,
	 * as well as any kits entering/exiting the factory (1 of each)
	 */
	
	private int x, y; //Coordinates where belt is to be drawn
	private int t; //Time for animation purposes
	private GraphicKit kitIn; //The Kit entering the factory
	private GraphicKit kitOut; //The Kit exiting the factory
	private boolean pickUp; //When a Kit has arrived
	private boolean export;
	private GraphicPanel GKAM;
	public static int width = 50, height = FactoryProductionPanel.HEIGHT;
	
	public GraphicKitBelt(int m, int n, GraphicPanel GKAM) {
		//Constructor
		x = m;
		y = n;
		t = 0;
		pickUp = false;
		kitIn = null;
		kitOut = null;
		export = false;
		this.GKAM = GKAM;
	}
	
	public void paint(Graphics g) {
		//Paints the conveyer belt
		
		//Main belt
		g.setColor(new Color(47, 41, 32));
		g.fillRect(x, y, width, height);
		
		//Side exit belt
		/*g.fillRect(x+120, y+300, 100, 100);
		g.fillRect(x+100, y+400, 20, 100);
		g.fillArc(x+20, y+300, 200, 200, 270, 90);*/
		
		//Lines to animate
		//Main conveyer belt
		g.setColor(new Color(224, 224, 205));
		for (int i = t; i < height; i += 50) {
			g.drawLine(x, i, x+width, i);
		}
		
		//Side conveyer belts
		/*for (int i = t + 300; i < 400; i += 50)
			g.drawLine(x+120, i, x+220, i);  
		g.drawLine(x+120, y+400, x+120+(int)(100*Math.cos(3.14*t/100)), y+400+(int)(100*Math.sin(3.14*t/100)));
		if (t < 20)
			g.drawLine(x+120-t, y+400, x+120-t, y+500);
		
		//The diagonal to push kit into ready station
		g.setColor(new Color(27, 21, 12));
		for (int i = 0; i < 5; i++)
			g.drawLine(x, 90+i, x+100, 190+i);
		
		//The ready station for a robot to take
		g.setColor(new Color(10, 5, 0));
		g.fillRoundRect(x+100, y+110, 50, 100, 20, 20);
		g.fillRect(x+100, y+110, 20, 100);*/
		
		//Draws the kit moving into the factory
		drawKitIn(g);
		
		//Draws the kit moving out of the factory
		drawKitOut(g);
	}
	
	public void inKit() {
		//Has a new kit enter the factory
		kitIn = new GraphicKit(x+(width-40)/2, y-80);
		pickUp = false;
	}
	
	public void outKit(GraphicKit kit) {
		//Has a kit exit the factory
		kitOut = kit;
		kitOut.move(x+(width-40)/2, y+305);
	}
	
	public void exportKit() {
		if (kitout())
			export = true;
	}
	
	public void drawKitIn(Graphics g) {
		//Draws the kit that's entering the factory
		if (kitin())
			kitIn.paint(g);
	}
	
	public void drawKitOut(Graphics g) {
		//Draws the kit that's exiting the factory
		if (kitout())
			kitOut.paint(g);
	}
	
	public void moveBelt(int v) {
		//Increments t for animation
		
		if (kitin() && !pickUp || kitout() && export)
		t += v;
		if (t >= 50)
			t = 0;
		
		//Moves the incoming kit along a path
		//TODO: NO MORE PATH
		if (kitin()) {
			if (kitIn.getY() <= y+300)
			kitIn.moveY(v);
			//if (kitIn.getY() >= y+40 && kitIn.getX() <= x+100)
				//kitIn.moveX(v);
			//if (kitIn.getX() >= x+105 && kitIn.getY() >= y+115) {
			if (kitIn.getY() >= y+305) {
				//Kit in completion
				if (!pickUp)
					GKAM.newEmptyKitDone();
				pickUp = true;
			}
			if (kitIn.getY() >= y+height)
				kitIn = null;
		}
		
		//Moves the outgoing kit along a path
		//TODO: NO MORE PATH
		if (kitout() && export) {
			/*if (kitOut.getY() <= y+370)
				kitOut.moveY(v);
			else if (kitOut.getY() <= y+400){
				kitOut.moveX(-v);
				kitOut.moveY(v);
			}
			else if (kitOut.getX() > x+80)
				kitOut.moveX(-v);
			else if (kitOut.getX() > x+25) {
				kitOut.moveX(-v);
				kitOut.moveY(v);
			}
			else*/
				kitOut.moveY(v);
			
			if (kitOut.getY() >= y+height) {
				kitOut = null;
				export = false;
				GKAM.exportKitDone();
			}
		}
	}
	
	public GraphicKit unKitIn() {
		//Removes kitIn
		GraphicKit temp = kitIn;
		kitIn = null;
		pickUp = false;
		return temp;
	}
	
	public boolean kitin() {
		//Returns if a kit is entering the factory
		return kitIn != null;
	}
	
	public boolean kitout() {
		//Returns if a kit is exiting the factory
		return kitOut != null;
	}
	
	public boolean pickUp() {
		//Returns if a kit that's entering the factory is ready to be picked up
		return pickUp;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
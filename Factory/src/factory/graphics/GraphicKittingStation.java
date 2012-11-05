package factory.graphics;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GraphicKittingStation {
	
	/*GraphicKittingStation.java (50x300) - Tobias Lee
	 * This does nothing right now.
	 * Eventually, it will be something like a graphical representation of the Kitting Station
	 */
	
	private int x, y; //Coordinates
	public static final int MAX_KITS = 2; //Max number of kits this station can hold
	private GraphicKit[] kits; //The kits it is holding
	private GraphicKit check; //The kit in the inspection area
	private int timer;
	GraphicKitAssemblyManager GKAM;
	
	public GraphicKittingStation(int x, int y, GraphicKitAssemblyManager GKAM) {
		//Constructor
		this.x = x;
		this.y = y;
		timer = -1;
		kits = new GraphicKit[MAX_KITS];
		this.GKAM = GKAM;
		for (int i = 0; i < MAX_KITS; i++)
			kits[i] = null;
		check = null;
	}
	
	public void paint(Graphics g) {
		//Draws the Kitting Station
		g.setColor(new Color(100, 50, 0));
		g.fillRoundRect(x, y, 50, 300, 20, 20);
		
		g.setColor(Color.black);
		g.fillOval(x-15, y+320, 80, 80);
		g.setColor(Color.white);
		g.drawString("TRASH", x+5, y+365);
		drawKits(g);
		
		//timer = 0;
		if (timer != -1) {
			timer++;
			g.setColor(Color.white);
			g.fillOval(x-5*timer+25, y+250-5*timer, 10*timer, 10*timer);
			if (timer == 5) {
				timer = -1;
				GKAM.pictureDone();
			}
		}
	}
	
	public void revalidateKits() {
		//Repositions kits to set positions
		for (int i = 0; i < MAX_KITS; i++)
			if (kits[i] != null)
				kits[i].move(x+5, y+100*i+10);
		if (check != null)
			check.move(x+5, y+210);
	}
	
	public void drawKits(Graphics g) {
		//Draws the kits
		revalidateKits();
		
		for (int i = 0; i < MAX_KITS; i++)
			if (kits[i] != null)
				kits[i].paint(g);
		if (check != null)
			check.paint(g);
	}
	
	/**DO NOT USE*/
	public boolean addKit(GraphicKit kit) {
		//Generically adds kit to slot 1 if available, slot 2 if 1 is open and 2 isn't, and returns false if both are full
		if (kits[0] == null)
			kits[0] = kit;
		else if (kits[1] == null)
			kits[1] = kit;
		else
			return false;
		return true;
	}
	
	public boolean addKit(GraphicKit kit, int index) {
		//Adds kit to index (0 or 1). Returns true if index is occupied, false otherwise
		if (index != 0 && index != 1) //!(index == 0 || index == 1)
			return addKit(kit);
		if (kits[index] == null)
			kits[index] = kit;
		else
			return false;
		return true;
	}
	
	public boolean addCheck(GraphicKit kit) {
		if (check != null)
			return false;
		check = kit;
		return true;
	}
	
	public GraphicKit getKit(int index) {
		//Gets the kit at the index
		return kits[index];
	}
	
	public GraphicKit getCheck() {
		return check;
	}
	
	public GraphicKit popKit(int index) {
		//Removes the kit at the index for transfer
		GraphicKit temp = kits[index];
		kits[index] = null;
		return temp;
	}
	
	public GraphicKit popCheck() {
		//Removes the kit at the checking station for transfer
		GraphicKit temp = check;
		check = null;
		return temp;
	}
	
	public boolean hasKits() {
		//Returns if the station has any kits
		return kits[0] != null || kits[1] != null;
	}
	
	public boolean maxed() {
		//Returns if the station is at maximum capacity or not
		return kits[0] != null && kits[1] != null;
	}
	
	public boolean hasCheck() {
		//If the station has a kit in the checking location
		return check != null;
	}
	
	public void dropCheck() {
		//Rather unnecessary, but kind of nice for readability
		check = null;
	}
	
	public void checkKit() {
		//Camera flashing
		timer = 0;
	}
}

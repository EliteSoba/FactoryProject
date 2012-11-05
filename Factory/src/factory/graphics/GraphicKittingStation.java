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
	public static final int MAX_KITS = 3; //Max number of kits this station can hold
	private ArrayList<GraphicKit> kits; //The kits it is holding
	
	public GraphicKittingStation(int x, int y) {
		//Constructor
		this.x = x;
		this.y = y;
		kits = new ArrayList<GraphicKit>();
	}
	
	public void paint(Graphics g) {
		//Draws the Kitting Station
		g.setColor(new Color(100, 50, 0));
		g.fillRoundRect(x, y, 50, 300, 20, 20);
		
		drawKits(g);
	}
	
	public void revalidateKits() {
		//Repositions kits to set positions
		for (int i = 0; i < kits.size(); i++) {
			kits.get(i).move(x+5, y+100*i+10);
		}
	}
	
	public void drawKits(Graphics g) {
		//Draws the kits
		revalidateKits();
		for (int i = 0; i < kits.size(); i++) {
			kits.get(i).paint(g);
		}
	}
	
	public void addKit(GraphicKit kit) {
		//Adds a kit to the station
		if (kits.size() >= MAX_KITS)
			return;
		//kit.move(x+10, y+100*kits.size()+10);
		kits.add(kit);
	}
	
	public GraphicKit getKit(int index) {
		//Gets the kit at the index
		return kits.get(index);
	}
	
	public GraphicKit popKit(int index) {
		//Removes the kit at the index for transfer
		return kits.remove(index);
	}
	
	public boolean hasKits() {
		//Returns if the station has any kits
		return kits.size() != 0;
	}
	
	public boolean maxed() {
		//Returns if the station is at maximum capacity or not
		return kits.size() == MAX_KITS;
	}
	
}

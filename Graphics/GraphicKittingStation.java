import java.awt.*;
import javax.swing.*;

public class GraphicKittingStation {
	
	/*GraphicKittingStation.java
	 * This does nothing right now.
	 * Eventually, it will be something like a graphical representation of the Kitting Station
	 */
	
	private int x, y; //Coordinates
	
	public GraphicKittingStation(int x, int y) {
		//Constructor
		this.x = x;
		this.y = y;
	}
	
	public void paint(Graphics g) {
		//Draws the Kitting Station
		g.setColor(new Color(100, 50, 0));
		g.fillRoundRect(x, y, 50, 300, 20, 20);
	}
	
}

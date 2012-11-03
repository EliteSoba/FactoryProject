package factory.graphics;
import java.awt.*;
import javax.swing.*;

public class GraphicKit {
	
	/*GraphicKit.java (40x80)
	 * This class paints a graphical representation of a kit
	 */
	
	private int x, y; //coordinates at which it's painted
	public static int width = 40, height = 80; //Static size of a Kit
	
	public GraphicKit(int m, int n) {
		//Constructor
		x = m;
		y = n;
	}
	
	public void paint(Graphics g) {
		//Paints the Kit
		g.setColor(new Color(241, 198, 67));
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		g.drawRect(x, y, width, height/4);
		g.drawRect(x, y+height/4, width, height/4);
		g.drawRect(x, y+height/2, width, height/4);
		g.drawRect(x, y+3*height/4, width, height/4);
		g.drawLine(x+width/2, y, x+width/2, y+height);
	}
	
	public void move(int v) {
		//Moves down v units
		y += v;
	}
	
	public void move(int x, int y) {
		//Moves to the given x and y coordinates
		this.x = x;
		this.y = y;
	}
	
	public void moveX(int v) {
		//Moves right v units
		x += v;
	}
	
	public int getX() {
		//Returns x
		return x;
	}
	
	public int getY() {
		//Returns y
		return y;
	}
	
}

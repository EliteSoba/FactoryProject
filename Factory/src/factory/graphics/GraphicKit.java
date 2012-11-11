package factory.graphics;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GraphicKit {
	
	/*GraphicKit.java (40x80) - Tobias Lee
	 * This class paints a graphical representation of a kit
	 */
	
	private int x, y; //coordinates at which it's painted
	private int direction; //2,4,6,8 direction of kit
	private ArrayList<GraphicItem> items;
	public static int width = 40, height = 80; //Static size of a Kit
	
	public GraphicKit(int m, int n) {
		//Constructor
		x = m;
		y = n;
		direction = 4;
		items = new ArrayList<GraphicItem>();
	}
	
	public void paint(Graphics g) {
		//Paints the Kit
		if (direction == 4 || direction == 6) {
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
		else if (direction == 2 || direction == 8) {
			g.setColor(new Color(241, 198, 67));
			g.fillRect(x, y, height, width);
			g.setColor(Color.black);
			g.drawRect(x, y, height, width);
			g.drawRect(x, y, height/4, width);
			g.drawRect(x+height/4, y, height/4, width);
			g.drawRect(x+height/2, y, height/4, width);
			g.drawRect(x+3*height/4, y, height/4, width);
			g.drawLine(x, y+width/2, x+height, y+width/2);
		}
		paintItems(g);
	}
	
	public void paintItems(Graphics g) {
		validateItems();
		for (int i = 0; i < items.size(); i++) {
			items.get(i).paint(g);
		}
	}
	
	public void moveY(int v) {
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
	
	public void setX(int x) {
		//Sets x
		this.x = x;
	}
	
	public int getY() {
		//Returns y
		return y;
	}
	
	public void setY(int y) {
		//Sets y
		this.y = y;
	}
	
	public void setDirection(int d) {
		direction = d;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void addItem(GraphicItem item) {
		items.add(item);
	}
	
	public GraphicItem getItem(int index) {
		if (index >= items.size())
			return items.get(items.size()-1);
		if (index < 0)
			return items.get(0);
		return items.get(index);
	}
	
	public void validateItems() {
		for (int i = 0; i < items.size(); i++) {
			switch (direction) {
			case 4:	
			case 6:	items.get(i).setX(x+(i%2)*21+1);
					items.get(i).setY(y+(i/2)*21+1);
					break;
			case 2:	
			case 8:	items.get(i).setX(x+(i/4)*21+1);
					items.get(i).setY(y+(i%4)*21+1);
			}
		}
	}
	
}

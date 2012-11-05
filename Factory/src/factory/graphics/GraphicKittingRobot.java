package factory.graphics;
import java.awt.*;
import javax.swing.*;

public class GraphicKittingRobot {
	
	/*GraphicKittingRobot.java (113x115) - Tobias Lee
	 * The graphical representation of the kitting robot.
	 * Currently has functionality to move from conveyer belt to kitting station
	 */
	
	private int x, y; //Coordinates
	private int startX, startY; //Original coordinates. startX is the only important one to make movement a bit easier
	private int beltX, beltY; //The location of the conveyer belt
	private int stationX, stationY; //The location of the kitting station
	private GraphicKit kit; //The kit the robot is holding. I'll draw it properly later when I actually have items to draw
	private int direction; //For now I'm using directions 2 = down, 4 = left, 6 = right, 8 = up. I'll probably change this in the future but this is the way I'm used to
	ImageIcon robot[]; //The icons for the robot with robot[direction] showing the robot facing the direction, and robot[direction-1] shows it facing that direction holding an empty kit
	JPanel GKAM; //The Panel to allow the drawing of ImageIcons
	
	public GraphicKittingRobot(JPanel GKAM, int x, int y) {
		//Constructor
		this.GKAM = GKAM;
		this.x = x;
		this.y = y;
		startX = x;
		startY = y;
		beltX = 0;
		beltY = 0;
		stationX = 400;
		stationY = 130;
		direction = 2;
		kit = null;
		robot = new ImageIcon[9];
		for (int i = 1; i < 9; i++)
			robot[i] = new ImageIcon("Images/Robot"+i+".gif");
	}
	
	public void paint(Graphics g) {
		//Paints the robot
		//robot[direction-(kitted()?1:0)].paintIcon(GKAM, g, x, y);
		int index = direction - (kitted()?1:0);
		g.drawImage(robot[index].getImage(), x, y, null);
		//robot[direction].paintIcon(GKAM, g, x, y);
		//drawKit(g);
	}
	
	public void drawKit(Graphics g) {
		//Unused for now. Later, will paint the kit in the robot's hands
		//if (kitted())
			//kit.paint(g);
		if (!kitted())
			return;
		if (direction == 4 || direction == 6)
			kit.setY(y+18);
		else
			kit.setX(x+18);
	}
	
	public boolean kitted() {
		//Returns if the robot is holding a kit or not
		return kit != null;
	}
	
	public void setKit(GraphicKit k) {
		//Sets the kit the robot is holding
		kit = k;
	}
	
	public GraphicKit unkit() {
		//Removes kit from robot. Returns to allow transfer from one agent to another
		GraphicKit temp = kit;
		kit = null;
		return temp;
	}
	
	//Movement patterns (To/From indicate whether a kit is arriving FROM or moving TO the next location
	public void moveToStartX(int v) {
		//Moves back to original x coordinate
		if (x > startX)
			moveX(-v);
		else if (x < startX)
			moveX(v);
	}
	
	public boolean moveFromBelt(int v) {
		//Moves to Conveyer Belt to fetch an empty kit. Returns true when task is complete
		if (x != startX && y != beltY+105)
			moveToStartX(v);
		else if (y > beltY+105)
			moveY(-v);
		else if (y < beltY+105)
			moveY(v);
		else if (x > beltX+120)
			moveX(-v);
		else
			return true;
		return false;
	}
	
	public boolean moveToStation(int v) {
		//Moves to Kitting Station to place empty kit. Returns true when task is complete
		if (x != startX && y != stationY+30)
			moveToStartX(v);
		else if (y > stationY+30)
			moveY(-v);
		else if (y < stationY+30)
			moveY(v);
		else if (x < stationX-70)
			moveX(v);
		else
			return true;
		return false;
	}
	
	public boolean moveFromStation(int v) {
		//Same thing as moveToStation, actually.
		return moveToStation(v);
	}
	
	public boolean moveToBelt(int v) {
		//Moves to belt to hand in finished product. Returns true when task is complete
		if (x != startX && y != beltY+285)
			moveToStartX(v);
		else if (y > beltY+285)
			moveY(-v);
		else if (y < beltY+285)
			moveY(v);
		else if (x > beltX+160)
			moveX(-v);
		else
			return true;
		return false;
	}
	
	//The following are all getters and setters
	public void moveX(int v) {
		x += v;
		direction = v > 0 ? 6 : 4;
	}
	public void moveY(int v) {
		y += v;
		direction = v > 0 ? 2 : 8;
	}
	public void moveTo(int x, int y) {
		setX(x);
		setY(y);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setBelt(int bx, int by) {
		beltX = bx;
		beltY = by;
	}
	public int getBeltX() {
		return beltX;
	}
	public void setBeltX(int beltX) {
		this.beltX = beltX;
	}
	public int getBeltY() {
		return beltY;
	}
	public void setBeltY(int beltY) {
		this.beltY = beltY;
	}
	public void setStation(int sx, int sy) {
		stationX = sx;
		stationY = sy;
	}
	public int getStationX() {
		return stationX;
	}
	public void setStationX(int stationX) {
		this.stationX = stationX;
	}
	public int getStationY() {
		return stationY;
	}
	public void setStationY(int stationY) {
		this.stationY = stationY;
	}
	public int getDirection() {
		return direction;
	}
	
}
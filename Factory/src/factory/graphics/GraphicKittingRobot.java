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
	GraphicPanel GKAM; //The Panel to allow the drawing of ImageIcons
	GraphicKitBelt belt;
	GraphicKittingStation station;

	//Movement booleans
	private boolean fromBelt;
	private boolean toStation;
	private boolean toBelt;
	private boolean toCheck;
	private boolean checkKit;
	private boolean fromCheck;
	private boolean purgeKit;
	private boolean toDump;
	private int stationTarget;
	
	public GraphicKittingRobot(GraphicPanel GKAM, GraphicKitBelt belt, GraphicKittingStation station, int x, int y) {
		//Constructor
		this.GKAM = GKAM;
		this.belt = belt;
		this.station = station;
		this.x = x;
		this.y = y;
		startX = x;
		startY = y;
		beltX = belt.getX()-belt.getX()%5;
		beltY = belt.getY()-belt.getY()%5;
		stationX = station.getX()-station.getX()%5;
		stationY = station.getY()-station.getY()%5;
		direction = 2;
		kit = null;
		robot = new ImageIcon[9];
		for (int i = 1; i < 9; i++)
			robot[i] = new ImageIcon("Images/Robot"+i+".gif");
		
		fromBelt = false;
		toStation = false;
		toBelt = false;
		toCheck = false;
		checkKit = false;
		fromCheck = false;
		purgeKit = false;
		toDump = false;
		stationTarget = 0;
	}
	
	public void paint(Graphics g) {
		//Paints the robot
		//int index = direction - (kitted()?1:0);
		g.drawImage(robot[direction].getImage(), x, y, null);
		//robot[index].paintIcon(GKAM, g, x, y);
		drawKit(g);
	}
	
	public void drawKit(Graphics g) {
		//Paints the kit in the robot's hands
		if (!kitted())
			return;
		switch (direction) {
		case 2:
			kit.setX(x+16);
			kit.setY(y+74);
			break;
		case 4:
			kit.setX(x);
			kit.setY(y+16);
			break;
		case 6:
			kit.setX(x+74);
			kit.setY(y+16);
			break;
		case 8:
			kit.setX(x+16);
			kit.setY(y);
			break;
		}
		kit.setDirection(direction);
		kit.paint(g);
		
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
	
	public boolean moveTo(int tX, int tY, int v) {
		//Generic move function
		if (x != startX && y != tY)
			moveToStartX(v);
		else if (y > tY)
			moveY(-v);
		else if (y < tY)
			moveY(v);
		else if (x > tX)
			moveX(-v);
		else if (x < tX)
			moveX(v);
		else
			return true;
		return false;
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
		return moveTo(beltX+5, beltY+290, v);
	}
	
	public boolean moveFromStation(int v, int target) {
		//Same as moveToStation, but for retrieving a kit
		return moveToStation(v, target);
	}
	
	public boolean moveToStation(int v, int target) {
		//Moves to the Kitting Station to put a kit in
		if (target != 0 && target != 1)
			return true;
		if (target == 0)
			return moveToStation1(v);
		return moveToStation2(v);
	}
	
	public boolean moveToStation1(int v) {
		//Moves to slot 1
		return moveTo(stationX-70, stationY-5, v);
	}
	
	public boolean moveToStation2(int v) {
		//Moves to slot 2
		return moveTo(stationX-70, stationY+95, v);
	}
	
	public boolean moveToCheck(int v) {
		//Moves to inspection station
		return moveTo(stationX-70, stationY+195, v);
	}
	
	public boolean moveToTrash(int v) {
		//Moves to trash
		return moveTo(stationX-70, stationY+305, v);
	}
	
	public boolean moveToBelt(int v) {
		//Moves to Conveyer belt for export
		//return moveTo(beltX+160, beltY+285, v);
		return moveFromBelt(v);
	}

	public void moveRobot(int v) {
		//Moving path control into separate method
		
		//From belt to station
		if (fromBelt) {
			if (moveFromBelt(v)) {
				fromBelt = false;
				setKit(belt.unKitIn());
				toStation = true;
			}
		}
		else if (toStation) {
			if (moveToStation(v, stationTarget)) {
				toStation = false;
				station.addKit(unkit(), stationTarget);
				GKAM.moveEmptyKitToSlotDone();
			}
		}
		
		//From station to inspection
		else if (checkKit) {
			if (moveFromStation(v, stationTarget)) {
				checkKit = false;
				setKit(station.popKit(stationTarget));
				toCheck = true;
			}
		}
		else if (toCheck) {
			if (moveToCheck(v)) {
				toCheck = false;
				station.addCheck(unkit());
				GKAM.moveKitToInspectionDone();
			}
		}
		
		//From inspection to belt
		else if (fromCheck) {
			if (moveToCheck(v)) {
				fromCheck = false;
				setKit(station.popCheck());
				toBelt = true;
			}
		}
		else if (toBelt) {
			if (moveToBelt(v)) {
				toBelt = false;
				belt.outKit(unkit());
				GKAM.exportKitDone();
			}
		}
		
		//From inspection to trash
		else if (purgeKit) {
			if (moveToCheck(v)) {
				purgeKit = false;
				setKit(station.popCheck());
				toDump = true;
			}
		}
		else if (toDump) {
			if (moveToTrash(v)) {
				toDump = false;
				unkit();
				GKAM.dumpKitAtInspectionDone();
			}
		}
		
		else
			moveToStartX(v);
	}
	
	//The following are all getters and setters
	public void moveX(int v) {
		x += v;
		direction = v > 0 ? 6 : 4;
		if (kitted())
			kit.setDirection(direction);
	}
	public void moveY(int v) {
		y += v;
		direction = v > 0 ? 2 : 8;
		if (kitted())
			kit.setDirection(direction);
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
	public boolean getFromBelt() {
		return fromBelt;
	}
	public void setFromBelt(boolean fromBelt) {
		this.fromBelt = fromBelt;
	}
	public boolean getToStation() {
		return toStation;
	}
	public void setToStation(boolean toStation) {
		this.toStation = toStation;
	}
	public boolean getToBelt() {
		return toBelt;
	}
	public void setToBelt(boolean toBelt) {
		this.toBelt = toBelt;
	}
	public boolean getToCheck() {
		return toCheck;
	}
	public void setToCheck(boolean toCheck) {
		this.toCheck = toCheck;
	}
	public boolean getFromCheck() {
		return fromCheck;
	}
	public void setFromCheck(boolean fromCheck) {
		this.fromCheck = fromCheck;
	}
	public boolean getCheckKit() {
		return checkKit;
	}
	public void setCheckKit(boolean checkKit) {
		this.checkKit = checkKit;
	}
	public boolean getPurgeKit() {
		return purgeKit;
	}
	public void setPurgeKit(boolean purgeKit) {
		this.purgeKit = purgeKit;
	}
	public boolean getToDump() {
		return toDump;
	}
	public void setToDump(boolean toDump) {
		this.toDump = toDump;
	}
	public int getStationTarget() {
		return stationTarget;
	}
	public void setStationTarget(int stationTarget) {
		this.stationTarget = stationTarget;
	}
	
}
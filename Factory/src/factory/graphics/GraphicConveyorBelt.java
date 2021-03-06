package factory.graphics;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Tobias Lee <p>
 * <b>{@code GraphicConveyorBelt.java}</b> (50x720) <br>
 * This displays and animates the conveyor belts,
 * as well as any kits entering/exiting the factory (1 of each)
 */

public class GraphicConveyorBelt {
	
	/**The x coordinate of the Conveyor Belt*/
	private int x;
	/**The y coordinate of the Conveyor Belt*/
	private int y;
	/**The current time in the animation*/
	private int t;
	/**The Kit entering the Factory*/
	private GraphicKit kitIn;
	/**The Kit exiting the Factory*/
	private GraphicKit kitOut;
	/**If a Kit is ready to be picked up or not*/
	private boolean pickUp;
	/**If a Kit is being exported out*/
	private boolean export;
	/**The GraphicPanel for intercomponent communication*/
	private GraphicPanel GP;
	/**The width of the Conveyor Belt*/
	public static int width = 50;
	/**The height of the Conveyor Belt*/
	public static int height = 720;
	
	/** Animation for conveyor belt */
	public ArrayList<Image> conveyorBeltAnimationImages;		// collection of images
	public int conveyorBeltAnimationCounter;					// counter
	public int conveyorBeltAnimationSpeed;						// animation speed
	
	/**
	 * Creates a Conveyor Belt at the given x and y coordinates
	 * @param x The initial x coordinate of the Conveyor Belt
	 * @param y The initial y coordinate of the Conveyor Belt
	 * @param GP The GraphicPanel to communicate with
	 */
	public GraphicConveyorBelt(int x, int y, GraphicPanel GP) {
		this.x = x;
		this.y = y;
		t = 0;
		pickUp = false;
		kitIn = null;
		kitOut = null;
		export = false;
		this.GP = GP;
		
		conveyorBeltAnimationImages = GraphicAnimation.loadAnimationFromFolder("Images/conveyorBelt/", 0, ".png");
		//System.out.println(conveyorBeltAnimationImages.size());
		conveyorBeltAnimationCounter = 0;
		conveyorBeltAnimationSpeed = 5;
	}
	
	/**
	 * Paints the Conveyor Belt
	 * @param g The specified graphics window
	 */
	public void paint(Graphics g) {
		// Animates conveyor belt
		g.drawImage(conveyorBeltAnimationImages.get(conveyorBeltAnimationCounter/conveyorBeltAnimationSpeed), 0, 0, null);
		if(kitin() && !pickUp || kitout() && export)		// only animates if the kit is moving
		{
			conveyorBeltAnimationCounter++;
			if(conveyorBeltAnimationCounter >= conveyorBeltAnimationImages.size()*conveyorBeltAnimationSpeed)
				conveyorBeltAnimationCounter = 0;
		}
		
		//Draws the kit moving into the factory
		drawKitIn(g);
		
		//Draws the kit moving out of the factory
		drawKitOut(g);
	}
	
	/**
	 * Sends a new Kit down the Conveyor Belt into the Factory
	 */
	public void inKit() {
		kitIn = new GraphicKit(x+(width-40)/2, y-80);
		pickUp = false;
	}
	
	/**
	 * Moves a Kit onto the Conveyor Belt to be exported out of the Factory
	 * @param kit The Kit to be exported
	 */
	public void outKit(GraphicKit kit) {
		kitOut = kit;
		if (kitOut == null)
			return;
		kitOut.move(x+(width-40)/2, y+300);
	}
	
	/**
	 * Moves a Kit ready to be exported out of the factory
	 * @see #outKit(GraphicKit)
	 */
	public void exportKit() {
		if (kitout())
			export = true;
		else
			GP.exportKitDone();
	}
	
	/**
	 * Draws the incoming Kit
	 * @param g The specified graphics window
	 */
	public void drawKitIn(Graphics g) {
		if (kitin())
			kitIn.paint(g);
	}
	
	/**
	 * Draws the outgoing Kit
	 * @param g The specified graphics window
	 */
	public void drawKitOut(Graphics g) {
		if (kitout())
			kitOut.paint(g);
	}
	
	/**
	 * Moves the belt along at velocity v
	 * @param v The velocity at which the belt moves
	 */
	public void moveBelt(int v) {
		//Only animates if kit is being imported/exported
		if (kitin() && !pickUp || kitout() && export)
			t += v;
		if (t >= 50)
			t = 0;
		
		//Moves the incoming kit along a path
		if (kitin()) {
			if (kitIn.getY() <= y+295)
				kitIn.moveY(v);
			if (kitIn.getY() > y+295) {
				//Kit import complete
				if (!pickUp)
					GP.newEmptyKitDone(); //Messages GraphicPanel about task completion 
				pickUp = true;
			}
		}
		
		//Moves the outgoing kit along a path
		if (kitout() && export) {
				kitOut.moveY(v);
			if (kitOut.getY() >= y+height) {
				//Kit export complete
				kitOut = null;
				export = false;
				GP.exportKitDone(); //Messages GraphicPanel about task completion
			}
		}
	}
	
	/**
	 * Removes the incoming Kit from the belt
	 * @return The removed GraphicKit
	 */
	public GraphicKit unKitIn() {
		GraphicKit temp = kitIn;
		kitIn = null;
		pickUp = false;
		return temp;
	}
	
	/**
	 * Checks if there is an incoming Kit on the belt
	 * @return {@code true} if there is an incoming Kit; {@code false} otherwise
	 */
	public boolean kitin() {
		return kitIn != null;
	}
	
	/**
	 * Checks if there is an outgoing Kit on the belt
	 * @return {@code true} if there is an outgoing Kit; {@code false} otherwise
	 */
	public boolean kitout() {
		return kitOut != null;
	}
	
	/**
	 * Checks if the incoming Kit is ready to be picked up
	 * @return {@code true} if the Kit is ready to be picked up; {@code false} otherwise
	 */
	public boolean pickUp() {
		return pickUp;
	}
	
	/**
	 * Gets the x coordinate of this Conveyor Belt
	 * @return The x coordinate of this Conveyor Belt
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the y coordinate of this Conveyor Belt
	 * @return The y coordinate of this Conveyor Belt
	 */
	public int getY() {
		return y;
	}
	
}
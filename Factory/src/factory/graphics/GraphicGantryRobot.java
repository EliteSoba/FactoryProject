package factory.graphics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import factory.Part;

/**
 * @author George Li<p>
 * <b>{@code GraphicGantryRobot.java}</b><br>
 * The code for controlling the gantry robot.<br>
 */

class GraphicGantryRobot extends GraphicRobot
{
	int destinationFeeder;		// The feeder the gantry robot should move to (zero-based indexing)
	GraphicBin bin;				// The bin the gantry robot is carrying.
	boolean hasBin;				// Whether or not the gantry robot has a bin.
	Image binImage;				// Image of the bin.
	String partPath;			// Filepath of the bin image.
	
	/**
	 * @deprecated Use GraphicGantryRobot(...) constructor
	 */
	public GraphicGantryRobot()
	{
	}
	
	/**
	 * Initializes all variables of gantry robot upon instantiation.
	 * @param init_x Initial x position.
	 * @param init_y Initial y position.
	 * @param init_theta Initial direction.
	 * @param init_dx Initial speed in x direction.
	 * @param init_dy Initial speed in y direction.
	 * @param init_dtheta Initial rotation speed.
	 * @param init_imageWidth Initial image width.
	 * @param init_imageHeight Initial image height.
	 * @param init_imagePath Initial image path.
	 */
	public GraphicGantryRobot(int init_x, int init_y, int init_theta, int init_dx, int init_dy, int init_dtheta, int init_imageWidth, int init_imageHeight, String init_imagePath)
	{
		// Initialize properties of the robot to passed-in and default values
		bin = null;
		hasBin = false;
		state = 0;					// 0 = idle, 1 = going to bin pickup, 2 = arrived at bin pickup, 3 = going to feeder (dropoff), 4 = arrived at feeder (dropoff),
									// 5 = arrived at feeder (pickup), 6 = arrived at feeder (pickup)
		destinationFeeder = -1;
		x = init_x;
		y = init_y;
		fx = init_x;
		fy = init_y;
		ftheta = 0;
		theta = init_theta;
		dx = init_dx;
		dy = init_dy;
		dtheta = init_dtheta;
		imageWidth = init_imageWidth;
		imageHeight = init_imageHeight;
		image = Toolkit.getDefaultToolkit().getImage(init_imagePath);
		binImage = Toolkit.getDefaultToolkit().getImage("Images/binCrate.png");
		// The parts robot will prioritize: up, right, left, down
		movementCheckingOrders = new int[4];
		movementCheckingOrders[0] = 0;
		movementCheckingOrders[1] = 1;
		movementCheckingOrders[2] = 3;
		movementCheckingOrders[3] = 2;
		partPath = "Images/eyesItem.png";
	}
	
	/**
	 * Sets the destination feeder as provided by the back-end.
	 * @param init_destinationFeeder
	 */
	public void setDestinationFeeder(int init_destinationFeeder)
	{
		destinationFeeder = init_destinationFeeder;
	}
	
	/**
	 * Gets the feeder the gantry robot should move to (zero-based indexing).
	 * @return destinationFeeder
	 */
	public int getDestinationFeeder()
	{
		return destinationFeeder;
	}
	
	/**
	 * Checks if gantry robot has a bin.
	 * @return hasBin
	 */
	public boolean hasBin()
	{
		return hasBin;
	}
	
	/**
	 * Sets the image path of the part being carried in the bin.
	 * @param path
	 */
	public void setPartPath(String path)
	{
		partPath = path;
	}
	
	/**
	 * Gets the image path of the part being carried in the bin.
	 * @return partPath
	 */
	public String getPartPath()
	{
		return partPath;
	}
	
	/**
	 * Sets the gantry robot's bin to the provided bin.
	 * @param init_bin
	 */
	public void giveBin(GraphicBin init_bin)
	{
		bin = init_bin;
		hasBin = true;
	}
	
	/**
	 * Gets the gantry robot's bin.
	 * @return bin
	 */
	public GraphicBin popBin()
	{
		hasBin = false;
		return bin;
	}
	
	/**
	 * Paints the robot (via superclass function) and paints the items the robot is carrying in its inventory.
	 */
	public void paint(Graphics g)
	{
		// Draw the robot
		super.paint(g);
		// Draw the bin if it has one
		if(hasBin)
			g.drawImage(binImage, x+imageWidth-5, y+10, 50, 95, null);
	}
}
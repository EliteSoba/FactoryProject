package factory.graphics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import factory.Part;

class GraphicGantryRobot extends GraphicRobot
{
	int destinationFeeder;
	GraphicBin bin;
	boolean hasBin;
	Image binImage;
	String partPath;
	
	public GraphicGantryRobot()
	{
		
	}
	public GraphicGantryRobot(int init_x, int init_y, int init_theta, int init_dx, int init_dy, int init_dtheta, int init_imageWidth, int init_imageHeight, String init_imagePath)
	{
		bin = null;
		hasBin = false;
		arrived = false;
		state = 0;					// 0 = idle, 1 = going to bin pickup, 2 = arrived at bin pickup, 3 = going to feeder (dropoff), 4 = arrived at feeder (dropoff),
									// 5 = arrived at feeder (pickup), 6 = arrived at feeder (pickup)
		destinationFeeder = -1;
		x = init_x;
		y = init_y;
		fx = init_x;
		fy = init_y;
		theta = init_theta;
		dx = init_dx;
		dy = init_dy;
		dtheta = init_dtheta;
		imageWidth = init_imageWidth;
		imageHeight = init_imageHeight;
		image = Toolkit.getDefaultToolkit().getImage(init_imagePath);
		binImage = Toolkit.getDefaultToolkit().getImage("Images/binCrate.png");
		
		partPath = "Images/eyesItem.png";
	}
	
	public void setDestinationFeeder(int init_destinationFeeder)
	{
		destinationFeeder = init_destinationFeeder;
	}
	public int getDestinationFeeder()
	{
		return destinationFeeder;
	}
	public void adjustShift(int amount)
	{
		if(theta == 0 || theta == 360)
			x -= amount;
	}
	public int getState()
	{
		return state;
	}
	public void paint(Graphics g)
	{
		// Draw the robot
		super.paint(g);
		// Draw the bin if it has one
		if(hasBin)
			g.drawImage(binImage, x+imageWidth-25, y, 50, 95, null);
	}
	public boolean hasBin()
	{
		return hasBin;
	}
	public void setPartPath(String path)
	{
		partPath = path;
	}
	public String getPartPath()
	{
		return partPath;
	}
	public void giveBin(GraphicBin init_bin)
	{
		bin = init_bin;
		hasBin = true;
	}
	public GraphicBin popBin()
	{
		hasBin = false;
		return bin;
	}
}
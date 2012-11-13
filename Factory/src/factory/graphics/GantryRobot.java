package factory.graphics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import factory.Part;

class GantryRobot
{
	int x, y;							// current position
	int dx, dy;						// change in position
	int theta;						// image angle
	int dtheta;						// change in image angle
	int imageWidth, imageHeight;		// image size
	Image image;
	int fx, fy;		// final position (destination)
	int destinationFeeder;
	boolean arrived;
	int state;
	GraphicBin bin;
	boolean hasBin;
	Image binImage;
	
	public GantryRobot()
	{
		
	}
	public GantryRobot(int init_x, int init_y, int init_theta, int init_dx, int init_dy, int init_dtheta, int init_imageWidth, int init_imageHeight, String init_imagePath)
	{
		bin = new GraphicBin(new Part("TestItem"));
		hasBin = false;
		arrived = false;
		state = -1;		// 0 = moving to nest, 1 = waiting at nest, 2 = waiting for next action, 3 = moving to kitting station, 4 = waiting at kitting station
						// 5 = moving to center, 6 = arrived at center
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
	}
	
	public void setDestination(int init_fx, int init_fy)
	{
		fx = init_fx;
		fy = init_fy;
	}

	public void setState(int init_state)
	{
		state = init_state;
	}
	
	public int getDestinationFeeder()
	{
		return destinationFeeder;
	}
	
	public void setDestinationFeeder(int init_destinationFeeder)
	{
		destinationFeeder = init_destinationFeeder;
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
		// Draw the parts robot itself
		g.drawImage(image, x, y, imageWidth, imageHeight, null);
		// Draw the bin if it has one
		if(hasBin)
			g.drawImage(binImage, x+imageWidth-25, y, 50, 95, null);
	}
	// Get functions
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public Image getImage()
	{
		return image;
	}
	public int getAngle()
	{
		return theta;
	}
	public int getImageWidth()
	{
		return imageWidth;
	}
	public int getImageHeight()
	{
		return imageHeight;
	}
	public GraphicBin takeBin()
	{
		hasBin = false;
		return bin;
	}
	public void move()
	{
		//System.out.println(fx + " " + fy);
		if(y == fy && x == fx)	// robot has arrived at destination
		{
			if(state == 0)
			{
				state = 1;
				hasBin = true;
				System.out.println("Arrived at bin pickup point, waiting for bin pickup.");
			}
			else if(state == 3)
			{
				state = 4;
				System.out.println("Arrived at feeder, waiting for feeder dumping.");
			}
		}
		else if(y > fy)
		{
			if(theta > 90 && theta < 270)
				theta -= dtheta;
			else if(theta < 90 || theta >= 270)
				theta += dtheta;
			else
				y -= dy;
		}
		else if(y < fy)
		{
			if(theta > 90 && theta < 270)
				theta += dtheta;
			else if(theta <= 90 || theta > 270)
				theta -= dtheta;
			else
				y += dy;
		}
		else if(x > fx)
		{
			if(theta < 180 && theta >= 0)
				theta += dtheta;
			else if(theta > 180 && theta <= 360)
				theta -= dtheta;
			else
				x -= dx;
		}
		else if(x < fx)
		{
			if(theta < 180 && theta > 0)
				theta -= dtheta;
			else if(theta > 180 && theta < 360)
				theta += dtheta;
			else
				x += dx;
		}
		if(theta < 0) theta = 360;
		else if(theta > 360) theta = 0;
		//System.out.println(theta);
	}
}
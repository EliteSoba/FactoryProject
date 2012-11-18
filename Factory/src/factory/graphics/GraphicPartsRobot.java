package factory.graphics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;

class GraphicPartsRobot extends GraphicAnimatedObject
{
	int fx, fy;		// final position (destination)
	int destinationNest, destinationKit;
	boolean arrived;
	int state;
	int itemIndex;
	
	public GraphicPartsRobot()
	{
		
	}
	public GraphicPartsRobot(int init_x, int init_y, int init_theta, int init_dx, int init_dy, int init_dtheta, int init_imageWidth, int init_imageHeight, String init_imagePath)
	{
		items = new ArrayList<GraphicItem>();
		arrived = false;
		state = 0;		// 0 = idle, 1 = going to nest, 2 = arrived at nest, 3 = going to station, 4 = arrived at station, 5 = going to center, 6 = arrived at center
		destinationNest = -1;
		destinationKit = -1;
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
		itemIndex = 0;
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
	
	public int getDestinationKit()
	{
		return destinationKit;
	}
	
	public void setDestinationKit(int init_destinationKit)
	{
		destinationKit = init_destinationKit;
	}
	
	public int getDestinationNest()
	{
		return destinationNest;
	}
	
	public void setDestinationNest(int init_destinationNest)
	{
		destinationNest = init_destinationNest;
	}

	public void setItemIndex(int index) {
		itemIndex = index;
	}
	public int getItemIndex() {
		return itemIndex;
	}
	public void adjustShift(int amount)
	{
		//if(theta == 0 || theta == 360)
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
		// Draw the items it's carrying
		for(int i = 0; i < items.size(); i++)
			items.get(i).paint(g, x+imageWidth-25,y+10+i*20);
	}
	public void move()
	{
		//System.out.println(fx + " " + fy);
		if(y == fy && x == fx)	// robot has arrived at destination
		{
			if(state % 2 == 1)
				state += 1;
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
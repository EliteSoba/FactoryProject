package factory.graphics;
import java.awt.Toolkit;
import java.util.ArrayList;

class PartsRobot extends AnimatedObject
{
	int fx, fy;		// final position (destination)
	int destinationNest, destinationKit;
	boolean arrived;
	int state;
	
	public PartsRobot()
	{
		
	}
	public PartsRobot(int init_x, int init_y, int init_theta, int init_dx, int init_dy, int init_dtheta, int init_imageWidth, int init_imageHeight, String init_imagePath)
	{
		items = new ArrayList<GraphicItem>();
		arrived = false;
		state = -1;		// 0 = moving to nest, 1 = waiting at nest, 2 = waiting for next action, 3 = moving to kitting station, 4 = waiting at kitting station
						// 5 = moving to center, 6 = arrived at center
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
	
	public void adjustShift(int amount)
	{
		if(theta == 0 || theta == 360)
			x -= amount;
	}
	public int getState()
	{
		return state;
	}
	public void move()
	{
		System.out.println(fx + " " + fy);
		if(y == fy && x == fx)	// robot has arrived at destination
		{
			if(state == 0)
			{
				state = 1;
				System.out.println("Arrived at nest, waiting for item pickup.");
			}
			else if(state == 3)
			{
				state = 4;
				System.out.println("Arrived at kitting station, waiting for item dropoff.");
			}
			else if(state == 5)		// going towards station
			{
				state = 6;
				System.out.println("Arrived at center.");
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
		System.out.println(theta);
	}
}
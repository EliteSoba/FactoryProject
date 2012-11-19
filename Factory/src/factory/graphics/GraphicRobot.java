package factory.graphics;

import java.awt.Graphics;
import java.awt.Image;

public class GraphicRobot extends GraphicAnimatedObject
{
	int fx, fy, ftheta;		// final position (destination) and final angle
	int destinationNest, destinationKit;
	boolean arrived;
	int state;
	int[] movementCheckingOrders;
	
	public void setDestination(int init_fx, int init_fy, int init_ftheta)
	{
		fx = init_fx;
		fy = init_fy;
		ftheta = init_ftheta;
	}

	public void setState(int init_state)
	{
		state = init_state;
	}
	public void adjustShift(int amount)
	{
		x -= amount;
	}
	public int getState()
	{
		return state;
	}
	public void move()
	{
		if(y == fy && x == fx)	// robot has arrived at destination
		{
			if(state % 2 == 1)
				state += 1;
			if(theta-ftheta == 0)
				theta += 0;
			else if(theta-ftheta < 180)
				theta -= dtheta;
			else if(theta-ftheta > 180)
				theta += dtheta;
			//System.out.println(theta + " " + ftheta);
		}
		for(int i = 0; i < movementCheckingOrders.length; i++)
		{
			switch(movementCheckingOrders[i])
			{
			case 0 : 	
				if(x < fx)
				{
					if(theta <= 180 && theta > 0)
						theta -= dtheta;
					else if(theta > 180 && theta < 360)
						theta += dtheta;
					else
						x += dx;
					break;
				}
			case 1 : 
				if(y > fy)
				{
					if(theta > 90 && theta < 270)
						theta -= dtheta;
					else if(theta < 90 || theta >= 270)
						theta += dtheta;
					else
						y -= dy;
					break;
				}
			case 2 : 
				if(x > fx)
				{
					if(theta < 180 && theta >= 0)
						theta += dtheta;
					else if(theta > 180 && theta <= 360)
						theta -= dtheta;
					else
						x -= dx;
					break;
				}
			case 3 : 
				if(y < fy)
				{
					if(theta > 90 && theta < 270)
						theta += dtheta;
					else if(theta <= 90 || theta > 270)
						theta -= dtheta;
					else
						y += dy;
					break;
				}
			}
		}
		//System.out.println(theta + " " + ftheta);
		
		if(theta < 0) theta = 360;
		else if(theta > 360) theta = 0;
	}
	public void paint(Graphics g)
	{
		// Draw the parts robot itself
		g.drawImage(image, x, y, imageWidth, imageHeight, null);
	}
}

import java.awt.Toolkit;

class PartsRobot extends AnimatedObject
{
	int fx, fy;		// final position (destination)
	int state;
	
	public PartsRobot()
	{
		
	}
	public PartsRobot(int init_x, int init_y, int init_theta, int init_dx, int init_dy, int init_dtheta, int init_imageWidth, int init_imageHeight, String init_imagePath)
	{
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
	
	public void move()
	{
		if(y > fy)
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
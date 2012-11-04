import java.awt.Toolkit;

class Item extends AnimatedObject
{
	int fx, fy;		// final position (destination)
	int state;
	
	public Item()
	{
		
	}
	public Item(int init_x, int init_y, int init_theta, int init_dx, int init_dy, int init_dtheta, int init_imageWidth, int init_imageHeight, String init_imagePath)
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
				y -= dy;
		}
		else if(y < fy)
		{
				y += dy;
		}
		else if(x > fx)
		{
				x -= dx;
		}
		else if(x < fx)
		{
				x += dx;
		}
	}
}
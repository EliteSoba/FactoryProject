import java.awt.Toolkit;

class Item extends AnimatedObject
{
	int fx, fy;		// final position (destination)
	
	public Item()
	{
		
	}
	public Item(int init_imageWidth, int init_imageHeight, String init_imagePath)
	{
		x = 0;
		y = 0;
		fx = 0;
		fy = 0;
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
package factory.graphics;
import java.awt.Toolkit;
import java.util.ArrayList;

class Station extends AnimatedObject
{	
	public Station()
	{
		
	}
	public Station(int init_x, int init_y, int init_theta, int init_dx, int init_dy, int init_dtheta, int init_imageWidth, int init_imageHeight, String init_imagePath)
	{
		items = new ArrayList<GraphicItem>();
		x = init_x;
		y = init_y;
		theta = init_theta;
		dx = init_dx;
		dy = init_dy;
		dtheta = init_dtheta;
		imageWidth = init_imageWidth;
		imageHeight = init_imageHeight;
		image = Toolkit.getDefaultToolkit().getImage(init_imagePath);
	}
}
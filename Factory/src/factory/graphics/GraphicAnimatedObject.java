package factory.graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

class GraphicAnimatedObject
{
	protected int x, y;							// current position
	protected int dx, dy;						// change in position
	protected int theta;						// image angle
	protected int dtheta;						// change in image angle
	protected int imageWidth, imageHeight;		// image size
	protected Image image;
	
	// Constructors
	public GraphicAnimatedObject()
	{
		
	}
	
	// Set functions
	public void setPosition(int init_x, int init_y)
	{
		x = init_x;
		y = init_y;
	}	
	public void setSpeed(int init_dx, int init_dy)
	{
		dx = init_dx;
		dy = init_dy;
	}
	public void setImage(String imagePath)
	{
		image = Toolkit.getDefaultToolkit().getImage(imagePath);
	}
	public void setAngle(int init_theta)
	{
		theta = init_theta;
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
}
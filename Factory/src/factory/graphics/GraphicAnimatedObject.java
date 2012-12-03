package factory.graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 * @author George Li<p>
 * <b>{@code GraphicAnimatedObject.java}</b><br>
 * This is a parent class for GraphicNest and GraphicRobot.<br>
 * Contains generic data and methods for position, movement, angle, and images.
 */

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
	/**
	 * Sets the position of the object.
	 * @param init_x
	 * @param init_y
	 */
	public void setPosition(int init_x, int init_y)
	{
		x = init_x;
		y = init_y;
	}	
	
	/**
	 * Sets the speed of the object.
	 * @param init_dx
	 * @param init_dy
	 */
	public void setSpeed(int init_dx, int init_dy)
	{
		dx = init_dx;
		dy = init_dy;
	}
	
	/**
	 * Sets the image of the object.
	 * @param imagePath
	 */
	public void setImage(String imagePath)
	{
		image = Toolkit.getDefaultToolkit().getImage(imagePath);
	}
	
	/**
	 * Sets the angle of the object.
	 * Measured counterclockwise from East.
	 * @param init_theta
	 */
	public void setAngle(int init_theta)
	{
		theta = init_theta;
	}
	
	// Get functions
	/**
	 * Gets the x coordinate of the object.
	 * @return x
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Gets the y coordinate of the object.
	 * @return y
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Gets the image of the object.
	 * @return image
	 */
	public Image getImage()
	{
		return image;
	}
	
	/**
	 * Gets the angle of the object in the direction it is facing.
	 * Measured counterclockwise from East.
	 * @return theta
	 */
	public int getAngle()
	{
		return theta;
	}
	
	/**
	 * Gets the image width of the object in pixels.
	 * @return imageWidth
	 */
	public int getImageWidth()
	{
		return imageWidth;
	}
	
	/**
	 * Gets the image height of the object in pixels.
	 * @return imageHeight
	 */
	public int getImageHeight()
	{
		return imageHeight;
	}
}
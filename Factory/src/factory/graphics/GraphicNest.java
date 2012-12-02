package factory.graphics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 * @author Minh La, George Li <p>
 * <b>{@code GraphicNest.java}</b> (50x720) <br>
 * This creates a nest and an array of items in the nest,
 */

class GraphicNest extends GraphicAnimatedObject
{
	/**An array of items in the nest**/
	protected ArrayList<GraphicItem> items;			// inventory of items
	
	/**
	 * Default constructor
	 */
	public GraphicNest()
	{
		
	}
	
	/**
	 * Creates a nest with specific locations, image width, height and path.
	 * @param init_x x-coordinate of the nest
	 * @param init_y y-coordinate of the nest
	 * @param init_theta the angle of where the nest is facing
	 * @param init_dx speed/velocity in the x direction
	 * @param init_dy speed/velocity in the y direction
	 * @param init_dtheta direction of the the nest is facing
	 * @param init_imageWidth the width of the image
	 * @param init_imageHeight the height of the image
	 * @param init_imagePath the image path of the image
	 */
	public GraphicNest(int init_x, int init_y, int init_theta, int init_dx, int init_dy, int init_dtheta, int init_imageWidth, int init_imageHeight, String init_imagePath)
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
	
	/**
	 * Paints the nest along with all its items
	 * @param g the specified graphics windows
	 */
	public void paint(Graphics g) {
		g.drawImage(image, x, y, null);							// paint nest
		for(int i = 0; i < items.size(); i++)					// paint each item in the nest
			items.get(i).paint(g, 3+x+(i/3)*25, 3+y+(i%3)*25);
	}
	
	/**
	 * Add items to the array of items in Nest
	 * @param newItem one item
	 */
	public void addItem(GraphicItem newItem)
	{
		items.add(newItem);
	}
	
	/**
	 * Clear out the items in the nest
	 */
	public void clearItems()
	{
		items.clear();
	}
	
	/**
	 *returns if the nest has items or not. 
	 *True - has items
	 *False - empty 
	 * @return {@code true} if the Nest has Items; {@code false} otherwise
	 */
	public boolean hasItem()
	{
		if(items.size() >= 1)
			return true;
		else
			return false;
	}
	
	/**
	 * Erases the last item and returns it
	 * @return the last item in the nest
	 */
	public GraphicItem popItem()
	{
		if (items.size() == 0)
			return null;
		GraphicItem lastItem = items.get(items.size()-1);		// get last item
		items.remove(items.size()-1);					// remove last item
		return lastItem;								// return last item
	}
	/**
	 * Gets an Item at the provided index and removes the Item
	 * @param index The index of the Item being taken
	 * @return The Item at the provided index; returns {@code null} if the index is invalid
	 */
	public GraphicItem popItemAt(int index) {
		if (index < 0 || index >= items.size())
			return null;
		GraphicItem returnedItem = items.get(index);
		items.remove(index);
		return returnedItem;
	}
	/**
	 * returns the size of the items in the nest
	 * @return size of items in the nest
	 */
	public int getSize()
	{
		return items.size();
	}
	
	/**
	 * returns the item at the wanted index
	 * @param i the index of item to be returned
	 * @return the item at i position in the array of items
	 */
	public GraphicItem getItemAt(int i)
	{
		return items.get(i);
	}
}
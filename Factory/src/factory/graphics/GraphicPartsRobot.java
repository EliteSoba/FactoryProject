package factory.graphics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

class GraphicPartsRobot extends GraphicRobot
{
	protected ArrayList<GraphicItem> items;			// inventory of items
	int itemIndex;
	
	public GraphicPartsRobot()
	{
		
	}
	
	public GraphicPartsRobot(int init_x, int init_y, int init_theta, int init_dx, int init_dy, int init_dtheta, int init_imageWidth, int init_imageHeight, String init_imagePath)
	{
		items = new ArrayList<GraphicItem>();
		for(int i = 0; i < 4; i++)
			items.add(null);
		arrived = false;
		state = 0;		// 0 = idle, 1 = going to nest, 2 = arrived at nest, 3 = going to station, 4 = arrived at station, 5 = going to center, 6 = arrived at center
		destinationNest = -1;
		destinationKit = -1;
		x = init_x;
		y = init_y;
		fx = init_x;
		fy = init_y;
		ftheta = 0;
		theta = init_theta;
		dx = init_dx;
		dy = init_dy;
		dtheta = init_dtheta;
		imageWidth = init_imageWidth;
		imageHeight = init_imageHeight;
		image = Toolkit.getDefaultToolkit().getImage(init_imagePath);
		itemIndex = 0;
		movementCheckingOrders = new int[4];
		movementCheckingOrders[0] = 1;
		movementCheckingOrders[0] = 0;
		movementCheckingOrders[0] = 2;
		movementCheckingOrders[0] = 3;
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
	public void addItem(GraphicItem newItem)
	{
		System.out.println("additem");
		for(int i = 0; i < 4; i++)
			if(items.get(i) == null)
			{
				System.out.println("found");
				items.set(i, newItem);
				break;
			}
	}
	public void clearItems()
	{
		items.clear();
	}
	public boolean hasItem()
	{
		if(items.size() >= 1)
			return true;
		else
			return false;
	}
	public GraphicItem popItem()
	{
		if (items.size() == 0)
			return null;
		GraphicItem lastItem = items.get(items.size()-1);		// get last item
		items.remove(items.size()-1);					// remove last item
		return lastItem;								// return last item
	}
	/**
	 * Gets an Item at the provided index and sets the Item to null
	 * @param index The index of the Item being taken
	 * @return The Item at the provided index; returns {@code null} if the index is invalid
	 */
	public GraphicItem popItemAt(int index) {
		if (index < 0 || index >= items.size())
			return null;
		GraphicItem returnedItem = items.get(index);
		items.set(index, null);
		return returnedItem;
	}
	public int getSize()
	{
		return items.size();
	}
	public GraphicItem getItemAt(int i)
	{
		return items.get(i);
	}
	public void paint(Graphics g)
	{
		// Draw the robot
		super.paint(g);
		// Draw the items the robot is carrying
		for(int i = 0; i < items.size(); i++)
			if(items.get(i) != null)
			items.get(i).paint(g, x+imageWidth-25,y+10+i*20);
	}

}
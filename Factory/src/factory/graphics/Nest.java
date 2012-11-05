package factory.graphics;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;

class Nest extends AnimatedObject
{
	ArrayList<Item> items;
	
	public Nest()
	{
		
	}
	public Nest(int init_x, int init_y, int init_theta, int init_dx, int init_dy, int init_dtheta, int init_imageWidth, int init_imageHeight, String init_imagePath)
	{
		items = new ArrayList<Item>();
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
	public void addItem(Item newItem)
	{
		items.add(newItem);
	}
	public void clearItems()
	{
		items.clear();
	}
	public Item popItem()
	{
		//if(items.size() >= 1)
		//{
			Item lastItem = items.get(items.size()-1);		// get last item
			items.remove(items.size()-1);					// remove last item
			return lastItem;								// return last item
		//}
	}
	public int getSize()
	{
		return items.size();
	}
	public Item getItemAt(int i)
	{
		return items.get(i);
	}
}
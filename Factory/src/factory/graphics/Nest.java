package factory.graphics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;

class Nest extends AnimatedObject
{
	ArrayList<GraphicItem> items;
	
	public Nest()
	{
		
	}
	public Nest(int init_x, int init_y, int init_theta, int init_dx, int init_dy, int init_dtheta, int init_imageWidth, int init_imageHeight, String init_imagePath)
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
	public void addItem(GraphicItem newItem)
	{
		items.add(newItem);
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
		GraphicItem lastItem = items.get(items.size()-1);		// get last item
		items.remove(items.size()-1);					// remove last item
		return lastItem;								// return last item
	}
	public int getSize()
	{
		return items.size();
	}
	public GraphicItem getItemAt(int i)
	{
		return items.get(i);
	}
	public void paint(Graphics g) {
		g.drawImage(image, x, y, null);							// paint nest
		for(int i = 0; i < items.size(); i++)					// paint each item in the nest
			items.get(i).paint(g, 3+x+(i/3)*25, 3+y+(i%3)*25);
	}
}
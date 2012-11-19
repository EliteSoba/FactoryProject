

package factory.graphics;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import factory.Part;

/**
 * @author Minh la <p>
 * <b>{@code GraphicBin.java}</b> (50x720) <br>
 * This creates a bin,
 * Contains an array of items of type Part passed from Agents
 */

public class GraphicBin{

	/**An array of the items in the bin**/
	ArrayList <GraphicItem> binItems;
	/** the image of the bin**/
	ImageIcon binImage;
	/** the name of the part**/
	String partName;
	/**How many items a bin can hold**/
	int binSize;
	/**What items the bin contain**/
	GraphicItem binType;
	/**Stablization timer of the parts**/
	int stabilizationTime;

	/**Creates a Bin with the specified part
	 * Creates an arraylist of items of that part
	 * @param part takes in a part from the Agents
	 */
	public GraphicBin(Part part){
		partName = part.name;
		binSize = 14;		//Number of items in bin
		binItems = new ArrayList<GraphicItem>();
		for(int i = 0; i < binSize;i++){
			binItems.add(new GraphicItem(-40, 0, "Images/"+partName+".png"));
		}
		binType = new GraphicItem(-40, 0, "Images/"+partName+".png");
		stabilizationTime = part.nestStabilizationTime * 1000 / GraphicPanel.DELAY;
		
		//binItemsStackImage = new ImageIcon("Images/binCrate.png");
		binImage = new ImageIcon("Images/binCrate.png");
	}
	
	/**
	 * gets the part name of the item
	 * @return name of the part, "eye" "sword"
	 */
	public String getPartName() {
		return partName;
	}

	/**
	 * gets the array of items in the bin
	 * @return array of items
	 */
	public ArrayList<GraphicItem> getBinItems(){
		return binItems;
	}

	/**
	 * gets the icon image of the bin
	 * @return bin image of the bin
	 */
	public ImageIcon getBinImage(){
		return binImage;
	}
	
	/**
	 * gets the type of the part inside the bin
	 * @return bin type
	 */
	public GraphicItem getBinType() {
		return binType;
	}
	
	/**
	 * gets the stabilization time for the parts
	 * @return stabilization time of the parts
	 */
	public int getStabilizationTime() {
		return stabilizationTime;
	}
	/**
	 * Paints the bin
	 * @param g specified graphical window
	 * @param x x-coordinate of the bin
	 * @param y y-coordinate of the bin
	 */
	public void paint(Graphics g, int x, int y) {
		g.drawImage(binImage.getImage(), x, y, null);
	}

}
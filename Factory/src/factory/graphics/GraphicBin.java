//Minh La

package factory.graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class GraphicBin{
	
	ArrayList <GraphicItem> binItems;
	ImageIcon binImage;
	
	public GraphicBin(){
		int binSize = 12 * 55;		//Number of items in bin
		binItems = new ArrayList<GraphicItem>();
		for(int i = 0; i < binSize;i++){
			binItems.add(new GraphicItem());
		}
		//Declaration of items in bin's location
		for(int i = 0;i < binItems.size() ;i = i + (binItems.size() / 4)){
			for(int j = 0; j < binItems.size() / 4;j++){
				binItems.get(i + j).setX(-40);
				binItems.get(i + j).setY(-40);
			}
		}
		binImage = new ImageIcon("./src/image/bin.png");
	}
	
	public ArrayList<GraphicItem> getBinItems(){
		return binItems;
	}
	
	public ImageIcon getBinImage(){
		return binImage;
	}
	
}
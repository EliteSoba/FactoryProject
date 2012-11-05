//Minh La

package factory.graphics;
import javax.swing.ImageIcon;


public class GraphicItem{
	
	int x,y;
	int vX,vY;
	int stepY, stepX, stepYSize, stepXSize;
	boolean divergeUp;
	//public boolean stateDiverge, stateLane;
	String imagePath;
	ImageIcon itemImage;
	
	public GraphicItem(){
		x = 0; y = 0; vX = 0; vY = 0;
		stepYSize = 5;		//Num of steps to finish diverge lane (vertical lane)
		stepXSize = 27;		//Num of steps to finish horizontal lane
		stepX = stepXSize;
		stepY = stepYSize;
		divergeUp = false;
		//stateDiverge = true; stateLane = false;
		imagePath = "Images/ear.png";
			itemImage = new ImageIcon(imagePath);
	}
	
	public ImageIcon getImageIcon(){
		return itemImage;
	}
	
	public int getX(){
		return x;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getvX(){
		return vX;
	}
	
	public void setvX(int vX){
		this.vX = vX;
	}
	
	public int getvY(){
		return vY;
	}
	
	public void setvY(int vY){
		this.vY = vY;
	}
	
	public int getStepX(){
		return stepX;
	}
	
	public void setStepX(int stepX){
		this.stepX = stepX;
	}
	
	public int getStepY(){
		return stepY;
	}
	
	public void setStepY(int stepY){
		this.stepY = stepY;
	}
}
package factory.graphics.GUILaneManager;


import java.awt.*;
import javax.swing.*;

public class GUIGraphicItem {
	
	private int x, y;
	private int vx, vy;
	private ImageIcon image;
	private int stepX, stepY;
	private boolean divergeUp;
	
	public GUIGraphicItem(int x, int y, String imagepath) {
		this.x = x;
		this.y = y;
		vx = 0;
		vy = 0;
		image = new ImageIcon(imagepath);
		stepX = 20;
		stepY = 5;
		
		
		
		
		
		
		divergeUp = false;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void moveX(int v) {
		x += v;
	}
	
	public void moveY(int v) {
		y += v;
	}
	
	public int getVX() {
		return vx;
	}
	
	public int getVY() {
		return vy;
	}
	
	public void setVX(int v) {
		vx = v;
	}
	
	public void setVY(int v) {
		vy = v;
	}
	
	public void moveVX() {
		x += vx;
	}
	
	public void moveVY() {
		y += vy;
	}
	
	public void paint(Graphics g) {
		g.drawImage(image.getImage(), x, y, null);
	}
	
	public void paint(Graphics g, int x, int y) {
		g.drawImage(image.getImage(), x, y, null);
	}
	
	public void setStepX(int sx) {
		stepX = sx;
	}
	
	public void setStepY(int sy) {
		stepY = sy;
	}
	
	public int getStepX() {
		return stepX;
	}
	
	public int getStepY() {
		return stepY;
	}
	
	public void setDivergeUp(boolean diverge) {
		divergeUp = diverge;
	}
	
	public boolean getDivergeUp() {
		return divergeUp;
	}
	
}
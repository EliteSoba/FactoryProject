package factory.graphics;
import java.awt.*;
import javax.swing.*;

public class FrameKitAssemblyManager extends JFrame{
	
	/*FrameKitAssemblyManager.java (800x600)
	 * This integrates the GraphicKitAssemblyManager with a small Control Panel to demonstrate commands
	 * This will be removed when the project gets integrated
	 */
	
	GraphicKitAssemblyManager GKAM; //The Graphics part
	ControlPanel CP; //The Swing control panel
	
	public FrameKitAssemblyManager() {
		//Constructor. BorderLayout
		GKAM = new GraphicKitAssemblyManager(this);
		GKAM.setPreferredSize(new Dimension(600, 600));
		this.add(GKAM, BorderLayout.CENTER);
		CP = new ControlPanel(this);
		CP.setPreferredSize(new Dimension(200, 600));
		this.add(CP, BorderLayout.LINE_END);
	}
	
	public void addInKit() {
		//Adds a Kit into the factory
		GKAM.addInKit();
	}
	
	public void addOutKit() {
		//Sends a Kit out of the factory
		GKAM.addOutKit();
	}
	
	public static void main(String args[]) {
		//Implements this JFrame
		FrameKitAssemblyManager FKAM = new FrameKitAssemblyManager();
		FKAM.setVisible(true);
		FKAM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FKAM.setSize(800, 600);
	}

}

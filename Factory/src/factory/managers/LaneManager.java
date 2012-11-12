//Ben Mayeux and Stephanie Reagle
//CS 200

package factory.managers;

import java.awt.GridLayout;

import javax.swing.JPanel;

import factory.client.Client;
import factory.graphics.GraphicLaneGraphicPanel;
import factory.graphics.GraphicLaneManagerClient;
import factory.swing.LaneManPanel;

public class LaneManager extends Client {
	private static final long serialVersionUID = 1L;


	public LaneManager(JPanel buttons, GraphicLaneGraphicPanel animation) {
		
		super(Client.Type.LANEMANAGER, buttons, animation); //what does this do?
		setInterface();
	}
	
	public static void main(String[] args){
		LaneManPanel buttons = new LaneManPanel();
		//GraphicLaneGraphicPanel animation = new GraphicLaneGraphicPanel(new GraphicLaneManagerClient());
		LaneManager l = new LaneManager(buttons, null);
		buttons.setManager(l);
		
	}
	
	public void sendMessage(int lane, int setting, String message){
		String set = new String("");
		if (message == "power"){
			set = "lm lma lanepowertoggle "+ lane;
		}
		else if (message == "red"){
			set = "lm lma set lanevibration "+ lane + " " + setting;
		}
		else if (message == "yellow"){
			set = "lm lma set lanevibration "+ lane + " " + setting;
		}
		else if (message == "green"){
			set = "lm lma set lanevibration "+ lane + " " + setting;
		}
		
	}
	
	public void setInterface() {
		this.setSize(800, 800);
		this.add(UI);
		this.setVisible(true);
				
		
		
	}

}

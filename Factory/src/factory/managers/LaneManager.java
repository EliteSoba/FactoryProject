//Ben Mayeux and Stephanie Reagle
//CS 200

package factory.managers;

import javax.swing.JPanel;

import factory.client.Client;
import factory.swing.LaneManPanel;

public class LaneManager extends Client {
	private static final long serialVersionUID = 1L;


	public LaneManager(JPanel buttons) {
		super(Client.Type.LANEMANAGER, buttons, null);
		setInterface();
	}
	
	public static void main(String[] args){
		LaneManPanel buttons = new LaneManPanel();
		LaneManager l = new LaneManager(buttons);
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
		//send via a function
	}
	
	public void setInterface() {
		this.setSize(800, 800);
		this.add(UI);
		this.setVisible(true);
		
	}

}

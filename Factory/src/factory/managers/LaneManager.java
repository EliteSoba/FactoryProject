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
		String cmd = new String("");
		if (message == "power"){
			cmd = "lm lma lanepowertoggle #"+ lane;
		}
		else if (message == "red"){
			cmd = "lm lma set lanevibration #"+ lane + " #" + setting;
		}
		else if (message == "yellow"){
			cmd = "lm lma set lanevibration #"+ lane + " #" + setting;
		}
		else if (message == "green"){
			cmd = "lm lma set lanevibration #"+ lane + " #" + setting;
		}
		
	}
	
	public void setInterface() {
		this.setSize(800, 800);
		this.add(UI);
		this.setVisible(true);
		
	}

}

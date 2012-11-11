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
	
	public void sendMessage(int lane){
		//Compile the message that you want to send
		String cmd = "lm lma powerlane "+ lane;
		//Use your client to send message
		
	}
	
	public void setInterface() {
		this.setSize(800, 800);
		this.add(UI);
		this.setVisible(true);
		
	}

}

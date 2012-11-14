//Ben Mayeux
package factory.managers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import factory.graphics.*;
import factory.client.Client;
import factory.swing.GantryManPanel;

public class GantryManager extends Client {
	static final long serialVersionUID = 8492299864169935860L;

	public GantryManager(JPanel buttons, JPanel animation) {
		super(Client.Type.GM, null, null);
		UI = new GantryManPanel();
		graphics = new GantryRobotPanel(this);
		setInterface();
	}
	public static void main(String[] args){
	    GantryManPanel buttons = new GantryManPanel();
	    JPanel animation = new JPanel(); //TODO where graphics panel goes 
		GantryManager k = new GantryManager(buttons, animation);
	}

	public void setInterface() {
		setSize(1780, 720);
		setLayout(new GridLayout(1,2));

		add(graphics);
		
		add(UI);
		setVisible(true);
		
	}
	
	public void sendMessage (String kitname, String quantity, String message) { // sends message out from swing
		String cmd = new String("");
		if (message ==  "robotRevolt"){
			//TODO get the format of the command from server //for V.2
		}
		
		else{
			cmd = "bad command";
		}
			sendCommand(cmd);
	}


	public void doCommand(ArrayList<String> pCmd) {
		//receive message
		//be able to handle err
	}
}

//Ben Mayeux
package factory.managers;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import factory.graphics.*;
import factory.client.Client;
import factory.swing.FactoryProdManPanel;
import factory.swing.GantryManPanel;

public class GantryManager extends Client {
	static final long serialVersionUID = 8492299864169935860L;
	
	GantryManPanel buttons;
	GantryRobotPanel animation;
	
	public GantryManager() {
		super(Client.Type.gm, null, null);
		
		buttons = new GantryManPanel();
		buttons.setManager(this);
		animation = new GantryRobotPanel(null); //TODO does not currently work but will by 11/13 -->Tobi
		
		setInterface();
	}
	
	public static void main(String[] args){
		GantryManager k = new GantryManager();
	}

	public void setInterface() {
		graphics = animation;
		UI = buttons;

		add(graphics, BorderLayout.CENTER);
		
		add(UI, BorderLayout.LINE_END);
		pack();
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

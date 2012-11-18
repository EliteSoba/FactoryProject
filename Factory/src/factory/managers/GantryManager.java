//Ben Mayeux
package factory.managers;

import java.awt.BorderLayout;
import java.util.ArrayList;

import factory.graphics.*;
import factory.client.Client;
import factory.swing.GantryManPanel;

public class GantryManager extends Client {
	static final long serialVersionUID = 8492299864169935860L;
	
	GantryManPanel buttons;
	GantryRobotPanel animation;
	
	public GantryManager() {
		super(Client.Type.gm, null, null);
		
		buttons = new GantryManPanel();
		buttons.setManager(this);
		animation = new GantryRobotPanel(this); //TODO does not currently work but will by 11/13 -->Tobi
		
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

	public void doCommand(ArrayList<String> pCmd) {
		//receive message
		//be able to handle err
	}
}

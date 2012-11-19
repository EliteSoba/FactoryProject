//Ben Mayeux and Stephanie Reagle
//CS 200

package factory.managers;

import java.awt.BorderLayout;
import java.util.ArrayList;

import factory.graphics.*;
import factory.swing.LaneManPanel;
import factory.client.Client;

public class LaneManager extends Client {

	private static final long serialVersionUID = 6767006307991802656L;
	
	LaneManPanel buttons;
	LanePanel animation;
	
	public LaneManager() {
		super(Client.Type.lm, null, null); 
		//LaneManPanel buttons = new LaneManPanel(); //to be implemented in V.2
		
		buttons = new LaneManPanel(this);
		animation = new LanePanel(this);
		
		setInterface();
	}
	
	public static void main(String[] args){
		LaneManager l = new LaneManager(); 
	}
	
	
	public void setInterface() {
		graphics = animation;
		UI = buttons;

		add(graphics, BorderLayout.CENTER);
		
		pack();
		
		//add(UI, BorderLayout.LINE_END);  //to be implemented in V.2
		setVisible(true);
	}

	public void doCommand(ArrayList<String> pCmd) {
		int size = pCmd.size();
		//parameters lay between i = 2 and i = size - 2
		String action = pCmd.get(0);
		String identifier = pCmd.get(1);
		if(action == "cmd"){

		}
		else if(action == "req"){

		}
		else if(action == "get"){

		}
		else if(action == "set"){

		}
		else if(action == "cnf"){

		}
		else if(action.equals("mcs"))
			   System.exit(0);
		else 
	   		  System.out.println("Stuff is FU with the server...\n(string does not contain a command type)");
	}
}

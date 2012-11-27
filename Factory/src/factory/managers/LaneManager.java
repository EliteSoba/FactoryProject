//Ben Mayeux and Stephanie Reagle
//CS 200

package factory.managers;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;

import factory.graphics.*;
import factory.*;
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
			
			// Commands from FeederAgent
			if (identifier.equals("startfeeding"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).feedFeeder(feederSlot);
			}
			else if (identifier.equals("stopfeeding"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).turnFeederOff(feederSlot);
			} 
			else if (identifier.equals("purgefeeder"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).purgeFeeder(feederSlot);
			}
			else if (identifier.equals("switchlane"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).switchFeederLane(feederSlot);
			}
			else if (identifier.equals("purgetoplane"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).purgeTopLane(feederSlot);
			}
			else if (identifier.equals("purgebottomlane"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).purgeBottomLane(feederSlot);
			}

		}
		else if(action == "req"){

		}
		else if(action == "get"){

		}
		else if(action == "set"){
			int feederSlot = Integer.valueOf(pCmd.get(2));
			GraphicBin bin = new GraphicBin(new Part(pCmd.get(3)));
			((LanePanel) graphics).setFeederBin(feederSlot, bin);

		}
		else if(action == "cnf"){

		}
		else if(action.equals("mcs")){
			   try {
					this.server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				   System.exit(0);
			   }
		else 
	   		  System.out.println("Stuff is FU with the server...\n(string does not contain a command type)");
	}
}

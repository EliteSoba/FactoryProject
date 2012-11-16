//Ben Mayeux, Stephanie Reagle, Marc Mendiola
//CS 200

package factory.managers;

import java.awt.*;
import java.util.ArrayList;

import factory.client.Client;
import factory.graphics.KitAssemblyPanel;
import factory.swing.KitAssManPanel;

public class KitAssemblyManager extends Client {
	private static final long serialVersionUID = -4230607892468748490L;
	
	KitAssManPanel buttons;
	KitAssemblyPanel animation;

		public KitAssemblyManager() {
			super(Client.Type.kam, null, null);
			
			buttons = new KitAssManPanel();
			buttons.setManager(this);
			animation = new KitAssemblyPanel(this); //TODO does not currently work but will by 11/13 -->Tobi
			
			this.setInterface();
		}
		public static void main(String[] args){
			KitAssemblyManager k = new KitAssemblyManager();
		}

		public void setInterface() {
			graphics = animation;
			UI = buttons;
			
			setLayout(new BorderLayout());

			add(graphics, BorderLayout.CENTER);
			pack();
			
			//add(UI, BorderLayout.LINE_END); //to be implemented in V.2
			setVisible(true);

		}
		
		public void sendMessage (String kitname, String quantity, String message) { // sends message out from swing
			String set = new String("");
			if (message == "incorrectKits"){
				//TODO get the format of the command from server //for V.2
			}
			else if (message == "kitRobotFreeze"){
				//TODO get the format of the command from server //for V.2
			}
			else{
				set = "bad command";
			}
			sendCommand(set);
		}

		public void doCommand(ArrayList<String> pCmd) {
			// TODO receive commands
			
		}
}

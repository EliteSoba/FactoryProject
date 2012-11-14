//Ben Mayeux and Stephanie Reagle
//CS 200

package factory.managers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import factory.client.Client;
import factory.graphics.GraphicKitAssemblyManager;

public class KitAssemblyManager extends Client {
	private static final long serialVersionUID = -4230607892468748490L;

		public KitAssemblyManager( GraphicKitAssemblyManager animation) {
			super(Client.Type.KAM, null, animation);
			setInterface();
		}
		public static void main(String[] args){
		    //KitAssManPanel buttons = new KitAssManPanel(); //to be implemented in V.2
		    GraphicKitAssemblyManager animation = new GraphicKitAssemblyManager(null);
			KitAssemblyManager k = new KitAssemblyManager(animation);
			//buttons.setManager(k);
		}

		public void setInterface() {
			setSize(1780, 720);
			GridBagConstraints c = new GridBagConstraints();
			setLayout(new GridBagLayout());
			
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = 0;
			c.gridy = 0;
			add(graphics, c);
			
			/*c.gridx = 2;
			add(UI, c);*/ //to be implemented in V.2
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

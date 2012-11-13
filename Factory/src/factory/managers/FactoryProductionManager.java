//Ben Mayeux and Stephanie Reagle
//CS 200

// Last edited: 11/11/12 3:27pm by Joey Huang
package factory.managers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import factory.client.Client;
import factory.graphics.GraphicPanel;
import factory.swing.FactoryProdManPanel;

public class FactoryProductionManager extends Client {
	static final long serialVersionUID = -2074747328301562732L;

		public FactoryProductionManager(JPanel buttons, GraphicPanel animation) {
			super(Client.Type.FACTORYPRODUCTIONMANAGER, buttons, animation);
			setInterface();
		}
		public static void main(String[] args){
		    FactoryProdManPanel buttons = new FactoryProdManPanel();
		    GraphicPanel animation = new GraphicPanel(null); //TODO does not currently work but will by 11/13 -->Tobi
			FactoryProductionManager f = new FactoryProductionManager(buttons, animation);
			buttons.setManager(f);
		}

		public void setInterface() {
			setSize(1780, 720);
			GridBagConstraints c = new GridBagConstraints();
			setLayout(new GridBagLayout());
			
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = 0;
			c.gridy = 0;
			add(graphics, c);
			
			c.gridx = 2;
			add(UI, c);
			setVisible(true);
			
		}

		public void sendMessage (String kitname, String quantity, String message) { // sends message out from swing
			String set = new String("");
			set = "fpm km cmd makekits " + quantity + " " + kitname;	
			sendCommand(set);
			//
		}
		
		public void sendDone(String process) { //sends message out from graphics (Tobi's Function)
			//Process changes depending on previous command
			sendCommand(process);
		}
		
		public void doCommand(ArrayList<String> pCmd) { 
			//receive message
			
		}
		
		/*public void populateKitList(ArrayList<String> kitList) {
			for (int i = 0; i < kitList.size();i++) {
				buttons.add(
			}
		}
		*/
}

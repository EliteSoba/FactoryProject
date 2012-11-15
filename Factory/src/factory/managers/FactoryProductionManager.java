//Ben Mayeux and Stephanie Reagle
//CS 200

// Last edited: 11/11/12 3:27pm by Joey Huang
package factory.managers;

import java.awt.BorderLayout;
import java.util.ArrayList;

import factory.client.Client;
import factory.graphics.FactoryProductionPanel;
import factory.swing.FactoryProdManPanel;

public class FactoryProductionManager extends Client {
	static final long serialVersionUID = -2074747328301562732L;
	
	FactoryProdManPanel buttons;
	FactoryProductionPanel animation;
	
		public FactoryProductionManager() {
			super(Client.Type.fpm, null, null);
			
			buttons = new FactoryProdManPanel();
			buttons.setManager(this);
			animation = new FactoryProductionPanel(null); //TODO does not currently work but will by 11/13 -->Tobi
			
			setInterface();
		}
		public static void main(String[] args){
		   FactoryProductionManager f = new FactoryProductionManager();
		   
		}

		@Override
		public void setInterface() {
			graphics = animation;
			UI = buttons;
			
			add(graphics, BorderLayout.CENTER);
			add(UI, BorderLayout.LINE_END);
			
			pack();
			setVisible(true);
		}

		public void sendOrder(String kitname, String quantity) { // request to server to send order to kitmanager
			output.println("fpm km cmd makekits " + quantity + " " + kitname);		
		}
		@Override
		public void doCommand(ArrayList<String> pCmd) {
			// TODO Auto-generated method stub
			
		}
		
		/*public void populateKitList(ArrayList<String> kitList) {
			for (int i = 0; i < kitList.size();i++) {
				buttons.add(
			}
		}
		*/
}

//Ben Mayeux and Stephanie Reagle
//CS 200

// Last edited: 11/11/12 3:27pm by Joey Huang
package factory.managers;

import java.util.ArrayList;

import javax.swing.JPanel;

import factory.client.Client;
import factory.swing.FactoryProdManPanel;

public class FactoryProductionManager extends Client {
	static final long serialVersionUID = -2074747328301562732L;

		public FactoryProductionManager(JPanel buttons) {
			super(Client.Type.fpm, buttons, null);
			setInterface();
		}
		public static void main(String[] args){
		    FactoryProdManPanel buttons = new FactoryProdManPanel();
			FactoryProductionManager f = new FactoryProductionManager(buttons);
		}

		@Override
		public void setInterface() {
			this.setSize(800, 800);
			this.add(UI);
			this.setVisible(true);
			
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
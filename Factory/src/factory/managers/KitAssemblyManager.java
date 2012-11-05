//Ben Mayeux and Stephanie Reagle
//CS 200

package factory.managers;

import javax.swing.JPanel;

import factory.client.Client;
import factory.swing.GantryManPanel;
import factory.swing.KitAssManPanel;

public class KitAssemblyManager extends Client {
	private static final long serialVersionUID = -4230607892468748490L;

		public KitAssemblyManager(JPanel buttons) {
			super(Client.Type.GANTRYROBOTMANAGER, buttons, null);
			setInterface();
		}
		public static void main(String[] args){
		    KitAssManPanel buttons = new KitAssManPanel();
			KitAssemblyManager k = new KitAssemblyManager(buttons);
		}

		@Override
		public void setInterface() {
			this.setSize(800, 800);
			this.add(UI);
			this.setVisible(true);
			
		}
}

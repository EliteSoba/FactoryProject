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
			
			buttons = new KitAssManPanel(this);
			animation = new KitAssemblyPanel(this);
			
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

		public void doCommand(ArrayList<String> pCmd) {
		}
}

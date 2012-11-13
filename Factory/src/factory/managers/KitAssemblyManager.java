//Ben Mayeux and Stephanie Reagle
//CS 200

package factory.managers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import factory.client.Client;
import factory.swing.GantryManPanel;
import factory.swing.KitAssManPanel;

public class KitAssemblyManager extends Client {
	private static final long serialVersionUID = -4230607892468748490L;

		public KitAssemblyManager(JPanel buttons, JPanel animation) {
			super(Client.Type.KITASSEMBLYMANAGER, buttons, animation);
			setInterface();
		}
		public static void main(String[] args){
		    KitAssManPanel buttons = new KitAssManPanel();
		    JPanel animation = new JPanel(); //TODO where graphics panel goes
			KitAssemblyManager k = new KitAssemblyManager(buttons, animation);
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

		public void doCommand(ArrayList<String> pCmd) {
			// TODO Auto-generated method stub
			
		}
}

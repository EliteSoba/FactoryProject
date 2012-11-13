//Ben Mayeux
package factory.managers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import factory.client.Client;
import factory.swing.GantryManPanel;

public class GantryManager extends Client {
	static final long serialVersionUID = 8492299864169935860L;

	public GantryManager(JPanel buttons, JPanel animation) {
		super(Client.Type.GANTRYROBOTMANAGER, buttons, animation);
		setInterface();
	}
	public static void main(String[] args){
	    GantryManPanel buttons = new GantryManPanel();
	    JPanel animation = new JPanel(); //TODO where graphics panel goes 
		GantryManager k = new GantryManager(buttons, animation);
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
		//receive message
		
	}
}

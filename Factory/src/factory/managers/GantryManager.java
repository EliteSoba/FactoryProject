//Ben Mayeux
package factory.managers;

import javax.swing.JPanel;

import factory.client.Client;
import factory.swing.GantryManPanel;

public class GantryManager extends Client {
	static final long serialVersionUID = 8492299864169935860L;

	public GantryManager(JPanel buttons) {
		super(Client.Type.GANTRYROBOTMANAGER, buttons, null);
		setInterface();
	}
	public static void main(String[] args){
	    GantryManPanel buttons = new GantryManPanel();
		GantryManager k = new GantryManager(buttons);
	}

	@Override
	public void setInterface() {
		this.setSize(800, 800);
		this.add(UI);
		this.setVisible(true);
		
	}
}

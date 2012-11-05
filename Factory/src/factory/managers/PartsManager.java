//Ben Mayeux and Stephanie Reagle
//CS 200

package factory.managers;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import factory.client.Client;
import factory.swing.PartsManPanel;

public class PartsManager extends Client {

	public PartsManager(JPanel buttons) {
		super(Client.Type.PARTSMANAGER, buttons, null);
		setInterface();
	}
	
	public static void main(String[] args){
	    PartsManPanel buttons = new PartsManPanel();
		PartsManager l = new PartsManager(buttons);
	}
	
	@Override
	public void setInterface() {
		this.setSize(800, 800);
		this.add(UI);
		this.setVisible(true);
		
	}

}

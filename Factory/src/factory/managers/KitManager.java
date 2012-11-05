//Ben Mayeux

package factory.managers;

import javax.swing.JPanel;

import factory.client.Client;
import factory.swing.KitManPanel;

public class KitManager extends Client {

	public KitManager(JPanel buttons) {
		super(Client.Type.KITMANAGER, buttons, null);
		setInterface();
	}
	public static void main(String[] args){
	    KitManPanel buttons = new KitManPanel();
		KitManager k = new KitManager(buttons);
	}

	@Override
	public void setInterface() {
		this.setSize(800, 800);
		this.add(UI);
		this.setVisible(true);
		
	}
}

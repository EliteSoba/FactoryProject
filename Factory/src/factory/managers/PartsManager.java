//NOT FUNCTIONAL YET

package factory.managers;

import javax.swing.JPanel;

import factory.client.Client;
import factory.swing.PartsManPanel;

public class PartsManager extends Client {

	public PartsManager(JPanel buttons) {
		super(Client.Type.KITMANAGER, buttons);
		setInterface();
	}
	
	public static void main(String[] args){
	    PartsManPanel buttons = new PartsManPanel();
		//PartsManager l = new PartsManager(buttons);
	}
	
	@Override
	public void setInterface() {
		this.setSize(800, 800);
		this.add(UI);
		this.setVisible(true);
		
	}

}

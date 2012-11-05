

package factory.managers;

import javax.swing.JPanel;

import factory.client.Client;
import factory.swing.KitManPanel;

public class LaneManager  extends Client {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LaneManager(JPanel buttons) {
		super(Client.Type.KITMANAGER, buttons);
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

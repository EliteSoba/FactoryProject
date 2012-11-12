//Ben Mayeux

package factory.managers;

import javax.swing.JPanel;

import factory.client.Client;
import factory.swing.KitManPanel;

public class KitManager extends Client {
	private static final long serialVersionUID = -3161852324870948654L;

	public KitManager(JPanel buttons) {
		super(Client.Type.KITMANAGER, buttons, null);
		setInterface();
	}
	public static void main(String[] args){
	    KitManPanel buttons = new KitManPanel();
		KitManager k = new KitManager(buttons);
		buttons.setManager(k);
	}

	public void sendMessage(String kitname, int setting, String message){
		String cmd = new String("");
		if (message == "power"){
			cmd =  "km kma set kitcontent #kitname #itemnumber #itemname"; //hi
			output.println(cmd);
		}
		
	}
	
	public void setInterface() {
		this.setSize(800, 800);
		this.add(UI);
		this.setVisible(true);
		
	}
}

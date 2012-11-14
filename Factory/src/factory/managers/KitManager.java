//Ben Mayeux

package factory.managers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import factory.client.Client;
import factory.swing.KitManPanel;

public class KitManager extends Client {
	private static final long serialVersionUID = -3161852324870948654L;

	public KitManager(JPanel buttons, JPanel animation) {
		super(Client.Type.KM, buttons, animation);
		setInterface();
	}
	public static void main(String[] args){
	    KitManPanel buttons = new KitManPanel();
	    JPanel animation = new JPanel();
		KitManager k = new KitManager(buttons, animation);
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

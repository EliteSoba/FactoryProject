//Ben Mayeux and Stephanie Reagle and Marc Mendiola
//CS 200
package factory.managers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import factory.client.Client;
import factory.swing.PartsManPanel;

public class PartsManager extends Client {
	private static final long serialVersionUID = -205350261062308096L;
	

	public PartsManager(JPanel buttons, JPanel animation) {
		super(Client.Type.PARTSMANAGER, buttons, animation);
		setInterface();
	}
	
	public static void main(String[] args){
	    PartsManPanel buttons = new PartsManPanel();
	    JPanel animation = new JPanel(); //TODO where graphics panel goes 
		PartsManager manager = new PartsManager(buttons, animation);
		buttons.setManager(manager);
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
	
	public void sendMessage(String option, String itemName, String filePath){
		String message = null;
		
		if(option.equals("add")){
			message = "pm km cmd addpartname " + itemName + " " + filePath;
		}
		
		else if (option.equals("remove")){
			message = "pm km cmd rmpartname " + itemName + " " + filePath;
		}
		
		sendCommand(message);
		
	}

	public void doCommand(ArrayList<String> pCmd) {
		
	}

}

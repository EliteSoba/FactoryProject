//Ben Mayeux and Stephanie Reagle and Marc Mendiola
//CS 200
package factory.managers;

package factory.managers;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import factory.client.Client;
import factory.swing.PartsManPanel;

public class PartsManager extends Client {
	private static final long serialVersionUID = -205350261062308096L;
	

	public PartsManager(JPanel buttons) {
		super(Client.Type.PARTSMANAGER, buttons, null);
		setInterface();
	}
	
	public static void main(String[] args){
	    PartsManPanel buttons = new PartsManPanel();
		PartsManager manager = new PartsManager(buttons);
		buttons.setPartsManager(manager);
	}
	
	@Override
	public void setInterface() {
		this.setSize(800, 800);
		this.add(UI);
		this.setVisible(true);
		
	}
	
	public void sendMessage(String option, String itemName, String filePath){
		String message;
		if(option.equals("add")){
			message = "pm km cmd addpartname " + itemName + " " + filePath;
		}else if (option.equals("remove")){
			message = "pm km cmd rmpartname " + itemName + " " + filePath;
		}
		
	}

}

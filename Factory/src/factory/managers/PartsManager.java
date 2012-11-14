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
	
	ArrayList<String> parts;
	// Kit Configurations ArrayList

	public PartsManager(JPanel buttons, JPanel animation) {
		super(Client.Type.PM, buttons, animation);
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

	@Override
	public void doCommand(ArrayList<String> pCmd) {
		int i = pCmd.size();
		String action = pCmd.get(0);
		String identifier = pCmd.get(1);
		if(action == "cmd"){
			/*if(identifier == command1)
			 * do(command1);
			 * else if(identifier == command2)
			 * do(command2);
			 */
		}
		else if(action == "req"){
			/*if(identifier == request1)
			 * do(request1);
			 * else if(identifier == request2)
			 * do(request2);
			 */
		}
		else if(action == "get"){
			/*if(identifier == get1)
			 * do(get1);
			 * else if(identifier == get2)
			 * do(get2);
			 */
		}
		else if(action == "set"){
			/*if(identifier == set1)
			 * do(set1);
			 * else if(identifier == set2)
			 * do(set2);
			 */
		}
		else if(action == "cnf"){
			/*if(identifier == confirm1)
			 * do(confirm1);
			 * else if(identifier == confirm2)
			 * do(confirm2);
			 */
		}
	}
	
	public ArrayList<String> getParts(){
		return parts;
	}

}
